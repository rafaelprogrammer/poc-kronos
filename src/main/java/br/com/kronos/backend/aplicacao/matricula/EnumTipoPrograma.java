package br.com.kronos.backend.aplicacao.matricula;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoPrograma {
	
    NORMAL(1, "Normal"),
    PORTADOR_NECESSIDADES_ESPECIAIS(2, "Portador de necessidaes especiais");
    
    private int id;
	private String label;

	EnumTipoPrograma(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoPrograma> listarTodos() {
		return Arrays.asList(EnumTipoPrograma.values());
	}
    
    public static EnumTipoPrograma porId(int id) {
		return Arrays.asList(EnumTipoPrograma.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}