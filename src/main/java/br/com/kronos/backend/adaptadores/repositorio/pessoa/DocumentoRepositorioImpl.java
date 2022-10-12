package br.com.kronos.backend.adaptadores.repositorio.pessoa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.Documento;
import br.com.kronos.backend.aplicacao.pessoa.DocumentoRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroDocumento;
import br.com.kronos.backend.aplicacao.pessoa.api.DocumentoDTO;

public class DocumentoRepositorioImpl extends SqlQueryRepositorio implements DocumentoRepositorio {
	
	public DocumentoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public int criar(Documento documento) {
		addFields("data_expedicao", "folha", "livro", 
				"nome_cartorio", "numero", "orgao_expedidor", "secao", 
				"serie", "zona", "id_pessoa", "id_cidade", "id_tipo");
		
		addValues(documento.getDataExpedicao(), documento.getFolha(), documento.getLivro(),
				documento.getNomeCartorio(), documento.getNumero(), documento.getOrgaoExpeditor(), documento.getSecao(), 
				documento.getSerie(), documento.getZona(), documento.getIdPessoa(), documento.getIdCidade(), 
				documento.getIdTipo());
		
		return insertAuto("documento");
	}
	
	@Override
	public int alterarArquivo(int id, int idArqAnexo) {
		addFields("id_arq_anexo");
		
		addValues(idArqAnexo, id);
		
		return update("documento", id);
	}
	
	@Override
	public int alterar(Documento documento) {
		addFields("data_expedicao", "folha", "livro", 
				"nome_cartorio", "numero", "orgao_expedidor", "secao", 
				"serie", "zona", "id_pessoa", "id_cidade", "id_tipo", "id_arq_anexo");
		
		addValues(documento.getDataExpedicao(), documento.getFolha(), documento.getLivro(),
				documento.getNomeCartorio(), documento.getNumero(), documento.getOrgaoExpeditor(), documento.getSecao(), 
				documento.getSerie(), documento.getZona(), documento.getIdPessoa(), documento.getIdCidade(), 
				documento.getIdTipo(), documento.getIdArqAnexo(), documento.getId());
		
		return update("documento", documento.getId());
	}
	
	@Override
	public DocumentoDTO buscarPorId(int id) {
		try {
			StringBuilder query = createQuery("select d.id, d.data_expedicao, d.folha, d.livro, d.nome_cartorio, d.numero, c.nome as nomeCidade, ");
			query.append("d.orgao_expedidor, d.secao, d.serie, d.zona, d.id_arq_anexo, d.id_pessoa, d.id_cidade, d.id_tipo from documento d ");
			query.append("join cidade c on (d.id_cidade = c.id) where 1=1 ");
			query = andEqual(query, "d.id", id);
			DocumentoDTO documento = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new DocumentoDTOMapper());
			return documento;
		} catch (EmptyResultDataAccessException e) {
			throw new ExcecaoDeNegocio("Documento não existe " + id, e);
		}
	} 
	
	@Override
	public List<DocumentoDTO> listar(FiltroDocumento filtro) {
		StringBuilder query = createQuery("select d.id, d.data_expedicao, d.folha, d.livro, d.nome_cartorio, d.numero, c.nome as nomeCidade, ");
		query.append("d.orgao_expedidor, d.secao, d.serie, d.zona, d.id_arq_anexo, d.id_pessoa, d.id_cidade, d.id_tipo from documento d ");
		query.append("join cidade c on (d.id_cidade = c.id) where 1=1 ");
		query = andEqual(query, "d.id", filtro.getId());
		query = andEqual(query, "d.id_pessoa", filtro.getIdPessoa());
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DocumentoDTOMapper());
	}
	
	@Override
	public int contar(FiltroDocumento filtro) {
		StringBuilder query = createQuery("select count(d.id) from documento d where 1=1 ");
		query = andEqual(query, "d.id", filtro.getId());
		query = andEqual(query, "d.id_pessoa", filtro.getIdPessoa());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(int id) {
		delete("documento", id);
		return true;
	}
	
	private class DocumentoDTOMapper implements RowMapper<DocumentoDTO> {

		@Override
		public DocumentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DocumentoDTO.builder()
					.id(rs.getInt("id"))
					.folha(rs.getString("folha"))
					.numero(rs.getString("numero"))
					.dataExpedicao(rs.getDate("data_expedicao").toLocalDate())
					.livro(rs.getString("livro"))
					.orgaoExpeditor(rs.getString("orgao_expedidor"))
					.secao(rs.getString("secao"))
					.serie(rs.getString("serie"))
					.nomeCartorio(rs.getString("nome_cartorio"))
					.zona(rs.getString("zona"))
					.idArqAnexo(rs.getInt("id_arq_anexo"))
					.idPessoa(rs.getLong("id_pessoa"))
					.idCidade(rs.getInt("id_cidade"))
					.nomeCidade(rs.getString("nomeCidade"))
					.idTipo(rs.getInt("id_tipo"))
					.build();
		}

	}

}
