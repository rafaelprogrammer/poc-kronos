package br.com.kronos.backend.adaptadores.repositorio.calendario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.calendario.CategoriaEvento;
import br.com.kronos.backend.aplicacao.calendario.CategoriaEventoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.FiltroCategoriaEvento;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CategoriaEventoRepositorioImpl extends SqlQueryRepositorio implements CategoriaEventoRepositorio {
	
	public CategoriaEventoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(CategoriaEvento categoriaEvento) {
        addFields("nome", "cor");
		
		addValues(categoriaEvento.getNome(), categoriaEvento.getCor());
		
		return (long) insertAuto("categoria_evento"); 
    }

	
	@Override
	public Long alterar(CategoriaEvento categoriaEvento) {
		addFields("nome", "cor");
		
		addValues(categoriaEvento.getNome(), categoriaEvento.getCor(), categoriaEvento.getId());
		
		return (long) update("categoria_evento", categoriaEvento.getId());
	}
	
	@Override
	public CategoriaEvento buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select c.nome, c.cor ");
            query.append("from categoria_evento c where 1=1 ");
            query = andEqual(query, "c.id", id);
			CategoriaEvento categoriaEvento = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new CategoriaEventoMapper());
			return categoriaEvento;
		} catch (EmptyResultDataAccessException e) {
			log.info("categoriaEvento não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<CategoriaEvento> listar(FiltroCategoriaEvento filtroCategoriaEvento) {
		StringBuilder query = createQuery("select c.nome, c.cor ");
        query.append("from categoria_evento c where 1=1 ");
        query = andEqual(query, "c.id", filtroCategoriaEvento.getId());
		query = andLike(query, "c.nome", filtroCategoriaEvento.getNome());
        query = andLike(query, "c.cor", filtroCategoriaEvento.getCor());
        query = orderBy(query, Order.ASC, "c.nome");
		query = limit(query, filtroCategoriaEvento.getQtdTotal(), filtroCategoriaEvento.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CategoriaEventoMapper());
	}
	
	@Override
	public int contar(FiltroCategoriaEvento filtroCategoriaEvento) {
		StringBuilder query = createQuery("select count(c.id) from categoria_evento c where 1=1 ");
		query = andEqual(query, "c.id", filtroCategoriaEvento.getId());
		query = andLike(query, "c.nome", filtroCategoriaEvento.getNome());
	    query = andLike(query, "c.cor", filtroCategoriaEvento.getCor());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("categoria_evento", id);
		return true;
	}
	
	private class CategoriaEventoMapper implements RowMapper<CategoriaEvento> {

		@Override
		public CategoriaEvento mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CategoriaEvento.builder()
					.id(rs.getLong("id"))
                    .nome(rs.getString("nome"))
                    .cor(rs.getString("cor"))
					.build();
		}
	}

}