
package br.com.kronos.backend.aplicacao.atestado;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Atestado {
    
    private Long id;
    private LocalDate dataEntrega; 
    private Long idPessoa;
    private Long idInstituicao;
    private Integer idTipoFalta;
    private Integer idArqAnexo;
    private LocalDate dataInicioVigencia; 
    private LocalDate dataFinalVigencia; 
    private Long idFuncionario;
    private Integer idTipoAtestado;
    private Long numeroDias;
    private Long idContrato;

	@Builder
    public Atestado(Long id, LocalDate dataEntrega, Long idPessoa, Long idInstituicao, Integer idTipoFalta, Integer idArqAnexo, LocalDate dataInicioVigencia,
    		LocalDate dataFinalVigencia, Long idFuncionario, Integer idTipoAtestado, Long numeroDias, Long idContrato) {

		super();
        this.id = id;
        this.dataEntrega = exigirNaoNulo(dataEntrega, "Data Entrega"); 
        this.idPessoa = exigirNaoNulo(idPessoa, "Pessoa");
        this.idInstituicao = exigirNaoNulo(idInstituicao, "Instituição"); 
        this.idTipoFalta = exigirNaoNulo(idTipoFalta, "Tipo de Falta");
        this.idArqAnexo = idArqAnexo == null || idArqAnexo == 0 ? null : idArqAnexo;
        this.dataInicioVigencia = exigirNaoNulo(dataInicioVigencia, "Data Inicio Vigência"); 
        this.dataFinalVigencia = exigirNaoNulo(dataFinalVigencia, "Data Final Vigência"); 
        this.idFuncionario = idFuncionario; 
        this.idTipoAtestado = idTipoAtestado != null ? idTipoAtestado : EnumTipoAtestado.ATESTADO_MEDICO.id(); 
        this.numeroDias = numeroDias != null ? numeroDias : ChronoUnit.DAYS.between(dataInicioVigencia, dataFinalVigencia);
        this.idContrato = idContrato;
      
	}
}