package br.com.kronos.backend.aplicacao.arquivo.api;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.arquivo.Arquivo;
import br.com.kronos.backend.aplicacao.arquivo.ArquivoRepositorio;
import br.com.kronos.backend.aplicacao.arquivo.FiltroArquivo;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.Pessoa;
import br.com.kronos.backend.aplicacao.pessoa.PessoaRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ArquivoServicoImpl implements ArquivoServico {
	
	
	@NonNull
	private final ArquivoRepositorio arquivoRepositorio;
	
	@NonNull
	private final PessoaRepositorio pessoaRepositorio;
	
	@NonNull
	private final ModelMapper modelMapper;

	@Override
	public ArquivoDTO buscarPorId(int id) {
		return modelMapper.map(arquivoRepositorio.buscarPorId(id), ArquivoDTO.class);
	}
	
	@Override
	public int criar(ArquivoDTO dto) {
		return arquivoRepositorio.criar(Arquivo.builder()
													.bytes(dto.getBytes())
													.contentType(dto.getContentType())
													.idTipoArquivo(dto.getIdTipoArquivo())
													.idTipoConteudo(dto.getIdTipoConteudo())
													.tamanho(dto.getTamanho())
													.nome(dto.getNome())
													.legenda(dto.getLegenda())
													.build());
	}

	@Override
	public PaginacaoDTO<ArquivoDTO> listar(FiltroArquivo filtro) throws ExcecaoDeNegocio {
		int total = arquivoRepositorio.contar(filtro);
		return PaginacaoDTO.<ArquivoDTO>builder().total(total).dados(arquivoRepositorio.listar(filtro)).build();
	}

	@Override
	public boolean excluir(int id) {
		return arquivoRepositorio.excluir(id);
	}
	
	@Override
	public ArquivoDTO buscarFotoPessoa(Long idPessoa) {
		Pessoa pessoa = pessoaRepositorio.buscarPorId(idPessoa);
		if (pessoa.getIdArqAnexo() !=null && pessoa.getIdArqAnexo() > 0) {
			return arquivoRepositorio.buscarPorId(pessoa.getIdArqAnexo());
		} else {
			return null;
		}
	}

}
