package br.com.kronos.backend.aplicacao.empresa;

import java.util.List;

public interface EmpresaRepositorio {
	
	int criar(Empresa empresa);
	List<Empresa> listar(FiltroEmpresa filtroEmpresa);
	boolean excluir(int id);
	int contar(FiltroEmpresa filtro);

}