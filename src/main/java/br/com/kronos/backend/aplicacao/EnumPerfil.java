package br.com.kronos.backend.aplicacao;

import java.util.Arrays;
import java.util.List;

public enum EnumPerfil {

	ADMINISTRADOR("Administrador", 2),
	ADMINISTRADOR_DE_USUARIOS("Administrador de usuários", 3),
	MANTENEDOR("Mantenedor", 4),
	DIRETOR("Diretor", 5),
	VICE_DIRETOR("Vice Diretor", 6),
	TESOUREIRO("Tesoureiro", 7),
	AUXILIAR_DE_TESOUREIRO("Auxiliar de Tesoureiro", 8),
	SECRETARIO("Secretário", 9),
	AUXILIAR_DE_SECRETARIO("Auxiliar de Secretário", 10),
	RECURSOS_HUMANOS("Recursos Humanos", 11),
	AUXILIAR_DE_RECURSOS_HUMANOS("Auxiliar de Recursos Humanos", 12),
	COORDENADOR_DE_CURSO("Coordenador de curso", 13),
	COORDENADOR_PEDAGOGICO("Coordenador pedagógico", 14),
	PROFESSOR("Professor", 15),
	RESPONSAVEL_FINANCEIRO("Responsável Financeiro", 16),
	RESPONSAVEL_PEDAGOGICO("Responsável Pedagógico", 17),
	ALUNO("Aluno", 18),
	BIBLIOTECARIO("Bibliotecário", 19),
	AUXILIAR_DE_BIBLIOTECARIO("Auxiliar de Bibliotecário", 20),
	ORIENTADOR_EDUCACIONAL("Orientador Educacional", 21),
	FUNCIONARIO("Funcionário", 22);
	
	
	private int id;
	private String label;
	
	EnumPerfil(String label, int id) {
		this.label = label;
		this.id = id;
	}
	
	public List<EnumPerfil> listarTodos() {
		return Arrays.asList(EnumPerfil.values());
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
}
