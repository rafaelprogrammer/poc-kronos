package br.com.kronos.backend.aplicacao.pessoa.api;

import java.io.Serializable;

import br.com.kronos.backend.aplicacao.pessoa.EnumOperadora;
import br.com.kronos.backend.aplicacao.pessoa.EnumTipoTelefone;
import br.com.kronos.backend.aplicacao.pessoa.EnumTipoUso;
import lombok.Builder;
import lombok.Data;

@Data
public class TelefonePessoaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
    private int numero;
	private boolean principal;	
	private boolean whatApp;
    private Long idPessoa;
    private Integer idCidade;
    private String ddd;
    private String nomeCidade;
    private Integer idOperadora;
    private Integer idTipoUso;
    private Integer idTipoTelefone;
    
    public TelefonePessoaDTO() {
    }

    @Builder
	public TelefonePessoaDTO(int id, int numero, boolean principal, boolean whatApp, Long idPessoa, Integer idCidade,
			Integer idOperadora, Integer idTipoUso, Integer idTipoTelefone, String ddd, String nomeCidade) {
		this.id = id;
		this.numero = numero;
		this.principal = principal;
		this.whatApp = whatApp;
		this.idPessoa = idPessoa;
		this.idCidade = idCidade;
		this.idOperadora = idOperadora;
		this.idTipoUso = idTipoUso;
		this.idTipoTelefone = idTipoTelefone;
		this.ddd = ddd;
		this.nomeCidade = nomeCidade;
	}
    
    public String getNomeOperadora() {
    	return EnumOperadora.porId(this.idOperadora).label();
    }
    
    public String getNomeTipoUso() {
    	return EnumTipoUso.porId(this.idTipoUso).label();
    }
    
    public String getNomeTipoTelefone() {
    	return EnumTipoTelefone.porId(this.idTipoTelefone).label();
    }
    
}