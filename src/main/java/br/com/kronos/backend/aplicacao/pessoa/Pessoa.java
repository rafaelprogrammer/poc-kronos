package br.com.kronos.backend.aplicacao.pessoa;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Pessoa { 
	
	private long id;
	private int numeroRegistro;
	private String email;
	private LocalDate dataNascimento;
	private String nome;
	private String nomeUsual;
	private String nomeSocial;
	private String cpf;
	private boolean bancoTalento;
	private float grauComportamento;
	private Integer idArqAnexo;
	private Integer idCidade;
	private Integer idGenero;
	private Integer idInstituicao;
	
	@Builder
	public Pessoa(long id, int numeroRegistro, String email, LocalDate dataNascimento, String nome, String nomeUsual,
			String nomeSocial, String cpf, boolean bancoTalento, float grauComportamento, Integer idArqAnexo, Integer idCidade,
			Integer idGenero, Integer idInstituicao) {
		super();
		this.id = id;
		this.numeroRegistro = requireNonNull(numeroRegistro, "numeroRegistro é obrigatório");
		this.email = requireNonNull(email, "email é obrigatório");
		this.dataNascimento = requireNonNull(dataNascimento, "dataNascimento é obrigatório");
		this.cpf = requireNonNull(cpf, "cpf é obrigatório");
		this.nome = requireNonNull(nome, "nome é obrigatório");
		this.nomeUsual = requireNonNull(nomeUsual, "nome é obrigatório");
		this.nomeSocial = requireNonNull(nomeSocial, "nomeSocial é obrigatório");
		this.bancoTalento = requireNonNull(bancoTalento, "bancoTalento é obrigatório");
		this.grauComportamento = grauComportamento;
		alterarFoto(idArqAnexo);
		this.idCidade = idCidade;
		this.idGenero = requireNonNull(idGenero, "idGenero é obrigatório");
		this.idInstituicao = requireNonNull(idInstituicao, "idInstitucicao é obrigatório");
	}
	
	public void alterarFoto(Integer idArqAnexo) {
		this.idArqAnexo = idArqAnexo != null && idArqAnexo > 0 ? idArqAnexo : null;
	}

}
