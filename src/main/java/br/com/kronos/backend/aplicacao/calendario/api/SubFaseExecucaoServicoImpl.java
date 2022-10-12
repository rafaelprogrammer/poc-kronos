package br.com.kronos.backend.aplicacao.calendario.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.calendario.FaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.FaseExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.FiltroSubFaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.SubFaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.SubFaseExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.curso.FiltroFase;
import br.com.kronos.backend.aplicacao.curso.SubFaseRepositorio;
import br.com.kronos.backend.aplicacao.curso.api.SubFaseResumoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class SubFaseExecucaoServicoImpl implements SubFaseExecucaoServico {

	@NonNull
	private SubFaseExecucaoRepositorio subFaseExecucaoRepositorio;

	@NonNull
	private SubFaseRepositorio subFaseRepositorio;
	
	@NonNull
	private FaseExecucaoRepositorio faseExecucaoRepositorio;
	
	@NonNull
	private ModelMapper modelMapper;
	
	@Override
	public void criar(List<SubFaseExecucaoDTO> dtos) {
		if (dtos != null && !dtos.isEmpty()) {
			Long idCurso = dtos.get(0).getIdCurso();
			Integer ano = dtos.get(0).getAnoPeriodoExecucao();
			List<DadosCriaSubFaseExecucaoDTO> dadosCriacao = subFaseExecucaoRepositorio.listarParaCriar(FiltroSubFaseExecucao.builder()
																							.idCurso(idCurso)
																							.anoPeriodoExecucao(ano)
																							.build());
			if (dadosCriacao != null && !dadosCriacao.isEmpty()) {
				dtos.stream().forEach(dto -> {
					dadosCriacao.stream().forEach(dado -> {
						if (dado.getSiglaSubFase().equals(dto.getSiglaSubFase())) {
							FaseExecucao faseExecucao = faseExecucaoRepositorio.buscarPorIdFaseEAno(dado.getIdFase(), ano);
							if (faseExecucao != null) {
								subFaseExecucaoRepositorio.criar(SubFaseExecucao.builder()
			                        .dataInicio(dto.getDataInicio())
			                        .dataFim(dto.getDataFim())
			                        .idFaseExecucao(faseExecucao.getId())
			                        .idSubFase(dado.getIdSubFase()).build());
							}
						}
					});	
				});
				
			}
		}
    }

	@Override
	public Long alterar(SubFaseExecucaoDTO subFaseExecucaoDTO) throws ExcecaoDeNegocio {
		return subFaseExecucaoRepositorio.alterar(SubFaseExecucao.builder()
										.id(subFaseExecucaoDTO.getId())
										.dataInicio(subFaseExecucaoDTO.getDataInicio())  
				                        .dataFim(subFaseExecucaoDTO.getDataFim())  
				                        .idFaseExecucao(subFaseExecucaoDTO.getIdFaseExecucao())  
				                        .idSubFase(subFaseExecucaoDTO.getIdSubFase()).build());	
	}

	@Override
	public SubFaseExecucaoDTO buscarPorId (long id) {
		return subFaseExecucaoRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<SubFaseExecucaoDTO> listar(FiltroSubFaseExecucao filtroSubFaseExecucao) throws ExcecaoDeNegocio {
		int total = subFaseExecucaoRepositorio.contar(filtroSubFaseExecucao);
		return PaginacaoDTO.<SubFaseExecucaoDTO>builder().total(total).dados(subFaseExecucaoRepositorio.listar(filtroSubFaseExecucao)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return subFaseExecucaoRepositorio.excluir(id);
	}

	@Override
	public List<SubFaseResumoDTO> listarParaGeracaoSubFaseExecucao(FiltroFase filtroFase) {
		return subFaseRepositorio.listarParaGeracaoSubFaseExecucao(filtroFase);
	}

	@Override
	@Cacheable(cacheNames = SubFaseExecucao.COMBO_CACHE_NAME_PERFIL_PROFESSORES, key="{#idTurma, #idDisciplina, #idPessoaUsuarioLogado}")
	public List<SubFaseExecucaoDTO> listarParaDiarioEFrequencia(Long idTurma, Long idDisciplina, Long idPessoaUsuarioLogado) {
		return subFaseExecucaoRepositorio.listarParaDiarioEFrequencia(idTurma, idDisciplina, idPessoaUsuarioLogado);
	}

}
