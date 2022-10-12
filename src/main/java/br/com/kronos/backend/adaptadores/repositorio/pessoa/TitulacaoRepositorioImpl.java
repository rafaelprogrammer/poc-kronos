package br.com.kronos.backend.adaptadores.repositorio.pessoa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroTitulacao;
import br.com.kronos.backend.aplicacao.pessoa.Titulacao;
import br.com.kronos.backend.aplicacao.pessoa.TitulacaoRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.api.TitulacaoDTO;

public class TitulacaoRepositorioImpl extends SqlQueryRepositorio implements TitulacaoRepositorio {
	
	public TitulacaoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	@Override
	public int criar(Titulacao titulacao) {
		
		addFields("data", "nome_titulo", "id_pessoa", "id_empresa", "id_tipo_titulo");
		
		addValues(titulacao.getData(), titulacao.getNomeTitulo(), titulacao.getIdPessoa(), titulacao.getIdEmpresa(), titulacao.getIdTipoTitulo());
		
		return insertAuto("titulacao");
	}
	
	@Override
	public int alterarArquivo(int id, int idArqAnexo) {
		
		addFields("id_arq_anexo");
		
		addValues(idArqAnexo, id);
		
		return update("titulacao", id);
	}
	
	@Override
	public int alterar(Titulacao titulacao) {
		addFields("data", "nome_titulo", "id_pessoa", "id_empresa", "id_tipo_titulo", "id_arq_anexo");
		
		addValues(titulacao.getData(), titulacao.getNomeTitulo(), titulacao.getIdPessoa(), 
				titulacao.getIdEmpresa(), titulacao.getIdTipoTitulo(), titulacao.getIdArqAnexo(), titulacao.getId());
		
		return update("titulacao", titulacao.getId());
	}
	
	@Override
	public TitulacaoDTO buscarPorId(int id) {
		try {
			StringBuilder query = createQuery("select t.id, t.data, t.nome_titulo, t.id_arq_anexo, t.id_pessoa, t.id_empresa, t.id_tipo_titulo, e.nome_fantasia ");
			query.append("from titulacao t ");
			query.append("join empresa e on (t.id_empresa = e.id) where 1=1 ");
			query = andEqual(query, "t.id", id);
			TitulacaoDTO dto = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new TitulacaoDTOMapper());
			return dto;
		} catch (EmptyResultDataAccessException e) {
			throw new ExcecaoDeNegocio("Titulação não existe " + id, e);
		}
	} 
	
	@Override
	public List<TitulacaoDTO> listar(FiltroTitulacao filtro) {
		StringBuilder query = createQuery("select t.id, t.data, t.nome_titulo, t.id_arq_anexo, t.id_pessoa, t.id_empresa, t.id_tipo_titulo, e.nome_fantasia ");
		query.append("from titulacao t ");
		query.append("join empresa e on (t.id_empresa = e.id) where 1=1 ");
		query = andEqual(query, "t.id", filtro.getId());
		query = andEqual(query, "t.id_pessoa", filtro.getIdPessoa());
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TitulacaoDTOMapper());
	}
	
	@Override
	public int contar(FiltroTitulacao filtro) {
		StringBuilder query = createQuery("select count(t.id) from titulacao t where 1=1 ");
		query = andEqual(query, "t.id", filtro.getId());
		query = andEqual(query, "t.id_pessoa", filtro.getIdPessoa());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(int id) {
		delete("titulacao", id);
		return true;
	}
	
	private class TitulacaoDTOMapper implements RowMapper<TitulacaoDTO> {

		@Override
		public TitulacaoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return TitulacaoDTO.builder()
					.id(rs.getInt("id"))
					.nomeTitulo(rs.getString("nome_titulo"))
					.data(rs.getDate("data").toLocalDate())
					.idArqAnexo(rs.getInt("id_arq_anexo"))
					.idPessoa(rs.getLong("id_pessoa"))
					.idEmpresa(rs.getInt("id_empresa"))
					.idTipoTitulo(rs.getInt("id_tipo_titulo"))
					.nomeEmpresa(rs.getString("nome_fantasia"))
					.build();
		}

	}

}
