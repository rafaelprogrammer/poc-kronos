package br.com.kronos.backend.aplicacao.conselho;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoFinalidadeConselho {
	
    CLASSE(1, "Conselho de classe"),
    FICHA_INDIVIDUAL(2, "Conselho análise ficha individual");

    private int id;
	private String label;

	EnumTipoFinalidadeConselho(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoFinalidadeConselho> listarTodos() {
		return Arrays.asList(EnumTipoFinalidadeConselho.values());
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}
