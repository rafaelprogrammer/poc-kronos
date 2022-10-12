package br.com.kronos.backend.aplicacao.diario.api;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AtividadeDTO implements Serializable {

    private static final long serialVersionUID = 1L;    
    private long id;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataPrevista;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataRealizacao;  
    private Long idHorario;
    private Long idSubFaseExecucao;
    private Long idFuncionario;
    private Long idTurma;
    private Long idDisciplina;
    private Long idPeriodoExecucao;
    private Long idPessoaUsuarioLogado;
    private String diaDaSemana;
    private String regimeLetivo;
    private String professor;
    private String bimestre;
    private List<String> horasAtividades;
    private boolean excluirDiasNaoLetivos;
    private boolean existeAvaliacao;
    private Integer anoTurma;

}
