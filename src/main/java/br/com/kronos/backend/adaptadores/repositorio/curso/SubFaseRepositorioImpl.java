package br.com.kronos.backend.adaptadores.repositorio.curso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.curso.FiltroFase;
import br.com.kronos.backend.aplicacao.curso.FiltroSubFase;
import br.com.kronos.backend.aplicacao.curso.SubFase;
import br.com.kronos.backend.aplicacao.curso.SubFaseRepositorio;
import br.com.kronos.backend.aplicacao.curso.api.SubFaseResumoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubFaseRepositorioImpl extends SqlQueryRepositorio implements SubFaseRepositorio {
	
	public SubFaseRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(SubFase subFase) {
        addFields("nome", "numero", "sigla", "id_tipo_periodo", 
                  "id_fase"); 
		
		addValues(subFase.getNome(), subFase.getNumero(), subFase.getSigla(),subFase.getIdTipoPeriodo(), 
                  subFase.getIdFase());
		
		return (long) insertAuto("sub_fase");
    }
    
	@Override
	public Long alterar(SubFase subFase) {
		addFields("nome", "numero", "sigla", "id_tipo_periodo", 
                  "id_fase"); 
		
		addValues(subFase.getNome(), subFase.getNumero(), subFase.getSigla(),subFase.getIdTipoPeriodo(), 
                  subFase.getIdFase(), subFase.getId());
		
		return (long) update("sub_fase", subFase.getId());
	}
	
	@Override
	public SubFase buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select s.id, s.nome, s.numero, s.sigla, s.id_tipo_periodo, ");
            query.append("s.id_fase from sub_fase s where 1=1 ");
            query = andEqual(query, "s.id", id);
			SubFase subFase = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new SubFaseMapper());
			return subFase;
		} catch (EmptyResultDataAccessException e) {
			log.info("subFase não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<SubFase> listar(FiltroSubFase filtroSubFase) {
        StringBuilder query = createQuery("select s.id, s.nome, s.numero, s.sigla, s.id_tipo_periodo, ");
        query.append("s.id_fase from sub_fase s where 1=1 ");
        query = andEqual(query, "s.id", filtroSubFase.getId());
        query = andEqual(query, "s.numero", filtroSubFase.getNumero()); 
        query = andEqual(query, "s.nome", filtroSubFase.getNome());
        query = andEqual(query, "s.sigla", filtroSubFase.getSigla());
        query = andEqual(query, "s.id_fase", filtroSubFase.getIdFase()); 
        query = orderBy(query, Order.ASC, "s.numero");
		query = limit(query, filtroSubFase.getQtdTotal(), filtroSubFase.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new SubFaseMapper());
	}
	
	@Override
	public int contar(FiltroSubFase filtroSubFase) {
		StringBuilder query = createQuery("select count(s.id) from sub_fase s where 1=1 ");
		query = andEqual(query, "s.id", filtroSubFase.getId());
        query = andEqual(query, "s.numero", filtroSubFase.getNumero()); 
        query = andEqual(query, "s.nome", filtroSubFase.getNome());
        query = andEqual(query, "s.sigla", filtroSubFase.getSigla());
        query = andEqual(query, "s.id_fase", filtroSubFase.getIdFase()); 
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("sub_fase", id);
		return true;
	}
	
	private class SubFaseMapper implements RowMapper<SubFase> {

		@Override
		public SubFase mapRow(ResultSet rs, int rowNum) throws SQLException {
			return SubFase.builder() 
                    .id(rs.getLong("id"))
                    .numero(rs.getInt("numero"))
                    .nome(rs.getString("nome"))
                    .sigla(rs.getString("sigla"))
                    .idTipoPeriodo(rs.getInt("id_tipo_periodo"))
                    .idFase(rs.getLong("id_fase"))
                    .build();
		}
	}
	
	@Override
	public List<SubFaseResumoDTO> listarParaCombo(FiltroFase filtroFase) {
        StringBuilder query = createQuery("select sf.id, sf.sigla from sub_fase sf ");
        query.append("left join fase f on (sf.id_fase = f.id) ");
        query.append("left join periodo p on (f.id_periodo = p.id) where 1=1 ");
        query = andEqual(query, "p.id_curso", filtroFase.getIdCurso());
        query = andEqual(query, "p.id", filtroFase.getIdPeriodo());
        query = orderBy(query, Order.ASC, "f.numero");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new SubFaseResumoDTOParaComboMapper());
	}
	
	private class SubFaseResumoDTOParaComboMapper implements RowMapper<SubFaseResumoDTO> {

		@Override
		public SubFaseResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return SubFaseResumoDTO.builder()
					.id(rs.getLong("id"))
                    .sigla(rs.getString("sigla"))
                    .build();
		}
	}
	
	@Override
	public List<SubFaseResumoDTO> listarParaGeracaoSubFaseExecucao(FiltroFase filtroFase) {
        StringBuilder query = createQuery("select distinct f.numero as numero_fase, f.sigla as sigla_fase, sf.numero, sf.sigla from sub_fase sf ");
        query.append("left join fase f on (sf.id_fase = f.id) ");
        query.append("left join periodo p on (f.id_periodo = p.id) where 1=1 ");
        query = andEqual(query, "p.id_curso", filtroFase.getIdCurso());
        query = orderBy(query, Order.ASC, "f.numero");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new SubFaseResumoDTOMapper());
	}
	
	private class SubFaseResumoDTOMapper implements RowMapper<SubFaseResumoDTO> {

		@Override
		public SubFaseResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return SubFaseResumoDTO.builder()
                    .numero(rs.getInt("numero"))
                    .sigla(rs.getString("sigla"))
                    .siglaFase(rs.getString("sigla_fase"))
                    .numeroFase(rs.getString("numero_fase"))
                    .build();
		}
	}

}