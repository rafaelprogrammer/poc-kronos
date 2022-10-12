package br.com.kronos.backend.aplicacao.matricula;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Credito {
    
    private Long id;
    private Double valor;
    private Integer cargaHorariaTotal;
    private Integer cargaHorariaPresencial;
    private Integer cargaHorariaDistancia;
    private int totalMinutosAusencia;
    private double percentualAusencia;
    private Long idContrato;
    private Long idDisciplina; 
    private Long idTurma;
    private Integer idTipoCredito;
    private Integer idTipoPrograma;
    private Double notaFinalNormal;
    private Double notaFinalExame;
    private Double notaConselho;
    private Double notaResultadoFinal;
    private Integer idTipoMencaoFinal;
    private boolean pendencia;

	@Builder
    public Credito(Long id, Double valor, Integer cargaHorariaTotal, Integer cargaHorariaPresencial, Integer cargaHorariaDistancia, 
                   int totalMinutosAusencia,  double percentualAusencia, Long idContrato, Long idDisciplina, Long idTurma,  
                   Integer idTipoCredito, Integer idTipoPrograma, Double notaFinalNormal, Double notaFinalExame, 
                   Double notaConselho, Double notaResultadoFinal, Integer idTipoMencaoFinal, boolean pendencia) {

		super();
        this.id = id;
        this.valor = exigirNaoNulo(valor, "Valor");  
        this.cargaHorariaTotal = exigirNaoNulo(cargaHorariaTotal, "Carga horária total");
        this.cargaHorariaPresencial = exigirNaoNulo(cargaHorariaPresencial, "Carga horária presencial");
        this.cargaHorariaDistancia = exigirNaoNulo(cargaHorariaDistancia, "Carga horária a distância"); 
        this.totalMinutosAusencia = totalMinutosAusencia;  
        this.percentualAusencia = percentualAusencia;
        this.idContrato = exigirNaoNulo(idContrato, "Contrato");
        this.idDisciplina = exigirNaoNulo(idDisciplina, "Disciplina"); 
        this.idTurma = exigirNaoNulo(idTurma, "Turma"); 
        this.idTipoCredito = exigirNaoNulo(idTipoCredito, "Tipo de crédito"); 
        this.idTipoPrograma = exigirNaoNulo(idTipoPrograma, "Tipo de programa"); 
        this.notaFinalNormal = notaFinalNormal; 
        this.notaFinalExame = notaFinalExame;
        this.notaConselho = notaConselho;
        this.notaResultadoFinal = notaResultadoFinal;
        this.idTipoMencaoFinal = idTipoMencaoFinal;
        this.pendencia = exigirNaoNulo(pendencia, "Pendência"); 
        
	}
}