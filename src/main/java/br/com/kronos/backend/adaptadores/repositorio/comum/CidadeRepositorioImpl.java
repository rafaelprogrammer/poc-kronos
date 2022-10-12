package br.com.kronos.backend.adaptadores.repositorio.comum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.aplicacao.comum.Cidade;
import br.com.kronos.backend.aplicacao.comum.CidadeRepositorio;
import br.com.kronos.backend.aplicacao.comum.FiltroCidade;

public class CidadeRepositorioImpl extends SqlQueryRepositorio implements CidadeRepositorio{
	
	public CidadeRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public List<Cidade> listar(FiltroCidade filtroCidade) {
		StringBuilder query = createQuery("select c.id, c.nome, c.id_estado, c.cod_area_tel, c.codigo_ibge, c.area from cidade c where 1=1");
		query = andEqual(query, "c.id", filtroCidade.getId());
		query = andLike(query, "c.nome", filtroCidade.getNome());
		query = andEqual(query, "c.id_estado", filtroCidade.getIdEstado());
		query = orderBy(query, Order.ASC, "c.nome");
		query = limit(query, filtroCidade.getQtdTotal(), filtroCidade.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CidadeMapper());
	}
	
	@Override
	public int contar(FiltroCidade filtroCidade) {
		StringBuilder query = createQuery("select count(c.id) from cidade c where 1=1 ");
		query = andEqual(query, "c.id", filtroCidade.getId());
		query = andLike(query, "c.nome", filtroCidade.getNome());
		query = andEqual(query, "c.id_estado", filtroCidade.getIdEstado());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class CidadeMapper implements RowMapper<Cidade> {

		@Override
		public Cidade mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Cidade.builder()
					.id(rs.getInt("id"))
					.nome(rs.getString("nome"))
					.codigoIbge(rs.getString("codigo_ibge"))
					.codAreaTel(rs.getString("cod_area_tel"))
					.area(rs.getString("area"))
					.idEstado(rs.getInt("id_estado"))
					.build();
		}

	}


}
