package br.com.kronos.backend.aplicacao.basecurricular;

import java.util.Arrays;
import java.util.List;

public enum EnumNivel {
	
    EDUCACAO_INFANTIL(1, "Educação Infantil"),
    ENSINO_FUNDAMENTAL(2, "Ensino Fundamental"),
    ENSINO_MEDIO(3, "Ensino Médio");
	
    private int id;
	private String label;

	EnumNivel(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumNivel> listarTodos() {
		return Arrays.asList(EnumNivel.values());
	}

	public static EnumNivel porId(int id) {
		return Arrays.asList(EnumNivel.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}