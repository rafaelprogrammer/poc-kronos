package br.com.kronos.backend.aplicacao.matricula.api;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.kronos.backend.aplicacao.matricula.EnumTipoComprovante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ComprovanteDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataEmissao;
    private String codigoValidacao;
    private String numeroProcesso;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataRegistro;
    private String numeroRegistro;
    private String livroRegistro;
    private String folhaRegistro;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataAverbacao;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataEntrega;
    private int idTipoComprovante;
    private Long idMatricula;
    private Long idFuncionarioEmissor;
    private Long idFuncionarioAverbacao;
    private Long idFuncionarioEntrega;
    private Long idPessoaRetirada;
    private Long idEmpresaRegistro;

	public String getNomeTipoComprovante() {
		return EnumTipoComprovante.porId(this.idTipoComprovante).label();
    }

    
}

