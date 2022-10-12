package br.com.kronos.backend.aplicacao.pessoa;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Titulacao {
	
	private int id;
    private LocalDate data;
    private String nomeTitulo;
    private Integer idArqAnexo;
    private Long idPessoa;
    private Integer idEmpresa;
    private Integer idTipoTitulo; 

	@Builder
	public Titulacao(int id, LocalDate data, String nomeTitulo, Integer idArqAnexo, Long idPessoa, Integer idEmpresa, Integer idTipoTitulo) {

        this.id = id;
        this.data = exigirNaoNulo(data, "data é obrigatório");
        this.nomeTitulo = exigirTamanho(nomeTitulo, "Nome do Título", 1, 100);
        this.idArqAnexo = idArqAnexo;
        this.idPessoa = exigirNaoNulo(idPessoa, "Pessoa"); 
        this.idEmpresa = exigirNaoNulo(idEmpresa, "Empresa");
        this.idTipoTitulo = exigirNaoNulo(idTipoTitulo, "Tipo do Título");
       
	}
}
