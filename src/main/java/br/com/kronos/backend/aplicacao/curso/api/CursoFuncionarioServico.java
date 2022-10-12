package br.com.kronos.backend.aplicacao.curso.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.curso.FiltroCursoFuncionario;

public interface CursoFuncionarioServico {
	
	Long criar(CursoFuncionarioDTO dto);
	PaginacaoDTO<CursoFuncionarioDTO>listar(FiltroCursoFuncionario filtro);
	boolean excluir(long id);
	CursoFuncionarioDTO buscarPorId(long id);
	Long alterar(CursoFuncionarioDTO dto);

}