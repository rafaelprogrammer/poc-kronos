package br.com.kronos.backend.aplicacao.arquivo.api;

import java.io.Serializable;
import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.kronos.backend.aplicacao.arquivo.EnumTipoArquivo;
import br.com.kronos.backend.aplicacao.arquivo.EnumTipoConteudo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ArquivoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	@JsonIgnore
	private byte[] bytes;
	private String contentType;
	private long tamanho;
	private String legenda;
	private String nome;
	private Integer idTipoArquivo;
	private Integer idTipoConteudo;
	
	public String getDadosBase64() {
		if (this.bytes == null) {
			return null;
		}
		return "data:" + this.contentType + ";base64," + Base64.getEncoder().encodeToString(this.bytes);
	}
	
	public String getNomeTipoArquivo() {
    	return EnumTipoArquivo.porId(this.idTipoArquivo).label();
    }
	
	public String getNomeTipoConteudo() {
    	return EnumTipoConteudo.porId(this.idTipoConteudo).label();
    }
}
