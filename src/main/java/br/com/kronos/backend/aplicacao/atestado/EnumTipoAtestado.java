package br.com.kronos.backend.aplicacao.atestado;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoAtestado {
	
    ATESTADO_MEDICO(1, "Atestado médico"),
    TRANSFERIDO(2, "Transferido"),
    PANDEMIA_COVID_19(3, "Pandemia COVID-19"),
    CANCELAMENTO(4, "Cancelamento");
	
    private int id;
	private String label;

	EnumTipoAtestado(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoAtestado> listarTodos() {
		return Arrays.asList(EnumTipoAtestado.values());
	}

	public static EnumTipoAtestado porId(int id) {
		return Arrays.asList(EnumTipoAtestado.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}