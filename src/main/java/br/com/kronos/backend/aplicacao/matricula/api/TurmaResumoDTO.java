package br.com.kronos.backend.aplicacao.matricula.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TurmaResumoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
    private int ano;
    private String sigla;
    private String siglaTurno;
}
