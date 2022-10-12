package br.com.kronos.backend.aplicacao.basecurricular.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroDireito;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;

public interface DireitoServico {
	
	Long criar(DireitoDTO direitoDTO) throws ExcecaoDeNegocio;
	Long alterar(DireitoDTO direitoDTO)throws ExcecaoDeNegocio;
	DireitoDTO buscarPorId(long id);
	PaginacaoDTO<DireitoDTO>listar(FiltroDireito filtroDireito) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	PaginacaoDTO<DireitoDTO> listarParaDisciplinaDireito(FiltroDireito filtroDireito);
}
