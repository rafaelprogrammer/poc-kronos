package br.com.kronos.backend.aplicacao.curso.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.curso.FiltroPortaria;
import br.com.kronos.backend.aplicacao.curso.Portaria;
import br.com.kronos.backend.aplicacao.curso.PortariaRepositorio;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class PortariaServicoImpl implements PortariaServico {

	@NonNull
	private PortariaRepositorio portariaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(PortariaDTO portariaDTO) throws ExcecaoDeNegocio {

			return portariaRepositorio.criar(Portaria.builder()
					                        .dataPublicacao(portariaDTO.getDataPublicacao())                                
                                            .dataInicioVigencia(portariaDTO.getDataInicioVigencia())
                                            .dataFinalVigencia(portariaDTO.getDataFinalVigencia())
                                            .descricao(portariaDTO.getDescricao())
                                            .documentoPublicacao(portariaDTO.getDocumentoPublicacao())
                                            .idTipoPortaria(portariaDTO.getIdTipoPortaria())
                                            .idCurso(portariaDTO.getIdCurso()).build());	

    }
	

	@Override
	public Long alterar(PortariaDTO portariaDTO) throws ExcecaoDeNegocio {
			return portariaRepositorio.alterar(Portaria.builder()
											.id(portariaDTO.getId())
											.dataPublicacao(portariaDTO.getDataPublicacao())                                
                                            .dataInicioVigencia(portariaDTO.getDataInicioVigencia())
                                            .dataFinalVigencia(portariaDTO.getDataFinalVigencia())
                                            .descricao(portariaDTO.getDescricao())
                                            .documentoPublicacao(portariaDTO.getDocumentoPublicacao())
                                            .idTipoPortaria(portariaDTO.getIdTipoPortaria())
                                            .idCurso(portariaDTO.getIdCurso()).build());

		
	}

	@Override
	public PortariaDTO buscarPorId (long id) {
		return modelMapper.map(portariaRepositorio.buscarPorId(id), PortariaDTO.class);
	}

	@Override
	public PaginacaoDTO<PortariaDTO> listar(FiltroPortaria filtroPortaria) throws ExcecaoDeNegocio {
			int total = portariaRepositorio.contar(filtroPortaria);
			List<PortariaDTO> Portarias = modelMapper.map(portariaRepositorio.listar(filtroPortaria),
					new TypeToken<List<PortariaDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<PortariaDTO>builder().total(total).dados(Portarias).build();
	}

	@Override
	public boolean excluir(Long id) {
		return portariaRepositorio.excluir(id);
	}

}
