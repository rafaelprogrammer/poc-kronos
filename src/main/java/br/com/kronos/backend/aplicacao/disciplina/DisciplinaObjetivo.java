package br.com.kronos.backend.aplicacao.disciplina;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DisciplinaObjetivo {
    
    private long id;
    private Long idDisciplina;
    private Long idObjetivo;
    private Long idSubFase;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFinalVigencia;
    
	@Builder
	public DisciplinaObjetivo(long id, Long idDisciplina, Long idObjetivo, LocalDate dataInicioVigencia, LocalDate dataFinalVigencia, Long idSubFase) {

        this.id = id;
        this.idDisciplina = exigirNaoNulo(idDisciplina, "Disciplina");
        this.idObjetivo = exigirNaoNulo(idObjetivo, "Objetivo");;
        this.idSubFase = idSubFase;
        this.dataInicioVigencia = exigirNaoNulo(dataInicioVigencia, "Data de início da vigência"); 
        this.dataFinalVigencia = dataFinalVigencia; 
	}
}
