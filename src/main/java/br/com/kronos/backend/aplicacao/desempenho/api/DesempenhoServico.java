package br.com.kronos.backend.aplicacao.desempenho.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.desempenho.FiltroDesempenho;
import br.com.kronos.backend.aplicacao.desempenho.FiltroDesempenhoAtitude;
import br.com.kronos.backend.aplicacao.desempenho.FiltroDesempenhoAvaliacao;

public interface DesempenhoServico {
	
	Long criar(DesempenhoDTO desempenhoDTO);
	Long alterar(DesempenhoDTO desempenhoDTO);
	DesempenhoDTO buscarPorId(long id);
	PaginacaoDTO<DesempenhoDTO> listar(FiltroDesempenho filtroDesempenho);
	boolean excluir(long id);
	
	void atribuirAtitudeDesempenho(DesempenhoAtitudeDTO dto);
	void excluirAtitudeDesempenho(DesempenhoAtitudeDTO dto);
	boolean verificarVinculoDesempenhoAtitude(FiltroDesempenhoAtitude filtro);
	
	void atribuirAvaliacaoDesempenho(DesempenhoAvaliacaoDTO dto);
	void excluirAvaliacaoDesempenho(DesempenhoAvaliacaoDTO dto);
	boolean verificarVinculoDesempenhoAvaliacao(FiltroDesempenhoAvaliacao filtro);
}
