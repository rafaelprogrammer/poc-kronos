package br.com.kronos.backend.aplicacao.avaliacao.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.avaliacao.FiltroGrupoAvaliacao;


public interface GrupoAvaliacaoServico {
	
	Long criar(GrupoAvaliacaoDTO grupoAvaliacaoDTO);
	Long alterar(GrupoAvaliacaoDTO grupoAvaliacaoDTO);
	GrupoAvaliacaoDTO buscarPorId(long id);
	PaginacaoDTO<GrupoAvaliacaoDTO>listar(FiltroGrupoAvaliacao filtroGrupoAvaliacao);
	boolean excluir(Long id);
	List<GrupoAvaliacaoDTO>listarParaCombo(FiltroGrupoAvaliacao filtroGrupoAvaliacao);
	boolean verificarSeExisteGrupoAvaliacaoCadastrado(FiltroGrupoAvaliacao filtroGrupoAvaliacao);
	
}

