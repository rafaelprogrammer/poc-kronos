package br.com.kronos.backend.aplicacao.matricula.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TurmaDTO implements Serializable {

    private static final long serialVersionUID = 1L;   
    private long id;
    private int ano;
    private String sigla;
    private boolean ativa;
    private boolean aberta;
    private boolean encerrada;
    private Long idPeriodoExecucao;
    private String tipoPeriodo;
    private Long idCalendario;
    private String calendario;
    private Long idInstituicao;
    private Long idCurso;
    private Integer idTipoTurno;
    private String turno;

    
}

