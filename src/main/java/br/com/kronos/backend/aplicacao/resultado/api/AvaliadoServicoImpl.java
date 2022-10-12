package br.com.kronos.backend.aplicacao.resultado.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.resultado.Avaliado;
import br.com.kronos.backend.aplicacao.resultado.AvaliadoRepositorio;
import br.com.kronos.backend.aplicacao.resultado.FiltroAvaliado;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class AvaliadoServicoImpl implements AvaliadoServico {

	@NonNull
	private AvaliadoRepositorio avaliadoRepositorio;
	
	@NonNull
	private ModelMapper modelMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criarAvaliados(List<AvaliadoDTO> dtos) throws ExcecaoDeNegocio {
		if (dtos != null && !dtos.isEmpty()) {
			avaliadoRepositorio.excluirPorAvaliacao(dtos.get(0).getIdAvaliacao());
			dtos.stream().forEach(avaliadoDTO -> {
				 avaliadoRepositorio.criar(Avaliado.builder()
	                        .idAvaliacao(avaliadoDTO.getIdAvaliacao())                               
                         .idCredito(avaliadoDTO.getIdCredito())
                         .idGrupoAvaliacao(avaliadoDTO.getIdGrupoAvaliacao() == 0 ? null : avaliadoDTO.getIdGrupoAvaliacao())
                         .descarte(avaliadoDTO.isDescarte())
                         .build());
			});
		}

		
    }

	@Override
	public Long criar(AvaliadoDTO avaliadoDTO) throws ExcecaoDeNegocio {

			return avaliadoRepositorio.criar(Avaliado.builder()
					                        .idAvaliacao(avaliadoDTO.getIdAvaliacao())                               
                                            .idCredito(avaliadoDTO.getIdCredito())
                                            .idGrupoAvaliacao(avaliadoDTO.getIdGrupoAvaliacao())
                                            .nota(avaliadoDTO.getNota())                               
                                            .idMencao(avaliadoDTO.getIdMencao())
                                            .descarte(avaliadoDTO.isDescarte())
                                            .motivoDescarte(avaliadoDTO.getMotivoDescarte()).build());
    }

	@Override
	public Long alterar(AvaliadoDTO avaliadoDTO) throws ExcecaoDeNegocio {
			return avaliadoRepositorio.alterar(Avaliado.builder()
											.id(avaliadoDTO.getId())
											.idAvaliacao(avaliadoDTO.getIdAvaliacao())                               
                                            .idCredito(avaliadoDTO.getIdCredito())
                                            .idGrupoAvaliacao(avaliadoDTO.getIdGrupoAvaliacao())
                                            .nota(avaliadoDTO.getNota())                               
                                            .idMencao(avaliadoDTO.getIdMencao())
                                            .descarte(avaliadoDTO.isDescarte())
                                            .motivoDescarte(avaliadoDTO.getMotivoDescarte()).build());
	}			

	@Override
	public AvaliadoDTO buscarPorId (long id) {
		return modelMapper.map(avaliadoRepositorio.buscarPorId(id), AvaliadoDTO.class);
	}

	@Override
	public PaginacaoDTO<AvaliadoDTO> listar(FiltroAvaliado filtroAvaliado) throws ExcecaoDeNegocio {
		int total = avaliadoRepositorio.contar(filtroAvaliado);
		List<AvaliadoDTO> avaliados = modelMapper.map(avaliadoRepositorio.listar(filtroAvaliado),
				new TypeToken<List<AvaliadoDTO>>() {
				}.getType());
		
		return  PaginacaoDTO.<AvaliadoDTO>builder().total(total).dados(avaliados).build();
	}

	@Override
	public boolean excluir(Long id) {
		return avaliadoRepositorio.excluir(id);
	}

	@Override
	public List<AvaliadoDTO> listarParaSelecaoNaAvaliacao(FiltroAvaliado filtroAvaliado) {
		return  avaliadoRepositorio.listarParaSelecaoNaAvaliacao(filtroAvaliado);
	}

	@Override
	public List<AvaliadoDTO> listarResultadoHabiliadeDoAvaliado(FiltroAvaliado filtroAvaliado) {
		return  avaliadoRepositorio.listarResultadoHabiliadeDoAvaliado(filtroAvaliado);
	}

}