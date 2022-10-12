
package br.com.kronos.backend.aplicacao.horario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HoraAtividade {
    
    private Long id;
    private LocalTime horaInicial;
    private LocalTime horaFinal;
    private int tempoCargaHoraria;
    private int tempoTrabalhoComputado;
    private int idTipoTurno;
    private Long idInstituicao;
    private Integer idTipoMatrizHorario;

	@Builder
    public HoraAtividade(Long id,  LocalTime horaInicial, LocalTime horaFinal, int tempoCargaHoraria, int tempoTrabalhoComputado, 
    		int idTipoTurno, Long idInstituicao, Integer idTipoMatrizHorario) {

		super();
        this.id = id;
        this.horaInicial = exigirNaoNulo(horaInicial, "Hora inicial"); 
        this.horaFinal = exigirNaoNulo(horaFinal, "Hora final"); 
        this.tempoCargaHoraria = exigirNaoNulo(tempoCargaHoraria, "Tempo de carga horária"); 
        this.tempoTrabalhoComputado = exigirNaoNulo(tempoTrabalhoComputado, "Tempo de trabalho computado"); 
        this.idTipoTurno = exigirNaoNulo(idTipoTurno, "Turno"); 
        this.idInstituicao = exigirNaoNulo(idInstituicao, "Instituição");
        this.idTipoMatrizHorario = exigirNaoNulo(idTipoMatrizHorario, "Tipo Matriz Horário");
	}
}
