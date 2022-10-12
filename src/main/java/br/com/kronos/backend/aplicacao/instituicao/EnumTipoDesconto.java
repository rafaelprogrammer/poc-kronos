package br.com.kronos.backend.aplicacao.instituicao;

import java.util.Arrays;
import java.util.List;

import br.com.kronos.backend.aplicacao.instituicao.EnumTipoDesconto;

public enum EnumTipoDesconto {
	
    CONVENIO(1, "Convênio"),
    BOLSA(2, "Bolsa"),
    ANTECIPACAO(3, "Antecipação"),
    EX_ALUNO(4,"Ex-aluno");
    

	private int id;
	private String label;

	
	EnumTipoDesconto(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoDesconto> listarTodos() {
		return Arrays.asList(EnumTipoDesconto.values());
	}
    
    public static EnumTipoDesconto porId(int id) {
		return Arrays.asList(EnumTipoDesconto.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}