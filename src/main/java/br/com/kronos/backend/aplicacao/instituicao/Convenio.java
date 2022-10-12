package br.com.kronos.backend.aplicacao.instituicao;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Convenio {
	private Long id;
    private Double percentualDesconto;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFinalVigencia;
    private int idTipoDesconto;
    private Long idEmpresa;
    private Long idInstitucicao;

	@Builder
	public Convenio(Long id, Double percentualDesconto, LocalDate dataInicioVigencia, LocalDate dataFinalVigencia, 
                    int idTipoDesconto, Long idEmpresa, Long idInstitucicao) {

		super();
        this.id = id;
        this.percentualDesconto = exigirNaoNulo(percentualDesconto, "Percentual de desconto");
        this.dataInicioVigencia = exigirNaoNulo(dataInicioVigencia, "Data de início de vigência");
        this.dataFinalVigencia = exigirNaoNulo(dataFinalVigencia, "Data de final de vigência"); 
        this.idTipoDesconto = exigirNaoNulo(idTipoDesconto, "Tipo de desconto");
        this.idEmpresa = exigirNaoNulo(idEmpresa, "Empresa");
        this.idInstitucicao = exigirNaoNulo(idInstitucicao, "Instituição");
       
	}
}
