package br.com.kronos.backend.aplicacao.curso.api;

import java.io.Serializable;

import br.com.kronos.backend.aplicacao.curso.EnumTipoPeriodo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PeriodoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private int numero;
    private String nome;
    private String sigla;
    private Long idCurso;
    private int idTipoPeriodo;
    private Long idFaixaAno;
    private String nomeFaixaAno;
    private Double valor;

    public String getNomeTipoPeriodo() {
		return EnumTipoPeriodo.porId(this.idTipoPeriodo).label();
    }
    	
}
