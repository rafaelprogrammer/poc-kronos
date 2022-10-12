package br.leg.camara.kronos.backend.controlador.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.kronos.backend.KronosBootApplication;
import br.com.kronos.backend.adaptadores.seguranca.Authority;
import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;
import br.com.kronos.backend.aplicacao.api.impl.UsuarioServico;
import br.leg.camara.kronos.backend.aplicacao.test.UsuarioExemplo;
import br.leg.camara.kronos.backend.config.test.ConfigDataSourcePgSqlTest;
import br.leg.camara.kronos.backend.test.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={KronosBootApplication.class, ConfigDataSourcePgSqlTest.class})
@AutoConfigureMockMvc
@ActiveProfiles("teste-controller")
public class UsuarioControladorTest {
	
	@Autowired
	private UsuarioServico usuarioServico;
	
	@Autowired
	private MockMvc mvc;
	
	private static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@Before
	public void excluir() throws Exception {
		mvc.perform(
				delete("/api/usuario/{id}", 1).accept(MediaType.APPLICATION_JSON)
				.headers(TestUtil.createHeadersSecurityToken()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", equalTo("Exclusão do usuário enviada com sucesso - " + 1)));
		TimeUnit.SECONDS.sleep(2);
	}
	
	
	
	@Test
	public void criar() throws Exception {
		mvc.perform(
				post("/api/usuario").accept(MediaType.APPLICATION_JSON)
				.headers(TestUtil.createHeadersSecurityToken())
				.content(asJsonString(UsuarioDTO.builder()
						.dataAtivacao(LocalDateTime.parse("2018-09-25 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
						.idPessoa(55L)
						.authorities(Arrays.asList(Authority.ADMINISTRADOR, Authority.ADMINISTRADOR_DE_USUARIOS))
						.idInstituicao(2L)
						.ativo(true)
						.senha("dfdfsadf").build())))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", equalTo("Cadastro do usuário enviado com sucesso - 55")));
		TimeUnit.SECONDS.sleep(2);
	}
	
	@Test
	public void alterar() throws Exception {
		UsuarioDTO usuarioDTO = UsuarioExemplo.criadoComSucessoDTO("selvaxxx", "selvaxxx").build();
		Long id = usuarioServico.criar(usuarioDTO);
		mvc.perform(
				put("/api/usuario/{id}", id).accept(MediaType.APPLICATION_JSON)
				.headers(TestUtil.createHeadersSecurityToken())
				.content(asJsonString(UsuarioDTO.builder().id(id).idPessoa(77L).ativo(false).build())))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", equalTo("Alteração do usuário enviada com sucesso - 77")));
		TimeUnit.SECONDS.sleep(2);
	}
	
	@Test
	public void listar() throws Exception {
		mvc.perform(
				get("/api/usuario").accept(MediaType.APPLICATION_JSON)
				.headers(TestUtil.createHeadersSecurityToken()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", equalTo(50)))
				.andExpect(jsonPath("$[0].dataCriacao", equalTo("05/09/2019 22:05:43")))
				.andExpect(jsonPath("$[0].idPessoa", equalTo(55)))
				.andExpect(jsonPath("$[1].id", equalTo(60)))
				.andExpect(jsonPath("$[1].dataCriacao", equalTo("05/09/2019 22:05:43")))
				.andExpect(jsonPath("$[1].idPessoa", equalTo(66)));
	}
	
	
	@Test
	public void buscarPorId() throws Exception {
		
		mvc.perform(
				get("/api/usuario/{id}", 50).accept(MediaType.APPLICATION_JSON)
				.headers(TestUtil.createHeadersSecurityToken()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", equalTo(50)))
				.andExpect(jsonPath("$.dataCriacao", equalTo("05/09/2019 22:05:43")))
				.andExpect(jsonPath("$.idPessoa", equalTo(55)));
	}
	
	

}
