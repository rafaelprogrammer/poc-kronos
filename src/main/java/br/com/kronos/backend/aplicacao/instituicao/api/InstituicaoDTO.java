package br.com.kronos.backend.aplicacao.instituicao.api;

import br.com.kronos.backend.aplicacao.instituicao.EnumTipoComposicaoValor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class InstituicaoDTO {
	
	private long id;
	private String nome;
	private boolean ativo;
	private long idArquivoAnexo;
	private long idEmpresa;
	private boolean mantenedora;
	private int idComposicaoValor;
	private double percentualJurosAtraso;
	private double percentualMultaAtraso;
	private double percentualBomPagador;
	
	public String getNomeTipoComposicaoValor() {
		return EnumTipoComposicaoValor.porId(this.idComposicaoValor).label();
    }
	
	InstituicaoDTO() {
	}

}

