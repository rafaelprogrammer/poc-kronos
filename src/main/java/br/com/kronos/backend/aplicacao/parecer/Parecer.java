
package br.com.kronos.backend.aplicacao.parecer;


import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Parecer {
    
    private Long id;
    private Long idSubFaseExecucao; 
    private Long idMatricula;
    private Long idTurma;
    private LocalDate data;
    private Long idFuncionario;
    private StringBuffer texto;   //Trata-se de umtexto grande formatado - duvida sobre StringBuffer
    private boolean rascunho;

	@Builder
    public Parecer (Long id, Long idSubFaseExecucao, Long idMatricula, Long idTurma, LocalDate data, Long idFuncionario, 
                    StringBuffer texto, boolean rascunho) {

		super();
        this.id = id;
        this.idSubFaseExecucao = exigirNaoNulo(idSubFaseExecucao, "Sub-fase (bimestre) de execução"); 
        this.idMatricula = exigirNaoNulo(idMatricula, "Matrícula (aluno)");
        this.idTurma = exigirNaoNulo(idTurma, "Turma"); 
        this.data = exigirNaoNulo(data, "Data"); 
        this.idFuncionario = exigirNaoNulo(idFuncionario, "Funcionário (professor)");
        this.texto = exigirNaoNulo(texto, "Texto do parecer");
        this.rascunho = exigirNaoNulo(rascunho, "Rascunho");  
	}
}