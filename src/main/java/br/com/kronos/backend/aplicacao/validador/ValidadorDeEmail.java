package br.com.kronos.backend.aplicacao.validador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;

public class ValidadorDeEmail {

	public static String isValidEmailAddressRegex(String email) {
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (!matcher.matches()) {
				throw new ExcecaoDeNegocio("Email informado é inválido - " + email);
			}
		}
		return email;
	}
}
