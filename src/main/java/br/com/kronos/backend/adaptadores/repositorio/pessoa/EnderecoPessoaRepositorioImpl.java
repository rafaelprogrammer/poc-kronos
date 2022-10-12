package br.com.kronos.backend.adaptadores.repositorio.pessoa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.EnderecoPessoa;
import br.com.kronos.backend.aplicacao.pessoa.EnderecoPessoaRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroEnderecoPessoa;

public class EnderecoPessoaRepositorioImpl extends SqlQueryRepositorio implements EnderecoPessoaRepositorio {
	
	public EnderecoPessoaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	@Override
	public Integer criar(EnderecoPessoa enderecoPessoa) {
		addFields("cep", "logradouro", "complemento", 
				"bairro", "localidade", "uf", "unidade", 
				"ibge", "gia", "id_tipo_endereco", "id_pessoa", "numero");
		
		addValues(enderecoPessoa.getCep(), enderecoPessoa.getLogradouro(), enderecoPessoa.getComplemento(),
				enderecoPessoa.getBairro(), enderecoPessoa.getLocalidade(), enderecoPessoa.getUf(), enderecoPessoa.getUnidade(), 
				enderecoPessoa.getIbge(), enderecoPessoa.getGia(), enderecoPessoa.getIdTipoEndereco(), enderecoPessoa.getIdPessoa(), enderecoPessoa.getNumero());
		
		return insertAuto("pessoa_endereco");
	}

	@Override
	public List<EnderecoPessoa> listar(FiltroEnderecoPessoa filtroEnderecoPessoa) {
		StringBuilder query = createQuery("select e.id, e.cep, e.logradouro, e.complemento, e.bairro, e.localidade, e.uf, e.unidade, ");
		query.append(" e.ibge, e.gia, e.id_tipo_endereco, e.id_pessoa, e.numero ");
		query.append(" from pessoa_endereco e where 1=1 ");
		query = andEqual(query, "e.id", filtroEnderecoPessoa.getId());
		query = andEqual(query, "e.cep", filtroEnderecoPessoa.getCep());
		query = andEqual(query, "e.id_pessoa", filtroEnderecoPessoa.getIdPessoa());
		query = limit(query, filtroEnderecoPessoa.getQtdTotal(), filtroEnderecoPessoa.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new EnderecoPessoaMapper());
	}
	
	@Override
	public int contar(FiltroEnderecoPessoa filtroEnderecoPessoa) {
		StringBuilder query = createQuery("select count(e.id) from pessoa_endereco e where 1=1 ");
		query = andEqual(query, "e.id", filtroEnderecoPessoa.getId());
		query = andEqual(query, "e.cep", filtroEnderecoPessoa.getCep());
		query = andEqual(query, "e.id_pessoa", filtroEnderecoPessoa.getIdPessoa());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Integer id) {
		delete("pessoa_endereco", id);
		return true;
	}
	
	private class EnderecoPessoaMapper implements RowMapper<EnderecoPessoa> {

		@Override
		public EnderecoPessoa mapRow(ResultSet rs, int rowNum) throws SQLException {
			return EnderecoPessoa.builder()
					.id(rs.getInt("id"))
					.cep(rs.getString("cep"))
					.logradouro(rs.getString("logradouro"))
					.complemento(rs.getString("complemento"))
					.bairro(rs.getString("bairro"))
					.localidade(rs.getString("localidade"))
					.uf(rs.getString("uf"))
					.unidade(rs.getString("unidade"))
					.ibge(rs.getString("ibge"))
					.gia(rs.getString("gia"))
					.idTipoEndereco(rs.getInt("id_tipo_endereco"))
					.idPessoa(rs.getInt("id_pessoa"))
					.numero(rs.getString("numero"))
					.build();
		}

	}


}
