package br.com.kronos.backend.adaptadores.repositorio.basecurricular;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroObjetivo;
import br.com.kronos.backend.aplicacao.basecurricular.Objetivo;
import br.com.kronos.backend.aplicacao.basecurricular.ObjetivoRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.api.ObjetivoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjetivoRepositorioImpl extends SqlQueryRepositorio implements ObjetivoRepositorio {
	
	public ObjetivoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Objetivo objetivo) {
        addFields("id_campo_experiencia", "descricao" , "codigo", "bncc",
        		"id_faixa_ano", "ativo", "id_instituicao" );
		
		addValues(objetivo.getIdCampoExperiencia(), objetivo.getDescricao(), objetivo.getCodigo(), objetivo.getBncc(),
				objetivo.getIdFaixaAno(), objetivo.isAtivo(), objetivo.getIdInstituicao()); 
		
		return (long) insertAuto("objetivo"); 
    }
		
	@Override
	public Long alterar(Objetivo objetivo) {
		addFields("id_campo_experiencia", "descricao" , "codigo", "bncc",
        		"id_faixa_ano", "ativo", "id_instituicao" );
		
		addValues(objetivo.getIdCampoExperiencia(), objetivo.getDescricao(), objetivo.getCodigo(), objetivo.getBncc(),
				objetivo.getIdFaixaAno(), objetivo.isAtivo(), objetivo.getIdInstituicao(), objetivo.getId());
		
		return (long) update("objetivo", objetivo.getId());
	}
		
	@Override
	public ObjetivoDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery("select o.id, o.id_campo_experiencia, o.id_instituicao, o.id_faixa_ano, o.id, ");
		    query.append("f.nome as faixa_ano, o.bncc, o.codigo, c.nome as campo_experiencia,  o.descricao, o.ativo  ");
		    query.append("from objetivo o ");
		    query.append("left join componente c on (o.id_campo_experiencia = c.id) ");
		    query.append("left join faixa_ano f on (o.id_faixa_ano = f.id) where 1=1 ");
            query = andEqual(query, "o.id", id);
            ObjetivoDTO objetivo = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new ObjetivoDTOMapper());
			return objetivo;
		} catch (EmptyResultDataAccessException e) {
			log.info("objetivo não existe - " + id);
			return null;
		}
	}
	
	@Override
	public List<ObjetivoDTO> listarParaDisciplinaObjetivo(FiltroObjetivo filtroObjetivo) {
		StringBuilder query = createQuery("select o.id, o.id_campo_experiencia, o.id_instituicao, o.id_faixa_ano, o.id, ");
	    query.append("f.nome as faixa_ano, o.bncc, o.codigo, c.nome as campo_experiencia,  o.descricao, o.ativo  ");
	    query.append("from objetivo o ");
	    query.append("left join componente c on (o.id_campo_experiencia = c.id) ");
	    query.append("left join faixa_ano f on (o.id_faixa_ano = f.id) where o.ativo is true ");
        query.append("and (o.bncc = true ");
        query = or(query, "o.id_instituicao", filtroObjetivo.getIdInstituicao(), "=");
        query.append(" )");
        query = andEqual(query, "o.faixa_ano", filtroObjetivo.getIdFaixaAno());
        query.append(" and not exists(");
        query.append(" select d.id_objetivo from disciplina_objetivo d ");
        query.append(" where d.data_final_vigencia is null ");
        query = andEqual(query, "d.id_disciplina", filtroObjetivo.getIdDisciplina());
        query.append(" and d.id_objetivo = o.id) ");
        query = orderBy(query, Order.ASC, "o.codigo");
        query = limit(query, filtroObjetivo.getQtdTotal(), filtroObjetivo.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ObjetivoDTOMapper());
	}
	
	@Override
	public int contarParaDisciplinaObjetivo(FiltroObjetivo filtroObjetivo) {
		StringBuilder query = createQuery("select count(o.id) ");
		query.append("from objetivo o ");
		query.append("left join componente c on (o.id_campo_experiencia = c.id) ");
		query.append("left join faixa_ano f on (o.id_faixa_ano = f.id) where o.ativo is true ");
        query.append("and (o.bncc = true ");
        query = or(query, "o.id_instituicao", filtroObjetivo.getIdInstituicao(), "=");
        query.append(" )");
        query = andEqual(query, "o.faixa_ano", filtroObjetivo.getIdFaixaAno());
        query.append(" and not exists(");
        query.append(" select d.id_objetivo from disciplina_objetivo d ");
        query.append(" where d.data_final_vigencia is null ");
        query = andEqual(query, "d.id_disciplina", filtroObjetivo.getIdDisciplina());
        query.append(" and d.id_objetivo = o.id) ");
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public List<ObjetivoDTO> listar(FiltroObjetivo filtroObjetivo) {
		StringBuilder query = createQuery("select o.id, o.id_campo_experiencia, o.id_instituicao, o.id_faixa_ano, o.id, ");
	    query.append("f.nome as faixa_ano, o.bncc, o.codigo, c.nome as campo_experiencia,  o.descricao, o.ativo  ");
	    query.append("from objetivo o ");
	    query.append("left join campo_experiencia c on (o.id_campo_experiencia = c.id) ");
	    query.append("left join faixa_ano f on (o.id_faixa_ano = f.id) where 1=1 ");
	    query.append("and (o.bncc = true ");
	    query = or(query, "o.id_instituicao", filtroObjetivo.getIdInstituicao(), "=");
	    query.append(" )");
	    query = andEqual(query, "o.id", filtroObjetivo.getId());      
	   	query = andEqual(query, "o.id_campo_experiencia", filtroObjetivo.getIdCampoExperiencia());
	   	query = andEqual(query, "o.codigo", filtroObjetivo.getCodigo());
	    query = andEqual(query, "o.ativo", filtroObjetivo.getAtivo());    
	   	query = andEqual(query, "o.bncc", filtroObjetivo.getBncc());      
	   	query = andEqual(query, "o.id_faixa_ano", filtroObjetivo.getIdFaixaAno()); 
	    query = orderBy(query, Order.ASC, "o.id_faixa_ano");
		query = limit(query, filtroObjetivo.getQtdTotal(), filtroObjetivo.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ObjetivoDTOMapper());
	}
	
	@Override
	public int contar(FiltroObjetivo filtroObjetivo) {
		  StringBuilder query = createQuery("select count(o.id) ");
		  query.append("from objetivo o ");
		  query.append("left join componente c on (o.id_campo_experiencia = c.id) ");
		  query.append("left join faixa_ano f on (o.id_faixa_ano = f.id) where 1=1 ");
		  query.append("and (o.bncc = true ");
		  query = or(query, "o.id_instituicao", filtroObjetivo.getIdInstituicao(), "=");
		  query.append(" )");
		  query = andEqual(query, "o.id", filtroObjetivo.getId());      
	   	  query = andEqual(query, "o.id_campo_experiencia", filtroObjetivo.getIdCampoExperiencia());
	   	  query = andEqual(query, "o.codigo", filtroObjetivo.getCodigo());
	      query = andEqual(query, "o.ativo", filtroObjetivo.getAtivo());   
	   	  query = andEqual(query, "o.bncc", filtroObjetivo.getBncc());      
	   	  query = andEqual(query, "o.id_faixa_ano", filtroObjetivo.getIdFaixaAno()); 
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("objetivo", id);
		return true;
	}
	
	private class ObjetivoDTOMapper implements RowMapper<ObjetivoDTO> {

		@Override
		public ObjetivoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ObjetivoDTO.builder()
					.id(rs.getLong("id"))
                    .idCampoExperiencia(rs.getLong("id_campo_experiencia"))
                    .camposExperiencia(rs.getString("campo_experiencia"))
                    .descricao(rs.getString("descricao"))
                    .codigo(rs.getString("codigo"))
                    .bncc(rs.getBoolean("bncc"))
                    .idFaixaAno(rs.getLong("id_faixa_ano"))
                    .faixaAno(rs.getString("faixa_ano"))
                    .ativo(rs.getBoolean("ativo"))
                    .idInstituicao(rs.getLong("id_instituicao"))
					.build();
		}
	}
}

