package br.com.kronos.backend.aplicacao.pessoa.api;

import java.io.Serializable;
import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FotoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private long idPessoa;
	private byte[] bytes;
	private String contentType;
	private long tamanho;
	private Integer idTipoArquivo;
	private Integer idTipoConteudo;
	
}
