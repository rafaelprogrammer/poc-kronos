package br.com.kronos.backend.aplicacao.matricula;

import java.util.List;

import br.com.kronos.backend.aplicacao.matricula.Comprovante;
import br.com.kronos.backend.aplicacao.matricula.FiltroComprovante;;

public interface ComprovanteRepositorio {
	
	Long criar(Comprovante comprovante);
	Long alterar(Comprovante comprovante);
	Comprovante buscarPorId(Long id);
	List<Comprovante> listar(FiltroComprovante filtroComprovante);
	boolean excluir(Long id);
	int contar(FiltroComprovante filtroComprovante);

}