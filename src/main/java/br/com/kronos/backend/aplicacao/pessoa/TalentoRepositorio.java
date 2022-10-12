package br.com.kronos.backend.aplicacao.pessoa;

import java.util.List;

public interface TalentoRepositorio {
	
	int contarPorIdPessoaETipo(FiltroTalento filtro);
	int criar(Talento talento);
	List<Talento> listar(FiltroTalento filtro);
	int contar(FiltroTalento filtro);
	boolean excluir(long id, int idTipoTalento);
	boolean excluirTodosDaPessoa(long idPessoa);
	List<Talento> buscarTalentosDaPessoa(long idPessoa);

}