package br.com.kronos.backend.aplicacao.funcionario;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.MesDTO;
import br.com.kronos.backend.aplicacao.funcionario.api.AusenteDTO;
import br.com.kronos.backend.aplicacao.funcionario.api.PontoDTO;
import br.com.kronos.backend.aplicacao.funcionario.api.TipoPeriodoPontoDTO;

public interface PontoRepositorio {
	
	Long criar(Ponto ponto);
	Long alterar(Ponto pont);
	List<PontoDTO> listar(FiltroPonto filtro);
	int contar(FiltroPonto filtro);
	List<TipoPeriodoPontoDTO> listarTiposPeriodos(FiltroPonto filtro);
	PontoDTO recuperarHorasPrevistas(FiltroPonto filtro);
	PontoDTO buscarPorId(Long id);
	int contarParaFolhaDePonto(FiltroPonto filtro);
	List<PontoDTO> listarParaFolhaDePonto(FiltroPonto filtro);
	List<Integer> listarAnos(Long idFuncionario);
	List<MesDTO> listarMeses(Long idFuncionario, Integer ano);
	Long alterarDadosLiberacao(PontoDTO ponto);
	Long alterarDadosHomologacao(PontoDTO ponto);
	List<AusenteDTO> listarAusentes(FiltroPonto filtro);

}