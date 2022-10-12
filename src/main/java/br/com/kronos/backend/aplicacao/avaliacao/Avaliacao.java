
package br.com.kronos.backend.aplicacao.avaliacao;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Avaliacao {
	
	public static final String COMBO_CACHE_NAME = "combo_avaliacoes";
    
    private long id;
    private LocalTime horaInicio;
    private boolean grupo;
    private Integer numeroMaxParticipante;
    private Integer peso;
    private String observacao;
    private boolean anulada;
    private String motivoAnulacao;
    private Integer idTipoFormato;
    private Integer idTipoRegistroNota;
    private Integer idTipoAbrangencia;
    private Long idAtividade;
    private Integer idTipoAvaliacao;
    private Integer tempoDuracao;
	private Long idTurma;
	private Long idDisciplina;

    @Builder
    public Avaliacao(long id, LocalTime horaInicio, boolean grupo, Integer numeroMaxParticipante, Integer peso, String observacao, 
                     boolean anulada,  String motivoAnulacao, Integer idTipoFormato, Integer idTipoRegistroNota, Integer idTipoAbrangencia, 
                     Long idAtividade, Integer idTipoAvaliacao, Integer tempoDuracao, Long idTurma, Long idDisciplina) {

        this.id = id;
        this.horaInicio = exigirNaoNulo(horaInicio, "Hora de início"); 
        this.grupo = exigirNaoNulo(grupo, "Grupo"); 
        this.numeroMaxParticipante = exigirNaoNulo(numeroMaxParticipante, "Número máximo de integrantes"); 
        this.peso = exigirNaoNulo(peso, "Peso");
        this.observacao = exigirTamanho(observacao, "Observação", 0 , 200); 
        this.anulada = exigirNaoNulo(anulada, "Anulada"); 
        this.motivoAnulacao =  exigirTamanho(motivoAnulacao, "Motivo Anulação", 0, 200); 
        this.idTipoFormato = exigirNaoNulo(idTipoFormato, "Formato"); 
        this.idTipoRegistroNota = exigirNaoNulo(idTipoRegistroNota, "Tipo de registro da nota"); 
        this.idTipoAbrangencia = exigirNaoNulo(idTipoAbrangencia, "Tipo de abrangência"); 
        this.idAtividade = exigirNaoNulo(idAtividade, "Atividade"); 
        this.idTipoAvaliacao = exigirNaoNulo(idTipoAvaliacao, "Tipo de avaliação"); 
        this.tempoDuracao = exigirNaoNulo(tempoDuracao, "Tempo de duração"); 
        this.idTurma = exigirNaoNulo(idTurma, "Turma"); 
        this.idDisciplina = exigirNaoNulo(idDisciplina, "Disciplina"); 
	}
}