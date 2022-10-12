package br.com.kronos.backend.adaptadores.repositorio.curso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.curso.Fase;
import br.com.kronos.backend.aplicacao.curso.FaseRepositorio;
import br.com.kronos.backend.aplicacao.curso.FiltroFase;
import br.com.kronos.backend.aplicacao.curso.api.FaseResumoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FaseRepositorioImpl extends SqlQueryRepositorio implements FaseRepositorio {
	
	public FaseRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Fase fase) {
        addFields("nome", "numero", "sigla", "id_tipo_periodo", 
                  "id_periodo"); 
		
		addValues(fase.getNome(), fase.getNumero(), fase.getSigla(),fase.getIdTipoPeriodo(), 
                  fase.getIdPeriodo());
		
		return (long) insertAuto("fase");
    }
    
	@Override
	public Long alterar(Fase fase) {
		addFields("nome", "numero", "sigla", "id_tipo_periodo", 
                  "id_periodo"); 
		
		addValues(fase.getNome(), fase.getNumero(), fase.getSigla(),fase.getIdTipoPeriodo(), 
                  fase.getIdPeriodo(), fase.getId());
		
		return (long) update("fase", fase.getId());
	}
	
	@Override
	public Fase buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select f.id, f.nome, f.numero, f.sigla, f.id_tipo_periodo, ");
            query.append("f.id_periodo from fase f where 1=1 ");
            query = andEqual(query, "f.id", id);
			Fase fase = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new FaseMapper());
			return fase;
		} catch (EmptyResultDataAccessException e) {
			log.info("fase não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Fase> listarParaCombo(FiltroFase filtroFase) {
		return this.listar(filtroFase);
	}
	
	@Override
	public List<Fase> listar(FiltroFase filtroFase) {
        StringBuilder query = createQuery("select f.id, f.nome, f.numero, f.sigla, f.id_tipo_periodo, ");
        query.append("f.id_periodo from fase f where 1=1 ");
        query = andEqual(query, "f.id", filtroFase.getId());
        query = andEqual(query, "f.numero", filtroFase.getNumero()); 
        query = andEqual(query, "f.nome", filtroFase.getNome());
        query = andEqual(query, "f.sigla", filtroFase.getSigla());
        query = andEqual(query, "f.id_periodo", filtroFase.getIdPeriodo()); 
        query = orderBy(query, Order.ASC, "f.numero");
		query = limit(query, filtroFase.getQtdTotal(), filtroFase.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new FaseMapper());
	}
	
	@Override
	public int contar(FiltroFase filtroFase) {
		StringBuilder query = createQuery("select count(f.id) from fase f where 1=1 ");
		query = andEqual(query, "f.id", filtroFase.getId());
        query = andEqual(query, "f.numero", filtroFase.getNumero()); 
        query = andEqual(query, "f.nome", filtroFase.getNome());
        query = andEqual(query, "f.sigla", filtroFase.getSigla());
        query = andEqual(query, "f.id_periodo", filtroFase.getIdPeriodo());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public List<FaseResumoDTO> listarParaGeracaoFaseExecucao(FiltroFase filtroFase) {
        StringBuilder query = createQuery("select distinct f.numero, f.sigla from fase f ");
        query.append("left join periodo p on (f.id_periodo = p.id) where 1=1 ");
        query = andEqual(query, "p.id_curso", filtroFase.getIdCurso());
        query = orderBy(query, Order.ASC, "f.numero");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new FaseResumoDTOMapper());
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("fase", id);
		return true;
	}
	
	private class FaseResumoDTOMapper implements RowMapper<FaseResumoDTO> {

		@Override
		public FaseResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return FaseResumoDTO.builder() 
                    .numero(rs.getInt("numero"))
                    .sigla(rs.getString("sigla"))
                    .build();
		}
	}
	
	private class FaseMapper implements RowMapper<Fase> {

		@Override
		public Fase mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Fase.builder() 
                    .id(rs.getLong("id"))
                    .numero(rs.getInt("numero"))
                    .nome(rs.getString("nome"))
                    .sigla(rs.getString("sigla"))
                    .idTipoPeriodo(rs.getInt("id_tipo_periodo"))
                    .idPeriodo(rs.getLong("id_periodo"))
                    .build();
		}
	}
}