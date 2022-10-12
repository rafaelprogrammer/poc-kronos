package br.com.kronos.backend.aplicacao.disciplina;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoDisciplina {
	
    TEORICA(1, "Teórica"),
    PRATICA(2, "Prática");

    private int id;
	private String label;

	EnumTipoDisciplina(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoDisciplina> listarTodos() {
		return Arrays.asList(EnumTipoDisciplina.values());
	}

	public static EnumTipoDisciplina porId(int id) {
		return Arrays.asList(EnumTipoDisciplina.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}
