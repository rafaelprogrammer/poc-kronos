package br.com.kronos.backend.aplicacao.resultado.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.resultado.FiltroResultadoFase;
import br.com.kronos.backend.aplicacao.resultado.ResultadoFase;
import br.com.kronos.backend.aplicacao.resultado.ResultadoFaseRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ResultadoFaseServicoImpl implements ResultadoFaseServico {

	@NonNull
	private ResultadoFaseRepositorio resultadoFaseRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(ResultadoFaseDTO resultadoFaseDTO) throws ExcecaoDeNegocio {

			return resultadoFaseRepositorio.criar(ResultadoFase.builder()
					                        .notaFinalNormal(resultadoFaseDTO.getNotaFinalNormal())                               
                                            .notaFinalRecuperacao(resultadoFaseDTO.getNotaFinalRecuperacao())
                                            .totalAusencia(resultadoFaseDTO.getTotalAusencia())
                                            .percentualAusencia(resultadoFaseDTO.getPercentualAusencia())                               
                                            .idFaseExecucao(resultadoFaseDTO.getIdFaseExecucao())
                                            .idCredito(resultadoFaseDTO.getIdCredito())
                                            .idMencao(resultadoFaseDTO.getIdMencao())
                                            .notaFinal(resultadoFaseDTO.getNotaFinal()).build());
    }

	@Override
	public Long alterar(ResultadoFaseDTO resultadoFaseDTO) throws ExcecaoDeNegocio {
			return resultadoFaseRepositorio.alterar(ResultadoFase.builder()
											.id(resultadoFaseDTO.getId())
											.notaFinalNormal(resultadoFaseDTO.getNotaFinalNormal())                               
                                            .notaFinalRecuperacao(resultadoFaseDTO.getNotaFinalRecuperacao())
                                            .totalAusencia(resultadoFaseDTO.getTotalAusencia())
                                            .percentualAusencia(resultadoFaseDTO.getPercentualAusencia())                               
                                            .idFaseExecucao(resultadoFaseDTO.getIdFaseExecucao())
                                            .idCredito(resultadoFaseDTO.getIdCredito())
                                            .idMencao(resultadoFaseDTO.getIdMencao())
                                            .notaFinal(resultadoFaseDTO.getNotaFinal()).build());	
	}

	@Override
	public ResultadoFaseDTO buscarPorId (long id) {
		return modelMapper.map(resultadoFaseRepositorio.buscarPorId(id), ResultadoFaseDTO.class);
	}

	@Override
	public PaginacaoDTO<ResultadoFaseDTO> listar(FiltroResultadoFase filtroResultadoFase) throws ExcecaoDeNegocio {
			int total = resultadoFaseRepositorio.contar(filtroResultadoFase);
			List<ResultadoFaseDTO> resultadosFase = modelMapper.map(resultadoFaseRepositorio.listar(filtroResultadoFase),
					new TypeToken<List<ResultadoFaseDTO>>() {
					}.getType());
			
			return  PaginacaoDTO.<ResultadoFaseDTO>builder().total(total).dados(resultadosFase).build();
	}

	@Override
	public boolean excluir(Long id) {
		return resultadoFaseRepositorio.excluir(id);
	}

}

