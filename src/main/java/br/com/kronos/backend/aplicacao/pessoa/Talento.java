package br.com.kronos.backend.aplicacao.pessoa;

import static java.util.Objects.requireNonNull;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Talento {

	private int id;
	private Long idPessoa;
	private int idTipoTalento;
	
	@Builder
	public Talento(int id, Long idPessoa, int idTipoTalento) {
		this.id = id;
		this.idPessoa = requireNonNull(idPessoa, "idPessoa é obrigatória");
		this.idTipoTalento = requireNonNull(idTipoTalento, "idTipoTalento é obrigatório");
	}
	
}
