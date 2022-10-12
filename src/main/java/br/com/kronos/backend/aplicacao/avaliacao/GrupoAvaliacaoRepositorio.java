package br.com.kronos.backend.aplicacao.avaliacao;

import java.util.List;

import br.com.kronos.backend.aplicacao.avaliacao.FiltroGrupoAvaliacao;
import br.com.kronos.backend.aplicacao.avaliacao.GrupoAvaliacao;
import br.com.kronos.backend.aplicacao.avaliacao.api.GrupoAvaliacaoDTO;

public interface GrupoAvaliacaoRepositorio {
	
	Long criar(GrupoAvaliacao grupoAvaliacao);
	Long alterar(GrupoAvaliacao grupoAvaliacao);
	GrupoAvaliacaoDTO buscarPorId(Long id);
	List<GrupoAvaliacaoDTO> listar(FiltroGrupoAvaliacao filtroGrupoAvaliacao);
	boolean excluir(Long id);
	int contar(FiltroGrupoAvaliacao filtroGrupoAvaliacao);
	boolean excluirPorAvaliacao(Long idAvaliacao);

}