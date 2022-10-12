package br.com.kronos.backend.aplicacao.disciplina.api;


import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaPreRequisito;

public interface DisciplinaPreRequisitoServico {
	
	void criar(List<DisciplinaPreRequisitoDTO> dtos);
	PaginacaoDTO<DisciplinaPreRequisitoDTO> listar(FiltroDisciplinaPreRequisito filtroDisciplinaPreRequisito);
	boolean excluir(long idDisciplina, long idDisciplinaPreRequisito);

}