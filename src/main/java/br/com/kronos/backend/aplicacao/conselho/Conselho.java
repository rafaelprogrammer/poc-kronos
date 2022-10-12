
package br.com.kronos.backend.aplicacao.conselho;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Conselho {
    
    private Long id;
    private Long idInstituicao; 
    private int idTipoFinalidadeConselho;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFinalVigencia;

	@Builder
    public Conselho(Long id, Long idInstituicao, int idTipoFinalidadeConselho, LocalDate dataInicioVigencia, LocalDate dataFinalVigencia) {

		super();
        this.id = id;
        this.idInstituicao = exigirNaoNulo(idInstituicao, "Instituição"); 
        this.idTipoFinalidadeConselho = exigirNaoNulo(idTipoFinalidadeConselho, "Tipo de finalidade");
        this.dataInicioVigencia = exigirNaoNulo(dataInicioVigencia, "Data de início de vigência"); 
        this.dataFinalVigencia = dataFinalVigencia;
	}
}
