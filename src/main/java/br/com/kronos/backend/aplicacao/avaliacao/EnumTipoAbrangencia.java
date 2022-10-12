package br.com.kronos.backend.aplicacao.avaliacao;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoAbrangencia {

    SUB_FASE(1, "Sub-fase (Bimestre)"),
    FASE(2, "Fase (Semestre)"),
    PERIODO(3, "Período (Ano)"),
    CONSELHO(4, "Conselho");
    
	private int id;
	private String label;

	
	EnumTipoAbrangencia(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoAbrangencia> listarTodos() {
		return Arrays.asList(EnumTipoAbrangencia.values());
	}

	public static EnumTipoAbrangencia porId(int id) {
		return Arrays.asList(EnumTipoAbrangencia.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}


