package br.com.kronos.backend.aplicacao.matricula;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroTurmaAlunoFuncao {

    private Long id;     
    private Long idMatricula;
    private Long idTurma;
    private Long idTipoFuncao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}