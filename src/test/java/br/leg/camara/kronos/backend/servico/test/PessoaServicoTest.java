package br.leg.camara.kronos.backend.servico.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroPessoa;
import br.com.kronos.backend.aplicacao.pessoa.api.PessoaDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.PessoaServico;
import br.com.kronos.backend.config.ConfigModelMapper;
import br.com.kronos.backend.config.ConfigRepositorio;
import br.com.kronos.backend.config.ConfigService;
import br.leg.camara.kronos.backend.aplicacao.test.PessoaExemplo;
import br.leg.camara.kronos.backend.config.test.ConfigDataSourcePgSqlTest;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ConfigDataSourcePgSqlTest.class, ConfigModelMapper.class, ConfigService.class, ConfigRepositorio.class })
public class PessoaServicoTest {


	@Autowired
	private PessoaServico pessoaServico;
	
	@After
	public void init() throws ExcecaoDeNegocio {
		pessoaServico.excluir(1);
	}

	@Test
	public void criar() throws ExcecaoDeNegocio, IOException {
		PessoaDTO pessoa = PessoaExemplo.criadoComSucessoDTO().build();
		long id = pessoaServico.criar(pessoa);
		assertThat(id, notNullValue());
	}
	
	@Test
	public void alterar() throws ExcecaoDeNegocio, IOException {
		PessoaDTO pessoa = PessoaExemplo.criadoComSucessoDTO().build();
		long id = pessoaServico.criar(pessoa);
		PessoaDTO pessoaAlterada = PessoaExemplo.criadoComSucessoDTO().id(id).email("xxxx@gmail.com").cpf("33860219057").build();
		pessoaServico.alterar(pessoaAlterada);
		pessoaAlterada = pessoaServico.buscarPorId(id);
		assertThat(pessoaAlterada.getId(), notNullValue());
		assertThat(pessoaAlterada.getEmail(), equalTo("xxxx@gmail.com"));
		assertThat(pessoaAlterada.getCpf(), equalTo("33860219057"));
	}
	
	@Test
	public void listarTodos() throws ExcecaoDeNegocio {
		PaginacaoDTO<PessoaDTO> pessoas = pessoaServico.listar(FiltroPessoa.builder().build());
		assertThat(pessoas.getDados().size(), equalTo(3));
		assertThat(pessoas.getDados().get(0), isA(PessoaDTO.class));
		assertThat(pessoas.getDados().get(0).getId(), equalTo(55));
		assertThat(pessoas.getDados().get(0).getDataNascimento(), equalTo(LocalDate.of(1965, 3, 9)));
		assertThat(pessoas.getDados().get(0).getNome(), equalTo("Francisco Daniel Batista de Oliveira"));
		assertThat(pessoas.getDados().get(0).getCpf(), equalTo("06849869817"));
		
		assertThat(pessoas.getDados().get(1), isA(PessoaDTO.class));
		assertThat(pessoas.getDados().get(1).getId(), equalTo(77));
		assertThat(pessoas.getDados().get(1).getDataNascimento(), equalTo(LocalDate.of(1999, 03, 9)));
		assertThat(pessoas.getDados().get(1).getNome(), equalTo("Paulo Silva"));
		assertThat(pessoas.getDados().get(1).getCpf(), equalTo("68679696072"));
		
		assertThat(pessoas.getDados().get(2), isA(PessoaDTO.class));
		assertThat(pessoas.getDados().get(2).getId(), equalTo(66));
		assertThat(pessoas.getDados().get(2).getDataNascimento(), equalTo(LocalDate.of(1984, 03, 9)));
		assertThat(pessoas.getDados().get(2).getNome(), equalTo("Rafael Alves Machado"));
		assertThat(pessoas.getDados().get(2).getCpf(), equalTo("98513346187"));
	}

}
