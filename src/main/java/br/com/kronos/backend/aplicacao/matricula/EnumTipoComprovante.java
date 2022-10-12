package br.com.kronos.backend.aplicacao.matricula;

import java.util.Arrays;
import java.util.List;

import br.com.kronos.backend.aplicacao.disciplina.EnumTipoDisciplina;

public enum EnumTipoComprovante {
	
    CERTIFICADO(1, "Certificado"),
    COMPROVANTE(2, "Diploma");
    
    private int id;
	private String label;

	EnumTipoComprovante(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoComprovante> listarTodos() {
		return Arrays.asList(EnumTipoComprovante.values());
	}
    
    public static EnumTipoComprovante porId(int id) {
		return Arrays.asList(EnumTipoComprovante.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}