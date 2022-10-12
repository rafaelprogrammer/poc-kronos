package br.com.kronos.backend.aplicacao.curso;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoTurno {
	
    MATUTINO(1, "Matutino", "M"),
    VESPERTINO(2, "Vespertino", "V"),
    NOTURNO(3, "Noturno", "N"),
    INTEGRAL(4, "Integral", "I");
    
    private int id;
	private String label;
	private String sigla;

	EnumTipoTurno(int id, String label, String sigla) {
		this.label = label;
		this.id = id;
		this.sigla = sigla;
    }
    
    public List<EnumTipoTurno> listarTodos() {
		return Arrays.asList(EnumTipoTurno.values());
	}
    
    public static EnumTipoTurno porId(int id) {
		return Arrays.asList(EnumTipoTurno.values()).stream().filter(o -> o.id == id).findAny().orElse(null);
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
	public String sigla() {
		return this.sigla;
	}
	
}