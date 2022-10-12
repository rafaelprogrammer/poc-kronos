package br.com.kronos.backend.aplicacao;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import br.com.kronos.backend.adaptadores.seguranca.Authority;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 7954325925563724664L;

	private long id;
	private List<Integer> idsPerfis;
	private List<Authority> authorities;
	private String senha;
	private Long idPessoa;
	private Long idInstituicao;
	private boolean ativo;
	private boolean bloqueado;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataAtivacao;

	@Builder
	public Usuario(long id, Long idPessoa, Long idInstituicao, String senha, boolean ativo, boolean bloqueado,
			LocalDateTime dataCriacao, LocalDateTime dataAtivacao, List<Authority> authorities, List<Integer> idsPerfis) {
		super();
		this.id = id;
		this.dataCriacao = dataCriacao == null ? DateUtil.dataHoraAtual() : dataCriacao;
		this.dataAtivacao = dataAtivacao;
		this.idPessoa = requireNonNull(idPessoa == 0 ? null : idPessoa, "idPessoa é obrigatório");
		this.idInstituicao = requireNonNull(idInstituicao == 0 ? null : idInstituicao, "idInstituicao é obrigatório");
		this.ativo = ativo;
		this.bloqueado = bloqueado;
		this.senha = senha;
		this.authorities = authorities;
		this.idsPerfis = idsPerfis;
	}
	
	public void addAuthorities(List<Integer> ids) {
		if(this.authorities == null) {
			this.authorities = new ArrayList<>();
		}
		authorities.addAll(Authority.converter(ids));
		Collections.unmodifiableCollection(this.authorities);
		this.idsPerfis = ids;
	}
	
	public List<Authority> getAuthorities() {
		return Authority.converter(idsPerfis);
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

}
