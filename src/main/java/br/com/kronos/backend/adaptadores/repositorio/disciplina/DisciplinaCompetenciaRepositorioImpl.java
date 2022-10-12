package br.com.kronos.backend.adaptadores.repositorio.disciplina;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaCompetencia;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaCompetenciaRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaCompetencia;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaCompetenciaDTO;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DisciplinaCompetenciaRepositorioImpl extends SqlQueryRepositorio implements DisciplinaCompetenciaRepositorio {
	
	public DisciplinaCompetenciaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(DisciplinaCompetencia disciplinaCompetencia) {
        addFields("id_disciplina", "id_competencia", "data_inicio_vigencia", "data_final_vigencia", "id_sub_fase" );
		
		addValues(disciplinaCompetencia.getIdDisciplina(), disciplinaCompetencia.getIdCompetencia(), disciplinaCompetencia.getDataInicioVigencia(), disciplinaCompetencia.getDataFinalVigencia(),
				disciplinaCompetencia.getIdSubFase()); 
		
		return (long) insertAuto("disciplina_competencia"); 
    }

	@Override
	public Long alterar(DisciplinaCompetencia disciplinaCompetencia) {
addFields("id_disciplina", "id_competencia", "data_inicio_vigencia", "data_final_vigencia", "id_sub_fase" );
		
		addValues(disciplinaCompetencia.getIdDisciplina(), disciplinaCompetencia.getIdCompetencia(), disciplinaCompetencia.getDataInicioVigencia(), disciplinaCompetencia.getDataFinalVigencia(),
				  disciplinaCompetencia.getIdSubFase(), disciplinaCompetencia.getId());
		
		return (long) update("disciplina_competencia", disciplinaCompetencia.getId());
	}
	
	@Override
	public DisciplinaCompetenciaDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery("select d.id, c.codigo, c.bncc, c.ativo, c.geral, c.descricao, d.id_disciplina, d.id_competencia, d.data_inicio_vigencia, d.data_final_vigencia  ");
	        query.append("from disciplina_competencia d ");
	        query.append("left join competencia c on (d.id_competencia = c.id) where 1=1 ");
            query = andEqual(query, "d.id", id);
            DisciplinaCompetenciaDTO disciplinaCompetencia = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new DisciplinaCompetenciaDTOMapper());
			return disciplinaCompetencia;
		} catch (EmptyResultDataAccessException e) {
			log.info("disciplinaCompetencia não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<DisciplinaCompetenciaDTO> listar(FiltroDisciplinaCompetencia filtroDisciplinaCompetencia) {
		StringBuilder query = createQuery("select d.id, c.codigo, c.bncc, c.ativo, c.geral, c.descricao, d.id_disciplina, d.id_competencia, d.data_inicio_vigencia, d.data_final_vigencia, sf.sigla as sigla_sub_fase  ");
        query.append("from disciplina_competencia d ");
        query.append("left join competencia c on (d.id_competencia = c.id) ");
        query.append("left join sub_fase sf on (d.id_sub_fase = sf.id) where 1=1 ");
        query = andEqual(query, "d.id", filtroDisciplinaCompetencia.getId());
		query = andEqual(query, "d.id_disciplina", filtroDisciplinaCompetencia.getIdDisciplina());
        query = andEqual(query, "d.id_competencia", filtroDisciplinaCompetencia.getIdCompetencia());
        query = andEqual(query, "d.data_inicio_vigencia", filtroDisciplinaCompetencia.getDataInicioVigencia());
        query = andEqual(query, "d.data_final_vigencia", filtroDisciplinaCompetencia.getDataFinalVigencia());
        query = orderBy(query, Order.ASC, "d.id_competencia");
		query = limit(query, filtroDisciplinaCompetencia.getQtdTotal(), filtroDisciplinaCompetencia.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaCompetenciaDTOMapper());
	}
	
	@Override
	public int contar(FiltroDisciplinaCompetencia filtroDisciplinaCompetencia) {
		StringBuilder query = createQuery("select count(d.id) ");
        query.append("from disciplina_competencia d ");
        query.append("left join competencia c on (d.id_competencia = c.id) where 1=1 ");
		query = andEqual(query, "d.id", filtroDisciplinaCompetencia.getId());
		query = andEqual(query, "d.id_disciplina", filtroDisciplinaCompetencia.getIdDisciplina());
        query = andEqual(query, "d.id_competencia", filtroDisciplinaCompetencia.getIdCompetencia());
        query = andEqual(query, "d.data_inicio_vigencia", filtroDisciplinaCompetencia.getDataInicioVigencia());
        query = andEqual(query, "d.data_final_vigencia", filtroDisciplinaCompetencia.getDataFinalVigencia());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("disciplina_competencia", id);
		return true;
	}
	
	private class DisciplinaCompetenciaDTOMapper implements RowMapper<DisciplinaCompetenciaDTO> {

		@Override
		public DisciplinaCompetenciaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaCompetenciaDTO.builder()
					.id(rs.getLong("id"))
					.idDisciplina(rs.getLong("id_disciplina"))
					.idCompetencia(rs.getLong("id_competencia"))
                    .dataInicioVigencia(rs.getDate("data_inicio_vigencia").toLocalDate())
                    .dataFinalVigencia(rs.getDate("data_final_vigencia") !=null ? rs.getDate("data_final_vigencia").toLocalDate() : null)
                    .codigo(rs.getString("codigo"))
                    .bncc(rs.getBoolean("bncc"))
                    .ativo(rs.getBoolean("ativo"))
                    .descricao(rs.getString("descricao"))
                    .geral(rs.getBoolean("geral"))
                    .siglaSubFase(rs.getObject("sigla_sub_fase") != null ? rs.getString("sigla_sub_fase") : null)
					.build();
		}
	}
	
	@Override
	public List<DisciplinaCompetenciaDTO> listarParaAtividadeDisciplinaCompetencia(FiltroDisciplinaCompetencia filtroDisciplinaCompetencia) {
		  StringBuilder query = createQuery("select dc.id, c.codigo, c.bncc, c.descricao, c.geral, sf.sigla, c.ativo ");
          query.append("from disciplina_competencia dc ");
          query.append("left join competencia c on (dc.id_competencia = c.id) ");
          query.append("left join sub_fase sf on (dc.id_sub_fase = sf.id) where 1=1 ");
          query.append("and (dc.data_final_vigencia is null ");
          query.append(" or dc.data_final_vigencia >= to_date('");
		  query.append(DateUtil.dataAtual().toString());
		  query.append("', 'yyyy-MM-dd'))");
          query = andEqual(query, "dc.id_disciplina", filtroDisciplinaCompetencia.getIdDisciplina());
          query = andLike(query, "c.codigo", filtroDisciplinaCompetencia.getCodigo());
          query = andLike(query, "c.descricao", filtroDisciplinaCompetencia.getDescricao());
          query.append(" and not exists(");
          query.append(" select ad.id_disciplina_competencia ");
          query.append(" from atividade_disciplina_competencia ad where 1=1 ");
          query = andEqual(query, "ad.id_atividade", filtroDisciplinaCompetencia.getIdAtividade());
          query.append(" and ad.id_disciplina_competencia = dc.id) ");
          query = andEqual(query, "dc.id_sub_fase", filtroDisciplinaCompetencia.getIdSubFase());
          query = orderBy(query, Order.ASC, "c.codigo");
          
          
          query = limit(query, filtroDisciplinaCompetencia.getQtdTotal(), filtroDisciplinaCompetencia.getNumeroPagina());
          
          
          
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaDireitoDTOParaAtividadeDisciplinaCompetenciaMapper());
	}
	
	@Override
	public int contarParaAtividadeDisciplinaCompetencia(FiltroDisciplinaCompetencia filtroDisciplinaCompetencia) {
		  StringBuilder query = createQuery("select count(dc.id) ");
		  query.append("from disciplina_competencia dc ");
          query.append("left join competencia c on (dc.id_competencia = c.id) ");
          query.append("left join sub_fase sf on (dc.id_sub_fase = sf.id) where 1=1 ");
          query.append("and (dc.data_final_vigencia is null ");
          query.append(" or dc.data_final_vigencia >= to_date('");
		  query.append(DateUtil.dataAtual().toString());
		  query.append("', 'yyyy-MM-dd'))");
          query = andEqual(query, "dc.id_disciplina", filtroDisciplinaCompetencia.getIdDisciplina());
          query.append(" and not exists(");
          query.append(" select ad.id_disciplina_competencia ");
          query.append(" from atividade_disciplina_competencia ad where 1=1 ");
          query = andEqual(query, "ad.id_atividade", filtroDisciplinaCompetencia.getIdAtividade());
          query.append(" and ad.id_disciplina_competencia = dc.id) ");
          query = andEqual(query, "dc.id_sub_fase", filtroDisciplinaCompetencia.getIdSubFase());
          return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class DisciplinaDireitoDTOParaAtividadeDisciplinaCompetenciaMapper implements RowMapper<DisciplinaCompetenciaDTO> {

		@Override
		public DisciplinaCompetenciaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaCompetenciaDTO.builder()
					.id(rs.getLong("id"))
                    .codigo(rs.getString("codigo"))
                    .bncc(rs.getBoolean("bncc"))
                    .geral(rs.getBoolean("geral"))
                    .descricao(rs.getString("descricao"))
                    .siglaSubFase(rs.getString("sigla"))
                    .ativo(rs.getBoolean("ativo"))
					.build();
		}
	}
}

