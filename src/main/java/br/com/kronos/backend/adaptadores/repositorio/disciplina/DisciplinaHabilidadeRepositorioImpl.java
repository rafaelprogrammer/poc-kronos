package br.com.kronos.backend.adaptadores.repositorio.disciplina;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaHabilidade;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaHabilidadeRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaHabilidade;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaHabilidadeDTO;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DisciplinaHabilidadeRepositorioImpl extends SqlQueryRepositorio implements DisciplinaHabilidadeRepositorio {
	
	public DisciplinaHabilidadeRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(DisciplinaHabilidade disciplinaHabilidade) {
        addFields("id_disciplina", "id_habilidade", "data_inicio_vigencia", "data_final_vigencia", "id_sub_fase" );
		
		addValues(disciplinaHabilidade.getIdDisciplina(), disciplinaHabilidade.getIdHabilidade(), disciplinaHabilidade.getDataInicioVigencia(), disciplinaHabilidade.getDataFinalVigencia(), disciplinaHabilidade.getIdSubFase()); 
		
		return (long) insertAuto("disciplina_habilidade"); 
    }

	@Override
	public Long alterar(DisciplinaHabilidade disciplinaHabilidade) {
addFields("id_disciplina", "id_habilidade", "data_inicio_vigencia", "data_final_vigencia", "id_sub_fase" );
		
		addValues(disciplinaHabilidade.getIdDisciplina(), disciplinaHabilidade.getIdHabilidade(), disciplinaHabilidade.getDataInicioVigencia(), disciplinaHabilidade.getDataFinalVigencia(),
				  disciplinaHabilidade.getIdSubFase(), disciplinaHabilidade.getId());
		
		return (long) update("disciplina_habilidade", disciplinaHabilidade.getId());
	}
	
	@Override
	public DisciplinaHabilidadeDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery("select d.id, h.codigo, h.bncc, h.ativo, h.descricao, d.id_disciplina, d.id_habilidade, d.data_inicio_vigencia, d.data_final_vigencia  ");
	        query.append("from disciplina_habilidade d ");
	        query.append("left join habilidade h on (d.id_habilidade = h.id) where 1=1 ");
            query = andEqual(query, "d.id", id);
            DisciplinaHabilidadeDTO disciplinaHabilidade = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new DisciplinaHabilidadeDTOMapper());
			return disciplinaHabilidade;
		} catch (EmptyResultDataAccessException e) {
			log.info("disciplinaHabilidade não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<DisciplinaHabilidadeDTO> listar(FiltroDisciplinaHabilidade filtroDisciplinaHabilidade) {
		StringBuilder query = createQuery("select d.id, h.codigo, h.bncc, h.ativo, h.descricao, d.id_disciplina, d.id_habilidade, d.data_inicio_vigencia, d.data_final_vigencia, sf.sigla as sigla_sub_fase  ");
        query.append("from disciplina_habilidade d ");
        query.append("left join habilidade h on (d.id_habilidade = h.id) ");
        query.append("left join sub_fase sf on (d.id_sub_fase = sf.id) where 1=1 ");
        query = andEqual(query, "d.id", filtroDisciplinaHabilidade.getId());
		query = andEqual(query, "d.id_disciplina", filtroDisciplinaHabilidade.getIdDisciplina());
        query = andEqual(query, "d.id_habilidade", filtroDisciplinaHabilidade.getIdHabilidade());
        query = andEqual(query, "d.data_inicio_vigencia", filtroDisciplinaHabilidade.getDataInicioVigencia());
        query = andEqual(query, "d.data_final_vigencia", filtroDisciplinaHabilidade.getDataFinalVigencia());
        query = orderBy(query, Order.ASC, "d.id_habilidade");
		query = limit(query, filtroDisciplinaHabilidade.getQtdTotal(), filtroDisciplinaHabilidade.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaHabilidadeDTOMapper());
	}
	
	@Override
	public int contar(FiltroDisciplinaHabilidade filtroDisciplinaHabilidade) {
		StringBuilder query = createQuery("select count(d.id) ");
		query.append("from disciplina_habilidade d ");
        query.append("left join habilidade h on (d.id_habilidade = h.id) where 1=1 ");
		query = andEqual(query, "d.id", filtroDisciplinaHabilidade.getId());
		query = andEqual(query, "d.id_disciplina", filtroDisciplinaHabilidade.getIdDisciplina());
        query = andEqual(query, "d.id_habilidade", filtroDisciplinaHabilidade.getIdHabilidade());
        query = andEqual(query, "d.data_inicio_vigencia", filtroDisciplinaHabilidade.getDataInicioVigencia());
        query = andEqual(query, "d.data_final_vigencia", filtroDisciplinaHabilidade.getDataFinalVigencia());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("disciplina_habilidade", id);
		return true;
	}
	
	private class DisciplinaHabilidadeDTOMapper implements RowMapper<DisciplinaHabilidadeDTO> {

		@Override
		public DisciplinaHabilidadeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaHabilidadeDTO.builder()
					.id(rs.getLong("id"))
					.idDisciplina(rs.getLong("id_disciplina"))
					.idHabilidade(rs.getLong("id_habilidade"))
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
	public List<DisciplinaHabilidadeDTO> listarParaAtividadeDisciplinaHabilidade(FiltroDisciplinaHabilidade filtroDisciplinaHabilidade) {
		  StringBuilder query = createQuery("select dh.id, h.codigo, h.bncc, h.descricao, sf.sigla, h.ativo ");
          query.append("from disciplina_habilidade dh ");
          query.append("left join habilidade h on (dh.id_habilidade = h.id) ");
          query.append("left join sub_fase sf on (dh.id_sub_fase = sf.id) where 1=1 ");
          query.append("and (dh.data_final_vigencia is null ");
          query.append(" or dh.data_final_vigencia >= to_date('");
		  query.append(DateUtil.dataAtual().toString());
		  query.append("', 'yyyy-MM-dd'))");
          query = andEqual(query, "dh.id_disciplina", filtroDisciplinaHabilidade.getIdDisciplina());
          query = andLike(query, "h.codigo", filtroDisciplinaHabilidade.getCodigoHabilidade());
          query = andLike(query, "h.descricao", filtroDisciplinaHabilidade.getDescricaoHabilidade());
          query.append(" and not exists(");
          query.append(" select ad.id_disciplina_habilidade ");
          query.append(" from atividade_disciplina_habilidade ad where 1=1 ");
          query = andEqual(query, "ad.id_atividade", filtroDisciplinaHabilidade.getIdAtividade());
          query.append(" and ad.id_disciplina_habilidade = dh.id) ");
          query = andEqual(query, "dh.id_sub_fase", filtroDisciplinaHabilidade.getIdSubFase());
          query = orderBy(query, Order.ASC, "h.codigo");
          query = limit(query, filtroDisciplinaHabilidade.getQtdTotal(), filtroDisciplinaHabilidade.getNumeroPagina());
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaDireitoDTOParaAtividadeDisciplinaHabilidadeMapper());
	}
	
	@Override
	public int contarParaAtividadeDisciplinaHabilidade(FiltroDisciplinaHabilidade filtroDisciplinaHabilidade) {
		  StringBuilder query = createQuery("select count(dh.id) ");
		  query.append("from disciplina_habilidade dh ");
		  query.append("left join habilidade h on (dh.id_habilidade = h.id) ");
          query.append("left join sub_fase sf on (dh.id_sub_fase = sf.id) where 1=1 ");
          query.append("and (dh.data_final_vigencia is null ");
          query.append(" or dh.data_final_vigencia >= to_date('");
		  query.append(DateUtil.dataAtual().toString());
		  query.append("', 'yyyy-MM-dd'))");
          query = andEqual(query, "dh.id_disciplina", filtroDisciplinaHabilidade.getIdDisciplina());
          query.append(" and not exists(");
          query.append(" select ad.id_disciplina_habilidade ");
          query.append(" from atividade_disciplina_habilidade ad where 1=1 ");
          query = andEqual(query, "ad.id_atividade", filtroDisciplinaHabilidade.getIdAtividade());
          query.append(" and ad.id_disciplina_habilidade = dh.id) ");
          query = andEqual(query, "dh.id_sub_fase", filtroDisciplinaHabilidade.getIdSubFase());
          return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class DisciplinaDireitoDTOParaAtividadeDisciplinaHabilidadeMapper implements RowMapper<DisciplinaHabilidadeDTO> {

		@Override
		public DisciplinaHabilidadeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaHabilidadeDTO.builder()
					.id(rs.getLong("id"))
                    .codigo(rs.getString("codigo"))
                    .bncc(rs.getBoolean("bncc"))
                    .descricao(rs.getString("descricao"))
                    .siglaSubFase(rs.getString("sigla"))
                    .ativo(rs.getBoolean("ativo"))
					.build();
		}
	}
	
	@Override
	public List<DisciplinaHabilidadeDTO> listarParaAvaliacaoHabilidade(FiltroDisciplinaHabilidade filtroDisciplinaHabilidade) {
		  StringBuilder query = createQuery("select dh.id, h.codigo, h.bncc, h.descricao ");
          query.append("from disciplina_habilidade dh ");
          query.append("left join habilidade h on (dh.id_habilidade = h.id) where 1=1 ");
          query.append(" and dh.data_inicio_vigencia  <= to_date('");
		  query.append(DateUtil.dataAtual().toString());
		  query.append("', 'yyyy-MM-dd') ");
          query.append(" and (dh.data_final_vigencia is null ");
          query.append(" or dh.data_final_vigencia >= to_date('");
		  query.append(DateUtil.dataAtual().toString());
		  query.append("', 'yyyy-MM-dd')) ");
		  query = andEqual(query, "dh.id_disciplina", filtroDisciplinaHabilidade.getIdDisciplina());
		  query.append(" and not exists ");
		  query.append("(select id_disciplina_habilidade from avaliacao_habilidade ah ");
		  query.append("where ah.id_disciplina_habilidade = dh.id ");
		  query = andEqual(query, "ah.id_avaliacao", filtroDisciplinaHabilidade.getIdAvaliacao());
		  query.append(")");
          query = orderBy(query, Order.ASC, "h.codigo");
          query = limit(query, filtroDisciplinaHabilidade.getQtdTotal(), filtroDisciplinaHabilidade.getNumeroPagina());
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaDireitoDTOParaAvaliacaoHabilidadeMapper());
	}
	
	@Override
	public int contarParaParaAvaliacaoHabilidade(FiltroDisciplinaHabilidade filtroDisciplinaHabilidade) {
		  StringBuilder query = createQuery("select count(dh.id) ");
		  query.append("from disciplina_habilidade dh ");
          query.append("left join habilidade h on (dh.id_habilidade = h.id) where 1=1 ");
          query.append(" and dh.data_inicio_vigencia  <= to_date('");
		  query.append(DateUtil.dataAtual().toString());
		  query.append("', 'yyyy-MM-dd') ");
          query.append(" and (dh.data_final_vigencia is null ");
          query.append(" or dh.data_final_vigencia >= to_date('");
		  query.append(DateUtil.dataAtual().toString());
		  query.append("', 'yyyy-MM-dd')) ");
		  query = andEqual(query, "dh.id_disciplina", filtroDisciplinaHabilidade.getIdDisciplina());
		  query.append(" and not exists ");
		  query.append("(select id_disciplina_habilidade from avaliacao_habilidade ah ");
		  query.append("where ah.id_disciplina_habilidade = dh.id ");
		  query = andEqual(query, "ah.id_avaliacao", filtroDisciplinaHabilidade.getIdAvaliacao());
		  query.append(")");
          return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class DisciplinaDireitoDTOParaAvaliacaoHabilidadeMapper implements RowMapper<DisciplinaHabilidadeDTO> {

		@Override
		public DisciplinaHabilidadeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaHabilidadeDTO.builder()
					.id(rs.getLong("id"))
                    .codigo(rs.getString("codigo"))
                    .bncc(rs.getBoolean("bncc"))
                    .descricao(rs.getString("descricao"))
					.build();
		}
	}
}
