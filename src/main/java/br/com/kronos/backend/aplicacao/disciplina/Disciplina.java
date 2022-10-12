package br.com.kronos.backend.aplicacao.disciplina;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Disciplina {
	
	public static final String COMBO_CACHE_NAME_PERFIL_PROFESSORES = "combo_disciplinas_perfil_professores";
	public static final String COMBO_CACHE_NAME_DISCIPLINA_UNIFICA = "combo_disciplinas_unificacoes";
    
    private long id;
    private Integer codigo;
    private String nome;
    private String sigla;
    private Integer cargaHorariaTotal;
    private Integer cargaHorariaPresencial;
    private Integer cargaHorariaDistancia;
    private boolean ativa;
    private boolean obrigatoria;
    private Double valor;
    private Integer idTipoDisciplina;
    private Integer idTipoRegimeLetivo;
    private Long idPeriodo;
    private Long idComponente;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFinalVigencia;
    private boolean preRequisitos;
    private Long idDisciplinaUnifica;

	@Builder
	public Disciplina(long id, Integer codigo, String nome, String sigla, Integer cargaHorariaTotal, Integer cargaHorariaPresencial, Integer cargaHorariaDistancia, 
    boolean ativa, boolean obrigatoria, Double valor, Integer idTipoDisciplina, Integer idTipoRegimeLetivo, Long idPeriodo, 
    Long idComponente, LocalDate dataInicioVigencia, LocalDate dataFinalVigencia, boolean preRequisitos, Long idDisciplinaUnifica) {

        this.id = id;
        this.codigo = exigirNaoNulo(codigo, "Código");
        this.nome = exigirTamanho(nome, "Nome", 1, 100);
        this.sigla = exigirTamanho(sigla, "Sigla", 1, 20);  
        this.cargaHorariaTotal = exigirNaoNulo(cargaHorariaTotal, "Carga horária total");
        this.cargaHorariaPresencial = exigirNaoNulo(cargaHorariaPresencial, "Carga horária presencial"); 
        this.cargaHorariaDistancia = exigirNaoNulo(cargaHorariaDistancia, "Carga horária à distância");
        this.ativa = exigirNaoNulo(ativa, "Ativa"); 
        this.obrigatoria = exigirNaoNulo(obrigatoria, "Obrigatória");
        this.valor = exigirNaoNulo(valor, "Valor"); 
        this.idTipoDisciplina = exigirNaoNulo(idTipoDisciplina, "Tipo da disciplina");
        this.idTipoRegimeLetivo = exigirNaoNulo(idTipoRegimeLetivo, "Tipo do regime letivo"); 
        this.idPeriodo = exigirNaoNulo(idPeriodo, "Período");
        this.idComponente = idComponente;
        this.dataInicioVigencia = exigirNaoNulo(dataInicioVigencia, "Data de início da vigência"); 
        this.dataFinalVigencia = dataFinalVigencia; 
        this.preRequisitos = exigirNaoNulo(preRequisitos, "Pré-requisitos");
        this.idDisciplinaUnifica = idDisciplinaUnifica;
	}
}
