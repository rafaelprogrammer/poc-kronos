package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.Componente;
import br.com.kronos.backend.aplicacao.basecurricular.ComponenteRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroComponente;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ComponenteServicoImpl implements ComponenteServico {

	@NonNull
	private ComponenteRepositorio componenteRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(ComponenteDTO componenteDTO) throws ExcecaoDeNegocio {

			return componenteRepositorio.criar(Componente.builder()
					                        .nome(componenteDTO.getNome()) 
					                        .idNivel(componenteDTO.getIdNivel())
					                        .ativo(componenteDTO.getAtivo())
					                        .bncc(componenteDTO.getBncc())
					                        .idInstituicao(componenteDTO.getIdInstituicao())
					                        .build());	

    }
	
	@Override
	public Long alterar(ComponenteDTO componenteDTO) throws ExcecaoDeNegocio {
			return componenteRepositorio.alterar(Componente.builder()
											    .id(componenteDTO.getId())
											    .nome(componenteDTO.getNome()) 
						                        .idNivel(componenteDTO.getIdNivel())
						                        .ativo(componenteDTO.getAtivo())
						                        .bncc(componenteDTO.getBncc())
						                        .idInstituicao(componenteDTO.getIdInstituicao())
						                        .build());			
	}

	@Override
	public ComponenteDTO buscarPorId (long id) {
		return componenteRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<ComponenteDTO> listar(FiltroComponente filtroComponente) throws ExcecaoDeNegocio {
			int total = componenteRepositorio.contar(filtroComponente);
			return PaginacaoDTO.<ComponenteDTO>builder().total(total).dados(componenteRepositorio.listar(filtroComponente)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return componenteRepositorio.excluir(id);
	}

	@Override
	public List<ComponenteDTO> listarParaCombo(FiltroComponente filtro) {
		return componenteRepositorio.listar(filtro);
	}

}
