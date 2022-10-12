package br.com.kronos.backend.aplicacao.matricula;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Historico {
    
    private Long id;
    private Long idMatricula;
    private Long idEmpresa;
    private Long idCredito;
    private int ano;
    private Double nota;
    private String mencao;
    private String disciplina;
    private String periodo;

	@Builder
    public Historico(Long id, Long idMatricula, Long idEmpresa, Long idCredito, int ano, Double nota, 
                    String mencao, String disciplina, String periodo) {

		super();
        this.id = id;
        this.idMatricula = exigirNaoNulo(idMatricula, "Matrícula");  
        this.idEmpresa = exigirNaoNulo(idEmpresa, "Empresa");
        this.idCredito = idCredito; 
        this.ano = exigirNaoNulo(ano, "Ano");
        this.nota = nota;  
        this.mencao = mencao; 
        this.disciplina = disciplina; 
        this.periodo = periodo;  
	}
}