package br.com.kronos.backend.aplicacao.disciplina.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaDireito;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;


public interface DisciplinaDireitoServico {
	
	void criar(DisciplinaDireitoDTO diarioDTO);
	void alterar(DisciplinaDireitoDTO diarioDTO);
	DisciplinaDireitoDTO buscarPorId(long id);
	PaginacaoDTO<DisciplinaDireitoDTO>listar(FiltroDisciplinaDireito filtroDisciplinaDireito) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	PaginacaoDTO<DisciplinaDireitoDTO> listarParaAtividadeDisciplinaDireito(FiltroDisciplinaDireito filtroDisciplinaDireito);
	
}
