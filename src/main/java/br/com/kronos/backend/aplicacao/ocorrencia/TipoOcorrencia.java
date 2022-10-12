
package br.com.kronos.backend.aplicacao.ocorrencia;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import java.time.LocalDate;

import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TipoOcorrencia {
	
	public static final String COMBO_CACHE_NAME_TIPOS_OCORRENCIAS_POR_DATA = "combo_tipos_ocorrencias_por_data";
    
    private Long id;
    private int fator; 
    private String codigo;
    private String descricao; 
    private Double valor;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFinalVigencia;
    private Long idInstituicao;

	@Builder
    public TipoOcorrencia(Long id, int fator, String codigo, String descricao, Double valor, 
                          LocalDate dataInicioVigencia, LocalDate dataFinalVigencia, Long idInstituicao) {

		super();
        this.id = id;
        this.fator = exigirNaoNulo(fator, "Fator"); 
        this.codigo = codigo; 
        this.descricao = exigirTamanho(descricao, "Descrição", 1, 100); 
        this.valor = exigirNaoNulo(valor, "Valor");
        if (this.valor < 0.00d || this.valor > 10.00d) {
        	throw new ExcecaoDeNegocio("Os limites de valores aceitáveis para o campo valor deverão estar entre 0.00 até 10.00");
        }
        this.dataInicioVigencia = exigirNaoNulo(dataInicioVigencia, "Data de início de vigência"); 
        this.dataFinalVigencia = dataFinalVigencia; 
        this.idInstituicao = exigirNaoNulo(idInstituicao, "Instituição");
	}
}