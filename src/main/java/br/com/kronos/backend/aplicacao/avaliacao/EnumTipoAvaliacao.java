package br.com.kronos.backend.aplicacao.avaliacao;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoAvaliacao {

    CORRENTE(1, "Corrente"),
    RECUPERACAO(2, "Recuperação"),
    EXAME_FINAL(3, "Exame Final"),
    CONSELHO(4, "Conselho"),
    ATIVIDADE_SALA(5, "Atividade em sala"),
    ATIVIDADE_EXTRA_CLASSE(6, "Atividade extra-classe");
    
	private int id;
	private String label;

	
	EnumTipoAvaliacao(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoAvaliacao> listarTodos() {
		return Arrays.asList(EnumTipoAvaliacao.values());
	}

	public static EnumTipoAvaliacao porId(int id) {
		return Arrays.asList(EnumTipoAvaliacao.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}


