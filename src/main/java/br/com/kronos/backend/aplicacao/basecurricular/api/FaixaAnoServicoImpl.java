package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.FaixaAno;
import br.com.kronos.backend.aplicacao.basecurricular.FaixaAnoRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroFaixaAno;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class FaixaAnoServicoImpl implements FaixaAnoServico {
	
	@NonNull
	private FaixaAnoRepositorio faixaAnoRepositorio;
	
	@NonNull
	private ModelMapper modelMapper;
	
	@Override
	public Integer criar(FaixaAnoDTO dto) throws ExcecaoDeNegocio {
		return faixaAnoRepositorio.criar(FaixaAno.builder()
				                        .nome(dto.getNome())
				                        .idNivel(dto.getIdNivel())
				                        .build());	
    }
	
	@Override
	public Integer alterar(FaixaAnoDTO dto) throws ExcecaoDeNegocio {
		return faixaAnoRepositorio.alterar(FaixaAno.builder()
				.id(dto.getId())
                .nome(dto.getNome())
                .idNivel(dto.getIdNivel())
                .build());		
	}

	@Override
	public FaixaAnoDTO buscarPorId (int id) {
		return faixaAnoRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<FaixaAnoDTO> listar(FiltroFaixaAno filtro) throws ExcecaoDeNegocio {
		int total = faixaAnoRepositorio.contar(filtro);
		return PaginacaoDTO.<FaixaAnoDTO>builder().total(total).dados(faixaAnoRepositorio.listar(filtro)).build();
	}
	
	@Override
	public boolean excluir(int id) {
		return faixaAnoRepositorio.excluir(id);
	}

	@Override
	public List<FaixaAnoDTO> listarParaCombo(FiltroFaixaAno filtro) {
		return faixaAnoRepositorio.listar(filtro);
	}

}
