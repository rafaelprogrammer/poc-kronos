package br.com.kronos.backend.aplicacao.ocorrencia;

import java.time.LocalDate;
import java.util.List;

import br.com.kronos.backend.aplicacao.ocorrencia.api.TipoOcorrenciaDTO;

public interface TipoOcorrenciaRepositorio {
	
	Long criar(TipoOcorrencia tipoOcorrencia);
	Long alterar(TipoOcorrencia tipoOcorrencia);
	TipoOcorrenciaDTO buscarPorId(Long id);
	List<TipoOcorrenciaDTO> listar(FiltroTipoOcorrencia filtroTipoOcorrencia);
	boolean excluir(Long id);
	int contar(FiltroTipoOcorrencia filtroTipoOcorrencia);
	List<TipoOcorrenciaDTO> listarParaOcorrencia(LocalDate dataOcorrencia);

}