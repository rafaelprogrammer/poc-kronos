package br.com.kronos.backend.aplicacao.calendario.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.calendario.FiltroSubFaseExecucao;
import br.com.kronos.backend.aplicacao.curso.FiltroFase;
import br.com.kronos.backend.aplicacao.curso.api.SubFaseResumoDTO;


public interface SubFaseExecucaoServico {
	
	Long alterar(SubFaseExecucaoDTO subFaseExecucaoDTO);
	SubFaseExecucaoDTO buscarPorId(long id);
	PaginacaoDTO<SubFaseExecucaoDTO>listar(FiltroSubFaseExecucao filtroSubFaseExecucao);
	boolean excluir(Long id);
	List<SubFaseResumoDTO> listarParaGeracaoSubFaseExecucao(FiltroFase filtroFase);
	void criar(List<SubFaseExecucaoDTO> dtos);
	List<SubFaseExecucaoDTO> listarParaDiarioEFrequencia(Long idTurma, Long idDisciplina, Long idPessoaUsuarioLogado);
}

