package br.com.kronos.backend.aplicacao;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HistoricoPessoa {
	private Long id;
    private LocalDateTime dataHora;
    private Long idPessoa;
    private Long idUsuario;
    private Long idTipoHistorico;


	@Builder
	public HistoricoPessoa(Long id, LocalDateTime dataHora, Long idPessoa, Long idUsuario, Long idTipoHistorico) {

		super();
        this.id = id;
        this.dataHora = requireNonNull(dataHora, "dataHora é obrigatório");
        this.idPessoa = requireNonNull(idPessoa, "idPessoa é obrigatório"); 
        this.idUsuario = requireNonNull(idUsuario, "idUsuario é obrigatório");
        this.idTipoHistorico = requireNonNull(idTipoHistorico, "idTipoHistorico é obrigatório");
       
	}
}
