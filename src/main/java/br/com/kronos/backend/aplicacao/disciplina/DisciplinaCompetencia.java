package br.com.kronos.backend.aplicacao.disciplina;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DisciplinaCompetencia {
    
    private long id;
    private Long idDisciplina;
    private Long idCompetencia;
    private Long idSubFase;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFinalVigencia;
    
	@Builder
	public DisciplinaCompetencia(long id, Long idDisciplina, Long idCompetencia, LocalDate dataInicioVigencia, LocalDate dataFinalVigencia, Long idSubFase) {

        this.id = id;
        this.idDisciplina = exigirNaoNulo(idDisciplina, "Disciplina");
        this.idCompetencia = exigirNaoNulo(idCompetencia, "Competência");
        this.idSubFase = idSubFase;
        this.dataInicioVigencia = exigirNaoNulo(dataInicioVigencia, "Data de início da vigência"); 
        this.dataFinalVigencia = dataFinalVigencia; 
	}
}

