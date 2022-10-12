package br.com.kronos.backend.aplicacao.matricula.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.curso.api.EstruturaDTO;
import br.com.kronos.backend.aplicacao.matricula.FiltroTurma;


public interface TurmaServico {
	
	void gerar(TurmaDTO turmaDTO);
	Long criar(TurmaDTO turmaDTO);
	Long alterar(TurmaDTO turmaDTO);
	TurmaDTO buscarPorId(long id);
	PaginacaoDTO<TurmaDTO>listar(FiltroTurma filtroTurma);
	boolean excluir(Long id);
	List<TurmaResumoDTO> listarParaTurmaContratoCombo(Long idPeriodoExecucao);
	List<Integer> listarAnosParaHorario();
	List<TurmaResumoDTO> listarParaHorario(Long idPeriodoExecucao);
	List<EstruturaDTO> visualizarEstrutura(FiltroTurma filtro);
	List<TurmaResumoDTO> listarParaModulosDosProfessores(Long idPeriodoExecucao, Long idPessoaUsuarioLogado);
	List<Integer> listarAnosFiltroIdCurso(Long idCurso);
	List<TurmaResumoDTO> listarPorIdCursoEAno(Long idCurso, Integer ano);
	List<TurmaResumoDTO> listarParaOcorrenciaCombo(Long idMatriculaSelecionada);
	List<TurmaDiarioFrequenciaDTO> listarParaDiarioFrequencia(Long idInstituicaoUsuarioLogado,
			Long idPessoaUsuarioLogado);
	
}