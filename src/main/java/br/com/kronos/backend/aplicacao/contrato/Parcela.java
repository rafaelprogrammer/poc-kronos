package br.com.kronos.backend.aplicacao.contrato;

import static br.com.kronos.backend.aplicacao.util.DecimalUtil.converterDoubleDoisDecimais;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Parcela {
    
    private long id;
    private Integer numero;
    private LocalDate dataVencimento;
    private Double valorOriginal;
    private LocalDate dataPagamento;
    private Double valorJuros;
    private Double valorMulta;
    private Double valorDesconto;
    private Double valorPagamento;
    private String observacao;
    private Integer idTipoFormaPagamento;
    private Long idContrato;

	@Builder
    public Parcela(long id, Integer numero, LocalDate dataVencimento, Double valorOriginal, LocalDate dataPagamento, Double valorJuros, 
                   Double valorMulta, Double valorDesconto, Double valorPagamento, String observacao, 
                   Integer idTipoFormaPagamento, Long idContrato) {

		super();
        this.id = id;
        this.numero = exigirNaoNulo(numero, "Número");  
        this.dataVencimento = exigirNaoNulo(dataVencimento, "data do vencimento");  
        exigirNaoNulo(valorOriginal, "Valor original");
        this.valorOriginal = valorOriginal != null ? converterDoubleDoisDecimais(valorOriginal) : 0.0;
        this.dataPagamento = dataPagamento;
        this.valorJuros = valorJuros != null ? converterDoubleDoisDecimais(valorJuros) : 0.0;
        this.valorMulta = valorMulta != null ? converterDoubleDoisDecimais(valorMulta) : 0.0; 
        this.valorDesconto = valorDesconto != null  ? converterDoubleDoisDecimais(valorDesconto) : 0.0;
        //TODO colocar a regra para gerar o valor de pagamento
        this.valorPagamento = valorPagamento != null ? converterDoubleDoisDecimais(valorPagamento) : 0.0;
        this.observacao = observacao;
        this.idTipoFormaPagamento = exigirNaoNulo(idTipoFormaPagamento, "Forma de pagamento");
        this.idContrato = exigirNaoNulo(idContrato, "Contrato"); 
	}
}
