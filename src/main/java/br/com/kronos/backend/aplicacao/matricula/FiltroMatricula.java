package br.com.kronos.backend.aplicacao.matricula;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroMatricula {

    private Long id;     
    private LocalDate data;
    private Integer anoInicioCurso;
    private Integer semestreInicioCurso;  
    private Integer anoConclusaoCurso;
    private Integer semestreConclusaoCurso;    
    private Long idPessoa;
    private Integer numeroRegistroPessoa;
    private String cpfPessoa;
    private String nomePessoa;
    private Long idCurso;
    private Integer idTipoSituacaoMatricula;
    private Integer idTipoResultado;
    private Long idEmpresaOrigem;
	private Integer qtdTotal;
	private Integer numeroPagina;
}