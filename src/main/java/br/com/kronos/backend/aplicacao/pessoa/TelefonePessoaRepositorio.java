package br.com.kronos.backend.aplicacao.pessoa;

import java.util.List;

import br.com.kronos.backend.aplicacao.pessoa.api.TelefonePessoaDTO;

public interface TelefonePessoaRepositorio {
	
	int criar(TelefonePessoa telefonePessoa);
	List<TelefonePessoaDTO> listar(FiltroTelefonePessoa filtroTelefonePessoa);
	public int contar(FiltroTelefonePessoa filtro);
	boolean excluir(int id);

}