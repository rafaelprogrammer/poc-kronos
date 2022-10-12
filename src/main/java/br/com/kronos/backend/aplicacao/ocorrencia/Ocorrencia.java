
package br.com.kronos.backend.aplicacao.ocorrencia;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.kronos.backend.aplicacao.validador.Validacoes;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Ocorrencia {
    
    private Long id;
    private LocalDate data; 
    private LocalTime hora;
    private String registro; 
    private Long idTurma;
    private Long idTipoOcorrencia;
    private Long idMatricula;
    private Long idFuncionarioRelator;
    private Long idFuncionarioRegistro;
    private LocalDate dataCiencia;
    private Long idResponsavelCiencia;
    private boolean anulado;
    private LocalDate dataAnulacao; 
    private String motivoAnulacao;
    private Long idFuncionarioAnulacao;

	@Builder
    public Ocorrencia(Long id, LocalDate data, LocalTime hora, String registro, Long idTurma, Long idTipoOcorrencia,  Long idMatricula,
                      Long idFuncionarioRelator, Long idFuncionarioRegistro, LocalDate dataCiencia, Long idResponsavelCiencia, 
                      boolean anulado, LocalDate dataAnulacao, String motivoAnulacao, Long idFuncionarioAnulacao) {

		super();
        this.id = id;
        this.data = exigirNaoNulo(data, "Data"); 
        this.hora = exigirNaoNulo(hora, "Hora"); 
        this.registro = Validacoes.exigirTamanho(registro, "Registro da ocorrência", 1, 500);       
        this.idTurma = exigirNaoNulo(idTurma, "Turma"); 
        this.idTipoOcorrencia = exigirNaoNulo(idTipoOcorrencia, "Tipo de ocorrência"); 
        this.idMatricula = exigirNaoNulo(idMatricula, "Matrícula (aluno)"); 
        this.idFuncionarioRelator = exigirNaoNulo(idFuncionarioRelator, "Funcionário relator"); 
        this.idFuncionarioRegistro = exigirNaoNulo(idFuncionarioRegistro, "Funcionário de registro"); 
        this.dataCiencia = dataCiencia; 
        this.idResponsavelCiencia = idResponsavelCiencia !=null && idResponsavelCiencia > 0 ? idResponsavelCiencia : null; 
        this.anulado = exigirNaoNulo(anulado, "Anulado"); 
        this.dataAnulacao = dataAnulacao; 
        this.motivoAnulacao = motivoAnulacao; 
        this.idFuncionarioAnulacao = idFuncionarioAnulacao !=null && idFuncionarioAnulacao > 0 ? idFuncionarioAnulacao : null; 
      
	}
}