package br.com.kronos.backend.aplicacao.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import br.com.kronos.backend.adaptadores.seguranca.Authority;
import lombok.Builder;
import lombok.Data;

@Data
public class UsuarioDTO implements UserDetails {

	private static final long serialVersionUID = 1L;
	private long id;
	private List<Integer> idsPerfis;
	@JsonIgnore
	private List<Authority> authorities;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dataCriacao;
    private String senha;
	private Long idPessoa;
	private String nome;
	private Long idInstituicao;
	private String instituicao;
	private boolean instituicaoMantenedora;
	private boolean instituicaoAtiva;
	private boolean ativo;
	private boolean bloqueado;
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime dataAtivacao;
    private String cpf;

	@Builder
	public UsuarioDTO(long id, LocalDateTime dataCriacao, String senha, Long idPessoa, Long idInstituicao, boolean instituicaoMantenedora, 
			boolean instituicaoAtiva, boolean ativo, boolean bloqueado, LocalDateTime dataAtivacao, List<Authority> authorities, String cpf, List<Integer> idsPerfis) {
		super();
		this.id = id;
		this.dataCriacao = dataCriacao;
		this.senha = senha;
		this.idPessoa = idPessoa;
		this.idInstituicao = idInstituicao;
		this.instituicaoMantenedora = instituicaoMantenedora;
		this.instituicaoAtiva = instituicaoAtiva;
		this.ativo = ativo;
		this.bloqueado = bloqueado;
		this.dataAtivacao = dataAtivacao;
		this.authorities = authorities;
		this.cpf = cpf;
		this.idsPerfis = idsPerfis;
	}
    
	UsuarioDTO () {
	}
	
	public List<Authority> getAuthorities() {
		return Authority.converter(idsPerfis);
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.cpf;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	
}
