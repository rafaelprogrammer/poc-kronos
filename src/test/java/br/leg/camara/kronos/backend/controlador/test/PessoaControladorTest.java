package br.leg.camara.kronos.backend.controlador.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import br.com.kronos.backend.aplicacao.pessoa.api.PessoaDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.PessoaServico;
import br.leg.camara.kronos.backend.aplicacao.test.PessoaExemplo;
import br.leg.camara.kronos.backend.config.test.ConfigDataSourcePgSqlTest;
import br.leg.camara.kronos.backend.test.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={KronosBootApplication.class, ConfigDataSourcePgSqlTest.class})
@AutoConfigureMockMvc
@ActiveProfiles("teste-controller")
public class PessoaControladorTest {
	
	@Autowired
	private PessoaServico pessoaServico;
	
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
				delete("/api/pessoa/{id}", 1).accept(MediaType.APPLICATION_JSON)
				.headers(TestUtil.createHeadersSecurityToken()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", equalTo("Exclusão da pessoa enviada com sucesso - " + 1)));
		TimeUnit.SECONDS.sleep(2);
	}
	
	
	@Test
	public void criar() throws Exception {
		mvc.perform(
				post("/api/pessoa").accept(MediaType.APPLICATION_JSON)
				.headers(TestUtil.createHeadersSecurityToken())
				.content(asJsonString(PessoaExemplo.criadoComSucessoDTO().build())))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", equalTo("Cadastro da pessoa enviado com sucesso - 48915011031")));
		TimeUnit.SECONDS.sleep(2);
	}
	
	@Test
	public void alterar() throws Exception {
		PessoaDTO pessoaDTO = PessoaExemplo.criadoComSucessoDTO().build();
		long id = pessoaServico.criar(pessoaDTO);
		mvc.perform(
				put("/api/pessoa/{id}", id).accept(MediaType.APPLICATION_JSON)
				.headers(TestUtil.createHeadersSecurityToken())
				.content(asJsonString(PessoaExemplo.criadoComSucessoDTO().id(id).cpf("82735282007").nome("Nome Alterado").build())))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", equalTo("Alteração da pessoa enviada com sucesso - 82735282007")));
		TimeUnit.SECONDS.sleep(2);
	}
	
	
	@Test
	public void listar() throws Exception {
		mvc.perform(
				get("/api/pessoa").accept(MediaType.APPLICATION_JSON)
				.headers(TestUtil.createHeadersSecurityToken()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].id", equalTo(55)))
				.andExpect(jsonPath("$[0].dataNascimento", equalTo("09/03/1965")))
				.andExpect(jsonPath("$[0].cpf", equalTo("06849869817")))
				.andExpect(jsonPath("$[0].nome", equalTo("Francisco Daniel Batista de Oliveira")))
				.andExpect(jsonPath("$[1].id", equalTo(77)))
				.andExpect(jsonPath("$[1].dataNascimento", equalTo("09/03/1999")))
				.andExpect(jsonPath("$[1].cpf", equalTo("68679696072")))
				.andExpect(jsonPath("$[1].nome", equalTo("Paulo Silva")))
				.andExpect(jsonPath("$[2].id", equalTo(66)))
				.andExpect(jsonPath("$[2].dataNascimento", equalTo("09/03/1984")))
				.andExpect(jsonPath("$[2].cpf", equalTo("98513346187")))
				.andExpect(jsonPath("$[2].nome", equalTo("Rafael Alves Machado")));
	}
	
	@Test
	public void listarUsuariosPorCpf() throws Exception {
		
		mvc.perform(
				get("/api/pessoa").param("cpf", "06849869817").accept(MediaType.APPLICATION_JSON)
				.headers(TestUtil.createHeadersSecurityToken()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", equalTo(55)))
				.andExpect(jsonPath("$[0].dataNascimento", equalTo("09/03/1965")))
				.andExpect(jsonPath("$[0].cpf", equalTo("06849869817")))
				.andExpect(jsonPath("$[0].nome", equalTo("Francisco Daniel Batista de Oliveira")));
	}
	
	@Test
	public void buscarPorId() throws Exception {
		
		mvc.perform(
				get("/api/pessoa/{id}", 77).accept(MediaType.APPLICATION_JSON)
				.headers(TestUtil.createHeadersSecurityToken()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", equalTo(77)))
				.andExpect(jsonPath("$.dataNascimento", equalTo("09/03/1999")))
				.andExpect(jsonPath("$.cpf", equalTo("68679696072")))
				.andExpect(jsonPath("$.nome", equalTo("Paulo Silva")));
	}
	
	

}
