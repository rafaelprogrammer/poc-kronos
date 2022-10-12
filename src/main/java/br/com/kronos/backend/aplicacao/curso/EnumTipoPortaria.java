package br.com.kronos.backend.aplicacao.curso;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoPortaria {
	
    AUTORIZACAO(1, "Autorização"),
    RENOVACAO(2, "Renovação"),
    RECONHECIMENTO(3, "Reconhecimento");

    private int id;
	private String label;

	EnumTipoPortaria(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoPortaria> listarTodos() {
		return Arrays.asList(EnumTipoPortaria.values());
	}

	public static EnumTipoPortaria porId(int id) {
		return Arrays.asList(EnumTipoPortaria.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}