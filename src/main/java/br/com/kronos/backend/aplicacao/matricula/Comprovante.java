package br.com.kronos.backend.aplicacao.matricula;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Comprovante {
    
    private Long id;
    private LocalDate dataEmissao;
    private String codigoValidacao;
    private String numeroProcesso;
    private LocalDate dataRegistro;
    private String numeroRegistro;
    private String livroRegistro;
    private String folhaRegistro;
    private LocalDate dataAverbacao;
    private LocalDate dataEntrega;
    private int idTipoComprovante;
    private Long idMatricula;
    private Long idFuncionarioEmissor;
    private Long idFuncionarioAverbacao;
    private Long idFuncionarioEntrega;
    private Long idPessoaRetirada;
    private Long idEmpresaRegistro;

	@Builder
    public Comprovante(Long id, LocalDate dataEmissao, String codigoValidacao, String numeroProcesso, LocalDate dataRegistro, 
                       String numeroRegistro, String livroRegistro, String folhaRegistro, LocalDate dataAverbacao, 
                       LocalDate dataEntrega, int idTipoComprovante, Long idMatricula, Long idFuncionarioEmissor, 
                       Long idFuncionarioAverbacao, Long idFuncionarioEntrega, Long idPessoaRetirada, Long idEmpresaRegistro) {

		super();
        this.id = id;
        this.dataEmissao = exigirNaoNulo(dataEmissao, "Data de emissão");
        this.codigoValidacao = exigirNaoNulo(codigoValidacao, "Código de validação");
        this.numeroProcesso = numeroProcesso;  
        this.dataRegistro = dataRegistro; 
        this.numeroRegistro = numeroRegistro; 
        this.livroRegistro = livroRegistro; 
        this.folhaRegistro = folhaRegistro; 
        this.dataAverbacao = dataAverbacao;  
        this.dataEntrega = dataEntrega; 
        this.idTipoComprovante = exigirNaoNulo(idTipoComprovante, "Tipo de comprovante");
        this.idMatricula = exigirNaoNulo(idMatricula, "Matrícula");  
        this.idFuncionarioEmissor = exigirNaoNulo(idFuncionarioEmissor, "Funcionário emissor");
        this.idFuncionarioAverbacao = idFuncionarioAverbacao; 
        this.idFuncionarioEntrega = idFuncionarioEntrega; 
        this.idPessoaRetirada = idPessoaRetirada; 
        this.idEmpresaRegistro = exigirNaoNulo(idEmpresaRegistro, "Empresa de registro");  
	}
}
