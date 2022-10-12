package br.com.kronos.backend.aplicacao.pessoa;

import static java.util.Objects.requireNonNull;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EnderecoPessoa {
	
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
    	
	@Builder
    public EnderecoPessoa(int id, String cep, String logradouro, String complemento, String bairro, String localidade, String uf, 
                           String unidade, String ibge, String gia, int idPessoa, int idTipoEndereco, String numero) {
        super();
        this.id = id;
        this.cep = requireNonNull(cep, "cep é obrigatório");		
        this.logradouro = requireNonNull(logradouro, "logradouro é obrigatório");	
        this.complemento = complemento;
        this.bairro = requireNonNull(bairro, "bairro é obrigatório");	
        this.localidade = requireNonNull(localidade, "localidade é obrigatório");	
        this.uf = requireNonNull(uf, "uf é obrigatório");
        this.unidade = unidade;
        this.ibge = requireNonNull(ibge, "ibge é obrigatório");	
        this.gia = gia;
        this.idPessoa = requireNonNull(idPessoa, "idPessoa é obrigatório");	
        this.idTipoEndereco = requireNonNull(idTipoEndereco, "idTipoEndereco é obrigatório");	
        this.numero = numero;

	}

}