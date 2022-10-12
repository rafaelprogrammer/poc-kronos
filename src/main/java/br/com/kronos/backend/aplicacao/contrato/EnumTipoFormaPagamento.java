package br.com.kronos.backend.aplicacao.contrato;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoFormaPagamento {
	
    EM_ANALISE(1, "Dinheiro"),
    CHEQUE(2, "Cheque"),
    BOLETO(3, "Boleto"),
    CARTAO_CREDITO(4, "Cartão de Crédito"),
    DEBITO_CONTA(5, "Débito em Conta"),
    DEPOSITO_CONTA(6, "Depósito em Conta"),
    TRANSFERENCIA_BANCARIA(7, "Transferência Bancária");
     
    private int id;
	private String label;

	EnumTipoFormaPagamento(int id, String label) {
		this.label = label;
		this.id = id;
    }
    
    public List<EnumTipoFormaPagamento> listarTodos() {
		return Arrays.asList(EnumTipoFormaPagamento.values());
	}
    
    public static EnumTipoFormaPagamento porId(int id) {
		return Arrays.asList(EnumTipoFormaPagamento.values()).stream().filter(o -> o.id == id).findAny().get();
	}
	
	public int id() {
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
}

