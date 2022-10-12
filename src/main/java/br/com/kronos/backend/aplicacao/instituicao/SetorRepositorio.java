package br.com.kronos.backend.aplicacao.instituicao;

import java.util.List;

public interface SetorRepositorio {
	
	Long criar(Setor telefonePessoa);
	Long alterar(Setor telefonePessoa);
	Setor buscarPorId(Long id);
	List<Setor> listar(FiltroSetor filtroSetor);
	boolean excluir(Long id);
	int contar(FiltroSetor filtroSetor);

}