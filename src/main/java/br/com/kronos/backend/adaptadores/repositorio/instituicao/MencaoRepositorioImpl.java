package br.com.kronos.backend.adaptadores.repositorio.instituicao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroMencao;
import br.com.kronos.backend.aplicacao.instituicao.Mencao;
import br.com.kronos.backend.aplicacao.instituicao.MencaoRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MencaoRepositorioImpl extends SqlQueryRepositorio implements MencaoRepositorio {
	
	public MencaoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Mencao mencao) {
        addFields("nome", "simbolo" );
		
		addValues(mencao.getNome(), mencao.getSimbolo()); 
		
		return (long) insertAuto("mencao"); 
    }
	
	@Override
	public Long alterar(Mencao mencao) {
		addFields("nome", "simbolo" );
		
		addValues(mencao.getNome(), mencao.getSimbolo(), mencao.getId());
		
		return (long) update("mencao", mencao.getId());
	}
		
	@Override
	public Mencao buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select m.id, m.nome, m.simbolo ");
            query.append("from mencao m where 1=1 ");
            query = andEqual(query, "m.id", id);
			Mencao mencao = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new MencaoMapper());
			return mencao;
		} catch (EmptyResultDataAccessException e) {
			log.info("mencao não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Mencao> listar(FiltroMencao filtroMencao) {
		  StringBuilder query = createQuery("select m.id, m.nome, m.simbolo ");
          query.append("from mencao m where m.id < 5 ");
          query = andEqual(query, "m.id", filtroMencao.getId());
	   	  query = andEqual(query, "m.nome", filtroMencao.getNome());
	   	  query = andEqual(query, "m.simbolo", filtroMencao.getSimbolo());
          query = orderBy(query, Order.ASC, "m.id");
		  query = limit(query, filtroMencao.getQtdTotal(), filtroMencao.getNumeroPagina());
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new MencaoMapper());
	}
	
	@Override
	public int contar(FiltroMencao filtroMencao) {
		StringBuilder query = createQuery("select count(m.id) from mencao m where 1=1 ");
		query = andEqual(query, "m.id", filtroMencao.getId());
		query = andEqual(query, "m.nome", filtroMencao.getNome());
	   	query = andEqual(query, "m.simbolo", filtroMencao.getSimbolo());
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("mencao", id);
		return true;
	}
	
	private class MencaoMapper implements RowMapper<Mencao> {

		@Override
		public Mencao mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Mencao.builder()
					.id(rs.getLong("id"))
                    .nome(rs.getString("nome"))
                    .simbolo(rs.getString("simbolo"))
					.build();
		}
	}
}

