package br.com.kronos.backend.aplicacao.avaliacao;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoFormato {
	
    QUESTIONARIO(1, "Questionário"),
    TAREFA(2, "Tarefa"),
    ATIVIDADE_PESQUISA(3, "Atividade de pesquisa"),
    ATIVIDADE_PRATICA(4, "Atividade prática");
    

	private int id;
	private String label;

	
	EnumTipoFormato(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoFormato> listarTodos() {
		return Arrays.asList(EnumTipoFormato.values());
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}


