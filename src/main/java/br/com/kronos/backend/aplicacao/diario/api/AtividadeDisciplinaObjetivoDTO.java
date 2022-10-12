package br.com.kronos.backend.aplicacao.diario.api;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AtividadeDisciplinaObjetivoDTO implements Serializable {

    private static final long serialVersionUID = 1L;    
    private Long idDisciplinaObjetivo;
    private Long idAtividade;
    private Long idObjetivo;
    private String codigoObjetivo;
    private String descricaoObjetivo;
    private List<Long> idsDisciplinaObjetivo;
    private Boolean bncc;
    private String siglaSubFase;

}
