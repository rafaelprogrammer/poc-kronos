package br.com.kronos.backend.aplicacao.pessoa;

import java.util.List;

public interface EnderecoPessoaRepositorio {
	
	Integer criar(EnderecoPessoa enderecoPessoa);
	List<EnderecoPessoa> listar(FiltroEnderecoPessoa filtroEnderecoPessoa);
	public int contar(FiltroEnderecoPessoa filtroEnderecoPessoa);
	boolean excluir(Integer id);

}