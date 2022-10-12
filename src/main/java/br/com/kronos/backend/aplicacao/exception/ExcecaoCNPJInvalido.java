package br.com.kronos.backend.aplicacao.exception;

public class ExcecaoCNPJInvalido extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcecaoCNPJInvalido(String msg) {
		super(msg);
	}
	
	public ExcecaoCNPJInvalido(String msg, Exception e) {
		super(msg, e);
	}
	
	public ExcecaoCNPJInvalido(Exception e) {
		super(e);
	}

}
