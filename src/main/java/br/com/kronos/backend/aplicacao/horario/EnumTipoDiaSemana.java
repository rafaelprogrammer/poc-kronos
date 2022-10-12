package br.com.kronos.backend.aplicacao.horario;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoDiaSemana {
	
    SEGUNDA(1, "Segunda-feira", "Seg"),
    TERCA(2, "Terça-feira", "Ter"),
    QUARTA(3, "Quarta-feira", "Qua"),
    QUINTA(4, "Quinta-feira", "Qui"),
    SEXTA(5, "Sexta-feira", "Sex"),
    SABADO(6, "Sábado", "Sab"),
	DOMINGO(7, "Domingo", "Dom");
	
	private int id;
	private String label;
	private String sigla;
	
	EnumTipoDiaSemana(int id, String label, String sigla) {
        this.id = id;
        this.label = label;
		this.sigla = sigla;
	}

	public List<EnumTipoDiaSemana> listarTodos() {
		return Arrays.asList(EnumTipoDiaSemana.values());
	}
	
	public static EnumTipoDiaSemana porId(int id) {
		return Arrays.asList(EnumTipoDiaSemana.values()).stream().filter(o -> o.id == id).findAny().get();
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
