package br.com.kronos.backend.aplicacao.desempenho;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DesempenhoAtitude {
 
    private Long idDesempenho;
    private Long idAtitude;
    private Double nota;
    private Long idMencao;
 
	@Builder
    public DesempenhoAtitude(Long idDesempenho, Long idAtitude, Double nota, Long idMencao ) {

		super();
        this.idDesempenho = exigirNaoNulo(idDesempenho, "Desempenho");
        this.idAtitude = exigirNaoNulo(idAtitude, "Atitude"); 
        this.nota = nota;
        this.idMencao = idMencao; 
       
	}
}
