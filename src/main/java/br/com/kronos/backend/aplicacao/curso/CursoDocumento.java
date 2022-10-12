package br.com.kronos.backend.aplicacao.curso;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CursoDocumento {
    
    private long id;
    private boolean original;
    private boolean autenticacao;
    private Integer numeroCopia;
    private Long idCurso;
    private Integer idTipoDocumento;

	@Builder
	public CursoDocumento(long id,  boolean original, boolean autenticacao, int numeroCopia, Long idCurso, int idTipoDocumento) {

		super();
        this.id = id;
        this.original = original;
        this.autenticacao = autenticacao;
        this.numeroCopia = exigirNaoNulo(numeroCopia, "numeroCopia é obrigatório");
        this.idCurso = exigirNaoNulo(idCurso, "idCurso é obrigatório"); 
        this.idTipoDocumento = exigirNaoNulo(idTipoDocumento, "idTipoDocumento é obrigatório");  
	}
}
