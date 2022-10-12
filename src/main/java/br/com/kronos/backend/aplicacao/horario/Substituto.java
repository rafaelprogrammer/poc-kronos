
package br.com.kronos.backend.aplicacao.horario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Substituto {
    
    private Long id;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private Long idFuncionario;
    private Long idHorario;

	@Builder
    public Substituto(Long id, LocalDate dataInicial, LocalDate dataFinal, Long idFuncionario, Long idHorario) {

		super();
        this.id = id;
        this.dataInicial = exigirNaoNulo(dataInicial, "Data inicial"); 
        this.dataFinal = exigirNaoNulo(dataFinal, "Data final"); 
        this.idFuncionario = exigirNaoNulo(idFuncionario, "Funcionário (professor)"); 
        this.idHorario = exigirNaoNulo(idHorario, "Horário"); 
	}
}