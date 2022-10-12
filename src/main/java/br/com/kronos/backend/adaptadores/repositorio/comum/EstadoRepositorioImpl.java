package br.com.kronos.backend.adaptadores.repositorio.comum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.aplicacao.comum.Estado;
import br.com.kronos.backend.aplicacao.comum.EstadoRepositorio;
import br.com.kronos.backend.aplicacao.comum.FiltroEstado;

public class EstadoRepositorioImpl extends SqlQueryRepositorio implements EstadoRepositorio{
	
	public EstadoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public List<Estado> listar(FiltroEstado filtroEstado) {
		StringBuilder query = createQuery("select e.id, e.nome, e.id_pais, e.codigo_ibge, e.sigla from estado e where 1=1 ");
		query = andEqual(query, "e.id", filtroEstado.getId());
		query = andLike(query, "e.nome", filtroEstado.getNome());
		query = andEqual(query, "c.id_pais", filtroEstado.getIdPais());
		query = orderBy(query, Order.ASC, "e.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new EstadoMapper());
	}
	
	private class EstadoMapper implements RowMapper<Estado> {

		@Override
		public Estado mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Estado.builder()
					.id(rs.getInt("id"))
					.nome(rs.getString("nome"))
					.codigoIbge(rs.getString("codigo_ibge"))
					.sigla(rs.getString("sigla"))
					.idPais(rs.getInt("id_pais"))
					.build();
		}

	}


}
