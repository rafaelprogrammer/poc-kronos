package br.com.kronos.backend.aplicacao.pessoa.api;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.arquivo.Arquivo;
import br.com.kronos.backend.aplicacao.arquivo.ArquivoRepositorio;
import br.com.kronos.backend.aplicacao.arquivo.EnumTipoConteudo;
import br.com.kronos.backend.aplicacao.arquivo.api.ArquivoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroTitulacao;
import br.com.kronos.backend.aplicacao.pessoa.Titulacao;
import br.com.kronos.backend.aplicacao.pessoa.TitulacaoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class TitulacaoServicoImpl implements TitulacaoServico {

	@NonNull
	private TitulacaoRepositorio titulacaoRepositorio;
	
	@NonNull
	private ArquivoRepositorio arquivoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public int criar(TitulacaoDTO dto) {
		return titulacaoRepositorio.criar(Titulacao.builder()
										.data(dto.getData())
										.idEmpresa(dto.getIdEmpresa())
										.idArqAnexo(dto.getIdArqAnexo())
										.idPessoa(dto.getIdPessoa())
										.idTipoTitulo(dto.getIdTipoTitulo())
										.nomeTitulo(dto.getNomeTitulo())
										.build());
	}

	@Override
	public int alterar(TitulacaoDTO dto) {
			return titulacaoRepositorio.alterar(Titulacao.builder()
					.id(dto.getId())
					.idEmpresa(dto.getIdEmpresa())
					.data(dto.getData())
					.idArqAnexo(dto.getIdArqAnexo() !=null && dto.getIdArqAnexo() > 0 ? dto.getIdArqAnexo() : null)
					.idPessoa(dto.getIdPessoa())
					.idTipoTitulo(dto.getIdTipoTitulo())
					.nomeTitulo(dto.getNomeTitulo())
					.build());
	}

	@Override
	public TitulacaoDTO buscarPorId(int id) {
		return modelMapper.map(titulacaoRepositorio.buscarPorId(id), TitulacaoDTO.class);
	}

	@Override
	public PaginacaoDTO<TitulacaoDTO> listar(FiltroTitulacao filtro) throws ExcecaoDeNegocio {
		int total = titulacaoRepositorio.contar(filtro);
		return PaginacaoDTO.<TitulacaoDTO>builder().total(total).dados(titulacaoRepositorio.listar(filtro)).build();
	}

	@Override
	public boolean excluir(int id) {
		return titulacaoRepositorio.excluir(id);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void alterarArquivo(TitulacaoDTO dto) {
		ArquivoDTO arquivoDTO = dto.getArquivoDTO();
		TitulacaoDTO titulacaoDTOBanco = titulacaoRepositorio.buscarPorId(dto.getId());
		int idArquivoAnexoAntigo = titulacaoDTOBanco.getIdArqAnexo();
		int idArquivo = arquivoRepositorio.criar(Arquivo.builder()
										.bytes(arquivoDTO.getBytes())
										.tamanho(arquivoDTO.getTamanho())
										.contentType(arquivoDTO.getContentType())
										.nome(arquivoDTO.getNome())
										.legenda(arquivoDTO.getLegenda())
										.idTipoConteudo(EnumTipoConteudo.DOCUMENTO.id())
										.build());
		
		titulacaoRepositorio.alterarArquivo(dto.getId(), idArquivo);
		arquivoRepositorio.excluir(idArquivoAnexoAntigo);
		
	}
}
