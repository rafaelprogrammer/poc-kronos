package br.com.kronos.backend.aplicacao.matricula;

import java.util.List;

import br.com.kronos.backend.aplicacao.calendario.api.DadosCriaTurmaDTO;
import br.com.kronos.backend.aplicacao.curso.Estrutura;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaDiarioFrequenciaDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaResumoDTO;

public interface TurmaRepositorio {
	
	Long criar(Turma turma);
	Long alterar(Turma turma);
	TurmaDTO buscarPorId(Long id);
	List<TurmaDTO> listar(FiltroTurma filtroTurma);
	boolean excluir(Long id);
	int contar(FiltroTurma filtroTurma);
	List<TurmaResumoDTO> listarParaTurmaContratoCombo(Long idPeriodoExecucao);
	List<Integer> listarAnosParaHorario();
	List<TurmaResumoDTO> listarParaHorario(Long idPeriodoExecucao);
	List<DadosCriaTurmaDTO> listarParaGeracaoDeTurmas(FiltroTurma filtro);
	List<Estrutura> visualizarEstrutura(FiltroTurma filtro);
	List<TurmaResumoDTO> listarParaModulosDosProfessores(Long idPeriodoExecucao, Long idPessoaUsuarioLogado);
	List<Integer> listarAnosFiltroIdCurso(Long idCurso);
	List<TurmaResumoDTO> listarPorIdCursoEAno(Long idCurso, Integer ano);
	List<TurmaResumoDTO> listarParaOcorrenciaCombo(Long idMatriculaSelecionada);
	List<TurmaDiarioFrequenciaDTO> listarParaDiarioFrequencia(Long idInstituicaoUsuarioLogado,
			Long idPessoaUsuarioLogado);
}