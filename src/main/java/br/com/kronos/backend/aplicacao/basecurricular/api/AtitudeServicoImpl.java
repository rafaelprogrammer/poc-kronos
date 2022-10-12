package br.com.kronos.backend.aplicacao.basecurricular.api;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.Atitude;
import br.com.kronos.backend.aplicacao.basecurricular.AtitudeRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroAtitude;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class AtitudeServicoImpl implements AtitudeServico {

	@NonNull
	private AtitudeRepositorio atitudeRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(AtitudeDTO atitudeDTO) throws ExcecaoDeNegocio {

			return atitudeRepositorio.criar(Atitude.builder()
					                        .nome(atitudeDTO.getNome()) 
					                        .codigo(atitudeDTO.getCodigo()) 
					                        .idInstituicao(atitudeDTO.getIdInstituicao())
					                        .idValor(atitudeDTO.getIdValor())
					                        .ativo(true) .build());	
    }
	
	@Override
	public Long alterar(AtitudeDTO atitudeDTO) throws ExcecaoDeNegocio {
			return atitudeRepositorio.alterar(Atitude.builder()
											    .id(atitudeDTO.getId())
											    .nome(atitudeDTO.getNome()) 
						                        .codigo(atitudeDTO.getCodigo()) 
						                        .idInstituicao(atitudeDTO.getIdInstituicao())
						                        .idValor(atitudeDTO.getIdValor())
						                        .ativo(atitudeDTO.isAtivo()) .build());		
	}

	@Override
	public AtitudeDTO buscarPorId (long id) {
		return atitudeRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<AtitudeDTO> listar(FiltroAtitude filtroAtitude) throws ExcecaoDeNegocio {
		int total = atitudeRepositorio.contar(filtroAtitude);
		return PaginacaoDTO.<AtitudeDTO>builder().total(total).dados(atitudeRepositorio.listar(filtroAtitude)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return atitudeRepositorio.excluir(id);
	}

}
