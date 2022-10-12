
package br.com.kronos.backend.aplicacao.calendario;

import static java.util.Objects.requireNonNull;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoriaEvento {
    
    private Long id;
    private String nome;
    private String cor;

	@Builder
    public CategoriaEvento(Long id, String nome, String cor) {

		super();
        this.id = id;
        this.nome = requireNonNull(nome, "nome é obrigatório"); 
        this.cor = requireNonNull(cor, "cor é obrigatório"); 
	}
}