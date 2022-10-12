package br.com.kronos.backend.aplicacao.matricula;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoResultado {
	
	APROVADO(1, "Aprovado", "AP"),
    REPROVADO(2, "Reprovado", "R"),
    REPROVALDO_POR_FALTA(3, "Reprovado por falta", "RF");
	
	private int id;
	private String label;
	private String sigla;
	
	EnumTipoResultado(int id, String label, String sigla) {
        this.id = id;
        this.label = label;
		this.sigla = sigla;
	}

	public List<EnumTipoResultado> listarTodos() {
		return Arrays.asList(EnumTipoResultado.values());
	}
	
	public static EnumTipoResultado porId(Integer id) {
		    if(id == null || id <= 0) {
		    	return null;
		    }
			return Arrays.asList(EnumTipoResultado.values()).stream().filter(o -> o.id == id).findAny().get();
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
