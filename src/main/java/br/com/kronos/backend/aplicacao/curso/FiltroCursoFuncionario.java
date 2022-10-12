package br.com.kronos.backend.aplicacao.curso;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroCursoFuncionario {

	private Long id;  
    private Long idFuncionario;
    private Long idCurso;
    private Long idTipoFuncao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}