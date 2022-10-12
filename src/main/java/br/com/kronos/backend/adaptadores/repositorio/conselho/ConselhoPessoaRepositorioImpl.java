package br.com.kronos.backend.adaptadores.repositorio.conselho;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.conselho.FiltroConselhoPessoa;
import br.com.kronos.backend.aplicacao.conselho.ConselhoPessoa;
import br.com.kronos.backend.aplicacao.conselho.ConselhoPessoaRepositorio;

public class ConselhoPessoaRepositorioImpl extends SqlQueryRepositorio implements ConselhoPessoaRepositorio {
	
	public ConselhoPessoaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public int criar(ConselhoPessoa conselhoPessoa) {
		addFields("id_pessoa", "id_conselho", "id_tipo_funcao" );
		
		addValues(conselhoPessoa.getIdPessoa(), conselhoPessoa.getIdConselho(), conselhoPessoa.getIdTipoFuncao() );
		
		return insert("conselho_pessoa");
	}

	@Override
	public List<ConselhoPessoa> listar(FiltroConselhoPessoa filtroConselhoPessoa) {
		StringBuilder query = createQuery("select c.id_pessoa, c.id_conselho, c.id_tipo_funcao ");
		query.append(" from conselho_pessoa c where 1=1 ");
		query = andEqual(query, "c.id_conselho", filtroConselhoPessoa.getIdConselho());
		query = limit(query, filtroConselhoPessoa.getQtdTotal(), filtroConselhoPessoa.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ConselhoPessoaMapper());
	}
	
	@Override
	public int contar(FiltroConselhoPessoa filtroConselhoPessoa) {
		StringBuilder query = createQuery("select count(c.id_pessoa) from conselho_pessoa c where 1=1 ");
		query = andEqual(query, "c.id_pessoa", filtroConselhoPessoa.getIdPessoa());
		query = andEqual(query, "c.id_conselho", filtroConselhoPessoa.getIdConselho());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(long idPessoa, long idConselho) {
		addFields("id_pessoa", "id_conselho");
		addValues(idPessoa, idConselho);
		delete("conselho_pessoa");
		return true;
	}
	
	private class ConselhoPessoaMapper implements RowMapper<ConselhoPessoa> {

		@Override
		public ConselhoPessoa mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ConselhoPessoa.builder()
					.idPessoa(rs.getLong("id_pessoa"))
					.idConselho(rs.getLong("id_conselho"))
					.build();
		}
	}

	@Override
	public int contarPorIdPessoaEConselho(FiltroConselhoPessoa filtroConselhoPessoa) {
		StringBuilder query = createQuery("select count(c.id_pessoa) from conselho_pessoa c where 1=1 ");
		query = andEqual(query, "c.id_pessoa", filtroConselhoPessoa.getIdPessoa());
		query = andEqual(query, "c.id_conselho", filtroConselhoPessoa.getIdConselho());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
}

