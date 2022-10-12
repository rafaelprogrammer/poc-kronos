package br.com.kronos.backend.aplicacao.instituicao;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Sala {
	private Long id;
    private String bloco;
    private String ala;
    private String andar;
    private String numero;
    private int lotacao;
    private boolean poolReserva;
	private boolean ativo;
    private Long idInstitucicao;
    private int idTipoSala;
    	
	@Builder
	public Sala(Long id, String bloco, String ala, String andar, String numero, int lotacao, boolean poolReserva, 
                boolean ativo, Long idInstitucicao, int idTipoSala) {

		super();
        this.id = id;
        this.bloco = bloco;
        this.ala = ala;
        this.andar = andar;
        this.numero = numero;
        this.lotacao = exigirNaoNulo(lotacao, "Lotação");
        this.poolReserva = exigirNaoNulo(poolReserva, "Pool de reserva");
        this.ativo = exigirNaoNulo(ativo, "Atvo");
        this.idInstitucicao = exigirNaoNulo(idInstitucicao, "Instiyuição");
        this.idTipoSala = exigirNaoNulo(idTipoSala, "Tipo de sala");
	}
}
