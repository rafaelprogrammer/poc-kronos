package br.com.kronos.backend.aplicacao.instituicao.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroLimite;
import br.com.kronos.backend.aplicacao.instituicao.Limite;
import br.com.kronos.backend.aplicacao.instituicao.api.LimiteServico;
import br.com.kronos.backend.aplicacao.instituicao.LimiteRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class LimiteServicoImpl implements LimiteServico {

	@NonNull
	private LimiteRepositorio limiteRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(LimiteDTO limiteDTO) throws ExcecaoDeNegocio {

			return limiteRepositorio.criar(Limite.builder()
					                        .idTipoMencao(limiteDTO.getIdTipoMencao())                                
                                            .idEscala(limiteDTO.getIdEscala())
                                            .maximo(limiteDTO.getMaximo())
                                            .minimo(limiteDTO.getMinimo()).build());	

    }
	
	@Override
	public Long alterar(LimiteDTO limiteDTO) throws ExcecaoDeNegocio {
			return limiteRepositorio.alterar(Limite.builder()
											    .id(limiteDTO.getId())
											    .idTipoMencao(limiteDTO.getIdTipoMencao())                                
	                                            .idEscala(limiteDTO.getIdEscala())
	                                            .maximo(limiteDTO.getMaximo())
	                                            .minimo(limiteDTO.getMinimo()).build());
	}

	@Override
	public LimiteDTO buscarPorId (long id) {
		return modelMapper.map(limiteRepositorio.buscarPorId(id), LimiteDTO.class);
	}

	@Override
	public PaginacaoDTO<LimiteDTO> listar(FiltroLimite filtroLimite) throws ExcecaoDeNegocio {
			int total = limiteRepositorio.contar(filtroLimite);
			List<LimiteDTO> limites = modelMapper.map(limiteRepositorio.listar(filtroLimite),
					new TypeToken<List<LimiteDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<LimiteDTO>builder().total(total).dados(limites).build();
	}

	@Override
	public boolean excluir(Long id) {
		return limiteRepositorio.excluir(id);
	}

}
