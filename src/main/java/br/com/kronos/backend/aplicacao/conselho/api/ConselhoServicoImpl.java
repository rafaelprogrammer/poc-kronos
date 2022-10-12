package br.com.kronos.backend.aplicacao.conselho.api;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.conselho.FiltroConselho;
import br.com.kronos.backend.aplicacao.conselho.Conselho;
import br.com.kronos.backend.aplicacao.conselho.ConselhoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ConselhoServicoImpl implements ConselhoServico {

	@NonNull
	private ConselhoRepositorio conselhoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(ConselhoDTO conselhoDTO) throws ExcecaoDeNegocio {

			return conselhoRepositorio.criar(Conselho.builder()
					                        .idInstituicao(conselhoDTO.getIdInstituicao())                               
                                            .idTipoFinalidadeConselho(conselhoDTO.getIdTipoFinalidadeConselho())
                                            .dataInicioVigencia(conselhoDTO.getDataInicioVigencia())
                                            .dataFinalVigencia(conselhoDTO.getDataFinalVigencia()).build());
    }

	@Override
	public Long alterar(ConselhoDTO conselhoDTO) throws ExcecaoDeNegocio {
			return conselhoRepositorio.alterar(Conselho.builder()
											.id(conselhoDTO.getId())
											.idInstituicao(conselhoDTO.getIdInstituicao())                               
                                            .idTipoFinalidadeConselho(conselhoDTO.getIdTipoFinalidadeConselho())
                                            .dataInicioVigencia(conselhoDTO.getDataInicioVigencia())
                                            .dataFinalVigencia(conselhoDTO.getDataFinalVigencia()).build());
	}

	@Override
	public ConselhoDTO buscarPorId (long id) {
		return modelMapper.map(conselhoRepositorio.buscarPorId(id), ConselhoDTO.class);
	}

	@Override
	public PaginacaoDTO<ConselhoDTO> listar(FiltroConselho filtroConselho) throws ExcecaoDeNegocio {
			int total = conselhoRepositorio.contar(filtroConselho);
			List<ConselhoDTO> conselhos = modelMapper.map(conselhoRepositorio.listar(filtroConselho),
					new TypeToken<List<ConselhoDTO>>() {
					}.getType());
			
			return  PaginacaoDTO.<ConselhoDTO>builder().total(total).dados(conselhos).build();
	}

	@Override
	public boolean excluir(Long id) {
		return conselhoRepositorio.excluir(id);
	}

}

