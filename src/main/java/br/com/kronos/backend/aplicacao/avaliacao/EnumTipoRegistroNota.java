package br.com.kronos.backend.aplicacao.avaliacao;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoRegistroNota {
	

    MEDIA_GLOBAL(1, "Média global"),
    POR_HABILIDADE(2, "Por habilidades");
    

	private int id;
	private String label;

	
	EnumTipoRegistroNota(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoRegistroNota> listarTodos() {
		return Arrays.asList(EnumTipoRegistroNota.values());
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}


