package br.com.kronos.backend.aplicacao.empresa;

import static java.util.Objects.requireNonNull;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TelefoneEmpresa {
	
    private Long id;
    private int numero;
	private boolean principal;	
	private boolean whatApp;
    private Long idEmpresa;
    private Long idCidade;
    private Long idOperadora;
    private Long idTipoUso;
    private Long idTipoTelefone;
  	
	@Builder
    public TelefoneEmpresa(Long id, int numero, boolean principal, boolean whatApp, Long idEmpresa, Long idCidade, 
                           Long idOperadora, Long idTipoUso, Long idTipoTelefone) {
        super();
        this.id = id;
        this.numero = requireNonNull(numero, "numero é obrigatório");		
        this.principal = requireNonNull(principal, "principal é obrigatório");	
        this.whatApp = requireNonNull(whatApp, "whatApp é obrigatório");
        this.idEmpresa = requireNonNull(idEmpresa, "idEmpresa é obrigatório");	
        this.idCidade = requireNonNull(idCidade, "idCidade é obrigatório");	
        this.idOperadora = requireNonNull(idOperadora, "idOperadora é obrigatório");	
        this.idTipoUso = requireNonNull(idTipoUso, "idTipoUso é obrigatório");
        this.idTipoTelefone = requireNonNull(idTipoTelefone, "idTipoTelefone é obrigatório");	
        

	}

}