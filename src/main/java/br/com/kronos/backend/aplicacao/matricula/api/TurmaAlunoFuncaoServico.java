package br.com.kronos.backend.aplicacao.matricula.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.matricula.FiltroTurmaAlunoFuncao;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaAlunoFuncaoDTO;


public interface TurmaAlunoFuncaoServico {
	
	Long criar(TurmaAlunoFuncaoDTO turmaAlunoFuncaoDTO) throws ExcecaoDeNegocio;
	Long alterar(TurmaAlunoFuncaoDTO turmaAlunoFuncaoDTO)throws ExcecaoDeNegocio;
	TurmaAlunoFuncaoDTO buscarPorId(long id);
	PaginacaoDTO<TurmaAlunoFuncaoDTO>listar(FiltroTurmaAlunoFuncao filtroTurmaAlunoFuncao) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	
}
