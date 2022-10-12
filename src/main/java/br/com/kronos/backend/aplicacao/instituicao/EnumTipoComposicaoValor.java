package br.com.kronos.backend.aplicacao.instituicao;

import java.util.Arrays;
import java.util.List;

import br.com.kronos.backend.aplicacao.instituicao.EnumTipoComposicaoValor;

public enum EnumTipoComposicaoValor {
	
    POR_PERIODO(1, "Por período"),
    POR_DISCIPLINA(2, "Por disciplina");
    

	private int id;
	private String label;

	
	EnumTipoComposicaoValor(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoComposicaoValor> listarTodos() {
		return Arrays.asList(EnumTipoComposicaoValor.values());
	}
    
    public static EnumTipoComposicaoValor porId(int id) {
		return Arrays.asList(EnumTipoComposicaoValor.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}
