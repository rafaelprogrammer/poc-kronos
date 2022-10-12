
package br.com.kronos.backend.aplicacao.calendario;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

import br.com.kronos.backend.aplicacao.validador.Validacoes;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Evento {
    
    private long id;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFinal;
    private boolean diaLetivo;
    private String descricao;
    private Long idCategoriaEvento;
    private Long idCalendario;
    

	@Builder
    public Evento(long id,  LocalDateTime dataHoraInicio, LocalDateTime dataHoraFinal, boolean diaLetivo, String descricao, 
                  Long idCategoriaEvento, Long idCalendario) {

		super();
        this.id = id;
        this.dataHoraInicio = requireNonNull(dataHoraInicio, "data hora início é obrigatória");
        this.dataHoraFinal = requireNonNull(dataHoraFinal, "data hora final é obrigatória");
        this.diaLetivo = diaLetivo; 
        this.descricao = Validacoes.exigirTamanho(descricao, "descricao é obrigatório", 1, 100); 
        this.idCategoriaEvento = requireNonNull(idCategoriaEvento, "idCategoriaEvento é obrigatório"); 
        this.idCalendario = requireNonNull(idCalendario, "idCalendario é obrigatório"); 
	}
}