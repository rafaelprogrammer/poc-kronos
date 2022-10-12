package br.com.kronos.backend.aplicacao.matricula;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoSala {
	
    ADMINISTRACAO(1, "Administração"),
    SALA_AULA(2, "Sala de aula"),
    LABORATORIO_INFORMATICA(3, "Laboratório de Informática"),
    BIBLIOTECA(4, "Biblioteca"),
    REFEITORIO(5, "Refeitório");

    private int id;
	private String label;

	
	EnumTipoSala(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoSala> listarTodos() {
		return Arrays.asList(EnumTipoSala.values());
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}