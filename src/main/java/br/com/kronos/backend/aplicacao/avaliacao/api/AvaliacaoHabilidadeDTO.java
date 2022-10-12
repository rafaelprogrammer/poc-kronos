package br.com.kronos.backend.aplicacao.avaliacao.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AvaliacaoHabilidadeDTO implements Serializable {

    private static final long serialVersionUID = 1L;      
    private long id;
    private Long idAvaliacao;
    private Long idDisciplinaHabilidade;
    private String codigo;
    private String descricao;
    private Boolean bncc;
    private Integer qtdResultado;
    	
}

