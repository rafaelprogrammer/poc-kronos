package br.com.kronos.backend.aplicacao.matricula;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MatriculaDocumento {
    
    private Long id;
    private LocalDate data;
    private Long idMatricula;
    private Long idDocumento;

	@Builder
    public MatriculaDocumento(Long id, LocalDate data, Long idMatricula, Long idDocumento) {

		super();
        this.id = id;
        this.data = exigirNaoNulo(data, "Data");  
        this.idMatricula = exigirNaoNulo(idMatricula, "Matrícula");
        this.idDocumento = exigirNaoNulo(idDocumento, "Documento");  
	}
}