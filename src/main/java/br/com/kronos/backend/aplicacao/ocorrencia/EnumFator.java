package br.com.kronos.backend.aplicacao.ocorrencia;

import java.util.Arrays;
import java.util.List;

public enum EnumFator {
	
    POSITIVO(1, "P"),
    NEGATIVO(-1, "N");

    private int valor;
	private String label;

	EnumFator(int valor, String label) {
		this.label = label;
		this.valor = valor;
    }
    
    public List<EnumFator> listarTodos() {
		return Arrays.asList(EnumFator.values());
	}

	public static EnumFator porValor(int valor) {
		return Arrays.asList(EnumFator.values()).stream().filter(o -> o.valor == valor).findAny().get();
	}
	
	public int valor() {
		return this.valor;
	}
	
	public String label() {
		return this.label;
	}
	
}