
package br.com.kronos.backend.aplicacao.atestado.api;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.kronos.backend.aplicacao.arquivo.api.ArquivoDTO;
import br.com.kronos.backend.aplicacao.atestado.EnumTipoFalta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AtestadoDTO implements Serializable {
    
	private static final long serialVersionUID = 1L;
	private long id;
	@JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataEntrega; 
    private Long idPessoa;
    private Long idInstituicao;
    private Integer idTipoFalta;
    private Integer idArqAnexo;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataInicioVigencia; 
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataFinalVigencia; 
    private Long idFuncionario;
    private Long numeroDias;
    @JsonIgnore
	private ArquivoDTO arquivoDTO;
    
    public String getNomeTipoFalta() {
    	return this.idTipoFalta != null ? EnumTipoFalta.porValor(this.idTipoFalta).label() : null;
    }

}