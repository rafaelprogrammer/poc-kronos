package br.com.kronos.backend.aplicacao.pessoa.api;

import java.io.Serializable;

import br.com.kronos.backend.aplicacao.pessoa.EnumTipoTalento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TalentoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private Long idPessoa;
    private int idTipoTalento;
    private boolean criacao;
    
    
    public String getNomeTipoTalento() {
    	return EnumTipoTalento.porId(this.idTipoTalento).label();
    }
	
}