package br.com.kronos.backend.aplicacao.conselho;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoFuncaoConselho {
	
    PRESIDENTE(1, "Presidente"),
    VICE_PRESIDENTE(2, "Vice-presidente"),
    MEMBRO(3, "Membro");

    private int id;
	private String label;

	EnumTipoFuncaoConselho(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoFuncaoConselho> listarTodos() {
		return Arrays.asList(EnumTipoFuncaoConselho.values());
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}
