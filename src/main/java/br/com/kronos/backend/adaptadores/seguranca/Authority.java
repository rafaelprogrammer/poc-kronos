package br.com.kronos.backend.adaptadores.seguranca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import br.com.kronos.backend.aplicacao.EnumPerfil;
import br.com.kronos.backend.aplicacao.api.dto.PerfilDTO;


public enum Authority implements GrantedAuthority, Serializable {

	ADMINISTRADOR("Administrador", 2),
	ADMINISTRADOR_DE_USUARIOS("Administrador de usuários", 3, "usuarios"),
	MANTENEDOR("Mantenedor", 4),
	DIRETOR("Diretor", 5, "tipos_ocorrencias", "ocorrencias", "relatorios_pedagogicos", "relatorios_alunos"),
	VICE_DIRETOR("Vice Diretor", 6, "relatorios_pedagogicos", "relatorios_alunos"),
	TESOUREIRO("Tesoureiro", 7, "contratos", "relatorios_pedagogicos", "relatorios_alunos"),
	AUXILIAR_DE_TESOUREIRO("Auxiliar de Tesoureiro", 8, "contratos", "relatorios_pedagogicos", "relatorios_alunos"),
	SECRETARIO("Secretário", 9, "pessoas", "cursos", "disciplinas",  "matriculas", "calendarios", "estruturas_anos_letivos", "tipos_ocorrencias", "ocorrencias", "atestados", "relatorios_pedagogicos", "relatorios_alunos"),
	AUXILIAR_DE_SECRETARIO("Auxiliar de Secretário", 10, "pessoas", "cursos", "disciplinas",  "matriculas", "atestados", "relatorios_pedagogicos", "relatorios_alunos"),
	RECURSOS_HUMANOS("Recursos Humanos", 11, "funcionarios", "homologacao_pontos"),
	AUXILIAR_DE_RECURSOS_HUMANOS("Auxiliar de Recursos Humanos", 12, "funcionarios", "homologacao_pontos"),
	COORDENADOR_DE_CURSO("Coordenador de curso", 13, "horarios", "estruturas_pedagogicas", "planos_pedagogicos", "tipos_ocorrencias", "ocorrencias", "relatorios_pedagogicos", "relatorios_alunos"),
	COORDENADOR_PEDAGOGICO("Coordenador pedagógico", 14, "tipos_ocorrencias", "relatorios_pedagogicos", "relatorios_alunos"),
	PROFESSOR("Professor", 15, "diarios", "frequencias", "avaliacoes", "resultados_avaliacoes_habilidades", "ocorrencias", "relatorios_pedagogicos", "relatorios_alunos", "resultados_bimestres"),
	RESPONSAVEL_FINANCEIRO("Responsável Financeiro", 16),
	RESPONSAVEL_PEDAGOGICO("Responsável Pedagógico", 17),
	ALUNO("Aluno", 18),
	BIBLIOTECARIO("Bibliotecário", 19),
	AUXILIAR_DE_BIBLIOTECARIO("Auxiliar de Bibliotecário", 20),
	ORIENTADOR_EDUCACIONAL("Orientador Educacional", 21, "tipos_ocorrencias", "ocorrencias"),
	FUNCIONARIO("Funcionário", 22, "pontos");
	
	private int id;
	private String nome;
	private String[] menus;
	
	Authority(String nome, int id, String... menus) {
		this.nome = nome;
		this.id = id;
		this.menus = menus;
	}
	
	public List<EnumPerfil> listarTodos() {
		return Arrays.asList(EnumPerfil.values());
	}
	
	public static List<PerfilDTO> autorities() {
		return Arrays.asList(Authority.values()).stream().map(a -> PerfilDTO.builder().id(a.id).nome(a.nome).build()).collect(Collectors.toList());
	}
	
	public static Authority porId(int id) {
		return Arrays.asList(Authority.values()).stream().filter(a -> a.id == id).findFirst().orElse(null);
	}

	@Override
    public String getAuthority() {
        return this.name();
    }
	
	public Integer id() {
		return this.id;
	}
	
	public String nome() {
		return this.nome;
	}
	
	public String[] menus() {
		return this.menus;
	}
	
	public static List<Authority> converter(List<Integer> authorities) {
		List<Authority> lista = new ArrayList<Authority>();
		if (authorities.stream().filter(id -> Authority.ADMINISTRADOR.id == id).findFirst().isPresent()) {
			return Arrays.asList(Authority.values()).stream().map(a -> Authority.porId(a.id)).collect(Collectors.toList());
		}
		authorities.stream().forEach(a -> {
			Authority authority = Authority.porId(a);
			if (authority != null) {
				lista.add(authority);
			}
		});
		return lista;
	}
}
