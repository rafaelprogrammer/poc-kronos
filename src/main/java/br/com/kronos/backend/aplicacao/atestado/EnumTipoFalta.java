package br.com.kronos.backend.aplicacao.atestado;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoFalta {
	
    FALTA_JUSTIFICADA(1, "Falta justificada", "FJ"),
    ESTUDO_DOMICILIAR(2, "Estudo domiciliar", "ED"),
	TRANSFERIDO(3, "Transferido", "T"),
	CANCELAMENTO(4, "Cancelamento", "C");
	
    private int valor;
	private String label;
	private String sigla;

	EnumTipoFalta(int valor, String label, String sigla) {
		this.label = label;
		this.valor = valor;
		this.sigla = sigla;
    }
    
    public List<EnumTipoFalta> listarTodos() {
		return Arrays.asList(EnumTipoFalta.values());
	}

	public static EnumTipoFalta porValor(int valor) {
		return Arrays.asList(EnumTipoFalta.values()).stream().filter(o -> o.valor == valor).findAny().get();
	}
	
	public int valor() {
		return this.valor;
	}
	
	public String label() {
		return this.label;
	}
	
	public String sigla() {
		return this.sigla;
	}
	
}