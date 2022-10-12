package br.com.kronos.backend.aplicacao.disciplina.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaFuncionario;

public interface DisciplinaFuncionarioServico {
	
	void criar(List<DisciplinaFuncionarioDTO> dtos);
	PaginacaoDTO<DisciplinaFuncionarioDTO> listar(FiltroDisciplinaFuncionario filtroDisciplinaFuncionario);
	boolean excluir(long idDisciplina, long idDisciplinaFuncionario);

}