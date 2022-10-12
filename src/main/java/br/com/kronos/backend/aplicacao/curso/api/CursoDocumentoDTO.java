package br.com.kronos.backend.aplicacao.curso.api;

import java.io.Serializable;

import br.com.kronos.backend.aplicacao.pessoa.EnumTipoDocumento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CursoDocumentoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private boolean original;
    private boolean autenticacao;
    private Integer numeroCopia;
    private Long idCurso;
    private Integer idTipoDocumento;

	
	public String getNomeTipoDocumento() {
		return EnumTipoDocumento.porId(this.idTipoDocumento).label();
    }
    	
}
