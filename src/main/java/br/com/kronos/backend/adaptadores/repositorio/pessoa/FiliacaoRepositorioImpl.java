package br.com.kronos.backend.adaptadores.repositorio.pessoa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.Filiacao;
import br.com.kronos.backend.aplicacao.pessoa.FiliacaoRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroFiliacao;
import br.com.kronos.backend.aplicacao.pessoa.api.FiliacaoDTO;

public class FiliacaoRepositorioImpl extends SqlQueryRepositorio implements FiliacaoRepositorio {
	
	public FiliacaoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public int criar(Filiacao filiacao) {
		addFields("data_filiacao", "filiacao_atual", "id_pessoa_pais", 
				"id_pessoa_filho", "id_tipo_filiacao");
		
		addValues(filiacao.getDataFiliacao(), filiacao.isFiliacaoAtual(), filiacao.getIdPessoaPais(), 
				filiacao.getIdPessoaFilho(), filiacao.getIdTipoFiliacao());
		
		return insertAuto("filiacao");
	}

	@Override
	public List<FiliacaoDTO> listar(FiltroFiliacao filtro) {
		StringBuilder query = createQuery("select f.id, f.data_filiacao, f.filiacao_atual, f.id_pessoa_filho, pf.nome as nomeFilho, f.id_pessoa_pais, pp.nome as nomePais, f.id_tipo_filiacao ");
		query.append(" from filiacao f ");
		query.append(" join pessoa pf on (f.id_pessoa_filho = pf.id) ");
		query.append(" join pessoa pp on (f.id_pessoa_pais = pp.id) where 1=1 ");
		query = andEqual(query, "f.id", filtro.getId());
		query = andEqual(query, "f.id_pessoa_filho", filtro.getIdPessoaFilho());
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new FiliacaoDTOMapper());
	}
	
	@Override
	public int contar(FiltroFiliacao filtro) {
		StringBuilder query = createQuery("select count(f.id) from filiacao f where 1=1 ");
		query = andEqual(query, "f.id", filtro.getId());
		query = andEqual(query, "f.id_pessoa_filho", filtro.getIdPessoaFilho());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(int id) {
		delete("filiacao", id);
		return true;
	}
	
	private class FiliacaoDTOMapper implements RowMapper<FiliacaoDTO> {

		@Override
		public FiliacaoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return FiliacaoDTO.builder()
					.id(rs.getInt("id"))
					.idPessoaFilho(rs.getLong("id_pessoa_filho"))
					.nomePessoaFilho(rs.getString("nomeFilho"))
					.idPessoaPais(rs.getLong("id_pessoa_pais"))
					.nomePessoaPais(rs.getString("nomePais"))
					.idTipoFiliacao(rs.getInt("id_tipo_filiacao"))
					.filiacaoAtual(rs.getBoolean("filiacao_atual"))
					.dataFiliacao(rs.getDate("data_filiacao").toLocalDate())
					.build();
		}

	}


}
