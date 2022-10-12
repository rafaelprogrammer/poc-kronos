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
public class CreditoContratoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long idContrato;
    private Long idDisciplina;
    private Long idMatricula;
    private Long idPeriodoExecucao;
    private String nomeDisciplina;
    private boolean disciplinaObrigatoria;
    private Integer cargaHorariaTotal;
    private Integer cargaHorariaPresencial;
    private Integer cargaHorariaDistancia;
    private boolean obrigatoria;
    private Double valor;
    private String siglaPeriodo;
    private Long idTurma;
    private String anoTurma;
    private String siglaTurma;
    private Integer idTipoCredito;
    private Integer idTipoPrograma;
    private boolean pendencia;

	public String getNomeTipoCredito() {
		return EnumTipoCredito.porId(this.idTipoCredito).label();
    }
	
	public String getNomeTipoPrograma() {
		return EnumTipoPrograma.porId(this.idTipoCredito).label();
    }

    
}

