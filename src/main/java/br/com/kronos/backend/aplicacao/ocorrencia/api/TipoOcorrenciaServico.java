package br.com.kronos.backend.aplicacao.ocorrencia.api;

import java.time.LocalDate;
import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.ocorrencia.FiltroTipoOcorrencia;


public interface TipoOcorrenciaServico {
	
	Long criar(TipoOcorrenciaDTO tipoOcorrenciaDTO);
	Long alterar(TipoOcorrenciaDTO tipoOcorrenciaDTO);
	TipoOcorrenciaDTO buscarPorId(long id);
	PaginacaoDTO<TipoOcorrenciaDTO>listar(FiltroTipoOcorrencia filtroTipoOcorrencia);
	boolean excluir(Long id);
	List<TipoOcorrenciaDTO> listarParaOcorrencia(LocalDate dataOcorrencia);
	
}
