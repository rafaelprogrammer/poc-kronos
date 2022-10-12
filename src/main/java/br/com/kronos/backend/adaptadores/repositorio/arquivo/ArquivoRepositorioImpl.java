package br.com.kronos.backend.adaptadores.repositorio.arquivo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.arquivo.Arquivo;
import br.com.kronos.backend.aplicacao.arquivo.ArquivoRepositorio;
import br.com.kronos.backend.aplicacao.arquivo.FiltroArquivo;
import br.com.kronos.backend.aplicacao.arquivo.api.ArquivoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArquivoRepositorioImpl extends SqlQueryRepositorio implements ArquivoRepositorio {
	
	public ArquivoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int criar(Arquivo arquivo) {
		
		addFields("arquivo", "arquivo_content_type", "data_inclusao", "legenda", 
				"nome", "tamanho", "id_tipo_arquivo", "id_tipo_conteudo");
		
		addValues(arquivo.getBytes(), arquivo.getContentType(), arquivo.getDataInclusao(), arquivo.getLegenda(), arquivo.getNome(),
				arquivo.getTamanho(), arquivo.getIdTipoArquivo(), arquivo.getIdTipoConteudo());
		
		return insertAuto("arquivo_anexo");
	}

	@Override
	@Transactional(readOnly=true)
	public ArquivoDTO buscarPorId(int id) {
		try {
			
			StringBuilder query = createQuery("select a.id, a.arquivo, a.arquivo_content_type, a.data_inclusao, a.legenda, a.nome, a.id_tipo_arquivo, a.tamanho, a.id_tipo_conteudo ");
			query.append(" from arquivo_anexo a where 1=1 ");
			query = andEqual(query, "a.id", id);
			
			return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new ArquivoMapper(true));
			
		} catch (EmptyResultDataAccessException e) {
			log.info("Arquivo não existe - " + id);
			return null;
		}
	}
	
	@Override
	public List<ArquivoDTO> listar(FiltroArquivo filtro) {
		StringBuilder query = createQuery("select a.id, a.arquivo_content_type, a.data_inclusao, a.legenda, a.nome, a.id_tipo_arquivo, a.tamanho, a.id_tipo_conteudo ");
		query.append(" from arquivo_anexo a ");
		query.append(" left join pessoa p on(a.id = p.id_arq_anexo) where 1=1 ");
		query = andEqual(query, "a.id", filtro.getId());
		query = andEqual(query, "p.id", filtro.getIdPessoa());
		query = andEqual(query, "a.id_tipo_arquivo", filtro.getIdTipoArquivo());
		query = andEqual(query, "a.id_tipo_conteudo", filtro.getIdTipoConteudo());
		query = andLike(query, "a.nome", filtro.getNome());
		query = orderBy(query, Order.DESC, "a.data_inclusao");
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ArquivoMapper(false));
	}
	
	@Override
	public int contar(FiltroArquivo filtro) {
		StringBuilder query = createQuery("select count(a.id) from arquivo_anexo a ");
		query.append(" left join pessoa p on(a.id = p.id_arq_anexo) where 1=1 ");
		query = andEqual(query, "a.id", filtro.getId());
		query = andLike(query, "a.nome", filtro.getNome());
		query = andEqual(query, "p.id", filtro.getIdPessoa());
		query = andEqual(query, "a.id_tipo_arquivo", filtro.getIdTipoArquivo());
		query = andEqual(query, "a.id_tipo_conteudo", filtro.getIdTipoConteudo());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class ArquivoMapper implements RowMapper<ArquivoDTO> {
		
		private boolean carregaDados;
		
		ArquivoMapper(boolean carregaDados) {
			this.carregaDados = carregaDados;
		}

		@Override
		public ArquivoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ArquivoDTO.builder()
					.id(rs.getInt("id"))
					.bytes(carregaDados ? rs.getBytes("arquivo") : null)
					.contentType(rs.getString("arquivo_content_type"))
					.legenda(rs.getString("legenda"))
					.nome(rs.getString("nome"))
					.tamanho(rs.getLong("tamanho"))
					.idTipoArquivo(rs.getInt("id_tipo_arquivo"))
					.idTipoConteudo(rs.getInt("id_tipo_conteudo"))
					.build();
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean excluir(int id) {
		delete("arquivo_anexo", id);
		return true;
	}
	
}
