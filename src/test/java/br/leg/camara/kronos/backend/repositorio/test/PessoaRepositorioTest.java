package br.leg.camara.kronos.backend.repositorio.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroPessoa;
import br.com.kronos.backend.aplicacao.pessoa.Pessoa;
import br.com.kronos.backend.aplicacao.pessoa.PessoaRepositorio;
import br.com.kronos.backend.config.ConfigRepositorio;
import br.leg.camara.kronos.backend.aplicacao.test.PessoaExemplo;
import br.leg.camara.kronos.backend.config.test.ConfigDataSourcePgSqlTest;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ConfigDataSourcePgSqlTest.class, ConfigRepositorio.class })
public class PessoaRepositorioTest {


	@Autowired
	private PessoaRepositorio pessoaRepositorio;
	
	@After
	public void init() throws ExcecaoDeNegocio {
		pessoaRepositorio.excluir(1);
	}

	@Test
	public void criar() throws ExcecaoDeNegocio, IOException {
		Pessoa pessoa = PessoaExemplo.criadoComSucesso().build();
		long id = pessoaRepositorio.criar(pessoa);
		assertThat(id, notNullValue());
	}
	
	@Test
	public void alterar() throws ExcecaoDeNegocio, IOException {
		Pessoa pessoa = PessoaExemplo.criadoComSucesso().build();
		long id = pessoaRepositorio.criar(pessoa);
		Pessoa pessoaAlterada = PessoaExemplo.criadoComSucesso().id(id).email("xxxx@gmail.com").cpf("33860219057").build();
		pessoaRepositorio.alterar(pessoaAlterada);
		pessoaAlterada = pessoaRepositorio.buscarPorId(id);
		assertThat(pessoaAlterada.getId(), notNullValue());
		assertThat(pessoaAlterada.getEmail(), equalTo("xxxx@gmail.com"));
		assertThat(pessoaAlterada.getCpf(), equalTo("33860219057"));
	}
	
	@Test
	public void listarTodos() throws ExcecaoDeNegocio {
		List<Pessoa> pessoas = pessoaRepositorio.listar(FiltroPessoa.builder().build());
		assertThat(pessoas.size(), equalTo(3));
		assertThat(pessoas.get(0), isA(Pessoa.class));
		assertThat(pessoas.get(0).getId(), equalTo(55));
		assertThat(pessoas.get(0).getDataNascimento(), equalTo(LocalDate.of(1965, 3, 9)));
		assertThat(pessoas.get(0).getNome(), equalTo("Francisco Daniel Batista de Oliveira"));
		assertThat(pessoas.get(0).getCpf(), equalTo("06849869817"));
		
		assertThat(pessoas.get(1), isA(Pessoa.class));
		assertThat(pessoas.get(1).getId(), equalTo(77));
		assertThat(pessoas.get(1).getDataNascimento(), equalTo(LocalDate.of(1999, 03, 9)));
		assertThat(pessoas.get(1).getNome(), equalTo("Paulo Silva"));
		assertThat(pessoas.get(1).getCpf(), equalTo("68679696072"));
		
		assertThat(pessoas.get(2), isA(Pessoa.class));
		assertThat(pessoas.get(2).getId(), equalTo(66));
		assertThat(pessoas.get(2).getDataNascimento(), equalTo(LocalDate.of(1984, 03, 9)));
		assertThat(pessoas.get(2).getNome(), equalTo("Rafael Alves Machado"));
		assertThat(pessoas.get(2).getCpf(), equalTo("98513346187"));
	}

}
