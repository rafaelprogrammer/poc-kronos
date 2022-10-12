package br.com.kronos.backend.aplicacao.pessoa;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import java.time.LocalDate;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(of="id")
@ToString
@Getter
public class Documento {
	
	private int id;
	private LocalDate dataExpedicao;
	private String folha;
	private String livro;
	private String nomeCartorio;
	private String orgaoExpeditor;
	private String numero;
	private String secao;
	private String serie;
	private String zona;
	private Integer idArqAnexo;
	private Long idPessoa;
	private Integer idCidade;
	private Integer idTipo;
	
	@Builder
	public Documento(int id, LocalDate dataExpedicao, String folha, String livro, String nomeCartorio,
			String orgaoExpeditor, String numero, String secao, String serie, String zona, Integer idArqAnexo,
			Long idPessoa, Integer idCidade, Integer idTipo) {
		
		this.id = id;
		this.dataExpedicao = exigirNaoNulo(dataExpedicao, "Data Expedição");
		this.folha = exigirTamanho(folha, "Folha", 0, 15);
		this.livro = exigirTamanho(livro, "Livro", 0, 15);
		this.nomeCartorio = exigirTamanho(nomeCartorio, "Nome do Cartório", 0, 100);
		this.orgaoExpeditor = orgaoExpeditor;
		this.numero = exigirTamanho(numero, "Número", 0, 15);
		this.secao = exigirTamanho(secao, "Seção", 0, 15);
		this.serie = exigirTamanho(serie, "Série", 0, 15);
		this.zona = exigirTamanho(zona, "Zona", 0, 15);;
		this.alterarArquivo(idArqAnexo);
		this.idPessoa = exigirNaoNulo(idPessoa, "Id da Pessoa");
		this.idCidade = exigirNaoNulo(idCidade, "Id da Cidade");
		this.idTipo = exigirNaoNulo(idTipo, "Id do Tipo");
	}
	
	public void alterarArquivo(Integer idArqAnexo) {
		this.idArqAnexo = idArqAnexo;
	}
	
}
