package br.com.kronos.backend.aplicacao.exception;

public class ExcecaoDeNegocio extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcecaoDeNegocio(String msg) {
		super(msg);
	}
	
	public ExcecaoDeNegocio(String msg, Exception e) {
		super(msg, e);
	}
	
	public ExcecaoDeNegocio(Exception e) {
		super(e);
	}

}
