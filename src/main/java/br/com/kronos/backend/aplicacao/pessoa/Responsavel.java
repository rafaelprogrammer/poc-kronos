package br.com.kronos.backend.aplicacao.pessoa;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Responsavel {
	private int id;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private int prioridade;
    private Long idPessoa;
    private Long idPessoaResponsavel;
    private Integer idTipoResponsavel;

	@Builder
	public Responsavel(int id, LocalDate dataInicio, LocalDate dataFim, int prioridade, Long idPessoa, Long idPessoaResponsavel, Integer idTipoResponsavel) {

		super();
        this.id = id;
        this.dataInicio = requireNonNull(dataInicio, "dataInicio é obrigatório");
        this.dataFim = dataFim;
        this.prioridade = requireNonNull(prioridade, "prioridade é obrigatório");
        this.idPessoa = requireNonNull(idPessoa, "idPessoa é obrigatório"); 
        this.idPessoaResponsavel = requireNonNull(idPessoaResponsavel, "idPessoaResponsavel é obrigatório");
        this.idTipoResponsavel = requireNonNull(idTipoResponsavel, "idTipoResponsavel é obrigatório");
       
	}
}