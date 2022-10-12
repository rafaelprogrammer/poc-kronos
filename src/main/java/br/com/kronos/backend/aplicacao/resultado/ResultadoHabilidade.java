
package br.com.kronos.backend.aplicacao.resultado;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResultadoHabilidade {
    
    private Long id;
    private Long idAvaliado;
    private Long idAvaliacaoHabilidade;
    private Double nota;
    private Long idMencao; 
    private boolean descarte; 
    private String motivoDescarte; 
  
	@Builder
    public ResultadoHabilidade(Long id, Long idAvaliado, Long idAvaliacaoHabilidade, Double nota, 
                         Long idMencao, boolean descarte, String motivoDescarte  ) {

		super();
        this.id = id;
        this.idAvaliado = exigirNaoNulo(idAvaliado, "Avaliado"); 
        this.idAvaliacaoHabilidade = exigirNaoNulo(idAvaliacaoHabilidade, "Avaliação e habilidade");    
        this.nota = nota; 
        this.idMencao = idMencao; 
        this.descarte = exigirNaoNulo(descarte, "Descarte"); 
        this.motivoDescarte = motivoDescarte; 
	}
}