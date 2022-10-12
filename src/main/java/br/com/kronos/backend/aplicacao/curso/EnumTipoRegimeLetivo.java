package br.com.kronos.backend.aplicacao.curso;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoRegimeLetivo {
	
    PRESENCIAL(1, "Presencial"),
    ENSINO_DISTANCIA(2, "Ensino a Distância"),
    MISTO(3, "Misto");

    private int id;
	private String label;

	EnumTipoRegimeLetivo(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoRegimeLetivo> listarTodos() {
		return Arrays.asList(EnumTipoRegimeLetivo.values());
	}

	public static EnumTipoRegimeLetivo porId(int id) {
		return Arrays.asList(EnumTipoRegimeLetivo.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}
