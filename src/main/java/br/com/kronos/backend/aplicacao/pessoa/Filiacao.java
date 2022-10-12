package br.com.kronos.backend.aplicacao.pessoa;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Filiacao {
	private int id;
    private LocalDate dataFiliacao;
    private boolean filiacaoAtual;
    private Long idPessoaPais;
    private Long idPessoaFilho;
    private int idTipoFiliacao;

	@Builder
	public Filiacao(int id, LocalDate dataFiliacao, boolean filiacaoAtual, Long idPessoaPais, Long idPessoaFilho, int idTipoFiliacao) {

		super();
        this.id = id;
        this.dataFiliacao = requireNonNull(dataFiliacao, "dataFiliacao é obrigatório");
        this.filiacaoAtual = requireNonNull(filiacaoAtual, "filiacaoAtual é obrigatório");
        this.idPessoaPais = requireNonNull(idPessoaPais, "idPessoaPais é obrigatório"); 
        this.idPessoaFilho = requireNonNull(idPessoaFilho, "idPessoaFilho é obrigatório");
        this.idTipoFiliacao = requireNonNull(idTipoFiliacao, "idTipoFiliacao é obrigatório");
       
	}
}