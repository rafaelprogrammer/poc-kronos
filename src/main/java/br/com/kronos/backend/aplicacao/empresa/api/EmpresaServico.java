package br.com.kronos.backend.aplicacao.empresa.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.empresa.FiltroEmpresa;

public interface EmpresaServico {
	
	int criar(EmpresaDTO dto);
	PaginacaoDTO<EmpresaDTO> listar(FiltroEmpresa filtro);
	boolean excluir(int id);

}