package br.com.kronos.backend.adaptadores.repositorio.disciplina;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaDireito;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaDireitoRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaDireito;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaDireitoDTO;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DisciplinaDireitoRepositorioImpl extends SqlQueryRepositorio implements DisciplinaDireitoRepositorio {
	
	public DisciplinaDireitoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(DisciplinaDireito disciplinaDireito) {
        addFields("id_disciplina", "id_direito", "data_inicio_vigencia", "data_final_vigencia", "id_sub_fase" );
		
		addValues(disciplinaDireito.getIdDisciplina(), disciplinaDireito.getIdDireito(), disciplinaDireito.getDataInicioVigencia(), disciplinaDireito.getDataFinalVigencia(), disciplinaDireito.getIdSubFase()); 
		
		return (long) insertAuto("disciplina_direito"); 
    }

	@Override
	public Long alterar(DisciplinaDireito disciplinaDireito) {
addFields("id_disciplina", "id_direito", "data_inicio_vigencia", "data_final_vigencia", "id_sub_fase" );
		
		addValues(disciplinaDireito.getIdDisciplina(), disciplinaDireito.getIdDireito(), disciplinaDireito.getDataInicioVigencia(), disciplinaDireito.getDataFinalVigencia(), disciplinaDireito.getIdSubFase(),
				  disciplinaDireito.getId());
		
		return (long) update("disciplina_direito", disciplinaDireito.getId());
	}
	
	@Override
	public DisciplinaDireitoDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery("select dd.id, dd.id_direito, dd.id_disciplina, d.codigo, d.bncc, d.ativo, d.descricao, dd.data_inicio_vigencia, dd.data_final_vigencia  ");
	        query.append("from disciplina_direito dd ");
	        query.append("left join direito d on (dd.id_direito = d.id) where 1=1 ");
	        DisciplinaDireitoDTO disciplinaDireito = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new DisciplinaDireitoDTOMapper());
			return disciplinaDireito;
		} catch (EmptyResultDataAccessException e) {
			log.info("disciplinaDireito não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<DisciplinaDireitoDTO> listar(FiltroDisciplinaDireito filtroDisciplinaDireito) {
		StringBuilder query = createQuery("select dd.id, dd.id_direito, dd.id_disciplina, d.codigo, d.bncc, d.ativo, d.descricao, dd.data_inicio_vigencia, dd.data_final_vigencia, sf.sigla as sigla_sub_fase  ");
        query.append("from disciplina_direito dd ");
        query.append("left join direito d on (dd.id_direito = d.id) ");
        query.append("left join sub_fase sf on (dd.id_sub_fase = sf.id) where 1=1 ");
        
        query = andEqual(query, "dd.id", filtroDisciplinaDireito.getId());
		query = andEqual(query, "dd.id_disciplina", filtroDisciplinaDireito.getIdDisciplina());
        query = andEqual(query, "dd.id_direito", filtroDisciplinaDireito.getIdDireito());
        query = andEqual(query, "dd.data_inicio_vigencia", filtroDisciplinaDireito.getDataInicioVigencia());
        query = andEqual(query, "dd.data_final_vigencia", filtroDisciplinaDireito.getDataFinalVigencia());
        query = orderBy(query, Order.ASC, "dd.id_direito");
		query = limit(query, filtroDisciplinaDireito.getQtdTotal(), filtroDisciplinaDireito.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaDireitoDTOMapper());
	}
	
	@Override
	public int contar(FiltroDisciplinaDireito filtroDisciplinaDireito) {
		StringBuilder query = createQuery("select count(d.id) from disciplina_direito d where 1=1 ");
		query = andEqual(query, "d.id", filtroDisciplinaDireito.getId());
		query = andEqual(query, "d.id_disciplina", filtroDisciplinaDireito.getIdDisciplina());
        query = andEqual(query, "d.id_direito", filtroDisciplinaDireito.getIdDireito());
        query = andEqual(query, "d.data_inicio_vigencia", filtroDisciplinaDireito.getDataInicioVigencia());
        query = andEqual(query, "d.data_final_vigencia", filtroDisciplinaDireito.getDataFinalVigencia());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("disciplina_direito", id);
		return true;
	}
	
	@Override
	public List<DisciplinaDireitoDTO> listarParaAtividadeDisciplinaDireito(FiltroDisciplinaDireito filtroDisciplinaDireito) {
		  StringBuilder query = createQuery("select dd.id, d.codigo, d.bncc, d.descricao, sf.sigla, d.ativo ");
          query.append("from disciplina_direito dd ");
          query.append("left join direito d on (dd.id_direito = d.id) ");
          query.append("left join sub_fase sf on (dd.id_sub_fase = sf.id) where 1=1 ");
          query.append("and (dd.data_final_vigencia is null ");
          query.append(" or dd.data_final_vigencia >= to_date('");
		  query.append(DateUtil.dataAtual().toString());
		  query.append("', 'yyyy-MM-dd'))");
          query = andEqual(query, "dd.id_disciplina", filtroDisciplinaDireito.getIdDisciplina());
          query = andLike(query, "d.codigo", filtroDisciplinaDireito.getCodigo());
          query = andLike(query, "d.descricao", filtroDisciplinaDireito.getDescricao());
          query.append(" and not exists(");
          query.append(" select ad.id_disciplina_direito ");
          query.append(" from atividade_disciplina_direito ad where 1=1 ");
          query = andEqual(query, "ad.id_atividade", filtroDisciplinaDireito.getIdAtividade());
          query.append(" and ad.id_disciplina_direito = dd.id) ");
          query = andEqual(query, "dd.id_sub_fase", filtroDisciplinaDireito.getIdSubFase());
          query = orderBy(query, Order.ASC, "d.codigo");
          query = limit(query, filtroDisciplinaDireito.getQtdTotal(), filtroDisciplinaDireito.getNumeroPagina());
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaDireitoDTOParaAtividadeDisciplinaDireitoMapper());
	}
	
	@Override
	public int contarParaAtividadeDisciplinaDireito(FiltroDisciplinaDireito filtroDisciplinaDireito) {
		  StringBuilder query = createQuery("select count(dd.id) ");
          query.append("from disciplina_direito dd ");
          query.append("left join direito d on (dd.id_direito = d.id) ");
          query.append("left join sub_fase sf on (dd.id_sub_fase = sf.id) where 1=1 ");
          query.append("and (dd.data_final_vigencia is null ");
          query.append(" or dd.data_final_vigencia >= to_date('");
		  query.append(DateUtil.dataAtual().toString());
		  query.append("', 'yyyy-MM-dd'))");
          query = andEqual(query, "dd.id_disciplina", filtroDisciplinaDireito.getIdDisciplina());
          query.append(" and not exists(");
          query.append(" select ad.id_disciplina_direito ");
          query.append(" from atividade_disciplina_direito ad where 1=1 ");
          query = andEqual(query, "ad.id_atividade", filtroDisciplinaDireito.getIdAtividade());
          query.append(" and ad.id_disciplina_direito = dd.id) ");
          query = andEqual(query, "dd.id_sub_fase", filtroDisciplinaDireito.getIdSubFase());
          return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class DisciplinaDireitoDTOParaAtividadeDisciplinaDireitoMapper implements RowMapper<DisciplinaDireitoDTO> {

		@Override
		public DisciplinaDireitoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaDireitoDTO.builder()
					.id(rs.getLong("id"))
                    .codigo(rs.getString("codigo"))
                    .bncc(rs.getBoolean("bncc"))
                    .descricao(rs.getString("descricao"))
                    .siglaSubFase(rs.getString("sigla"))
                    .ativo(rs.getBoolean("ativo"))
					.build();
		}
	}
	
	private class DisciplinaDireitoDTOMapper implements RowMapper<DisciplinaDireitoDTO> {

		@Override
		public DisciplinaDireitoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaDireitoDTO.builder()
					.id(rs.getLong("id"))
					.idDisciplina(rs.getLong("id_disciplina"))
                    .idDireito(rs.getLong("id_direito"))
                    .dataInicioVigencia(rs.getDate("data_inicio_vigencia").toLocalDate())
                    .dataFinalVigencia(rs.getDate("data_final_vigencia") !=null ? rs.getDate("data_final_vigencia").toLocalDate() : null)
                    .codigo(rs.getString("codigo"))
                    .bncc(rs.getBoolean("bncc"))
                    .ativo(rs.getBoolean("ativo"))
                    .siglaSubFase(rs.getObject("sigla_sub_fase") != null ? rs.getString("sigla_sub_fase") : null)
                    .descricao(rs.getString("descricao"))
					.build();
		}
	}
}
