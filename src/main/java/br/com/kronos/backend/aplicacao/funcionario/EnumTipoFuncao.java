package br.com.kronos.backend.aplicacao.funcionario;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoFuncao {
    
   
	DIRETOR(1, "Diretor", 1),
    VICE_DIRETOR(2, "Vice-Diretor", 1),
    COORDENADOR(3, "Coordenador", 2),
    PROFESSOR(4, "Professor", 3),
    TESOUREIRO(5, "Tesoureiro", 5),
    SECRETARIO(6, "Secretário", 5),
    AUXILIAR_ADMINISTRATIVO(7, "Auxilar Administrativo", 5),
    INSPETOR(8, "Inspetor", 5),
    REPRESENTANTE_TURMA(9, "Representante da turma", 6),
    GUARDIAO(10, "Guardião", 6),
    ALMOXARIFE(11, "Almoxarife", 5),
    CHEFE_SEGURANCA(12, "Chefe da segurança", 5),
    AUXILIAR_SEGURANCA(13, "Auxilar de segurança", 5),
	COORDENADOR_TECNOLOGIA(14, "Coordenador de Tecnologia", 5),
	BIBLIOTECARIO(15, "Bibliotecário", 5),
	AUXILIAR_DE_SALA(16, "Auxiliar de sala", 3);
	
	private int id;
	private String label;
	private int categoria;
	
	EnumTipoFuncao(int id, String label, int categoria) {
		this.label = label;
		this.id = id;
		this.categoria = categoria;
	}

	public List<EnumTipoFuncao> listarTodos() {
		return Arrays.asList(EnumTipoFuncao.values());
	}

	public static EnumTipoFuncao porId(int id) {
		return Arrays.asList(EnumTipoFuncao.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
	public int categoria() {
		return this.categoria;
	}

}


