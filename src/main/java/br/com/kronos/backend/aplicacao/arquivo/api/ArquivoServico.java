package br.com.kronos.backend.aplicacao.arquivo.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.arquivo.FiltroArquivo;

public interface ArquivoServico {
	
	int criar (ArquivoDTO dto);
	boolean excluir (int id);
	ArquivoDTO buscarPorId(int id);
	PaginacaoDTO<ArquivoDTO> listar(FiltroArquivo filtro);
	ArquivoDTO buscarFotoPessoa(Long idPessoa);

}
