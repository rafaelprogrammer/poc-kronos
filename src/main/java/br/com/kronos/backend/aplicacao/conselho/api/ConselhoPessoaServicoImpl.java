package br.com.kronos.backend.aplicacao.conselho.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.conselho.ConselhoPessoa;
import br.com.kronos.backend.aplicacao.conselho.ConselhoPessoaRepositorio;
import br.com.kronos.backend.aplicacao.conselho.FiltroConselhoPessoa;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class ConselhoPessoaServicoImpl implements ConselhoPessoaServico {

	@NonNull
	private ConselhoPessoaRepositorio conselhoPessoaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void criar(List<ConselhoPessoaDTO> dtos) throws ExcecaoDeNegocio {
		try {
			//TODO melhorar essa criacao utilizando in()
			dtos.stream().forEach(dto -> {
				if(conselhoPessoaRepositorio.contar(FiltroConselhoPessoa.builder().idPessoa(dto.getIdPessoa()).idConselho(dto.getIdConselho()).build()) == 0) {
					conselhoPessoaRepositorio.criar(ConselhoPessoa.builder()
						.idPessoa(dto.getIdPessoa())
						.idConselho(dto.getIdConselho())
						.idTipoFuncao(dto.getIdTipoFuncao())
						.build());
				}
			});
		} catch (RuntimeException e) {
			log.error("Erro ao vincular pessoa no conselho", e);
			throw new ExcecaoDeNegocio("Erro ao vincular pessoa no conselho", e);
		}
	}

	
	@Override
	public PaginacaoDTO<ConselhoPessoaDTO> listar(FiltroConselhoPessoa filtroConselhoPessoa) throws ExcecaoDeNegocio {
		try {
			
			int total = conselhoPessoaRepositorio.contar(filtroConselhoPessoa);
			
			List<ConselhoPessoaDTO> conselhoPessoas = modelMapper.map(conselhoPessoaRepositorio.listar(filtroConselhoPessoa),
					new TypeToken<List<ConselhoPessoaDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<ConselhoPessoaDTO>builder().total(total).dados(conselhoPessoas).build();
		} catch (RuntimeException e) {
			log.error("Erro ao listar pessoas do conselho", e);
			throw new ExcecaoDeNegocio("Erro ao listar pessoas do conselho", e);
		}
	}

	@Override
	public boolean excluir(long idPessoa, long idConselho) {
		try {
			return conselhoPessoaRepositorio.excluir(idPessoa, idConselho);
		} catch (RuntimeException e) {
			log.error("Erro ao excluir pessoa - " + idPessoa  + " do conselho - " + idConselho, e);
			throw new ExcecaoDeNegocio("Erro ao excluir pessoa - " + idPessoa  + " do conselho - " + idConselho, e);
		}
	}

}
