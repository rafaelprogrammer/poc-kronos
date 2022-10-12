package br.com.kronos.backend.adaptadores.repositorio.pessoa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroTelefonePessoa;
import br.com.kronos.backend.aplicacao.pessoa.TelefonePessoa;
import br.com.kronos.backend.aplicacao.pessoa.TelefonePessoaRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.api.TelefonePessoaDTO;

public class TelefonePessoaRepositorioImpl extends SqlQueryRepositorio implements TelefonePessoaRepositorio {
	
	public TelefonePessoaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	@Override
	public int criar(TelefonePessoa telefone) {
		addFields("numero", "principal", "what_app", 
				"id_pessoa", "id_cidade", "id_operadora", "id_tipo_uso", 
				"id_tipo_telefone");
		
		addValues(telefone.getNumero(), telefone.isPrincipal(), telefone.isWhatApp(),
				telefone.getIdPessoa(), telefone.getIdCidade(), telefone.getIdOperadora(), telefone.getIdTipoUso(), 
				telefone.getIdTipoTelefone());
		
		return insertAuto("pessoa_telefone");
	}

	@Override
	public List<TelefonePessoaDTO> listar(FiltroTelefonePessoa filtro) {
		StringBuilder query = createQuery("select t.id, t.numero, t.principal, t.what_app, t.id_pessoa, t.id_cidade, c.nome as nomeCidade, c.cod_area_tel, t.id_operadora, t.id_tipo_uso, ");
		query.append(" t.id_tipo_telefone ");
		query.append(" from pessoa_telefone t ");
		query.append(" join cidade c on (t.id_cidade = c.id) where 1=1 ");
		query = andEqual(query, "t.id", filtro.getId());
		query = andEqual(query, "t.numero", filtro.getNumero());
		query = andEqual(query, "t.id_pessoa", filtro.getIdPessoa());
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TelefonePessoaMapper());
	}
	
	@Override
	public int contar(FiltroTelefonePessoa filtro) {
		StringBuilder query = createQuery("select count(t.id) from pessoa_telefone t where 1=1 ");
		query = andEqual(query, "t.id", filtro.getId());
		query = andEqual(query, "t.numero", filtro.getNumero());
		query = andEqual(query, "t.id_pessoa", filtro.getIdPessoa());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(int id) {
		delete("pessoa_telefone", id);
		return true;
	}
	
	private class TelefonePessoaMapper implements RowMapper<TelefonePessoaDTO> {

		@Override
		public TelefonePessoaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return TelefonePessoaDTO.builder()
					.id(rs.getInt("id"))
					.numero(rs.getInt("numero"))
					.principal(rs.getBoolean("principal"))
					.whatApp(rs.getBoolean("what_app"))
					.idPessoa(rs.getLong("id_pessoa"))
					.idCidade(rs.getInt("id_cidade"))
					.nomeCidade(rs.getString("nomeCidade"))
					.ddd(rs.getString("cod_area_tel"))
					.idOperadora(rs.getInt("id_operadora"))
					.idTipoUso(rs.getInt("id_tipo_uso"))
					.idTipoTelefone(rs.getInt("id_tipo_telefone"))
					.build();
		}

	}


}
