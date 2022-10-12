package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroNivel;
import br.com.kronos.backend.aplicacao.basecurricular.Nivel;
import br.com.kronos.backend.aplicacao.basecurricular.api.NivelServico;
import br.com.kronos.backend.aplicacao.basecurricular.NivelRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class NivelServicoImpl implements NivelServico {

	@NonNull
	private NivelRepositorio nivelRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Integer criar(NivelDTO nivelDTO) throws ExcecaoDeNegocio {

			return nivelRepositorio.criar(Nivel.builder()
					                        .nome(nivelDTO.getNome()).build());	

    }
	
	@Override
	public Integer alterar(NivelDTO nivelDTO) throws ExcecaoDeNegocio {
			return nivelRepositorio.alterar(Nivel.builder()
											    .id(nivelDTO.getId())
											    .nome(nivelDTO.getNome()).build());		
	}

	@Override
	public NivelDTO buscarPorId (int id) {
		return modelMapper.map(nivelRepositorio.buscarPorId(id), NivelDTO.class);
	}

	@Override
	public PaginacaoDTO<NivelDTO> listar(FiltroNivel filtroNivel) throws ExcecaoDeNegocio {
			int total = nivelRepositorio.contar(filtroNivel);
			List<NivelDTO> niveis = modelMapper.map(nivelRepositorio.listar(filtroNivel),
					new TypeToken<List<NivelDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<NivelDTO>builder().total(total).dados(niveis).build();
	}

	@Override
	public boolean excluir(int id) {
		return nivelRepositorio.excluir(id);
	}

	@Override
	public List<NivelDTO> listarParaCombo() {
		return modelMapper.map(nivelRepositorio.listar(FiltroNivel.builder().build()),
				new TypeToken<List<NivelDTO>>() {
				}.getType());
	}

}
