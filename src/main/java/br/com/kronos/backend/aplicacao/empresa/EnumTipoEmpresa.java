package br.com.kronos.backend.aplicacao.empresa;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoEmpresa {
	
    FORNECEDOR(1, "Fornecedor"),
    CONVENIADO(2, "Conveniado"),
    INSTITUICAO_ENSINO(3, "Instituição de Ensino");
    

	private int id;
	private String label;

	
	EnumTipoEmpresa(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoEmpresa> listarTodos() {
		return Arrays.asList(EnumTipoEmpresa.values());
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}
