package br.com.kronos.backend.aplicacao.basecurricular.api;

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
public class HabilidadeDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private Long idComponente;
    private String componente;
    private String descricao;
    private String codigo;
    private Boolean ativo;
    private Boolean bncc;
    private String eixo;
    private String campoAtuacao;
    private String praticaLinguagem;
    private String objetoConhecimento;
    private String unidadeTematica;
    private Long idInstituicao;
    private Integer idNivel;
    private String nivel;
    private List<Long> idsCompetencias;
    private List<Long> idsFaixasAnos;
    
}

