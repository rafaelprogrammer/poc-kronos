package br.com.kronos.backend.aplicacao.matricula.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class HistoricoDTO implements Serializable {

    private static final long serialVersionUID = 1L; 
    private Long id;
    private Long idMatricula;
    private Long idEmpresa;
    private Long idCredito;
    private int ano;
    private Double nota;
    private String mencao;
    private String disciplina;
    private String periodo;
    
}
