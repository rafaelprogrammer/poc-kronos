package br.com.kronos.backend.aplicacao.curso;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoPeriodo {
	
    BIMESTRE(1, "Bimestre"),
    TRIMESTRE(2, "Trimestre"),
    SEMESTRE(3, "Semestre"),
    ANUAL(4, "Anual"),
    MODULO(5, "Módulo");

    private int id;
	private String label;

	EnumTipoPeriodo(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoPeriodo> listarTodos() {
		return Arrays.asList(EnumTipoPeriodo.values());
	}

	public static EnumTipoPeriodo porId(int id) {
		return Arrays.asList(EnumTipoPeriodo.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}


