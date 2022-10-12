package br.com.kronos.backend.aplicacao.pessoa;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoTitulo {
	
    GRADUACAO(1, "Graduação"),
    POS_GRADUACAO_LATO_SENSU(2, "Pós-graduação Latu-Senso (Especialização)"),
    POS_GRADUACAO_STRICTO_SENSU(3, "Pós-graduação Stricto-Senso (Mestrado)"),
    DOUTORADO(4, "Doutorado"),
    POS_SOUTORADO(5, "Pós-Doutorado");

    private int id;
	private String label;

	EnumTipoTitulo(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoTitulo> listarTodos() {
		return Arrays.asList(EnumTipoTitulo.values());
	}
    
    public static EnumTipoTitulo porId(int id) {
		return Arrays.asList(EnumTipoTitulo.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}
