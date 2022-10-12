package br.com.kronos.backend.aplicacao.api.impl.comum;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaisDTO;
import br.com.kronos.backend.aplicacao.comum.FiltroPais;
import br.com.kronos.backend.aplicacao.comum.PaisRepositorio;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class PaisServicoImpl implements PaisServico {
	
	@NonNull
	private PaisRepositorio paisRepositorio;
	
	@NonNull
	private ModelMapper modelMapper;

	@Override
	public List<PaisDTO> listar(FiltroPais filtroEstadoPais) throws ExcecaoDeNegocio {
		try {
			List<PaisDTO> paisDTOS = modelMapper.map(paisRepositorio.listar(filtroEstadoPais),
					new TypeToken<List<PaisDTO>>() {
					}.getType());
			
			return paisDTOS;
			
		} catch (RuntimeException e) {
			log.error("Erro ao listar paises", e);
			throw new ExcecaoDeNegocio("Erro ao listar paises", e);
		}
	}

}
