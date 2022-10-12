package br.com.kronos.backend.aplicacao.arquivo;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoArquivo {
	
	IMAGEM(1, "Imagem"),
	TEXTO(2, "Texto"),
    FORMATO_PORTAVEL(3, "Formato portável");
	
	private int id;
	private String label;

	
	EnumTipoArquivo(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoArquivo> listarTodos() {
		return Arrays.asList(EnumTipoArquivo.values());
	}
	
    public static EnumTipoArquivo porId(int id) {
		return Arrays.asList(EnumTipoArquivo.values()).stream().filter(o -> o.id == id).findAny().get();
	}
    
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}
