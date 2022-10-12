package br.com.kronos.backend.aplicacao.resultado;

import java.util.List;

import br.com.kronos.backend.aplicacao.resultado.FiltroAvaliado;
import br.com.kronos.backend.aplicacao.resultado.api.AvaliadoDTO;

public interface AvaliadoRepositorio {
	
	Long criar(Avaliado avaliado);
	Long alterar(Avaliado avaliado);
	Avaliado buscarPorId(Long id);
	List<Avaliado> listar(FiltroAvaliado filtroAvaliado);
	boolean excluir(Long id);
	int contar(FiltroAvaliado filtroAvaliado);
	List<AvaliadoDTO> listarParaSelecaoNaAvaliacao(FiltroAvaliado filtroAvaliado);
	int contarParaSelecaoNaAvaliacao(FiltroAvaliado filtroAvaliado);
	boolean excluirPorAvaliacao(Long idAvaliacao);
	List<AvaliadoDTO> listarResultadoHabiliadeDoAvaliado(FiltroAvaliado filtroAvaliado);
	int contarResultadoHabiliadeDoAvaliado(FiltroAvaliado filtroAvaliado);
	void criarAvaliadoAutomatico(Long idAtividadeSelecionada);
}