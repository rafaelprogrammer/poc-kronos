package br.com.kronos.backend.aplicacao.funcionario.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.MesDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.funcionario.FiltroPonto;


public interface PontoServico {
	
	Long criar(PontoDTO ponto);
	Long alterar(PontoDTO ponto);
	PaginacaoDTO<PontoDTO> listar(FiltroPonto filtro);
	List<TipoPeriodoPontoDTO> listarTiposPeriodos(FiltroPonto filtro);
	PontoDTO recuperarHorasPrevistas(FiltroPonto filtro);
	PontoDTO buscarPorId(Long id, Long idFuncionario);
	Long criarTarefaOnline(PontoDTO dto);
	PaginacaoDTO<PontoDTO> listarParaFolhaDePonto(FiltroPonto filtro);
	List<Integer> listarAnos(Long idFuncionario);
	List<MesDTO> listarMeses(Long idFuncionario, Integer ano);
	void liberar(PontoDTO dto);
	void cancelarLiberacao(PontoDTO dto);
	void homologar(PontoDTO dto);
	void cancelarHomologacao(PontoDTO dto);
	List<AusenteDTO> listarAusentes(FiltroPonto filtro);
	void gerarAusentes(List<AusenteDTO> dtos);
}
