package br.com.kronos.backend.aplicacao.calendario.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.calendario.FiltroPeriodoExecucao;
import br.com.kronos.backend.aplicacao.calendario.PeriodoExecucao;
import br.com.kronos.backend.aplicacao.calendario.PeriodoExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.curso.FiltroPeriodo;
import br.com.kronos.backend.aplicacao.curso.PeriodoRepositorio;
import br.com.kronos.backend.aplicacao.curso.api.EstruturaDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class PeriodoExecucaoServicoImpl implements PeriodoExecucaoServico {

	@NonNull
	private PeriodoExecucaoRepositorio periodoExecucaoRepositorio;
	
	@NonNull
	private PeriodoRepositorio periodoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criar(PeriodoExecucaoDTO periodoExecucaoDTO) throws ExcecaoDeNegocio {
		List<Long> idsPeriodos = periodoRepositorio.retornarIdsPeriodoParaGerarPeriodosDeExecucao(FiltroPeriodo.builder()
																									.idCurso(periodoExecucaoDTO.getIdCurso())
																									.anoPeriodoExecucao(periodoExecucaoDTO.getAno())
																									.build());
		if (idsPeriodos !=null && !idsPeriodos.isEmpty()) {
			idsPeriodos.stream().forEach(idPeriodo -> {
				periodoExecucaoRepositorio.criar(PeriodoExecucao.builder()					             
                        .idCalendario(periodoExecucaoDTO.getIdCalendario())  
                        .idPeriodo(idPeriodo)
                        .ano(periodoExecucaoDTO.getAno()).build());
			});
		}
    }
	
	
	@Override
	public Long alterar(PeriodoExecucaoDTO periodoExecucaoDTO) throws ExcecaoDeNegocio {
			return periodoExecucaoRepositorio.alterar(PeriodoExecucao.builder()
											.id(periodoExecucaoDTO.getId())
											.idCalendario(periodoExecucaoDTO.getIdCalendario())  
					                        .idPeriodo(periodoExecucaoDTO.getIdPeriodo())
					                        .ano(periodoExecucaoDTO.getAno()).build());	
	}

	@Override
	public PeriodoExecucaoDTO buscarPorId (long id) {
		return modelMapper.map(periodoExecucaoRepositorio.buscarPorId(id), PeriodoExecucaoDTO.class);
	}

	@Override
	public PaginacaoDTO<PeriodoExecucaoDTO> listar(FiltroPeriodoExecucao filtroPeriodoExecucao) throws ExcecaoDeNegocio {
			int total = periodoExecucaoRepositorio.contar(filtroPeriodoExecucao);
			List<PeriodoExecucaoDTO> periodosExecucao = modelMapper.map(periodoExecucaoRepositorio.listar(filtroPeriodoExecucao),
					new TypeToken<List<PeriodoExecucaoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<PeriodoExecucaoDTO>builder().total(total).dados(periodosExecucao).build();
	}

	@Override
	public boolean excluir(Long id) {
		return periodoExecucaoRepositorio.excluir(id);
	}


	@Override
	public PaginacaoDTO<PeriodoExecucaoEstruturaAnoLetivoDTO> listarParaEstruturaAnoLetivo(
			FiltroPeriodoExecucao filtroPeriodoExecucao) {
		int total = periodoExecucaoRepositorio.contarParaEstruturaAnoLetivo(filtroPeriodoExecucao);
		List<PeriodoExecucaoEstruturaAnoLetivoDTO> periodos = modelMapper.map(periodoExecucaoRepositorio.listarParaEstruturaAnoLetivo(filtroPeriodoExecucao),
				new TypeToken<List<PeriodoExecucaoEstruturaAnoLetivoDTO>>() {
				}.getType());
		return PaginacaoDTO.<PeriodoExecucaoEstruturaAnoLetivoDTO>builder().total(total).dados(periodos).build();
	}


	@Override
	public PeriodoExecucaoEstruturaAnoLetivoDTO buscarPorIdParaEstruturaAnoLetivo(Long id) {
		return periodoExecucaoRepositorio.buscarPorIdParaEstruturaAnoLetivo(id);
	}
	
	@Override
	public List<EstruturaDTO> visualizarEstrutura(FiltroPeriodoExecucao filtroPeriodoExecucao) {
		return modelMapper.map(periodoExecucaoRepositorio.visualizarEstrutura(filtroPeriodoExecucao),
				new TypeToken<List<EstruturaDTO>>() {
				}.getType());
	}


	@Override
	@Cacheable(cacheNames = PeriodoExecucao.COMBO_CACHE_NAME_PERFIL_PROFESSORES, key="{#idCurso, #idPessoaUsuarioLogado}")
	public List<PeriodoExecucaoResumoDTO> listarParaModulosDosProfessores(Long idCurso, Long idPessoaUsuarioLogado) {
		return periodoExecucaoRepositorio.listarParaModulosDosProfessores(idCurso, idPessoaUsuarioLogado);
	}

}

