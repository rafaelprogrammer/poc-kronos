package br.com.kronos.backend.aplicacao.pessoa.api;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.kronos.backend.aplicacao.pessoa.EnumTipoFiliacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FiliacaoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataFiliacao;
    private boolean filiacaoAtual;
    private Long idPessoaPais;
    private String nomePessoaPais;
    private Long idPessoaFilho;
    private String nomePessoaFilho;
    private int idTipoFiliacao;
    
    
    public String getNomeTipoFiliacao() {
    	return EnumTipoFiliacao.porId(this.idTipoFiliacao).label();
    }
	
}