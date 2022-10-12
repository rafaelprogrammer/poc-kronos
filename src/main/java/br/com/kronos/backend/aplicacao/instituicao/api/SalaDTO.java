package br.com.kronos.backend.aplicacao.instituicao.api;
import java.io.Serializable;

import br.com.kronos.backend.aplicacao.instituicao.EnumTipoSala;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SalaDTO implements Serializable {

    private static final long serialVersionUID = 1L;   
    private Long id;
    private String bloco;
    private String ala;
    private String andar;
    private String numero;
    private int lotacao;
    private boolean pollReserva;
	private boolean ativo;
    private Long idInstitucicao;
    private int idTipoSala;
    
    
	public String getNomeTipoDesconto() {
		return EnumTipoSala.porId(this.idTipoSala).label();
    }
    	
}