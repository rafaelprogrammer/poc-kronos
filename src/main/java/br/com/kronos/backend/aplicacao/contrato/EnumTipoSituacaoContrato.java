package br.com.kronos.backend.aplicacao.contrato;

import java.util.Arrays;
import java.util.List;

import br.com.kronos.backend.aplicacao.contrato.EnumTipoSituacaoContrato;

public enum EnumTipoSituacaoContrato {
	
	PRE_MATRICULA(1, "Pré-matrícula"),
	VALIDADO(2, "Validado"),
    ANALISE_FINANCEIRO(3, "Análise financeiro"),
    AGUARDANDO_APROVACAO(4, "Aguardando aprovação"),
    AGUARDANDO_ASSINATURA(5, "Aguardando assinatura"),
    CONFIRMADO(6, "Confirmado"),
    TRANCADO(7, "Trancado"),
    TRANSFERIDO(8, "Transferido"),
    CANCELADO(9, "Cancelado"),
    DESISTENCIA(10, "Desistência");

    private int id;
	private String label;

	EnumTipoSituacaoContrato(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoSituacaoContrato> listarTodos() {
		return Arrays.asList(EnumTipoSituacaoContrato.values());
	}
    public static EnumTipoSituacaoContrato porId(int id) {
		return Arrays.asList(EnumTipoSituacaoContrato.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}	
}