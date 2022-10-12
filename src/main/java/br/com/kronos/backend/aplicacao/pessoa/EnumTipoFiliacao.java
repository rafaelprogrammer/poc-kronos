package br.com.kronos.backend.aplicacao.pessoa;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoFiliacao {
	
	PAI(1, "Pai"),
    MAE(2, "Mãe");
    

	private int id;
	private String label;

	
	EnumTipoFiliacao(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoFiliacao> listarTodos() {
		return Arrays.asList(EnumTipoFiliacao.values());
	}
    
    public static EnumTipoFiliacao porId(int id) {
		return Arrays.asList(EnumTipoFiliacao.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}
