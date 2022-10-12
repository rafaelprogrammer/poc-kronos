package br.com.kronos.backend.aplicacao.pessoa;

import java.util.List;

import br.com.kronos.backend.aplicacao.pessoa.api.PessoaResumoDTO;

public interface PessoaRepositorio {
	
	Long criar(Pessoa pessoa);
	Long alterar(Pessoa pessoa);
	Pessoa buscarPorId(long id);
	List<Pessoa> listar(FiltroPessoa filtroPessoa);
	boolean excluir(long id);
	int contar(FiltroPessoa filtroPessoa);
	List<PessoaResumoDTO> listarParaSelecionarAluno(FiltroPessoa filtroPessoa);
	int contarParaSelecionarAluno(FiltroPessoa filtroPessoa);
	Long alterarGrauComportamento(Long id, Float grauComportamento);

}
