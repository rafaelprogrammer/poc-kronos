
package br.com.kronos.backend.aplicacao.diario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Diario {
    
    private long id;
    private String procedimento;
    private String recurso;
    private String observacao;
    private Long idAtividade;
    private Integer idTipoPrograma;

	@Builder
    public Diario(long id, String procedimento, String recurso, String observacao, Long idAtividade, Integer idTipoPrograma) {

        this.id = id;
        this.procedimento = exigirTamanho(procedimento, "Procedimento", 1, 500); 
        this.recurso = exigirTamanho(recurso, "Recurso", 1, 500); 
        this.observacao = exigirTamanho(observacao, "Observação", 0, 500); 
        this.idAtividade = exigirNaoNulo(idAtividade, "Atividade"); 
        this.idTipoPrograma = exigirNaoNulo(idTipoPrograma, "Tipo de programa"); 
	}
}