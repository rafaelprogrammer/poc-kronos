package br.leg.camara.kronos.backend.repositorio.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.kronos.backend.aplicacao.FiltroUsuario;
import br.com.kronos.backend.aplicacao.Usuario;
import br.com.kronos.backend.aplicacao.UsuarioPessoa;
import br.com.kronos.backend.aplicacao.UsuarioRepositorio;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.config.ConfigRepositorio;
import br.leg.camara.kronos.backend.aplicacao.test.UsuarioExemplo;
import br.leg.camara.kronos.backend.config.test.ConfigDataSourcePgSqlTest;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ConfigDataSourcePgSqlTest.class, ConfigRepositorio.class})
public class UsuarioRepositorioTest {
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	private Long id;
	
	@After
	public void init() throws ExcecaoDeNegocio {
		usuarioRepositorio.excluir(1L);
	}
	
	@Test
	public void criar() throws ExcecaoDeNegocio, InterruptedException {
		Usuario usuario = UsuarioExemplo.criadoComSucesso("senha555").build();
		id = usuarioRepositorio.criar(usuario);
		assertThat(id, notNullValue());
	}
	
	@Test
	public void alterar() throws ExcecaoDeNegocio, InterruptedException {
		Long id = 50L;
		Usuario usuarioAlterado = UsuarioExemplo.criadoComSucesso("senhaxxx").id(id).idPessoa(77L).idInstituicao(3L).build();
		usuarioRepositorio.alterar(usuarioAlterado);
		usuarioAlterado = usuarioRepositorio.buscarPorId(id);
		assertThat(usuarioAlterado.getId(), notNullValue());
		assertThat(usuarioAlterado.getIdPessoa(), equalTo(77));
		assertThat(usuarioAlterado.getIdInstituicao(), equalTo(3));
	}

	@Test
	public void listar() throws ExcecaoDeNegocio {
		List<UsuarioPessoa> usuarios = usuarioRepositorio.listar(FiltroUsuario.builder().build());
		assertThat(usuarios.size(), equalTo(2));
	}
	
	@Test
	public void buscarPorUsuario() throws ExcecaoDeNegocio {
		Usuario usuario = usuarioRepositorio.buscarPorUsuario("fdboeti@gmail.com");
		assertThat(usuario.getIdPessoa(), equalTo(55));
	}

}
