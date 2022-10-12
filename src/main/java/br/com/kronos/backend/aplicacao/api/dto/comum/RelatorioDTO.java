package br.com.kronos.backend.aplicacao.api.dto.comum;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RelatorioDTO  implements Serializable {

    
	private static final long serialVersionUID = 1L;
	
	private byte[] dados;
    private String nome;
    @JsonIgnore
	private Long idSubFaseExecucao;
    @JsonIgnore
	private Long idDisciplina;
    @JsonIgnore
    private boolean encerraDiario;
    @JsonIgnore
    private boolean reabreDiario;

}
