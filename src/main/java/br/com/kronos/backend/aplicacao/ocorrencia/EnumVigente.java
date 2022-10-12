package br.com.kronos.backend.aplicacao.ocorrencia;

public enum EnumVigente {
	
    TODOS("Todos"),
    SIM("Sim"),
    NAO("Não");

	private String label;

	EnumVigente(String label) {
		this.label = label;
    }
	
	public String label() {
		return this.label;
	}
	
}