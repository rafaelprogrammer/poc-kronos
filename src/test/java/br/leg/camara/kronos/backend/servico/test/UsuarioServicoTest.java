package br.leg.camara.kronos.backend.servico.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.kronos.backend.aplicacao.FiltroUsuario;
import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;
import br.com.kronos.backend.aplicacao.api.dto.UsuarioPessoaDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.api.impl.UsuarioServico;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.config.ConfigModelMapper;
import br.com.kronos.backend.config.ConfigRepositorio;
import br.com.kronos.backend.config.ConfigService;
import br.leg.camara.kronos.backend.aplicacao.test.UsuarioExemplo;
import br.leg.camara.kronos.backend.config.test.ConfigDataSourcePgSqlTest;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ConfigDataSourcePgSqlTest.class, ConfigModelMapper.class, ConfigService.class, ConfigRepositorio.class})
public class UsuarioServicoTest {
	
	@Autowired
	private UsuarioServico usuarioServico;
	
	@After
	public void init() throws ExcecaoDeNegocio {
		usuarioServico.excluir(1L);
	}
	
	@Test
	public void criar() throws ExcecaoDeNegocio {
		UsuarioDTO usuarioDTO = UsuarioExemplo.criadoComSucessoDTO("teste44", "senha555").build();
		usuarioServico.criar(usuarioDTO);
	}
	
	@Test
	public void alterar() throws ExcecaoDeNegocio {
		UsuarioDTO usuarioDTO = UsuarioExemplo.criadoComSucessoDTO("teste5550", "senha666").id(60).idPessoa(77L).build();
		usuarioServico.alterar(usuarioDTO);
	}
	
	@Test
	public void listar() throws ExcecaoDeNegocio {
		PaginacaoDTO<UsuarioPessoaDTO> usuarios = usuarioServico.listar(FiltroUsuario.builder().build());
		assertThat(usuarios.getDados(), equalTo(2));
	}
	
	@Test
	public void buscarPorId() throws ExcecaoDeNegocio {
		UsuarioDTO usuario = usuarioServico.buscarPorId(60L);
		assertThat(usuario.getId(), equalTo(60));
	}
	
	@Test
	public void buscarPorEmail() throws ExcecaoDeNegocio {
		UsuarioDTO usuario = usuarioServico.buscarPorUsuario("fdboeti@gmail.com");
		assertThat(usuario.getIdPessoa(), equalTo(55));
	}

}
