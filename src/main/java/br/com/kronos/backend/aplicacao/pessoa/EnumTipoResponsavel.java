package br.com.kronos.backend.aplicacao.pessoa;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoResponsavel {
	
    FINANCEIRO(1, "Financeiro"),
    ALTERAPEDAGOGICO(2, "Pedagógico"),
    TRANSPPORTE(3, "Transporte");
    

	private int id;
	private String label;

	
	EnumTipoResponsavel(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoResponsavel> listarTodos() {
		return Arrays.asList(EnumTipoResponsavel.values());
	}
    
    public static EnumTipoResponsavel porId(int id) {
		return Arrays.asList(EnumTipoResponsavel.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}