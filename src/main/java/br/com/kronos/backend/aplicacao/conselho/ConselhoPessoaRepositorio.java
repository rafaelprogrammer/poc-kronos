package br.com.kronos.backend.aplicacao.conselho;

import java.util.List;

public interface ConselhoPessoaRepositorio {
    int contarPorIdPessoaEConselho(FiltroConselhoPessoa filtroConselhoPessoa);
	int criar(ConselhoPessoa conselhoPessoa);
	List<ConselhoPessoa> listar(FiltroConselhoPessoa filtroConselhoPessoa);
	int contar(FiltroConselhoPessoa filtroConselhoPessoa);
	boolean excluir(long idPessoa, long idConselho);

}
