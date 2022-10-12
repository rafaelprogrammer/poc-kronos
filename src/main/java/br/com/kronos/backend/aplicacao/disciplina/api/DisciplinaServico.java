package br.com.kronos.backend.aplicacao.disciplina.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplina;


public interface DisciplinaServico {
	
	Long criar(DisciplinaDTO disciplinaDTO);
	Long alterar(DisciplinaDTO disciplinaDTO);
	DisciplinaDTO buscarPorId(long id);
	PaginacaoDTO<DisciplinaDTO>listar(FiltroDisciplina filtroDisciplina);
	List<DisciplinaResumoDTO> listarParaUsoEmPreRequisito(long idCurso, int numeroPeriodo, long idDisciplina);
	List<Long> buscarPreRequisitosRegistrados(long idDisciplina);
	boolean excluir(Long id);
	void registrarPreRequisito(DisciplinaPreRequisitoDTO dto);
	boolean excluirPreRequisitos(DisciplinaPreRequisitoDTO dto);
	List<DisciplinaResumoDTO> listarPorPeriodoExecucao(long idPeriodoExecucao);
	List<DisciplinaResumoDTO> listarParaCombo(FiltroDisciplina filtro);
	List<DisciplinaResumoDTO> listarParaModulosDosProfessores(Long idTurma, Long idPessoaUsuarioLogado);
	List<DisciplinaResumoDTO> listarParaComboUnificacao(FiltroDisciplina filtro);
	
}