package br.com.kronos.backend.aplicacao.empresa;

import java.util.List;

public interface TelefoneEmpresaRepositorio {
	
	Long criar(TelefoneEmpresa telefoneEmpresa);
	Long alterar(TelefoneEmpresa telefoneEmpresa);
	TelefoneEmpresa buscarPorId(Long id);
	List<TelefoneEmpresa> listar(FiltroTelefoneEmpresa filtroTelefoneEmpresa);
	boolean excluir(Long id);

}