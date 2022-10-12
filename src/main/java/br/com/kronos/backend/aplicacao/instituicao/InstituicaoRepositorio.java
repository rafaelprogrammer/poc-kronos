package br.com.kronos.backend.aplicacao.instituicao;

import java.util.List;

import br.com.kronos.backend.aplicacao.instituicao.Instituicao;
import br.com.kronos.backend.aplicacao.instituicao.FiltroInstituicao;

public interface InstituicaoRepositorio {
	
	Instituicao buscarPorId(long id);
	Long criar(Instituicao instituicao);
	Long alterar(Instituicao instituicao);
	List<Instituicao> listar(FiltroInstituicao filtroInstituicao);
	boolean excluir(Long id);
	int contar(FiltroInstituicao filtroInstituicao);

}
