package br.leg.camara.kronos.backend.aplicacao.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import br.com.kronos.backend.adaptadores.seguranca.Authority;
import br.com.kronos.backend.aplicacao.Usuario;
import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;

public class UsuarioExemplo {

	public static Usuario.UsuarioBuilder criadoComSucesso(String senha) {
		return Usuario.builder()
		.id(555)
		.dataCriacao(LocalDateTime.parse("2018-09-25 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
		.dataAtivacao(LocalDateTime.parse("2018-09-25 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
		.idPessoa(55L)
		.authorities(Arrays.asList(Authority.ADMINISTRADOR, Authority.ADMINISTRADOR_DE_USUARIOS))
		.idInstituicao(2L)
		.ativo(true)
		.senha(senha);
	}
	
	public static UsuarioDTO.UsuarioDTOBuilder criadoComSucessoDTO(String username, String senha) {
		return UsuarioDTO.builder()
				.id(555)
				.dataCriacao(LocalDateTime.parse("2018-09-25 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
				.dataAtivacao(LocalDateTime.parse("2018-09-25 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
				.idPessoa(55L)
				.authorities(Arrays.asList(Authority.ADMINISTRADOR, Authority.ADMINISTRADOR_DE_USUARIOS))
				.idInstituicao(2L)
				.ativo(true)
				.senha(senha);
	}
	
	public static Usuario.UsuarioBuilder semOsCamposObrigatorios() {
		return Usuario.builder();
	}
	
	public static Usuario.UsuarioBuilder semIdPessoa() {
		return criadoComSucesso("senhaxxx").idPessoa(0L);
	}
	
}
