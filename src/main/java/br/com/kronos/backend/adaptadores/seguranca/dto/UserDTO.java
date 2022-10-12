package br.com.kronos.backend.adaptadores.seguranca.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 91901774547107674L;
	
	private Long idUsuario;
    private Long idInstituicao;
    private Long idPessoa;
    private String nome;
    private String instituicao;
    private boolean instituicaoMantenedora;
	private boolean instituicaoAtiva;

	UserDTO() {
	}


}
