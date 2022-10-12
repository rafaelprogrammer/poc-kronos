package br.com.kronos.backend.aplicacao.calendario;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoSituacaoCalendario {

    EM_ELABORACAO(1, "Em elaboração"),
    CONCLUIDO(2, "Concluído"),
    APROVADO(3, "Aprovado");
    
	private int id;
	private String label;

	
	EnumTipoSituacaoCalendario(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoSituacaoCalendario> listarTodos() {
		return Arrays.asList(EnumTipoSituacaoCalendario.values());
	}

	public static EnumTipoSituacaoCalendario porId(int id) {
		return Arrays.asList(EnumTipoSituacaoCalendario.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}
