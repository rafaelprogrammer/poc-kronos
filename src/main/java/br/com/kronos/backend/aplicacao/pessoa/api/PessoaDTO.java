package br.com.kronos.backend.aplicacao.pessoa.api;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.Builder;
import lombok.Data;

@Data
public class PessoaDTO implements Serializable {
	
	 
	private static final long serialVersionUID = 1L;
	private long id;
	private int numeroRegistro;
	private String email;
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate dataNascimento;
	private String nome;
	private String nomeUsual;
	private String nomeSocial;
	private String cpf;
	private boolean bancoTalento;
	private float grauComportamento;
	private Integer idArqAnexo;
	private Integer idPais;
	private Integer idCidade;
	private Integer idGenero;
	private Integer idInstituicao;
	
	public PessoaDTO(){
	}

	@Builder
	public PessoaDTO(long id, int numeroRegistro, String email, LocalDate dataNascimento, String nome, String nomeUsual,
			String nomeSocial, String cpf, boolean bancoTalento, float grauComportamento, Integer idArqAnexo, Integer idPais,
			Integer idCidade, Integer idGenero, Integer idInstituicao) {
		this.id = id;
		this.numeroRegistro = numeroRegistro;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.nome = nome;
		this.nomeUsual = nomeUsual;
		this.nomeSocial = nomeSocial;
		this.cpf = cpf;
		this.bancoTalento = bancoTalento;
		this.grauComportamento = grauComportamento;
		this.idArqAnexo = idArqAnexo;
		this.idPais = idPais;
		this.idCidade = idCidade;
		this.idGenero = idGenero;
		this.idInstituicao = idInstituicao;
	}
	
	

}
