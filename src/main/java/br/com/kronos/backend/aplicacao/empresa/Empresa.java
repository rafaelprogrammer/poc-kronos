package br.com.kronos.backend.aplicacao.empresa;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;
import static br.com.kronos.backend.aplicacao.validador.ValidadorCNPJ.isCNPJ;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Empresa {
	
	private int id;
	private String cnpj;	
	private String inscricaoEstadual;
    private String emailContato;
    private String nomeContato;
	private String nomeFantasia;
    private String razaoSocial;
    private String site;
	
	@Builder
    public Empresa(int id, String cnpj, String inscricaoEstadual, String emailContato, String nomeContato, 
                   String nomeFantasia, String razaoSocial, String site) {
        this.id = id;
        this.cnpj = isCNPJ(exigirTamanho(cnpj, "CNPJ", 14, 14));	
        this.inscricaoEstadual = exigirTamanho(inscricaoEstadual, "Inscrição Estadual", 1, 14);
        this.emailContato = exigirTamanho(emailContato, "Email de Contato", 0, 100);
        this.nomeContato = exigirTamanho(nomeContato, "Nome do Contato", 1, 100);
        this.nomeFantasia = exigirTamanho(nomeFantasia, "Nome Fantasia", 1, 100);
        this.razaoSocial = exigirTamanho(razaoSocial, "Razão Social", 1, 100);
        this.site = exigirTamanho(site, "Site", 0, 100);
	}

}
