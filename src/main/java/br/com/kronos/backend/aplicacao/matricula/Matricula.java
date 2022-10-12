package br.com.kronos.backend.aplicacao.matricula;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Matricula {
	
	public static final String COMBO_CACHE_NAME_OCORRENCIAS_POR_PESSOA = "combo_matriculas_ocorrencias_por_pessoa";
    
    private long id;
    private LocalDate data;
    private Integer anoInicioCurso;
    private Integer semestreInicioCurso;
    private LocalDate dataInicioCurso;
    private Integer anoConclusaoCurso;
    private Integer semestreConclusaoCurso;
    private LocalDate dataConclusaoCurso;
    private LocalDate dataColacaoGrau;
    private Long idPessoa;
    private Long idCurso;
    private Integer idTipoSituacaoMatricula;
    private Integer idTipoResultado;
    private Long idEmpresaOrigem;

	@Builder
	public Matricula(long id, LocalDate data, Integer anoInicioCurso, Integer semestreInicioCurso, LocalDate dataInicioCurso, Integer anoConclusaoCurso, 
			Integer semestreConclusaoCurso, LocalDate dataConclusaoCurso, LocalDate dataColacaoGrau,  Long idPessoa, Long idCurso, 
                     Integer idTipoSituacaoMatricula, Integer idTipoResultado, Long idEmpresaOrigem) {

        this.id = id;
        this.data = exigirNaoNulo(data, "Data");
        this.anoInicioCurso = exigirNaoNulo(anoInicioCurso, "Ano de início do curso");
        this.semestreInicioCurso = exigirNaoNulo(semestreInicioCurso, "Semestre de início do curso");  
        this.dataInicioCurso = exigirNaoNulo(dataInicioCurso, "Data de início do curso"); 
        this.anoConclusaoCurso = anoConclusaoCurso; 
        this.semestreConclusaoCurso = semestreConclusaoCurso;  
        this.dataConclusaoCurso = dataConclusaoCurso; 
        this.dataColacaoGrau = dataColacaoGrau;  
        this.idPessoa = exigirNaoNulo(idPessoa, "Aluno");
        this.idCurso = exigirNaoNulo(idCurso, "Curso");  
        this.idTipoSituacaoMatricula = exigirNaoNulo(idTipoSituacaoMatricula, "Situação da matrícula");  
        this.idTipoResultado = idTipoResultado; 
        this.idEmpresaOrigem = idEmpresaOrigem;  

	}
}
