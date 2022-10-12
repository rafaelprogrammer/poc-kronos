package br.com.kronos.backend.aplicacao.funcionario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;


@Getter
public class Ponto {
	
	public static final String CACHE_NAME = "pontos";
	public static final String CACHE_NAME_AUSENTES = "ausentes";
	
	private Long id;
	private boolean tarefaOnline;
    private LocalTime horaInicialRegistro;
    private LocalTime horaFinalRegistro;
    private LocalTime horaInicialInformado;
    private LocalTime horaFinalInformado;
    private LocalDate data;
    private Integer idTipoPeriodoPonto;
    private Long idConfiguracaoPonto;
    private Long idFuncionarioLiberacao;
    private LocalDate dataLiberacao;
    private LocalDate dataHomologacao;
    private Long idFuncionarioHomologacao;
    private String descricao;
    
    @Builder
    public Ponto(Long id, boolean tarefaOnline, LocalTime horaInicialRegistro, LocalTime horaFinalRegistro,
			LocalTime horaInicialInformado, LocalTime horaFinalInformado, LocalDate data, Integer idTipoPeriodoPonto,
			Long idConfiguracaoPonto, Long idFuncionarioLiberacao, LocalDate dataLiberacao, LocalDate dataHomologacao,
			Long idFuncionarioHomologacao, String descricao) {
		this.id = id;
		this.tarefaOnline = tarefaOnline;
		this.horaInicialRegistro = horaInicialRegistro;
		this.horaFinalRegistro = horaFinalRegistro;
		this.horaInicialInformado = horaInicialInformado;
		this.horaFinalInformado = horaFinalInformado;
		this.data = exigirNaoNulo(data, "Data");
		this.idTipoPeriodoPonto = exigirNaoNulo(idTipoPeriodoPonto, "Tipo Período PontoDTO");
		this.idConfiguracaoPonto = exigirNaoNulo(idConfiguracaoPonto, "Configuração PontoDTO");
		this.idFuncionarioLiberacao = idFuncionarioLiberacao;
		this.dataLiberacao = dataLiberacao;
		this.dataHomologacao = dataHomologacao;
		this.idFuncionarioHomologacao = idFuncionarioHomologacao;
		this.descricao = exigirTamanho(descricao, "Descrição", 0, 200);
	}

	
}
