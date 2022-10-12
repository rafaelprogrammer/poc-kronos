package br.com.kronos.backend.aplicacao.ocorrencia;

import java.util.List;

import br.com.kronos.backend.aplicacao.ocorrencia.api.OcorrenciaDTO;

public interface OcorrenciaRepositorio {
	
	Long criar(Ocorrencia ocorrencia);
	Long alterar(Ocorrencia ocorrencia);
	OcorrenciaDTO buscarPorId(Long id);
	List<OcorrenciaDTO> listar(FiltroOcorrencia filtroOcorrencia);
	boolean excluir(Long id);
	int contar(FiltroOcorrencia filtroOcorrencia);
	Float returnaValorGrauComportamento(Long idPessoaSelecionada);

}