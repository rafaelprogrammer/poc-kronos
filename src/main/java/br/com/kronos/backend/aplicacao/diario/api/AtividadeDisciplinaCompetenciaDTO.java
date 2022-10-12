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
public class AtividadeDisciplinaCompetenciaDTO implements Serializable {

    private static final long serialVersionUID = 1L;    
    private Long idDisciplinaCompetencia;
    private Long idAtividade;
    private Long idCompetencia;
    private String codigoCompetencia;
    private String descricaoCompetencia;
    private List<Long> idsDisciplinaCompetencia;
    private Boolean bncc;
    private String siglaSubFase;
    

}
