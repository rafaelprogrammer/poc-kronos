
package br.com.kronos.backend.aplicacao.resultado;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = "id")
@Getter
public class Avaliado {
    
    private Long id;
    private Long idAvaliacao;
    private Long idCredito;
    private Long idGrupoAvaliacao;
    private Double nota;
    private Long idMencao; 
    private boolean descarte; 
    private String motivoDescarte; 
  
	@Builder
    public Avaliado(Long id, Long idAvaliacao, Long idCredito, Long idGrupoAvaliacao, Double nota, 
                         Long idMencao, boolean descarte, String motivoDescarte  ) {

		super();
        this.id = id;
        this.idAvaliacao = exigirNaoNulo(idAvaliacao, "Avaliação"); 
        this.idCredito = exigirNaoNulo(idCredito, "Crédito (Aluno)");
        this.idGrupoAvaliacao = idGrupoAvaliacao; 
        this.nota = nota; 
        this.idMencao = idMencao; 
        this.descarte = exigirNaoNulo(descarte, "Descarte"); 
        this.motivoDescarte = motivoDescarte; 
	}
}
