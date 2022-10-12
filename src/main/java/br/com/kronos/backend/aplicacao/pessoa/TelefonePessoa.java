package br.com.kronos.backend.aplicacao.pessoa;

import static java.util.Objects.requireNonNull;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TelefonePessoa {
	
    private int id;
    private int numero;
	private boolean principal;	
	private boolean whatApp;
    private Long idPessoa;
    private Integer idCidade;
    private Integer idOperadora;
    private Integer idTipoUso;
    private Integer idTipoTelefone;
  	
	@Builder
    public TelefonePessoa(int id, int numero, boolean principal, boolean whatApp, Long idPessoa, Integer idCidade, 
                           Integer idOperadora, Integer idTipoUso, Integer idTipoTelefone) {
        super();
        this.id = id;
        this.numero = requireNonNull(numero, "numero é obrigatório");		
        this.principal = requireNonNull(principal, "principal é obrigatório");	
        this.whatApp = requireNonNull(whatApp, "whatApp é obrigatório");
        this.idPessoa = requireNonNull(idPessoa, "idEmpresa é obrigatório");	
        this.idCidade = requireNonNull(idCidade, "idCidade é obrigatório");	
        this.idOperadora = requireNonNull(idOperadora, "idOperadora é obrigatório");	
        this.idTipoUso = requireNonNull(idTipoUso, "idTipoUso é obrigatório");
        this.idTipoTelefone = requireNonNull(idTipoTelefone, "idTipoTelefone é obrigatório");	
        

	}

}