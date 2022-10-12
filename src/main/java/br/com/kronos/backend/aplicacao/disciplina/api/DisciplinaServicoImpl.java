package br.com.kronos.backend.aplicacao.disciplina.api;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.disciplina.Disciplina;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplina;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DisciplinaServicoImpl implements DisciplinaServico {

	@NonNull
	private DisciplinaRepositorio disciplinaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(DisciplinaDTO disciplinaDTO) throws ExcecaoDeNegocio {

			return disciplinaRepositorio.criar(Disciplina.builder()
					                        .codigo(disciplinaDTO.getCodigo())                                
                                            .nome(disciplinaDTO.getNome())
                                            .sigla(disciplinaDTO.getSigla())
                                            .cargaHorariaTotal(disciplinaDTO.getCargaHorariaTotal())
                                            .cargaHorariaPresencial(disciplinaDTO.getCargaHorariaPresencial())
                                            .cargaHorariaDistancia(disciplinaDTO.getCargaHorariaDistancia())
                                            .ativa(disciplinaDTO.isAtiva())
                                            .obrigatoria(disciplinaDTO.isObrigatoria())
                                            .preRequisitos(disciplinaDTO.isPreRequisitos())
                                            .valor(disciplinaDTO.getValor())
                                            .idTipoDisciplina(disciplinaDTO.getIdTipoDisciplina())
                                            .idTipoRegimeLetivo(disciplinaDTO.getIdTipoRegimeLetivo())
                                            .idComponente(disciplinaDTO.getIdComponente())
                                            .idPeriodo(disciplinaDTO.getIdPeriodo())
                                            .idDisciplinaUnifica(disciplinaDTO.getIdDisciplinaUnificacao())
                                            .dataInicioVigencia(disciplinaDTO.getDataInicioVigencia())
                                            .dataFinalVigencia(disciplinaDTO.getDataFinalVigencia()).build());	

    }
	
	@Override
	public Long alterar(DisciplinaDTO disciplinaDTO) throws ExcecaoDeNegocio {
			return disciplinaRepositorio.alterar(Disciplina.builder()
											.id(disciplinaDTO.getId())
											.codigo(disciplinaDTO.getCodigo())                                
                                            .nome(disciplinaDTO.getNome())
                                            .sigla(disciplinaDTO.getSigla())
                                            .cargaHorariaTotal(disciplinaDTO.getCargaHorariaTotal())
                                            .cargaHorariaPresencial(disciplinaDTO.getCargaHorariaPresencial())
                                            .cargaHorariaDistancia(disciplinaDTO.getCargaHorariaDistancia())
                                            .ativa(disciplinaDTO.isAtiva())
                                            .preRequisitos(disciplinaDTO.isPreRequisitos())
                                            .obrigatoria(disciplinaDTO.isObrigatoria())
                                            .valor(disciplinaDTO.getValor())
                                            .idTipoDisciplina(disciplinaDTO.getIdTipoDisciplina())
                                            .idTipoRegimeLetivo(disciplinaDTO.getIdTipoRegimeLetivo())
                                            .idComponente(disciplinaDTO.getIdComponente())
                                            .idPeriodo(disciplinaDTO.getIdPeriodo())
                                            .idDisciplinaUnifica(disciplinaDTO.getIdDisciplinaUnificacao())
                                            .dataInicioVigencia(disciplinaDTO.getDataInicioVigencia())
                                            .dataFinalVigencia(disciplinaDTO.getDataFinalVigencia()).build());

		
	}

	@Override
	public DisciplinaDTO buscarPorId (long id) {
		return disciplinaRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<DisciplinaDTO> listar(FiltroDisciplina filtroDisciplina) throws ExcecaoDeNegocio {
		int total = disciplinaRepositorio.contar(filtroDisciplina);
		
		return PaginacaoDTO.<DisciplinaDTO>builder().total(total).dados(disciplinaRepositorio.listar(filtroDisciplina)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return disciplinaRepositorio.excluir(id);
	}

	@Override
	public List<DisciplinaResumoDTO> listarParaUsoEmPreRequisito(long idCurso, int numeroPeriodo, long idDisciplina) {
		return disciplinaRepositorio.listarParaUsoEmPreRequisito(idCurso, numeroPeriodo, idDisciplina);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void registrarPreRequisito(DisciplinaPreRequisitoDTO dto) {
		if(dto.getIdsDisciplinasPreRequisitos() != null && dto.getIdsDisciplinasPreRequisitos().length > 0) {
			this.excluirPreRequisitos(dto);
			Arrays.asList(dto.getIdsDisciplinasPreRequisitos()).stream().forEach(idDisciplinaPreRequisito -> {
				disciplinaRepositorio.registrarPreRequisito(dto.getIdDisciplina(), idDisciplinaPreRequisito);
			});
		}
	}

	@Override
	public boolean excluirPreRequisitos(DisciplinaPreRequisitoDTO dto) {
		return disciplinaRepositorio.excluirPreRequisitos(dto.getIdDisciplina());
	}

	@Override
	public List<Long> buscarPreRequisitosRegistrados(long idDisciplina) {
		return disciplinaRepositorio.buscarPreRequisitosRegistrados(idDisciplina);
	}

	@Override
	public List<DisciplinaResumoDTO> listarPorPeriodoExecucao(long idPeriodoExecucao) {
		return disciplinaRepositorio.listarPorPeriodoExecucao(idPeriodoExecucao);
	}

	@Override
	public List<DisciplinaResumoDTO> listarParaCombo(FiltroDisciplina filtro) {
		return disciplinaRepositorio.listarParaCombo(filtro);
	}

	
	@Override
	@Cacheable(cacheNames = Disciplina.COMBO_CACHE_NAME_PERFIL_PROFESSORES, key="{#idTurma, #idPessoaUsuarioLogado}")
	public List<DisciplinaResumoDTO> listarParaModulosDosProfessores(Long idTurma, Long idPessoaUsuarioLogado) {
		return disciplinaRepositorio.listarParaModulosDosProfessores(idTurma, idPessoaUsuarioLogado);
	}

	@Override
	@Cacheable(cacheNames = Disciplina.COMBO_CACHE_NAME_DISCIPLINA_UNIFICA, key="#filtro.hashCode()")
	public List<DisciplinaResumoDTO> listarParaComboUnificacao(FiltroDisciplina filtro) {
		return disciplinaRepositorio.listarParaComboUnificacao(filtro);
	}

}


