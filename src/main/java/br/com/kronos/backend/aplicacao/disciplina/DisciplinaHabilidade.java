package br.com.kronos.backend.aplicacao.disciplina;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DisciplinaHabilidade {
    
    private long id;
    private Long idDisciplina;
    private Long idHabilidade;
    private Long idSubFase;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFinalVigencia;
    
	@Builder
	public DisciplinaHabilidade(long id, Long idDisciplina, Long idHabilidade, LocalDate dataInicioVigencia, LocalDate dataFinalVigencia, Long idSubFase) {

        this.id = id;
        this.idDisciplina = exigirNaoNulo(idDisciplina, "Disciplina");
        this.idHabilidade = exigirNaoNulo(idHabilidade, "habilidade");
        this.idSubFase = idSubFase;
        this.dataInicioVigencia = exigirNaoNulo(dataInicioVigencia, "Data de início da vigência"); 
        this.dataFinalVigencia = dataFinalVigencia; 
	}
}
