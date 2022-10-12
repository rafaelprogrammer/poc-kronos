package br.com.kronos.backend.aplicacao.funcionario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;


@Getter
public class ConfiguracaoPonto {
	
	public static final String CACHE_NAME = "configuracoes_pontos";
	
	private Long id;
	private Long idFuncionario;
	private boolean tarefaOnline;
    private LocalTime horaInicialP1;
    private LocalTime horaFinalP1;
    private LocalTime horaInicialP2;
    private LocalTime horaFinalP2;
    private LocalTime horaInicialP3;
    private LocalTime horaFinalP3;
    private LocalDate dataInicialVigencia;
    private LocalDate dataFinalVigencia;
    private Integer cargaHorariaSemanal;
    private LocalTime tolerancia;
    
    @Builder
	public ConfiguracaoPonto(Long id, Long idFuncionario, boolean tarefaOnline, LocalTime horaInicialP1,
			LocalTime horaFinalP1, LocalTime horaInicialP2, LocalTime horaFinalP2, LocalTime horaInicialP3,
			LocalTime horaFinalP3, LocalDate dataInicialVigencia, LocalDate dataFinalVigencia,
			Integer cargaHorariaSemanal, LocalTime tolerancia) {
		this.id = id;
		this.idFuncionario = exigirNaoNulo(idFuncionario, "Funcionário");
		this.tarefaOnline = tarefaOnline;
		this.horaInicialP1 = horaInicialP1;
		this.horaFinalP1 = horaFinalP1;
		this.horaInicialP2 = horaInicialP2;
		this.horaFinalP2 = horaFinalP2;
		this.horaInicialP3 = horaInicialP3;
		this.horaFinalP3 = horaFinalP3;
		this.dataInicialVigencia = exigirNaoNulo(dataInicialVigencia, "Data Inicial Vigência");
		this.dataFinalVigencia = exigirNaoNulo(dataFinalVigencia, "Data Final Vigência");
		this.cargaHorariaSemanal = exigirNaoNulo(cargaHorariaSemanal, "Carga Horária Semana");
		this.tolerancia = exigirNaoNulo(tolerancia, "Tolerância");
	}

}
