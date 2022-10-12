package br.com.kronos.backend.aplicacao.atestado;

import java.util.List;

public interface AtestadoRepositorio {
	
	Long criar(Atestado atestado);
	Long alterar(Atestado atestado);
	Atestado buscarPorId(Long id);
	List<Atestado> listar(FiltroAtestado filtro);
	boolean excluir(Long id);
	int contar(FiltroAtestado filtro);
	int alterarArquivo(Long id, int idArqAnexo);
	public boolean verificarAtestadoConflitante(FiltroAtestado filtroAtestado);

}