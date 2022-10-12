package br.com.kronos.backend.aplicacao.pessoa.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.arquivo.Arquivo;
import br.com.kronos.backend.aplicacao.arquivo.ArquivoRepositorio;
import br.com.kronos.backend.aplicacao.arquivo.EnumTipoConteudo;
import br.com.kronos.backend.aplicacao.arquivo.api.ArquivoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.Documento;
import br.com.kronos.backend.aplicacao.pessoa.DocumentoRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroDocumento;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DocumentoServicoImpl implements DocumentoServico {

	@NonNull
	private DocumentoRepositorio documentoRepositorio;
	
	@NonNull
	private ArquivoRepositorio arquivoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public int criar(DocumentoDTO dto) {
			return documentoRepositorio.criar(Documento.builder()
											.dataExpedicao(dto.getDataExpedicao())
											.folha(dto.getFolha())
											.livro(dto.getLivro())
											.nomeCartorio(dto.getNomeCartorio())
											.numero(dto.getNumero())
											.orgaoExpeditor(dto.getOrgaoExpeditor())
											.secao(dto.getSecao())
											.zona(dto.getZona())
											.serie(dto.getSerie())
											.idArqAnexo(dto.getIdArqAnexo())
											.idCidade(dto.getIdCidade())
											.idPessoa(dto.getIdPessoa())
											.idTipo(dto.getIdTipo())
											.build());
			
	}

	@Override
	public int alterar(DocumentoDTO dto) {
			return documentoRepositorio.alterar(Documento.builder()
					.id(dto.getId())
					.dataExpedicao(dto.getDataExpedicao())
					.folha(dto.getFolha())
					.livro(dto.getLivro())
					.nomeCartorio(dto.getNomeCartorio())
					.numero(dto.getNumero())
					.orgaoExpeditor(dto.getOrgaoExpeditor())
					.secao(dto.getSecao())
					.zona(dto.getZona())
					.serie(dto.getSerie())
					.idArqAnexo(dto.getIdArqAnexo())
					.idCidade(dto.getIdCidade())
					.idPessoa(dto.getIdPessoa())
					.idTipo(dto.getIdTipo())
					.build());
	}

	@Override
	public DocumentoDTO buscarPorId(int id) {
		return modelMapper.map(documentoRepositorio.buscarPorId(id), DocumentoDTO.class);
	}

	@Override
	public PaginacaoDTO<DocumentoDTO> listar(FiltroDocumento filtro) throws ExcecaoDeNegocio {
			int total = documentoRepositorio.contar(filtro);
			List<DocumentoDTO> documentos = modelMapper.map(documentoRepositorio.listar(filtro),
					new TypeToken<List<DocumentoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<DocumentoDTO>builder().total(total).dados(documentos).build();
	}

	@Override
	public boolean excluir(int id) {
		return documentoRepositorio.excluir(id);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void alterarArquivo(DocumentoDTO dto) {
		ArquivoDTO arquivoDTO = dto.getArquivoDTO();
		DocumentoDTO documentoDTOBanco = documentoRepositorio.buscarPorId(dto.getId());
		int idArquivoAnexoAntigo = documentoDTOBanco.getIdArqAnexo();
		int idArquivo = arquivoRepositorio.criar(Arquivo.builder()
										.bytes(arquivoDTO.getBytes())
										.tamanho(arquivoDTO.getTamanho())
										.contentType(arquivoDTO.getContentType())
										.nome(arquivoDTO.getNome())
										.legenda(arquivoDTO.getLegenda())
										.idTipoConteudo(EnumTipoConteudo.DOCUMENTO.id())
										.build());
		
		documentoRepositorio.alterarArquivo(dto.getId(), idArquivo);
		arquivoRepositorio.excluir(idArquivoAnexoAntigo);
		
	}
}
