package br.com.kronos.backend.aplicacao.disciplina;

import java.util.List;

import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaCreditoDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaResumoDTO;;

public interface DisciplinaRepositorio {
	
	Long criar(Disciplina disciplina);
	Long alterar(Disciplina disciplina);
	DisciplinaDTO buscarPorId(Long id);
	List<DisciplinaDTO> listar(FiltroDisciplina filtroDisciplina);
	List<DisciplinaCreditoDTO> listarParaGeracaoDeCreditos(FiltroDisciplina filtroDisciplina);
	List<DisciplinaResumoDTO> listarParaUsoEmPreRequisito(long idCurso, int numeroPeriodo, long idDisciplina);
	List<Long> buscarPreRequisitosRegistrados(long idDisciplina);
	boolean excluir(Long id);
	int contar(FiltroDisciplina filtroDisciplina);
	void registrarPreRequisito(long idDisciplina, long idDisciplinaPreRequisito);
	boolean excluirPreRequisitos(Long idDisciplina);
	List<DisciplinaCreditoDTO> listarParaGeracaoDeCreditosPendentes(FiltroDisciplina filtroDisciplina);
	List<DisciplinaResumoDTO> listarPorPeriodoExecucao(long idPeriodoExecucao);
	List<DisciplinaResumoDTO> listarParaCombo(FiltroDisciplina filtro);
	List<DisciplinaResumoDTO> listarParaModulosDosProfessores(Long idTurma, Long idPessoaUsuarioLogado);
	List<DisciplinaResumoDTO> listarParaComboUnificacao(FiltroDisciplina filtro);

}