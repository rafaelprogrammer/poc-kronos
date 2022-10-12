package br.com.kronos.backend.aplicacao.pessoa;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoEndereco {
	
    RESIDENCIAL(1, "Residencial"),
    COMERCIAL(2, "Comercial");
    

	private int id;
	private String label;

	
	EnumTipoEndereco(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoEndereco> listarTodos() {
		return Arrays.asList(EnumTipoEndereco.values());
	}
    
    public static EnumTipoEndereco porId(int id) {
		return Arrays.asList(EnumTipoEndereco.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}