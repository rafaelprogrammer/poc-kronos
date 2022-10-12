package br.com.kronos.backend.aplicacao.arquivo;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoConteudo {
	
	FOTO(1, "Foto"),
	LOGOTIPO(2, "Logotipo"),
    DOCUMENTO(3, "Documento");
	
	private int id;
	private String label;

	
	EnumTipoConteudo(int id, String label) {
		this.label = label;
		this.id = id;
	}

	public List<EnumTipoConteudo> listarTodos() {
		return Arrays.asList(EnumTipoConteudo.values());
	}
	
	public static EnumTipoConteudo porId(int id) {
		return Arrays.asList(EnumTipoConteudo.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}
