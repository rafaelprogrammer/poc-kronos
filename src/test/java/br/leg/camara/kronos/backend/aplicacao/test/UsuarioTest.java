package br.leg.camara.kronos.backend.aplicacao.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;

import br.com.kronos.backend.aplicacao.Usuario;

public class UsuarioTest {

	@Test
	public void criarUsuarioComSucesso() {
		Usuario usuario = UsuarioExemplo.criadoComSucesso("usertestexxx").build();
		assertThat(usuario, isA(Usuario.class));
		assertThat(usuario.getDataCriacao(),
				equalTo(LocalDateTime.of(2018, 9, 25, 15, 0)));
		assertThat(usuario.getIdPessoa(), equalTo(55));
		assertThat(usuario.getSenha(), equalTo("usertestexxx"));
	}
	

	@Test(expected = Exception.class)
	public void criarUsuarioSemIdPessoa() {
		UsuarioExemplo.semIdPessoa().build();
	}
	
}
