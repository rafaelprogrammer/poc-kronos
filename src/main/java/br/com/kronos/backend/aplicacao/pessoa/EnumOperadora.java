package br.com.kronos.backend.aplicacao.pessoa;

import java.util.Arrays;
import java.util.List;

public enum EnumOperadora {
	
	TIM(1, "TIM", 41),
    VIVO(2, "VIVO", 15),
    OI(3, "OI", 31),
    CLARO(4, "CLARO", 21),
    NEXTEL(5, "NEXTEL", 99),
    SERCONTEL(6, "SERCONTEL", 43),
    ALGAR(7, "ALGAR", 12);
	
	private int id;
	private String label;
	private int codigo;
	
	EnumOperadora(int id, String label, int codigo) {
		this.label = label;
		this.id = id;
		this.codigo = codigo;
	}
	
	public List<EnumOperadora> listarTodos() {
		return Arrays.asList(EnumOperadora.values());
	}
	
	public static EnumOperadora porId(int id) {
		return Arrays.asList(EnumOperadora.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
	public int codigo() {
		return this.codigo;
	}

}
