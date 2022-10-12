package br.com.kronos.backend.aplicacao.ocorrencia.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.ocorrencia.FiltroOcorrencia;
import br.com.kronos.backend.aplicacao.ocorrencia.api.OcorrenciaDTO;


public interface OcorrenciaServico {
	
	Long criar(OcorrenciaDTO ocorrenciaDTO) throws ExcecaoDeNegocio;
	Long alterar(OcorrenciaDTO ocorrenciaDTO)throws ExcecaoDeNegocio;
	OcorrenciaDTO buscarPorId(long id);
	PaginacaoDTO<OcorrenciaDTO>listar(FiltroOcorrencia filtroOcorrencia) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	Float returnaValorGrauComportamento(Long idPessoaSelecionada);
	
}
