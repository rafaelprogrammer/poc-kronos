package br.com.kronos.backend.aplicacao.instituicao;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Instituicao {
	
	private long id;
	private String nome;
	private boolean ativo;
	private Long idArquivoAnexo;
	private Long idEmpresa;
	private boolean mantenedora;
	private Integer idComposicaoValor;
	private Double percentualJurosAtraso;
	private Double percentualMultaAtraso;
	private Double percentualBomPagador;
	
	@Builder
	public Instituicao(long id, String nome, boolean ativo, Long idArquivoAnexo, Long idEmpresa, 
			           boolean mantenedora, Integer idComposicaoValor, Double percentualJurosAtraso, Double percentualMultaAtraso,
			           Double percentualBomPagador ) {

        this.id = id;
        this.nome = exigirTamanho(nome, "Nome", 1, 80);
        this.ativo = exigirNaoNulo(ativo, "Ativo");
        this.idArquivoAnexo = idArquivoAnexo;
        this.idEmpresa = exigirNaoNulo(idEmpresa, "Empresa");
        this.mantenedora = exigirNaoNulo(mantenedora, "Mmantenedora");
        this.idComposicaoValor = exigirNaoNulo(idComposicaoValor, "Composição de valor");
        this.percentualJurosAtraso = exigirNaoNulo(percentualJurosAtraso, "Percentual de juros por atraso");
        this.percentualMultaAtraso = exigirNaoNulo(percentualMultaAtraso, "Percentual de multa por atraso");
        this.percentualBomPagador = exigirNaoNulo(percentualBomPagador, "Percentual de bom pagador");
	}

}
