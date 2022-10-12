package br.com.kronos.backend.aplicacao.frequencia;

import java.util.Arrays;

public enum EnumTipoFrequencia {

	P("Presença"),
	F("Falta"),
	FJ("Falta Justificada"),
	I("Indefinido"),
	AD("Atividade Domiciliar"),
	C("Cancelamento"),
	T("Transferência");
	
	private String label;
	
	EnumTipoFrequencia(String label) {
		this.label = label;
    }
	
	public static EnumTipoFrequencia tipo(String tipo) {
		return Arrays.asList(EnumTipoFrequencia.values()).stream().filter(e -> e.name().equals(tipo)).findAny().orElse(null);
	}
	
	
	public String label() {
		return this.label;
	}
	
}
