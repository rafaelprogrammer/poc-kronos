package br.com.kronos.backend.aplicacao.pessoa;

import java.util.List;

import br.com.kronos.backend.aplicacao.pessoa.api.TitulacaoDTO;

public interface TitulacaoRepositorio {
	
	int criar(Titulacao titulacao);
	int alterar(Titulacao titulacao);
	int alterarArquivo(int id, int idArqAnexo);
	TitulacaoDTO buscarPorId(int id);
	List<TitulacaoDTO> listar(FiltroTitulacao filtroTitulacao);
	int contar(FiltroTitulacao filtro);
	boolean excluir(int id);

}