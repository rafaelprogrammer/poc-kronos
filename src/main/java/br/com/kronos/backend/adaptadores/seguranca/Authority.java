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
	PROFESSOR("Professor", 15, "diarios", "frequencias", "avaliacoes", "resultados_avaliacoes_habilidades", "ocorrencias", "relatorios_pedagogicos", "relatorios_alunos", "resultados_bimestres");
	
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
