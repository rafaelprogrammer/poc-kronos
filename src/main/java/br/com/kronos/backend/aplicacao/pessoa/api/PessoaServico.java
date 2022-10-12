package br.com.kronos.backend.aplicacao.pessoa.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.pessoa.FiltroPessoa;

public interface PessoaServico {
	
	Long criar(PessoaDTO pessoaDTO);
	Long alterar(PessoaDTO pessoaDTO);
	PessoaDTO buscarPorId(long id);
	PaginacaoDTO<PessoaDTO> listar(FiltroPessoa filtroPessoa);
	boolean excluir(long id);
	void alterarFoto(FotoDTO dto);
	PaginacaoDTO<PessoaResumoDTO> listarParaSelecionarAluno(FiltroPessoa filtroPessoa);
	boolean verificarCPFCadastrado(Long id, String cpf);

}
