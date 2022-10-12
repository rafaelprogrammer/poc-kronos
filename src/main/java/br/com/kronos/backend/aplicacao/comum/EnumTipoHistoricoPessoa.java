package br.com.kronos.backend.aplicacao.comum;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoHistoricoPessoa {
	
    NCLUSAO(1,	"Inclusão"),
    ALTERACAO_CADASTRO(2, "Alteração de cadastro"),
    INCLUSAO_ENDERECO(3, "Inclusão de Endereço"),
    EXCLUSAO_ENDERECO(4, "Exclusão de Endereço"),
    INCLUSAO_RESPONSAVEL(5,	"Inclusão de Responsável"),
    EXCLUSAO_RESPONSAVEL(6,	"Exclusão de Responsável"),
    INCLUSAO_TALENTO(7,	"Inclusão de Talento"),
    EXCLUSAO_TALENTO(8,	"Exclusão de Talento"),
    INCLUSAO_FILIACAO(9, "Exclusão de Filiação"),
    EXCLUSAO_FILIACAO(10, "Exclusão de Filiação"),
    INCLUSAO_TITULACAO(11, "Inclusão de Titulação"),
    EXCLUSAO_TITULACAO(12, "Exclusão de Titulação"),
    INCLUSAO_TELEFONE(13, "Inclusão de Telefone"),
    EXCLUSAO_TELEFONE(14, "Exclusão de Telefone"),
    INCLUSAO_DOCUMENTO(15, "Inclusão de Documento"),
    EXCLUSAO_DOCUMENTO(16, "Exclusão de Documento");
    

	private int id;
	private String label;

	
	EnumTipoHistoricoPessoa(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoHistoricoPessoa> listarTodos() {
		return Arrays.asList(EnumTipoHistoricoPessoa.values());
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}
