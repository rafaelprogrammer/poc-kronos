package br.com.kronos.backend.aplicacao.pessoa;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoUso {
	
    PESSOAL(1, "Pessoal"),
    RESIDENCIAL(2, "Residencial"),
    TRABALHO(3, "Trabalho");

    private int id;
	private String label;

	EnumTipoUso(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoUso> listarTodos() {
		return Arrays.asList(EnumTipoUso.values());
	}
    
    public static EnumTipoUso porId(int id) {
		return Arrays.asList(EnumTipoUso.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}