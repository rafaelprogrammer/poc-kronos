package br.com.kronos.backend.aplicacao.contrato.api;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.kronos.backend.aplicacao.contrato.EnumTipoSituacaoContrato;
import br.com.kronos.backend.aplicacao.instituicao.EnumTipoComposicaoValor;
import br.com.kronos.backend.aplicacao.instituicao.api.ConvenioContratoDTO;
import br.com.kronos.backend.aplicacao.matricula.api.CreditoContratoDTO;
import br.com.kronos.backend.aplicacao.contrato.EnumTipoFormaPagamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ContratoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private Integer numero;
    private Integer ano;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate data;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataVencimentoPrimeiraParcela;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataVencimentoUltimaParcela;
    private Integer diaVencimentoParcela;
    private Integer numeroParcelas;
    private Double percentualJurosAtraso;
    private Double percentualMultaAtraso;
    private Double percentualBomPagador;
    private Double valorTotalOriginal;
    private String observacao;
    private Integer idTipoSituacaoContrato;
    private Integer idTipoFormaPagamento;
    private Long idInstituicao;
    private Long idPeriodoExecucao;
    private String nomePeriodo;
    private Long idMatricula;
    private Long idResponsavelFinanceiro;
    private Long idEmpresaOrigem;
    private String nomeEmpresaOrigem;
    private Double valorDescontoConvenio;
    private Double percentualDescontoConvenio;
    private Double valorTotalFinal;
    private Integer idTipoResultado;
    private boolean dependencia;
    private Integer idTipoComposicaoValor;
    private Double valorTotalPadrao;
    private Double valorTotalPendencia;
    private List<CreditoContratoDTO> creditosContratos;
    private List<ConvenioContratoDTO> conveniosContratos;
    private List<ParcelaDTO> parcelas;
    
	
	public String getNomeTipoSituacaoContrato() {
		return EnumTipoSituacaoContrato.porId(this.idTipoSituacaoContrato).label();
    }
    
    public String getNomeTipoFormaPagamento() {
		return this.idTipoFormaPagamento != null ? EnumTipoFormaPagamento.porId(this.idTipoFormaPagamento).label() : null;
    }
    
    public String getNomeTipoComposicaoValor() {
		return this.idTipoComposicaoValor != null ? EnumTipoComposicaoValor.porId(this.idTipoComposicaoValor).label() : null;
    }
    	
}

