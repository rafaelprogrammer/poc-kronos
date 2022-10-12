package br.com.kronos.backend.adaptadores.repositorio.disciplina;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaObjetivo;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaObjetivoRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaObjetivo;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaObjetivoDTO;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DisciplinaObjetivoRepositorioImpl extends SqlQueryRepositorio implements DisciplinaObjetivoRepositorio {
	
	public DisciplinaObjetivoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(DisciplinaObjetivo disciplinaObjetivo) {
        addFields("id_disciplina", "id_objetivo", "data_inicio_vigencia", "data_final_vigencia", "id_sub_fase" );
		
		addValues(disciplinaObjetivo.getIdDisciplina(), disciplinaObjetivo.getIdObjetivo(), disciplinaObjetivo.getDataInicioVigencia(), disciplinaObjetivo.getDataFinalVigencia(), disciplinaObjetivo.getIdSubFase()); 
		
		return (long) insertAuto("disciplina_objetivo"); 
    }

	@Override
	public Long alterar(DisciplinaObjetivo disciplinaObjetivo) {
addFields("id_disciplina", "id_objetivo", "data_inicio_vigencia", "data_final_vigencia", "id_sub_fase" );
		
		addValues(disciplinaObjetivo.getIdDisciplina(), disciplinaObjetivo.getIdObjetivo(), disciplinaObjetivo.getDataInicioVigencia(), disciplinaObjetivo.getDataFinalVigencia(),
				disciplinaObjetivo.getIdSubFase(), disciplinaObjetivo.getId());
		
		return (long) update("disciplina_objetivo", disciplinaObjetivo.getId());
	}
	
	@Override
	public DisciplinaObjetivoDTO buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select d.id, d.id_disciplina, d.id_objetivo, d.data_inicio_vigencia, d.data_final_vigencia,  ");
            query.append("o.codigo, o.bncc, o.ativo, o.descricao ");
            query.append("from disciplina_objetivo d where 1=1 ");
            query = andEqual(query, "d.id", id);
            DisciplinaObjetivoDTO disciplinaObjetivo = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new DisciplinaObjetivoDTOMapper());
			return disciplinaObjetivo;
		} catch (EmptyResultDataAccessException e) {
			log.info("disciplinaObjetivo não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<DisciplinaObjetivoDTO> listar(FiltroDisciplinaObjetivo filtroDisciplinaObjetivo) {
		StringBuilder query = createQuery("select d.id, d.id_disciplina, d.id_objetivo, d.data_inicio_vigencia, d.data_final_vigencia,  ");
		query.append("o.codigo, o.bncc, o.ativo, o.descricao, sf.sigla as sigla_sub_fase ");
        query.append("from disciplina_objetivo d ");
        query.append("left join objetivo o on (d.id_objetivo = o.id) ");
        query.append("left join sub_fase sf on (d.id_sub_fase = sf.id) where 1=1 ");
        query = andEqual(query, "d.id", filtroDisciplinaObjetivo.getId());
		query = andEqual(query, "d.id_disciplina", filtroDisciplinaObjetivo.getIdDisciplina());
        query = andEqual(query, "d.id_objetivo", filtroDisciplinaObjetivo.getIdObjetivo());
        query = andEqual(query, "d.data_inicio_vigencia", filtroDisciplinaObjetivo.getDataInicioVigencia());
        query = andEqual(query, "d.data_final_vigencia", filtroDisciplinaObjetivo.getDataFinalVigencia());
        query = orderBy(query, Order.ASC, "d.id_objetivo");
		query = limit(query, filtroDisciplinaObjetivo.getQtdTotal(), filtroDisciplinaObjetivo.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaObjetivoDTOMapper());
	}
	
	@Override
	public int contar(FiltroDisciplinaObjetivo filtroDisciplinaObjetivo) {
		StringBuilder query = createQuery("select count(d.id) ");
        query.append("from disciplina_objetivo d ");
        query.append("left join objetivo o on (d.id_objetivo = o.id) where 1=1 ");
		query = andEqual(query, "d.id", filtroDisciplinaObjetivo.getId());
		query = andEqual(query, "d.id_disciplina", filtroDisciplinaObjetivo.getIdDisciplina());
        query = andEqual(query, "d.id_objetivo", filtroDisciplinaObjetivo.getIdObjetivo());
        query = andEqual(query, "d.data_inicio_vigencia", filtroDisciplinaObjetivo.getDataInicioVigencia());
        query = andEqual(query, "d.data_final_vigencia", filtroDisciplinaObjetivo.getDataFinalVigencia());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("disciplina_objetivo", id);
		return true;
	}
	
	private class DisciplinaObjetivoDTOMapper implements RowMapper<DisciplinaObjetivoDTO> {

		@Override
		public DisciplinaObjetivoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaObjetivoDTO.builder()
					.id(rs.getLong("id"))
					.idDisciplina(rs.getLong("id_disciplina"))
					.idObjetivo(rs.getLong("id_objetivo"))
                    .dataInicioVigencia(rs.getDate("data_inicio_vigencia").toLocalDate())
                    .dataFinalVigencia(rs.getDate("data_final_vigencia") !=null ? rs.getDate("data_final_vigencia").toLocalDate() : null)
                    .codigo(rs.getString("codigo"))
                    .bncc(rs.getBoolean("bncc"))
                    .ativo(rs.getBoolean("ativo"))
                    .descricao(rs.getString("descricao"))
                    .siglaSubFase(rs.getObject("sigla_sub_fase") != null ? rs.getString("sigla_sub_fase") : null)
					.build();
		}
	}
	
	@Override
	public List<DisciplinaObjetivoDTO> listarParaAtividadeDisciplinaObjetivo(FiltroDisciplinaObjetivo filtroDisciplinaObjetivo) {
		  StringBuilder query = createQuery("select dob.id, o.codigo, o.bncc, sf.sigla, o.descricao, o.ativo ");
          query.append("from disciplina_objetivo dob ");
          query.append("left join objetivo o on (dob.id_objetivo = o.id) ");
          query.append("left join sub_fase sf on (dob.id_sub_fase = sf.id) where 1=1 ");
          query.append("and (dob.data_final_vigencia is null ");
          query.append(" or dob.data_final_vigencia >= to_date('");
		  query.append(DateUtil.dataAtual().toString());
		  query.append("', 'yyyy-MM-dd'))");
          query = andEqual(query, "dob.id_disciplina", filtroDisciplinaObjetivo.getIdDisciplina());
          query = andLike(query, "o.codigo", filtroDisciplinaObjetivo.getCodigo());
          query = andLike(query, "o.descricao", filtroDisciplinaObjetivo.getDescricao());
          query.append(" and not exists(");
          query.append(" select ad.id_disciplina_objetivo ");
          query.append(" from atividade_disciplina_objetivo ad where 1=1 ");
          query = andEqual(query, "ad.id_atividade", filtroDisciplinaObjetivo.getIdAtividade());
          query.append(" and ad.id_disciplina_objetivo = dob.id) ");
          query = andEqual(query, "dob.id_sub_fase", filtroDisciplinaObjetivo.getIdSubFase());
          query = orderBy(query, Order.ASC, "o.codigo");
          query = limit(query, filtroDisciplinaObjetivo.getQtdTotal(), filtroDisciplinaObjetivo.getNumeroPagina());
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaDireitoDTOParaAtividadeDisciplinaObjetivoMapper());
	}
	
	@Override
	public int contarParaAtividadeDisciplinaObjetivo(FiltroDisciplinaObjetivo filtroDisciplinaObjetivo) {
		  StringBuilder query = createQuery("select count(dob.id) ");
		  query.append("from disciplina_objetivo dob ");
          query.append("left join objetivo o on (dob.id_objetivo = o.id) ");
          query.append("left join sub_fase sf on (dob.id_sub_fase = sf.id) where 1=1 ");
          query.append("and (dob.data_final_vigencia is null ");
          query.append(" or dob.data_final_vigencia >= to_date('");
		  query.append(DateUtil.dataAtual().toString());
		  query.append("', 'yyyy-MM-dd'))");
          query = andEqual(query, "dob.id_disciplina", filtroDisciplinaObjetivo.getIdDisciplina());
          query.append(" and not exists(");
          query.append(" select ad.id_disciplina_objetivo ");
          query.append(" from atividade_disciplina_objetivo ad where 1=1 ");
          query = andEqual(query, "ad.id_atividade", filtroDisciplinaObjetivo.getIdAtividade());
          query.append(" and ad.id_disciplina_objetivo = dob.id) ");
          query = andEqual(query, "dob.id_sub_fase", filtroDisciplinaObjetivo.getIdSubFase());
          return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class DisciplinaDireitoDTOParaAtividadeDisciplinaObjetivoMapper implements RowMapper<DisciplinaObjetivoDTO> {

		@Override
		public DisciplinaObjetivoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaObjetivoDTO.builder()
					.id(rs.getLong("id"))
                    .codigo(rs.getString("codigo"))
                    .bncc(rs.getBoolean("bncc"))
                    .descricao(rs.getString("descricao"))
                    .siglaSubFase(rs.getString("sigla"))
                    .ativo(rs.getBoolean("ativo"))
					.build();
		}
	}
}

