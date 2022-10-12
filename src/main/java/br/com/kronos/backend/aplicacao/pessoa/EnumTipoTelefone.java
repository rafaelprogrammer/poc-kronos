package br.com.kronos.backend.aplicacao.pessoa;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoTelefone {
	
    CELULAR(1, "Celular"),
    FIXO(2, "Fixo"),
    RADIO(3, "Rádio");
    

	private int id;
	private String label;

	
	EnumTipoTelefone(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoTelefone> listarTodos() {
		return Arrays.asList(EnumTipoTelefone.values());
	}
    
    public static EnumTipoTelefone porId(int id) {
		return Arrays.asList(EnumTipoTelefone.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}
