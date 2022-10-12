package br.com.kronos.backend.aplicacao.exception;

public class ExcecaoCPFInvalido extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcecaoCPFInvalido(String msg) {
		super(msg);
	}
	
	public ExcecaoCPFInvalido(String msg, Exception e) {
		super(msg, e);
	}
	
	public ExcecaoCPFInvalido(Exception e) {
		super(e);
	}

}
