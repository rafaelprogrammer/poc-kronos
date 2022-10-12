package br.com.kronos.backend.aplicacao.curso;


import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Curso { 
	
	public static final String COMBO_CACHE_NAME = "combo_cursos";
	public static final String COMBO_CACHE_NAME_PERFIL_PROFESSORES = "combo_cursos_perfil_professores";
    
    private long id;
    private String nome;
    private String sigla;
    private String tituloMasculino;
    private String tituloFeminino;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFinalVigencia;
    private LocalDate dataLimiteConclusao;
    private int tempoMaximoConclusao;
    private int tempoMaximoDescontinuo;
    private int cargaHoraria;
    private Double notaMinimaAprovacaoDireta;
    private Double notaMinimaAprovacaoRecuperacao;
    private boolean ativo;
    private Integer idNivel;
    private int idTipoRegimeLetivo;
    private int idTipoTurno;
    private Integer idInstituicao;
    private Integer idTipoMatrizHorario;
    

	@Builder
	public Curso(long id, String nome, String sigla, String tituloMasculino, String tituloFeminino, LocalDate dataInicioVigencia, 
                 LocalDate dataFinalVigencia, LocalDate dataLimiteConclusao, int tempoMaximoConclusao, int tempoMaximoDescontinuo, 
                 int cargaHoraria, Double notaMinimaAprovacaoDireta, Double notaMinimaAprovacaoRecuperacao, boolean ativo, 
                 Integer idNivel, int idTipoRegimeLetivo, int idTipoTurno, Integer idInstituicao, Integer idTipoMatrizHorario) {

        this.id = id;
        this.nome = exigirTamanho(nome, "Nome", 1, 100);
        this.sigla = exigirTamanho(sigla, "Sigla", 1, 15);
        this.tituloMasculino = exigirTamanho(tituloMasculino, "Título masculino", 1, 100);
        this.tituloFeminino = exigirTamanho(tituloFeminino, "Título feminino", 1, 100);
        this.dataInicioVigencia = exigirNaoNulo(dataInicioVigencia, "Datade início de vigência");
        this.dataFinalVigencia = dataFinalVigencia;
        this.dataLimiteConclusao = dataLimiteConclusao;
        this.tempoMaximoConclusao = exigirNaoNulo(tempoMaximoConclusao, "Tempo máximo de conclusão"); 
        this.tempoMaximoDescontinuo = exigirNaoNulo(tempoMaximoDescontinuo, "Tempo máximo descontinuo");
        this.cargaHoraria = exigirNaoNulo(cargaHoraria, "Carga horária"); 
        this.notaMinimaAprovacaoDireta = exigirNaoNulo(notaMinimaAprovacaoDireta, "Nota mínima para aprovação direta");
        this.notaMinimaAprovacaoRecuperacao = exigirNaoNulo(notaMinimaAprovacaoRecuperacao, "Nota mínima para aprovação em recuperação");
        this.ativo = exigirNaoNulo(ativo, "Ativo");
        this.idNivel = exigirNaoNulo(idNivel, "Nível");
        this.idTipoRegimeLetivo = exigirNaoNulo(idTipoRegimeLetivo, "Tipo de regime letivo");
        this.idTipoTurno = exigirNaoNulo(idTipoTurno, "Tipo de turno");
        this.idInstituicao = exigirNaoNulo(idInstituicao, "Instituição");
        this.idTipoMatrizHorario = idTipoMatrizHorario;
       
	}
}
