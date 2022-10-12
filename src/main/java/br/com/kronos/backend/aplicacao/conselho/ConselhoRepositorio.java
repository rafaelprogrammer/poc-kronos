package br.com.kronos.backend.aplicacao.conselho;

import java.util.List;

import br.com.kronos.backend.aplicacao.conselho.ConselhoPessoa;
import br.com.kronos.backend.aplicacao.conselho.FiltroConselhoPessoa;
import br.com.kronos.backend.aplicacao.conselho.FiltroConselho;

public interface ConselhoRepositorio {
	
	Long criar(Conselho conselho);
	Long alterar(Conselho conselho);
	Conselho buscarPorId(Long id);
	List<Conselho> listar(FiltroConselho filtroConselho);
	boolean excluir(Long id);
	int contar(FiltroConselho filtroConselho);
	
	
	void vincularPessoaConselho(ConselhoPessoa conselhoPessoa);
	void desvincularPessoaConselho(ConselhoPessoa conselhoPessoa);
	boolean verificarVinculoConselhoPessoa(FiltroConselhoPessoa filtroConselhoPessoa);

}