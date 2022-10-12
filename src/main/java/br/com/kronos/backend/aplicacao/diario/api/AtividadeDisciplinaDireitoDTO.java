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
public class AtividadeDisciplinaDireitoDTO implements Serializable {

    private static final long serialVersionUID = 1L;    
    private Long idDisciplinaDireito;
    private Long idAtividade;
    private Long idDireito;
    private String codigoDireito;
    private String descricaoDireito;
    private List<Long> idsDisciplinaDireito;
    private Boolean bncc;
    private String siglaSubFase;

}
