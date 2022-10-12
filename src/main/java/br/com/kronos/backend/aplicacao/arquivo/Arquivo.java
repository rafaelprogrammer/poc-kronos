package br.com.kronos.backend.aplicacao.arquivo;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import java.time.LocalDate;

import br.com.kronos.backend.aplicacao.util.DateUtil;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(of="id")
@ToString(of= {"nome", "contentType"})
@Getter
public class Arquivo {

	private int id;
	private byte[] bytes;
	private String contentType;
	private LocalDate dataInclusao;
	private String legenda;
	private String nome;
	private long tamanho;
	private Integer idTipoArquivo;
	private Integer idTipoConteudo;
	
	@Builder
	public Arquivo(int id, byte[] bytes, String contentType, LocalDate dataInclusao, String legenda, String nome,
			long tamanho, int idTipoArquivo, int idTipoConteudo) {
		this.id = id;
		this.bytes = exigirNaoNulo(bytes, "Arquivo");
		this.contentType =  exigirTamanho(contentType, "ContentType", 1, 255);
		this.dataInclusao = dataInclusao == null ? DateUtil.dataAtual() : dataInclusao;
		this.legenda = exigirTamanho(legenda, "Legenda", 1, 200);
		this.nome = exigirTamanho(nome, "Nome", 1, 255);
		if(tamanho == 0) {
			throw new IllegalArgumentException("Tamanho tem que ser maior do que 0");
		}
		this.tamanho = tamanho;
		if (this.contentType.contains("image")) {
			this.idTipoArquivo = EnumTipoArquivo.IMAGEM.id();
		}
		if (this.contentType.equals("application/pdf")) {
			this.idTipoArquivo = EnumTipoArquivo.FORMATO_PORTAVEL.id();
		}
		this.idTipoConteudo = exigirNaoNulo(idTipoConteudo, "Tipo de Conteúdo");
	}
	
}
