package br.com.kronos.backend.aplicacao.avaliacao.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.avaliacao.AvaliacaoHabilidade;
import br.com.kronos.backend.aplicacao.avaliacao.AvaliacaoHabilidadeRepositorio;
import br.com.kronos.backend.aplicacao.avaliacao.FiltroAvaliacaoHabilidade;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class AvaliacaoHabilidadeServicoImpl implements AvaliacaoHabilidadeServico {

	@NonNull
	private AvaliacaoHabilidadeRepositorio avaliacaoHabilidadeRepositorio;

	@NonNull
	private ModelMapper modelMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criarAvaliacoesHabilidades(List<AvaliacaoHabilidadeDTO> dtos) throws ExcecaoDeNegocio {
		
		if (dtos != null && !dtos.isEmpty()) {
			dtos.stream().forEach(dto -> {
				criar(dto);
			});
		}

    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long criar(AvaliacaoHabilidadeDTO avaliacaoHabilidadeDTO) throws ExcecaoDeNegocio {

			return avaliacaoHabilidadeRepositorio.criar(AvaliacaoHabilidade.builder()
					                        .idAvaliacao(avaliacaoHabilidadeDTO.getIdAvaliacao())
                                            .idDisciplinaHabilidade(avaliacaoHabilidadeDTO.getIdDisciplinaHabilidade()).build());	

    }

	@Override
	public Long alterar(AvaliacaoHabilidadeDTO avaliacaoHabilidadeDTO) throws ExcecaoDeNegocio {
			return avaliacaoHabilidadeRepositorio.alterar(AvaliacaoHabilidade.builder()
											.id(avaliacaoHabilidadeDTO.getId())
											.idAvaliacao(avaliacaoHabilidadeDTO.getIdAvaliacao())
                                            .idDisciplinaHabilidade(avaliacaoHabilidadeDTO.getIdDisciplinaHabilidade()).build());	
	}

	@Override
	public AvaliacaoHabilidadeDTO buscarPorId (long id) {
		return avaliacaoHabilidadeRepositorio.buscarPorId(id);
	}

	@Override
	public List<AvaliacaoHabilidadeDTO> listar(FiltroAvaliacaoHabilidade filtroAvaliacaoHabilidade) throws ExcecaoDeNegocio {
		
		return avaliacaoHabilidadeRepositorio.listar(filtroAvaliacaoHabilidade);
	}

	@Override
	public boolean excluir(Long id) {
		return avaliacaoHabilidadeRepositorio.excluir(id);
	}

}

