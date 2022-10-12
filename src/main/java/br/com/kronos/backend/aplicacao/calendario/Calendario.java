
package br.com.kronos.backend.aplicacao.calendario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Calendario {
    
    private long id;
    private Integer numero;
    private Integer ano;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
    private LocalDate dataInicioAtribuicaoCredito;
    private LocalDate dataFinalAtribuicaoCredito;
    private LocalDate dataInicioAnoLetivo;
    private LocalDate dataFinalAnoLetivo;
    private String descricao;
    private LocalDate dataAprovacao;
    private Long idInstituicao;
    private Long idFuncionarioAprovacao;
    private Integer idTipoSituacaoCalendario;
    private LocalDate dataConclusao;
    private Long idFuncionarioElaboracao;
  
	@Builder
    public Calendario(long id, Integer numero, Integer ano, LocalDate dataInicio, LocalDate dataFinal, LocalDate dataInicioAtribuicaoCredito, 
                      LocalDate dataFinalAtribuicaoCredito, LocalDate dataInicioAnoLetivo, LocalDate dataFinalAnoLetivo, 
                      String descricao, LocalDate dataAprovacao, Long idInstituicao, Long idFuncionarioAprovacao, 
                      Integer idTipoSituacaoCalendario,  LocalDate dataConclusao, Long idFuncionarioElaboracao) {

        this.id = id;
        this.numero = numero; 
        this.ano = exigirNaoNulo(ano, "Ano"); 
        this.dataInicio = exigirNaoNulo(dataInicio, "Data de início"); 
        this.dataFinal = exigirNaoNulo(dataFinal, "Data final"); 
        this.dataInicioAtribuicaoCredito = exigirNaoNulo(dataInicioAtribuicaoCredito, "Data de início de matricula"); 
        this.dataFinalAtribuicaoCredito = exigirNaoNulo(dataFinalAtribuicaoCredito, "Data final de matricula"); 
        this.dataInicioAnoLetivo = exigirNaoNulo(dataInicioAnoLetivo, "Data de inicio do ano letivo"); 
        this.dataFinalAnoLetivo = exigirNaoNulo(dataFinalAnoLetivo, "Data final do ano letivo"); 
        this.descricao = exigirTamanho(descricao, "Descrição", 1, 500); 
        this.dataAprovacao = dataAprovacao;
        this.idInstituicao = exigirNaoNulo(idInstituicao, "Instituição"); 
        this.idFuncionarioAprovacao = idFuncionarioAprovacao; 
        this.idTipoSituacaoCalendario = id == 0 ? EnumTipoSituacaoCalendario.EM_ELABORACAO.id() : 
        		exigirNaoNulo(idTipoSituacaoCalendario, "Situação do calendário"); 
        this.dataConclusao = dataConclusao;
        this.idFuncionarioElaboracao = idFuncionarioElaboracao;
	}
}
