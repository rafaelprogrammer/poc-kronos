package br.com.kronos.backend.aplicacao.curso;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Portaria {
    
    private long id;
    private LocalDate dataPublicacao;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFinalVigencia;   
    private String descricao;
    private String documentoPublicacao;
    private Integer idTipoPortaria;
    private Long idCurso;

	@Builder
	public Portaria(long id, LocalDate dataPublicacao, LocalDate dataInicioVigencia, LocalDate dataFinalVigencia, String descricao, 
                    String documentoPublicacao, Integer idTipoPortaria, Long idCurso ) {

        this.id = id;
        this.dataPublicacao = exigirNaoNulo(dataPublicacao, "Data de publicação");
        this.dataInicioVigencia = exigirNaoNulo(dataInicioVigencia, "Data de início de vigência");
        this.dataFinalVigencia = dataFinalVigencia;
        this.descricao = exigirTamanho(descricao, "Descrição", 1, 200);
        this.documentoPublicacao = exigirTamanho(documentoPublicacao, "Documento de publicação", 1, 200);
        this.idTipoPortaria = exigirNaoNulo(idTipoPortaria, "Tipo de portaria"); 
        this.idCurso = exigirNaoNulo(idCurso, "Curso");      
	}
}