package br.com.kronos.backend.aplicacao.calendario;

import java.util.List;

import br.com.kronos.backend.aplicacao.calendario.api.CalendarioDTO;

public interface CalendarioRepositorio {
	
	Long criar(Calendario calendario);
	Long alterar(Calendario calendario);
	CalendarioDTO buscarPorId(Long id);
	List<CalendarioDTO> listar(FiltroCalendario filtroCalendario);
	boolean excluir(Long id);
	int contar(FiltroCalendario filtroCalendario);
	List<Integer> listarNumerosPorAno(Integer ano);
	List<Integer> listarAnos();
	void alterarSituacao(Long id, EnumTipoSituacaoCalendario situacao, Long idFuncionarioElaboracao, Long idFuncionarioAprovacao);
	long recuperarIdPeriodoExecucao(Long idPeriodoExecucao);

}