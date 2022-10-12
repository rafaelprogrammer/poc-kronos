package br.com.kronos.backend.aplicacao.funcionario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;


@Getter
public class Funcionario {
	
	public static final String CACHE_NAME = "funcionarios";
	public static final String COMBO_CACHE_NAME_OCORRENCIAS_POR_DATA_E_INSTITUICAO = "combo_funcionarios_ocorrencias_por_data_e_instituicao";
    
	private Long id;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;
    private Long idPessoa;
    private Long idInstituicao;

	@Builder
	public Funcionario(Long id, LocalDate dataAdmissao, LocalDate dataDemissao, Long idPessoa, Long idInstituicao) {

		super();
        this.id = id;
        this.dataAdmissao = exigirNaoNulo(dataAdmissao, "Data de admissão");
        this.dataDemissao = dataDemissao;
        this.idPessoa = exigirNaoNulo(idPessoa, "Pessoa");
        this.idInstituicao = exigirNaoNulo(idInstituicao, "Instituição");
       
	}
}
