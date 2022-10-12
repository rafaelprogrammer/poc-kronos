package br.com.kronos.backend.adaptadores.repositorio.comum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.aplicacao.comum.FiltroPais;
import br.com.kronos.backend.aplicacao.comum.Pais;
import br.com.kronos.backend.aplicacao.comum.PaisRepositorio;

public class PaisRepositorioImpl extends SqlQueryRepositorio implements PaisRepositorio{
	
	public PaisRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public List<Pais> listar(FiltroPais filtroPais) {
		StringBuilder query = createQuery("select p.id, p.nome, p.ddi, p.gentilico, p.sigla from pais p where 1=1 ");
		query = andEqual(query, "p.id", filtroPais.getId());
		query = andLike(query, "p.nome", filtroPais.getNome());
		query = andEqual(query, "p.sigla", filtroPais.getSigla());
		query = orderBy(query, Order.ASC, "p.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new PaisMapper());
	}
	
	private class PaisMapper implements RowMapper<Pais> {

		@Override
		public Pais mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Pais.builder()
					.id(rs.getInt("id"))
					.nome(rs.getString("nome"))
					.ddi(rs.getString("ddi"))
					.sigla(rs.getString("sigla"))
					.gentilico(rs.getString("gentilico"))
					.build();
		}

	}


}
