package br.com.kronos.backend.aplicacao.disciplina.api;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DisciplinaFuncionarioDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private Long idDisciplina;
    private Long idFuncionario;
    private boolean criacao;
    
    
//  
//  public String getNomeDisciplina() {
//   	return 
//   }
//  
//  public String getNomeFuncionario() {
//   	return 
//   }    

}
