package br.com.kronos.backend.aplicacao.matricula.api;

import java.io.Serializable;

import br.com.kronos.backend.aplicacao.matricula.EnumTipoCredito;
import br.com.kronos.backend.aplicacao.matricula.EnumTipoPrograma;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreditoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private double valor;
    private int cargaHorariaTotal;
    private int cargaHorariaPresencial;
    private int cargaHorariaDistancia;
    private int totalMinutosAusencia;
    private double percentualAusencia;
    private Long idContrato;
    private Long idDisciplina; 
    private Long idTurma;
    private int idTipoCredito;
    private int idTipoPrograma;
    private double notaFinalNormal;
    private double notaFinalExame;
    private double notaConselho;
    private double notaResultadoFinal;
    private int idTipoMencaoFinal;
    private boolean pendencia;

	public String getNomeTipoCredito() {
		return EnumTipoCredito.porId(this.idTipoCredito).label();
    }
	
	public String getNomeTipoProgrma() {
		return EnumTipoPrograma.porId(this.idTipoCredito).label();
    }

    
}

