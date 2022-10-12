package br.com.kronos.backend.aplicacao.curso.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.curso.FiltroCursoEscala;

public interface CursoEscalaServico {
	
	void criar(List<CursoEscalaDTO> dtos);
	PaginacaoDTO<CursoEscalaDTO> listar(FiltroCursoEscala filtroCursoEscala);
	boolean excluir(long idCurso, long idEscala);

}