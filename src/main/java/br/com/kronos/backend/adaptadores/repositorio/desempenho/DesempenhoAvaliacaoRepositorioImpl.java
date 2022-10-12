package br.com.kronos.backend.adaptadores.repositorio.desempenho;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.desempenho.FiltroDesempenhoAvaliacao;
import br.com.kronos.backend.aplicacao.desempenho.DesempenhoAvaliacao;
import br.com.kronos.backend.aplicacao.desempenho.DesempenhoAvaliacaoRepositorio;

public class DesempenhoAvaliacaoRepositorioImpl extends SqlQueryRepositorio implements DesempenhoAvaliacaoRepositorio {
	
	public DesempenhoAvaliacaoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public int criar(DesempenhoAvaliacao desempenhoAvaliacao) {
		addFields("id_desempenho", "id_avaliacao");
		
		addValues(desempenhoAvaliacao.getIdDesempenho(), desempenhoAvaliacao.getIdAvaliacao());
		
		return insert("desempenho_avaliacao");
	}

	@Override
	public List<DesempenhoAvaliacao> listar(FiltroDesempenhoAvaliacao filtroDesempenhoAvaliacao) {
		StringBuilder query = createQuery("select d.id_desempenho, d.id_avaliacao ");
		query.append(" from desempenho_avaliacao d where 1=1 ");
		query = andEqual(query, "d.id_desempenho", filtroDesempenhoAvaliacao.getIdDesempenho());
		query = andEqual(query, "d.id_avaliacao", filtroDesempenhoAvaliacao.getIdAvaliacao());
		query = limit(query, filtroDesempenhoAvaliacao.getQtdTotal(), filtroDesempenhoAvaliacao.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DesempenhoAvaliacaoMapper());
	}
	
	@Override
	public int contar(FiltroDesempenhoAvaliacao filtroDesempenhoAvaliacao) {
		StringBuilder query = createQuery("select count(d.id_avaliacao) from desempenho_avaliacao d where 1=1 ");
		query = andEqual(query, "d.id_desempenho", filtroDesempenhoAvaliacao.getIdDesempenho());
		query = andEqual(query, "d.id_avaliacao", filtroDesempenhoAvaliacao.getIdAvaliacao());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(long idDesempenho, long idAvaliacao) {
		addFields("id_desempenho", "id_avaliacao");
		addValues(idDesempenho, idAvaliacao);
		delete("desempenho_avaliacao");
		return true;
	}
	
	private class DesempenhoAvaliacaoMapper implements RowMapper<DesempenhoAvaliacao> {

		@Override
		public DesempenhoAvaliacao mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DesempenhoAvaliacao.builder()
					.idDesempenho(rs.getLong("id_desempenho"))
					.idAvaliacao(rs.getLong("id_avaliacao"))
					.build();
		}
	}

	@Override
	public int contarPorIdDesempenhoEAvaliacao(FiltroDesempenhoAvaliacao filtroDesempenhoAvaliacao) {
		StringBuilder query = createQuery("select count(d.id_avaliacao) from desempenho_avaliacao d where 1=1 ");
		query = andEqual(query, "d.id_desempenho", filtroDesempenhoAvaliacao.getIdDesempenho());
		query = andEqual(query, "d.id_avaliacao", filtroDesempenhoAvaliacao.getIdAvaliacao());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
}

