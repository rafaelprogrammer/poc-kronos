package br.com.kronos.backend.aplicacao.calendario.api;

import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.calendario.FaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.FaseExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.FiltroFaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.PeriodoExecucao;
import br.com.kronos.backend.aplicacao.calendario.PeriodoExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.curso.FaseRepositorio;
import br.com.kronos.backend.aplicacao.curso.FiltroFase;
import br.com.kronos.backend.aplicacao.curso.api.FaseResumoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class FaseExecucaoServicoImpl implements FaseExecucaoServico {

	@NonNull
	private FaseExecucaoRepositorio faseExecucaoRepositorio;
	
	@NonNull
	private FaseRepositorio faseRepositorio;
	
	@NonNull
	private PeriodoExecucaoRepositorio periodoExecucaoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public void criar(List<FaseExecucaoDTO> dtos) {
		if (dtos != null && !dtos.isEmpty()) {
			Long idCurso = dtos.get(0).getIdCurso();
			Integer ano = dtos.get(0).getAnoPeriodoExecucao();
			List<DadosCriaFaseExecucaoDTO> dadosCriacao = faseExecucaoRepositorio.listarParaCriar(FiltroFaseExecucao.builder()
																							.idCurso(idCurso)
																							.ano(ano)
																							.build());
			if (dadosCriacao != null && !dadosCriacao.isEmpty()) {
				dtos.stream().forEach(dto -> {
					dadosCriacao.stream().forEach(dado -> {
						if (dado.getSiglaFase().equals(dto.getSiglaFase())) {
							PeriodoExecucao periodoExecucao = periodoExecucaoRepositorio.buscarPorIdPeriodoEAno(dado.getIdPeriodo(), ano);
							if (periodoExecucao != null) {
								faseExecucaoRepositorio.criar(FaseExecucao.builder()
			                        .dataInicio(dto.getDataInicio())  
			                        .dataFim(dto.getDataFim())
			                        .idPeriodoExecucao(periodoExecucao.getId())  
			                        .idFase(dado.getIdFase()).build());
							}
						}
					});
				});
				
			}
		}
    }
	
	@Override
	public Long alterar(FaseExecucaoDTO faseExecucaoDTO) throws ExcecaoDeNegocio {
			return faseExecucaoRepositorio.alterar(FaseExecucao.builder()
											.id(faseExecucaoDTO.getId())
											.dataInicio(faseExecucaoDTO.getDataInicio())  
					                        .dataFim(faseExecucaoDTO.getDataFim())
					                        .idPeriodoExecucao(faseExecucaoDTO.getIdPeriodoExecucao())  
					                        .idFase(faseExecucaoDTO.getIdFase()).build());
	}

	@Override
	public FaseExecucaoDTO buscarPorId (long id) {
		return faseExecucaoRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<FaseExecucaoDTO> listar(FiltroFaseExecucao filtroFaseExecucao) throws ExcecaoDeNegocio {
			int total = faseExecucaoRepositorio.contar(filtroFaseExecucao);
			return PaginacaoDTO.<FaseExecucaoDTO>builder().total(total).dados(faseExecucaoRepositorio.listar(filtroFaseExecucao)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return faseExecucaoRepositorio.excluir(id);
	}

	@Override
	public List<FaseResumoDTO> listarParaGeracaoFaseExecucao(FiltroFase filtroFase) {
		return faseRepositorio.listarParaGeracaoFaseExecucao(filtroFase);
	}

	@Override
	public List<FaseExecucaoResumoDTO> listaParaCombo(Long idPeriodoExecucao) {
		return faseExecucaoRepositorio.listaParaCombo(idPeriodoExecucao);
	}


}
