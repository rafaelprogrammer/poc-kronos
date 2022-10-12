package br.com.kronos.backend.adaptadores.repositorio.pessoa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroTalento;
import br.com.kronos.backend.aplicacao.pessoa.Talento;
import br.com.kronos.backend.aplicacao.pessoa.TalentoRepositorio;

public class TalentoRepositorioImpl extends SqlQueryRepositorio implements TalentoRepositorio {
	
	public TalentoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public int criar(Talento talento) {
		addFields("id_pessoa", "id_tipo_talento");
		
		addValues(talento.getIdPessoa(), talento.getIdTipoTalento());
		
		return insert("pessoa_talento");
	}

	@Override
	public List<Talento> listar(FiltroTalento filtro) {
		StringBuilder query = createQuery("select t.id_pessoa, t.id_tipo_talento ");
		query.append(" from pessoa_talento t where 1=1 ");
		query = andEqual(query, "t.id_pessoa", filtro.getIdPessoa());
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TalentoMapper());
	}
	
	@Override
	public int contar(FiltroTalento filtro) {
		StringBuilder query = createQuery("select count(t.id_pessoa) from pessoa_talento t where 1=1 ");
		query = andEqual(query, "t.id_pessoa", filtro.getIdPessoa());
		query = andEqual(query, "t.id_tipo_talento", filtro.getIdTipoTalento());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(long id, int idTipoTalento) {
		addFields("id_pessoa", "id_tipo_talento");
		addValues(id, idTipoTalento);
		delete("pessoa_talento");
		return true;
	}
	
	private class TalentoMapper implements RowMapper<Talento> {

		@Override
		public Talento mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Talento.builder()
					.idPessoa(rs.getLong("id_pessoa"))
					.idTipoTalento(rs.getInt("id_tipo_talento"))
					.build();
		}

	}

	@Override
	public int contarPorIdPessoaETipo(FiltroTalento filtro) {
		StringBuilder query = createQuery("select count(t.id_pessoa) from pessoa_talento t where 1=1 ");
		query = andEqual(query, "t.id_pessoa", filtro.getIdPessoa());
		query = andEqual(query, "t.id_tipo_talento", filtro.getIdTipoTalento());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}

	@Override
	public List<Talento> buscarTalentosDaPessoa(long idPessoa) {
		StringBuilder query = createQuery("select t.id_pessoa, t.id_tipo_talento ");
		query.append(" from pessoa_talento t where 1=1 ");
		query = andEqual(query, "t.id_pessoa", idPessoa);
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TalentoMapper());
	}

	@Override
	public boolean excluirTodosDaPessoa(long idPessoa) {
		addFields("id_pessoa");
		addValues(idPessoa);
		delete("pessoa_talento");
		return true;
	}


}
