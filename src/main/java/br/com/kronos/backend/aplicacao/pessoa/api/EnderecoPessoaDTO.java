package br.com.kronos.backend.aplicacao.pessoa.api;

import java.io.Serializable;

import br.com.kronos.backend.aplicacao.pessoa.EnumTipoEndereco;
import lombok.Builder;
import lombok.Data;

@Data
public class EnderecoPessoaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String cep;	
	private String logradouro;
    private String complemento;
    private String bairro;
	private String localidade;
    private String uf;
    private String unidade;
    private String ibge;
    private String gia;
    private int idPessoa;
	private int idTipoEndereco;
    private String numero;
    
    EnderecoPessoaDTO(){
    }

    @Builder
	public EnderecoPessoaDTO(int id, String cep, String logradouro, String complemento, String bairro,
			String localidade, String uf, String unidade, String ibge, String gia, int idPessoa, int idTipoEndereco,
			String numero) {
		super();
		this.id = id;
		this.cep = cep;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.localidade = localidade;
		this.uf = uf;
		this.unidade = unidade;
		this.ibge = ibge;
		this.gia = gia;
		this.idPessoa = idPessoa;
		this.idTipoEndereco = idTipoEndereco;
		this.numero = numero;
	}
    
    public String getNomeTipoEndereco() {
    	return EnumTipoEndereco.porId(this.idTipoEndereco).label();
    }
    
    

}