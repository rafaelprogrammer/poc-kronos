package br.com.kronos.backend.adaptadores.repositorio.desempenho;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.desempenho.FiltroDesempenhoAtitude;
import br.com.kronos.backend.aplicacao.desempenho.DesempenhoAtitude;
import br.com.kronos.backend.aplicacao.desempenho.DesempenhoAtitudeRepositorio;

public class DesempenhoAtitudeRepositorioImpl extends SqlQueryRepositorio implements DesempenhoAtitudeRepositorio {
	
	public DesempenhoAtitudeRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public int criar(DesempenhoAtitude desempenhoAtitude) {
		addFields("id_desempenho", "id_atitude", "nota", "id_mencao");
		
		addValues(desempenhoAtitude.getIdDesempenho(), desempenhoAtitude.getIdAtitude(), desempenhoAtitude.getNota(), desempenhoAtitude.getIdMencao());
		
		return insert("desempenho_atitude");
	}

	@Override
	public List<DesempenhoAtitude> listar(FiltroDesempenhoAtitude filtroDesempenhoAtitude) {
		StringBuilder query = createQuery("select d.id_desempenho, d.id_atitude, d.nota, d.id_mencao ");
		query.append(" from desempenho_atitude d where 1=1 ");
		query = andEqual(query, "d.id_desempenho", filtroDesempenhoAtitude.getIdDesempenho());
		query = andEqual(query, "d.id_atitude", filtroDesempenhoAtitude.getIdAtitude());
		query = limit(query, filtroDesempenhoAtitude.getQtdTotal(), filtroDesempenhoAtitude.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DesempenhoAtitudeMapper());
	}
	
	@Override
	public int contar(FiltroDesempenhoAtitude filtroDesempenhoAtitude) {
		StringBuilder query = createQuery("select count(d.id_atitude) from desempenho_atitude d where 1=1 ");
		query = andEqual(query, "d.id_desempenho", filtroDesempenhoAtitude.getIdDesempenho());
		query = andEqual(query, "d.id_atitude", filtroDesempenhoAtitude.getIdAtitude());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(long idDesempenho, long idAtitude) {
		addFields("id_desempenho", "id_atitude");
		addValues(idDesempenho, idAtitude);
		delete("desempenho_atitude");
		return true;
	}
	
	private class DesempenhoAtitudeMapper implements RowMapper<DesempenhoAtitude> {

		@Override
		public DesempenhoAtitude mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DesempenhoAtitude.builder()
					.idDesempenho(rs.getLong("id_curso"))
					.idAtitude(rs.getLong("id_escala"))
					.nota(rs.getDouble("nota"))
					.idMencao(rs.getLong("id_mencao"))
					.build();
		}

	}

	@Override
	public int contarPorIdDesempenhoEAtitude(FiltroDesempenhoAtitude filtroDesempenhoAtitude) {
		StringBuilder query = createQuery("select count(d.id_atitude) from desempenho_atitude d where 1=1 ");
		query = andEqual(query, "d.id_desempenho", filtroDesempenhoAtitude.getIdDesempenho());
		query = andEqual(query, "d.id_atitude", filtroDesempenhoAtitude.getIdAtitude());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
}

