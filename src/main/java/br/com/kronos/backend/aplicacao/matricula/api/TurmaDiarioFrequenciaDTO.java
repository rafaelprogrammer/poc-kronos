package br.com.kronos.backend.aplicacao.matricula.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TurmaDiarioFrequenciaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private Long idTurma;
    private Long idDisciplina;
    private String nome;
    private Long idNivel;
    private Long idPeriodo;
    private Long idPeriodoExecucao;
    private Long idFaixaAno;
    private Long idCurso;
    private int anoTurma;
}
