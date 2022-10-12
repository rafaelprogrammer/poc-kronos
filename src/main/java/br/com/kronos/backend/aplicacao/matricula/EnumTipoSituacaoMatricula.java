package br.com.kronos.backend.aplicacao.matricula;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoSituacaoMatricula {
	
    PRE_MATRICULA(1, "Pré-matrícula"),
    VALIDADA(2, "Validada"),
    CONFIRMADA(3, "Confirmada"),
    TRANCADA(4, "Trancada"),
    TRANSFERIDA(5, "Transferida"),
    CANCELADA(6, "Cancelada"),
    DESISTENCIA(7, "Desistência");
    
    private int id;
	private String label;

	EnumTipoSituacaoMatricula(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoSituacaoMatricula> listarTodos() {
		return Arrays.asList(EnumTipoSituacaoMatricula.values());
	}
    
    public static EnumTipoSituacaoMatricula porId(int id) {
		return Arrays.asList(EnumTipoSituacaoMatricula.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}

