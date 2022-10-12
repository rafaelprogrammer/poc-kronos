package br.com.kronos.backend.aplicacao.pessoa;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoDocumento {
	
	REGISTRO_NASCIMENTO(1, "Registro de Nascimento"),
	IDENTIDADE(2, "Identidade (RG)"),
    CPF(3, "Código de Pessoa Física (CPF)"),
    CERTIFICADO(4, "Certificado"),
    DIPLOMA(5, "Diploma"),
    HISTORICO(6, "Histórico Escolar"),
	TITULO_ELEITOR(7, "Título Eleitor");
	
	private int id;
	private String label;

	
	EnumTipoDocumento(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoDocumento> listarTodos() {
		return Arrays.asList(EnumTipoDocumento.values());
	}
    
    public static EnumTipoDocumento porId(int id) {
		return Arrays.asList(EnumTipoDocumento.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}
