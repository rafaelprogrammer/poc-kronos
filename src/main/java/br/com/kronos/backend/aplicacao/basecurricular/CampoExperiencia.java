package br.com.kronos.backend.aplicacao.basecurricular;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CampoExperiencia {
    
    private long id;
    private String nome;
    private Long idNivel;

	@Builder
	public CampoExperiencia(long id, String nome, Long idNivel) {

		super();
        this.id = id;
        this.nome = exigirTamanho(nome, "Nome", 1, 100); 
        this.idNivel = exigirNaoNulo(idNivel, "Nível");   
	}
}