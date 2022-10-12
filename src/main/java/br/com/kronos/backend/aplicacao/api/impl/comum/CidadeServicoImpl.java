package br.com.kronos.backend.aplicacao.api.impl.comum;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.CidadeDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.comum.CidadeRepositorio;
import br.com.kronos.backend.aplicacao.comum.FiltroCidade;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class CidadeServicoImpl implements CidadeServico {
	
	@NonNull
	private CidadeRepositorio cidadeRepositorio;
	
	@NonNull
	private ModelMapper modelMapper;

	@Override
	public PaginacaoDTO<CidadeDTO> listar(FiltroCidade filtroCidade) throws ExcecaoDeNegocio {
		try {
			int total = cidadeRepositorio.contar(filtroCidade);
			List<CidadeDTO> cidadesDTOS = modelMapper.map(cidadeRepositorio.listar(filtroCidade),
					new TypeToken<List<CidadeDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<CidadeDTO>builder().total(total).dados(cidadesDTOS).build();
			
		} catch (RuntimeException e) {
			log.error("Erro ao listar cidades", e);
			throw new ExcecaoDeNegocio("Erro ao listar cidades", e);
		}
	}

}
