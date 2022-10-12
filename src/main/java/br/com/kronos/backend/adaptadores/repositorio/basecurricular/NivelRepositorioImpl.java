package br.com.kronos.backend.adaptadores.repositorio.basecurricular;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroNivel;
import br.com.kronos.backend.aplicacao.basecurricular.Nivel;
import br.com.kronos.backend.aplicacao.basecurricular.NivelRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NivelRepositorioImpl extends SqlQueryRepositorio implements NivelRepositorio {
	
	public NivelRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Integer criar(Nivel nivel) {
        addFields("nome" );
		
		addValues(nivel.getNome()); 
		
		return insertAuto("nivel"); 
    }
	
	@Override
	public Integer alterar(Nivel nivel) {
		addFields("nome" );
		
		addValues(nivel.getNome(), nivel.getId());
		
		return update("nivel", nivel.getId());
	}
		
	@Override
	public Nivel buscarPorId(Integer id) {
		try {
            StringBuilder query = createQuery("select n.id, n.nome ");
            query.append("from nivel n where 1=1 ");
            query = andEqual(query, "n.id", id);
			Nivel nivel = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new NivelMapper());
			return nivel;
		} catch (EmptyResultDataAccessException e) {
			log.info("nivel não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Nivel> listar(FiltroNivel filtroNivel) {
		  StringBuilder query = createQuery("select n.id, n.nome ");
          query.append("from nivel n where 1=1 ");
          query = andEqual(query, "n.id", filtroNivel.getId());
	   	  query = andLike(query, "n.nome", filtroNivel.getNome());
          query = orderBy(query, Order.ASC, "n.nome");
		  query = limit(query, filtroNivel.getQtdTotal(), filtroNivel.getNumeroPagina());
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new NivelMapper());
	}
	
	@Override
	public int contar(FiltroNivel filtroNivel) {
		  StringBuilder query = createQuery("select count(n.id) from nivel n where 1=1 ");
		  query = andEqual(query, "n.id", filtroNivel.getId());
	   	  query = andEqual(query, "n.nome", filtroNivel.getNome());
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Integer id) {
		delete("nivel", id);
		return true;
	}
	
	private class NivelMapper implements RowMapper<Nivel> {

		@Override
		public Nivel mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Nivel.builder()
					.id(rs.getInt("id"))
                    .nome(rs.getString("nome"))
					.build();
		}
	}
}

