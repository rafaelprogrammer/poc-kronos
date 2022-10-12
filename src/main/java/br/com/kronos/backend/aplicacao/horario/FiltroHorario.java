package br.com.kronos.backend.aplicacao.horario;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroHorario {

    private Long id;  
    private Boolean regular;
    private Long idFuncionario;
    private Long idDisciplina;
    private Long idTurma;
    private Integer idTipoDiaSemana;
    private Integer idTipoRegimeLetivo;
    private Integer idTipoMatrizHorario;
    private boolean inclusao;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
	private Integer qtdTotal;
	private Integer numeroPagina;
}