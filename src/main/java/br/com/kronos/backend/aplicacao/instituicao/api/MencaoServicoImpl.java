package br.com.kronos.backend.aplicacao.instituicao.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroMencao;
import br.com.kronos.backend.aplicacao.instituicao.Mencao;
import br.com.kronos.backend.aplicacao.instituicao.api.MencaoServico;
import br.com.kronos.backend.aplicacao.instituicao.MencaoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class MencaoServicoImpl implements MencaoServico {

	@NonNull
	private MencaoRepositorio mencaoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(MencaoDTO mencaoDTO) throws ExcecaoDeNegocio {

			return mencaoRepositorio.criar(Mencao.builder()
					                        .nome(mencaoDTO.getNome())                                
                                            .simbolo(mencaoDTO.getSimbolo()).build());	

    }
	
	@Override
	public Long alterar(MencaoDTO mencaoDTO) throws ExcecaoDeNegocio {
			return mencaoRepositorio.alterar(Mencao.builder()
											    .id(mencaoDTO.getId())
											    .nome(mencaoDTO.getNome())                                
	                                            .simbolo(mencaoDTO.getSimbolo()).build());
	}

	@Override
	public MencaoDTO buscarPorId (long id) {
		return modelMapper.map(mencaoRepositorio.buscarPorId(id), MencaoDTO.class);
	}

	@Override
	public PaginacaoDTO<MencaoDTO> listar(FiltroMencao filtroMencao) throws ExcecaoDeNegocio {
			int total = mencaoRepositorio.contar(filtroMencao);
			List<MencaoDTO> mencoes = modelMapper.map(mencaoRepositorio.listar(filtroMencao),
					new TypeToken<List<MencaoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<MencaoDTO>builder().total(total).dados(mencoes).build();
	}
	
	@Override
	public List<MencaoDTO> listarParaCombo(FiltroMencao filtroMencao) throws ExcecaoDeNegocio {
		return modelMapper.map(mencaoRepositorio.listar(filtroMencao),
				new TypeToken<List<MencaoDTO>>() {
				}.getType());
	}

	@Override
	public boolean excluir(Long id) {
		return mencaoRepositorio.excluir(id);
	}

}

