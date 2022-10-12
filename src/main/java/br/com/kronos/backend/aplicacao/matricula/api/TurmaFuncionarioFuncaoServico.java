package br.com.kronos.backend.aplicacao.matricula.api;


import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.matricula.FiltroTurmaFuncionarioFuncao;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaFuncionarioFuncaoDTO;


public interface TurmaFuncionarioFuncaoServico {
	
	Long criar(TurmaFuncionarioFuncaoDTO turmaFuncionarioFuncaoDTO) throws ExcecaoDeNegocio;
	Long alterar(TurmaFuncionarioFuncaoDTO turmaFuncionarioFuncaoDTO)throws ExcecaoDeNegocio;
	TurmaFuncionarioFuncaoDTO buscarPorId(long id);
	PaginacaoDTO<TurmaFuncionarioFuncaoDTO>listar(FiltroTurmaFuncionarioFuncao filtroTurmaFuncionarioFuncao) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	
}
