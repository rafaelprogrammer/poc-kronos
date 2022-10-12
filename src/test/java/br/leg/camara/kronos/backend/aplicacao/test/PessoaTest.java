package br.leg.camara.kronos.backend.aplicacao.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;

import org.junit.Test;

import br.com.kronos.backend.aplicacao.exception.ExcecaoCPFInvalido;
import br.com.kronos.backend.aplicacao.exception.ExcecaoEmailInvalido;
import br.com.kronos.backend.aplicacao.pessoa.Pessoa;

public class PessoaTest {

	@Test
	public void criarPessoaComSucesso() {
		Pessoa pessoa = PessoaExemplo.criadoComSucesso().build();
		assertThat(pessoa, isA(Pessoa.class));
		assertThat(pessoa.getDataNascimento(),
				equalTo(LocalDate.of(2018, 9, 25)));
		assertThat(pessoa.getNome(), equalTo("Teste Pessoa"));
		assertThat(pessoa.getCpf(), equalTo("48915011031"));
	}
	

	@Test(expected = Exception.class)
	public void criarPessoaSemCamposObrigatorios() {
		PessoaExemplo.semOsCamposObrigatorios().build();
	}
	
	@Test(expected = ExcecaoEmailInvalido.class)
	public void criarPessoaEmailInvalido() {
		PessoaExemplo.criadoComSucesso().email("222@sadfasdf").build();
	}
	
	@Test(expected = ExcecaoCPFInvalido.class)
	public void criarPessoaCPFInvalido() {
		PessoaExemplo.criadoComSucesso().cpf("45678234212").build();
	}
	
}
