package br.com.kronos.backend.aplicacao.resultado.api;

import java.util.List;
import java.util.stream.IntStream;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.resultado.FiltroResultadoHabilidade;
import br.com.kronos.backend.aplicacao.resultado.ResultadoHabilidade;
import br.com.kronos.backend.aplicacao.resultado.ResultadoHabilidadeRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ResultadoHabilidadeServicoImpl implements ResultadoHabilidadeServico {

	@NonNull
	private ResultadoHabilidadeRepositorio resultadoHabilidadeRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criar(ResultadoHabilidadeDTO resultadoHabilidadeDTO) throws ExcecaoDeNegocio {
		if (resultadoHabilidadeDTO.getIdsAvaliados() != null && !resultadoHabilidadeDTO.getIdsAvaliados().isEmpty()) {
			IntStream.range(0, resultadoHabilidadeDTO.getIdsAvaliados().size()).forEach(index -> {
				Long idMencao = resultadoHabilidadeDTO.getIdsMencoes().get(index);
				Long idAvaliado = resultadoHabilidadeDTO.getIdsAvaliados().get(index);
			    resultadoHabilidadeRepositorio.excluirPorAvaliadoEAvaliacaoHabilidade(idAvaliado, resultadoHabilidadeDTO.getIdAvaliacaoHabilidade());
				resultadoHabilidadeRepositorio.criar(ResultadoHabilidade.builder()
							.idAvaliado(idAvaliado)                               
		                    .idAvaliacaoHabilidade(resultadoHabilidadeDTO.getIdAvaliacaoHabilidade())
		                    .nota(resultadoHabilidadeDTO.getNota())
		                    .idMencao(idMencao == null || idMencao == 0 ? resultadoHabilidadeDTO.getIdMencao() : idMencao)
		                    .descarte(resultadoHabilidadeDTO.isDescarte())                               						                    			                    
		                    .motivoDescarte(resultadoHabilidadeDTO.getMotivoDescarte()).build());
			});
		}
    }
	
	@Override
	public Long alterar(ResultadoHabilidadeDTO resultadoHabilidadeDTO) throws ExcecaoDeNegocio {
			return resultadoHabilidadeRepositorio.alterar(ResultadoHabilidade.builder()
											.id(resultadoHabilidadeDTO.getId())
											.idAvaliado(resultadoHabilidadeDTO.getIdAvaliado())                               
						                    .idAvaliacaoHabilidade(resultadoHabilidadeDTO.getIdAvaliacaoHabilidade())
						                    .nota(resultadoHabilidadeDTO.getNota())
						                    .idMencao(resultadoHabilidadeDTO.getIdMencao())
						                    .descarte(resultadoHabilidadeDTO.isDescarte())                               						                    			                    
						                    .motivoDescarte(resultadoHabilidadeDTO.getMotivoDescarte()).build());
	}

	@Override
	public ResultadoHabilidadeDTO buscarPorId (long id) {
		return modelMapper.map(resultadoHabilidadeRepositorio.buscarPorId(id), ResultadoHabilidadeDTO.class);
	}

	@Override
	public PaginacaoDTO<ResultadoHabilidadeDTO> listar(FiltroResultadoHabilidade filtroResultadoHabilidade) throws ExcecaoDeNegocio {
			int total = resultadoHabilidadeRepositorio.contar(filtroResultadoHabilidade);
			List<ResultadoHabilidadeDTO> resultadosHabilidades = modelMapper.map(resultadoHabilidadeRepositorio.listar(filtroResultadoHabilidade),
					new TypeToken<List<ResultadoHabilidadeDTO>>() {
					}.getType());
			
			return  PaginacaoDTO.<ResultadoHabilidadeDTO>builder().total(total).dados(resultadosHabilidades).build();
	}

	@Override
	public boolean excluir(Long id) {
		return resultadoHabilidadeRepositorio.excluir(id);
	}

}
