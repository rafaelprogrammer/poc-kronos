package br.com.kronos.backend.aplicacao.empresa.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class EmpresaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String cnpj;	
	private String inscricaoEstadual;
    private String emailContato;
    private String nomeContato;
	private String nomeFantasia;
    private String razaoSocial;
    private String site;

}
