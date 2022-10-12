package br.com.kronos.backend.aplicacao.instituicao;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Limite {
    
    private Long id;
    private int idTipoMencao;  
    private Long idEscala;
    private Double maximo;
    private Double minimo;

	@Builder
	public Limite(Long id, int idTipoMencao, Long idEscala, Double maximo, Double minimo) {

		super();
        this.id = id;
        this.idTipoMencao = exigirNaoNulo(idTipoMencao, "Tipo de menção");
        this.idEscala = exigirNaoNulo(idEscala, "Escala");
        this.maximo = exigirNaoNulo(maximo, "Limite máximo");  
        this.minimo = exigirNaoNulo(minimo, "Limite Mínimo");       
	}
}