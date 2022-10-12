package br.com.kronos.backend.aplicacao.calendario.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.calendario.FiltroCategoriaEvento;
import br.com.kronos.backend.aplicacao.calendario.CategoriaEvento;
import br.com.kronos.backend.aplicacao.calendario.api.CategoriaEventoServico;
import br.com.kronos.backend.aplicacao.calendario.CategoriaEventoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class CategoriaEventoServicoImpl implements CategoriaEventoServico {

	@NonNull
	private CategoriaEventoRepositorio categoriaEventoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(CategoriaEventoDTO categoriaEventoDTO) throws ExcecaoDeNegocio {

			return categoriaEventoRepositorio.criar(CategoriaEvento.builder()
					                        .nome(categoriaEventoDTO.getNome())                                
                                            .cor(categoriaEventoDTO.getCor()).build());	

    }
		
	@Override
	public Long alterar(CategoriaEventoDTO categoriaEventoDTO) throws ExcecaoDeNegocio {
			return categoriaEventoRepositorio.alterar(CategoriaEvento.builder()
											.id(categoriaEventoDTO.getId())
											 .nome(categoriaEventoDTO.getNome())                                
	                                         .cor(categoriaEventoDTO.getCor()).build());		
	}

	@Override
	public CategoriaEventoDTO buscarPorId (long id) {
		return modelMapper.map(categoriaEventoRepositorio.buscarPorId(id), CategoriaEventoDTO.class);
	}

	@Override
	public PaginacaoDTO<CategoriaEventoDTO> listar(FiltroCategoriaEvento filtroCategoriaEvento) throws ExcecaoDeNegocio {
			int total = categoriaEventoRepositorio.contar(filtroCategoriaEvento);
			List<CategoriaEventoDTO> categoriasEventos = modelMapper.map(categoriaEventoRepositorio.listar(filtroCategoriaEvento),
					new TypeToken<List<CategoriaEventoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<CategoriaEventoDTO>builder().total(total).dados(categoriasEventos).build();
	}

	@Override
	public boolean excluir(Long id) {
		return categoriaEventoRepositorio.excluir(id);
	}

}
