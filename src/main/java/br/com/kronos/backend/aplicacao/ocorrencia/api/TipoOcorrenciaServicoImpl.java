package br.com.kronos.backend.aplicacao.ocorrencia.api;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.ocorrencia.FiltroTipoOcorrencia;
import br.com.kronos.backend.aplicacao.ocorrencia.TipoOcorrencia;
import br.com.kronos.backend.aplicacao.ocorrencia.TipoOcorrenciaRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class TipoOcorrenciaServicoImpl implements TipoOcorrenciaServico {

	@NonNull
	private TipoOcorrenciaRepositorio tipoOcorrenciaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(TipoOcorrenciaDTO tipoOcorrenciaDTO) throws ExcecaoDeNegocio {

			return tipoOcorrenciaRepositorio.criar(TipoOcorrencia.builder()
					.fator(tipoOcorrenciaDTO.getFator())                                
                    .codigo(tipoOcorrenciaDTO.getCodigo())
                    .descricao(tipoOcorrenciaDTO.getDescricao())
                    .valor(tipoOcorrenciaDTO.getValor())
                    .idInstituicao(tipoOcorrenciaDTO.getIdInstituicao())
                    .dataInicioVigencia(tipoOcorrenciaDTO.getDataInicioVigencia())
                    .dataFinalVigencia(tipoOcorrenciaDTO.getDataFinalVigencia()).build());	

}

	@Override
	public Long alterar(TipoOcorrenciaDTO tipoOcorrenciaDTO) throws ExcecaoDeNegocio {
			return tipoOcorrenciaRepositorio.alterar(TipoOcorrencia.builder()
												.id(tipoOcorrenciaDTO.getId())
												.fator(tipoOcorrenciaDTO.getFator())                                
							                    .codigo(tipoOcorrenciaDTO.getCodigo())
							                    .descricao(tipoOcorrenciaDTO.getDescricao())
							                    .valor(tipoOcorrenciaDTO.getValor())
							                    .idInstituicao(tipoOcorrenciaDTO.getIdInstituicao())
							                    .dataInicioVigencia(tipoOcorrenciaDTO.getDataInicioVigencia())
							                    .dataFinalVigencia(tipoOcorrenciaDTO.getDataFinalVigencia()).build());
		
	}

	@Override
	public TipoOcorrenciaDTO buscarPorId (long id) {
		return tipoOcorrenciaRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<TipoOcorrenciaDTO> listar(FiltroTipoOcorrencia filtroTipoOcorrencia) throws ExcecaoDeNegocio {
		int total = tipoOcorrenciaRepositorio.contar(filtroTipoOcorrencia);
		
		return PaginacaoDTO.<TipoOcorrenciaDTO>builder().total(total).dados(tipoOcorrenciaRepositorio.listar(filtroTipoOcorrencia)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return tipoOcorrenciaRepositorio.excluir(id);
	}

	@Override
	@Cacheable(cacheNames = TipoOcorrencia.COMBO_CACHE_NAME_TIPOS_OCORRENCIAS_POR_DATA, key="{#dataOcorrencia}")
	public List<TipoOcorrenciaDTO> listarParaOcorrencia(LocalDate dataOcorrencia) {
		return tipoOcorrenciaRepositorio.listarParaOcorrencia(dataOcorrencia);
	}

}

