package br.com.kronos.backend.aplicacao.matricula;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import br.com.kronos.backend.aplicacao.validador.Validacoes;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Turma {
	
	public static final String COMBO_CACHE_DIARIO_FREQUENCIA = "combo_turmas_diario_frequencia";
	public static final String COMBO_CACHE_NAME_PERFIL_PROFESSORES = "combo_turmas_perfil_professores";
	public static final String COMBO_CACHE_NAME_ANOS_POR_CURSO = "combo_anos_turmas_por_curso";
	public static final String COMBO_CACHE_NAME_POR_CURSO_E_ANO = "combo_turmas_por_curso_e_ano";
	public static final String COMBO_CACHE_NAME_OCORRENCIAS_POR_MATRICULA = "combo_turmas_ocorrencias_por_matricula";
	
    private long id;
    private Integer ano;
    private String sigla;
    private boolean ativa;
    private boolean aberta;
    private boolean encerrada;
    private Long idPeriodoExecucao;
    private Long idCalendario;
    private Integer idTipoTurno;

	@Builder
    public Turma(long id, Integer ano, String sigla, boolean ativa, boolean aberta, boolean encerrada, Long idPeriodoExecucao, Long idCalendario,
    		Integer idTipoTurno) {

		super();
        this.id = id;
        this.ano = exigirNaoNulo(ano, "Ano");  
        this.sigla = Validacoes.exigirTamanho(sigla, "Sigla", 1, 10);
        this.ativa = ativa;
        this.aberta = aberta; 
        this.encerrada = encerrada; 
        this.idPeriodoExecucao = exigirNaoNulo(idPeriodoExecucao, "Período de execução"); 
        this.idCalendario = exigirNaoNulo(idCalendario, "Calendário"); 
        this.idTipoTurno = exigirNaoNulo(idTipoTurno, "Tipo turno"); 
	}
}