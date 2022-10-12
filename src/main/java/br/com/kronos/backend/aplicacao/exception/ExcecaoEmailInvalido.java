package br.com.kronos.backend.aplicacao.exception;

public class ExcecaoEmailInvalido extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcecaoEmailInvalido(String msg) {
		super(msg);
	}
	
	public ExcecaoEmailInvalido(String msg, Exception e) {
		super(msg, e);
	}
	
	public ExcecaoEmailInvalido(Exception e) {
		super(e);
	}

}
