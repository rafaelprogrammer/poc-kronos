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
import br.com.kronos.backend.aplicacao.pessoa.EnumTipoTitulo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TitulacaoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate data;
    private String nomeTitulo;
    private Integer idArqAnexo;
    private Long idPessoa;
    private Integer idEmpresa;
    private String nomeEmpresa;
    private Integer idTipoTitulo; 
    @JsonIgnore
	private ArquivoDTO arquivoDTO;

    public String getNomeTipoTitulo() {
		return EnumTipoTitulo.porId(this.idTipoTitulo).label();
	}
}
