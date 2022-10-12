package br.com.kronos.backend.aplicacao.pessoa.api;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.kronos.backend.aplicacao.arquivo.api.ArquivoDTO;
import br.com.kronos.backend.aplicacao.pessoa.EnumTipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DocumentoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate dataExpedicao;
	private String folha;
	private String livro;
	private String nomeCartorio;
	private String orgaoExpeditor;
	private String numero;
	private String secao;
	private String serie;
	private String zona;
	private Integer idArqAnexo;
	@JsonIgnore
	private ArquivoDTO arquivoDTO;
	private Long idPessoa;
	private Integer idCidade;
	private String nomeCidade;
	private Integer idTipo;
	
	public String getNomeTipo() {
		return EnumTipoDocumento.porId(this.idTipo).label();
	}
	
	
}
