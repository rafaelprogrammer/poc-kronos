package br.com.kronos.backend.aplicacao.contrato;

import static br.com.kronos.backend.aplicacao.util.DecimalUtil.converterDoubleDoisDecimais;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Contrato {
    
    private long id;
    private Integer numero;
    private Integer ano;
    private LocalDate data;
    private LocalDate dataVencimentoPrimeiraParcela;
    private LocalDate dataVencimentoUltimaParcela;
    private Integer diaVencimentoParcela;
    private Integer numeroParcelas;
    private Double percentualJurosAtraso;
    private Double percentualMultaAtraso;
    private Double percentualBomPagador;
    private double valorTotalOriginal;
    private String observacao;
    private Integer idTipoSituacaoContrato;
    private Integer idTipoFormaPagamento;
    private Long idInstituicao;
    private Long idPeriodoExecucao;
    private Long idMatricula;
    private Long idResponsavelFinanceiro;
    private Long idEmpresaOrigem;
    private Double valorDescontoConvenio;
    private Double percentualDescontoConvenio;
    private Double valorTotalFinal;
    private Integer idTipoResultado;
    private boolean dependencia;
    private Integer idTipoComposicaoValor;
    private double valorTotalPadrao;
    private double valorTotalPendencia;
    

	@Builder
    public Contrato(long id, Integer numero, Integer ano, LocalDate data, LocalDate dataVencimentoPrimeiraParcela, 
                    LocalDate dataVencimentoUltimaParcela, Integer diaVencimentoParcela, Integer numeroParcelas, Double percentualJurosAtraso, 
                    Double percentualMultaAtraso, Double percentualBomPagador, double valorTotalOriginal, String observacao, Integer idTipoSituacaoContrato, 
                    Integer idTipoFormaPagamento, Long idInstituicao, Long idPeriodoExecucao, Long idMatricula, Long idResponsavelFinanceiro, 
                    Long idEmpresaOrigem, Double valorDescontoConvenio, Double percentualDescontoConvenio, Double valorTotalFinal, 
                    Integer idTipoResultado, boolean dependencia, Integer idTipoComposicaoValor, double valorTotalPadrao, 
                    double valorTotalPendencia ) {

        this.id = id;
        this.numero = numero;  
        this.ano = exigirNaoNulo(ano, "Ano");  
        this.data = exigirNaoNulo(data, "Data");
        this.dataVencimentoPrimeiraParcela = dataVencimentoPrimeiraParcela;
        this.dataVencimentoUltimaParcela = dataVencimentoUltimaParcela;
        this.diaVencimentoParcela = diaVencimentoParcela;
        this.numeroParcelas = numeroParcelas;
        this.percentualJurosAtraso = percentualJurosAtraso != null ? percentualJurosAtraso : 0.0;
        this.percentualMultaAtraso = percentualMultaAtraso != null ? percentualMultaAtraso : 0.0;
        this.percentualBomPagador = percentualBomPagador != null ? percentualBomPagador : 0.0;
        this.valorTotalOriginal = converterDoubleDoisDecimais(valorTotalOriginal);
        this.observacao = exigirTamanho(observacao, "Observação", 0, 500);
        this.idTipoSituacaoContrato = numero != null ? exigirNaoNulo(idTipoSituacaoContrato, "Situação do contrato") : EnumTipoSituacaoContrato.PRE_MATRICULA.id();
        this.idTipoFormaPagamento = idTipoFormaPagamento;
        this.idInstituicao = exigirNaoNulo(idInstituicao, "Instituição"); 
        this.idPeriodoExecucao = exigirNaoNulo(idPeriodoExecucao, "Período"); 
        this.idMatricula = exigirNaoNulo(idMatricula, "Matrícula"); 
        this.idResponsavelFinanceiro = idResponsavelFinanceiro;
        this.idEmpresaOrigem = idEmpresaOrigem;
        this.percentualDescontoConvenio = percentualDescontoConvenio;
        alterarValorDescontoConvenio(valorDescontoConvenio);
        alterarValorTotalFinal(valorTotalFinal);
        this.idTipoResultado = idTipoResultado;
        this.dependencia = exigirNaoNulo(dependencia, "Possui dependências");
        this.idTipoComposicaoValor = exigirNaoNulo(idTipoComposicaoValor, "Tipo de composição de valor");
        this.valorTotalPadrao = converterDoubleDoisDecimais(valorTotalPadrao);
        this.valorTotalPendencia = converterDoubleDoisDecimais(valorTotalPendencia);
	}
	
	void alterarValorTotalFinal(Double valorTotalFinal) {
		this.valorTotalFinal = valorDescontoConvenio != null ? valorTotalOriginal - valorDescontoConvenio : valorTotalOriginal;
		this.valorTotalFinal = converterDoubleDoisDecimais(this.valorTotalFinal);
	}
	
	void alterarValorDescontoConvenio(Double valorDescontoConvenio) {
		if (percentualDescontoConvenio != null && valorTotalOriginal > 0) {
			this.valorDescontoConvenio = converterDoubleDoisDecimais(percentualDescontoConvenio * valorTotalOriginal / 100);
		} else {
			this.valorDescontoConvenio = valorDescontoConvenio != null ? converterDoubleDoisDecimais(valorDescontoConvenio) : 0.0;
		}
	}
}
