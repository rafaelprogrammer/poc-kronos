package br.com.kronos.backend.aplicacao.funcionario;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoPeriodoPonto {
    
   
	TAREFA_ONLINE(0, "Tarefa online"),
	PRIMEIRO_PERIODO(1, "1º periodo"),
	SEGUNDO_PERIODO(2, "2º periodo"),
	TERCEIRO_PERIODO(3, "3º periodo");
   
	private int id;
	private String label;
	
	EnumTipoPeriodoPonto(int id, String label) {
		this.label = label;
		this.id = id;
	}

	public List<EnumTipoPeriodoPonto> listarTodos() {
		return Arrays.asList(EnumTipoPeriodoPonto.values());
	}

	public static EnumTipoPeriodoPonto porId(int id) {
		return Arrays.asList(EnumTipoPeriodoPonto.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}


