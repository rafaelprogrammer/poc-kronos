package br.com.kronos.backend.aplicacao.pessoa;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoGenero {
	
	MASCULINO(1, "Masculino", "M"),
	FEMININO(2, "Feminino", "F");
	
	private int id;
	private String label;
	private String sigla;
	
	EnumTipoGenero(int id, String label, String sigla) {
		this.label = label;
		this.id = id;
		this.sigla = sigla;
	}

	public List<EnumTipoGenero> listarTodos() {
		return Arrays.asList(EnumTipoGenero.values());
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
	public String sigla() {
		return this.sigla;
	}

}
