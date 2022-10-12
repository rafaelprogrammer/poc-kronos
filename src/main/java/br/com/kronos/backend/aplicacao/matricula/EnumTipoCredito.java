package br.com.kronos.backend.aplicacao.matricula;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoCredito {
	
    NORMAL(1, "Normal"),
    DEPENDENCIA(2, "Dependência"),
    COMPLEMENTAR(3, "Complementar"),
    ADAPTACAO(4, "Adaptação");
    
    private int id;
	private String label;

	EnumTipoCredito(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoCredito> listarTodos() {
		return Arrays.asList(EnumTipoCredito.values());
	}
    
    public static EnumTipoCredito porId(int id) {
		return Arrays.asList(EnumTipoCredito.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}