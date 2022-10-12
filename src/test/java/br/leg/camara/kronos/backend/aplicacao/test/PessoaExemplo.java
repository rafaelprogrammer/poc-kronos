package br.leg.camara.kronos.backend.aplicacao.test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.kronos.backend.aplicacao.pessoa.Pessoa;
import br.com.kronos.backend.aplicacao.pessoa.api.PessoaDTO;

public class PessoaExemplo {

	public static Pessoa.PessoaBuilder criadoComSucesso() {
		return Pessoa.builder()
		.id(555)
		.dataNascimento(LocalDate.parse("2018-09-25", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
		.cpf("48915011031")
		.nome("Teste Pessoa")
		.nomeSocial("TPessoa")
		.nomeUsual("TPessoaT")
		.bancoTalento(true)
		.email("teste@gmail.com")
		.numeroRegistro(88999)
		.grauComportamento(5.8f)
		.idArqAnexo(2)
		.idCidade(1)
		.idGenero(2)
		.idInstituicao(2);
		
	}
	
	public static PessoaDTO.PessoaDTOBuilder criadoComSucessoDTO() {
		return PessoaDTO.builder()
		.id(555)
		.dataNascimento(LocalDate.parse("2018-09-25", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
		.cpf("48915011031")
		.nome("Teste Pessoa")
		.nomeSocial("TPessoa")
		.nomeUsual("TPessoaT")
		.bancoTalento(true)
		.email("teste@gmail.com")
		.numeroRegistro(88999)
		.grauComportamento(5.8f)
		.idArqAnexo(2)
		.idCidade(1)
		.idGenero(2)
		.idPais(30)
		.idInstituicao(2);
		
	}
	
	public static Pessoa.PessoaBuilder semOsCamposObrigatorios() {
		return Pessoa.builder();
	}
	
}
