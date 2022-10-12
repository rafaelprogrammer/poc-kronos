package br.com.kronos.backend.aplicacao.validador;

import java.util.function.Predicate;

public abstract class Validacoes {
	
	public static <T> T exigirNaoNulo(T valor, String nomeCampo) {
		if (valor == null) {
			throw new IllegalArgumentException("O campo " + nomeCampo + " é obrigatório");
		}
		return valor;
	}
	
	public static String exigirNaoVazio(String valor, String nomeCampo) {
		if (valor == null || valor.trim().isEmpty()) {
			throw new IllegalArgumentException("O campo " + nomeCampo + " não pode ser vazio");
		}
		return valor.trim();
	}
	
	public static String exigirTamanho(String valor, String nomeCampo, int minimo, int maximo) {
		if(minimo > maximo) {
			throw new IllegalArgumentException("minimo " + minimo + " não pode ser maior que o máximo " + maximo);
		}
		if (minimo > 0) {
			valor = exigirNaoVazio(valor, nomeCampo);
		}
		if (valor == null) {
			return null;
		}
		valor = valor.trim();
		if (valor.length() < minimo || valor.length() > maximo) {
			throw new IllegalArgumentException(
					nomeCampo + " deve ter no mínimo " + minimo + " e no máximo " + maximo + " caracteres.");
		}
		return valor;
	}
	
	public static <T> T validarCondicao(T valor, Predicate<T> condicao, String mensagemDeErro) {
		if (!condicao.test(valor)) {
			throw new IllegalArgumentException(mensagemDeErro);
		}
		return valor;
	}
}
