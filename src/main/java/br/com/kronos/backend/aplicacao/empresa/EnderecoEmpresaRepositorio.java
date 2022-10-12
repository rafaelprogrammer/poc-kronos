package br.com.kronos.backend.aplicacao.empresa;

import java.util.List;

public interface EnderecoEmpresaRepositorio {
	
	Integer criar(EnderecoEmpresa enderecoEmpresa);
	Integer alterar(EnderecoEmpresa enderecoEmpresa);
	EnderecoEmpresa buscarPorId(Integer id);
	List<EnderecoEmpresa> listar(FiltroEnderecoEmpresa filtroEnderecoEmpresa);
	boolean excluir(Integer id);

}