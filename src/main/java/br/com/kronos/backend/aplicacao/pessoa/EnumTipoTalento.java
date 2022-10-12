package br.com.kronos.backend.aplicacao.pessoa;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoTalento {
	
    PROFESSOR_MATEMATICA(1, "Professor de Matemática"), 
    PROFESSOR_GEOGRAFIA(2, " Professor de Geografia"), 
    PROFESSOR_HISTORIA(3, "Professor de História"), 
    PROFESSOR_INGLES(4, "Professor de Inglês"), 
    PROFESSOR_BIOLOGIA(5, "Professor de Biologia"), 
    PROFESSOR_FISICA(6, "Professor de Física"), 
    PROFESSOR_QUIMICA(7, "Professor de Química"),
    COORDENADOR_ENSINO_INFANTIL(8, "Coordenador Ensino Infantil"),
    COORDENADOR_ENSINO_FUNDAMENTAL_I(9, "Coordenador Ensino Fundamental I"),  
    COORDENADOR_ENSINOFUNDAMENTAL_II(10, "Coordenador Ensino Fundamental II"), 
    COORDENADOR_ENSINO_MEDIO(11, "Coordenador Ensino Médio");
    

	private int id;
	private String label;

	
	EnumTipoTalento(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoTalento> listarTodos() {
		return Arrays.asList(EnumTipoTalento.values());
	}
    
    public static EnumTipoTalento porId(int id) {
		return Arrays.asList(EnumTipoTalento.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}


