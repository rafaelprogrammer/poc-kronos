package br.com.kronos.backend.aplicacao.curso.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.curso.FiltroFase;
import br.com.kronos.backend.aplicacao.curso.Fase;
import br.com.kronos.backend.aplicacao.curso.api.FaseServico;
import br.com.kronos.backend.aplicacao.curso.FaseRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class FaseServicoImpl implements FaseServico {

	@NonNull
	private FaseRepositorio FaseRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(FaseDTO faseDTO) throws ExcecaoDeNegocio {
			return FaseRepositorio.criar(Fase.builder()
                                            .numero(faseDTO.getNumero())                                
                                            .nome(faseDTO.getNome())
											.sigla(faseDTO.getSigla())	
                                            .idTipoPeriodo(faseDTO.getIdTipoPeriodo())
											.idPeriodo(faseDTO.getIdPeriodo()).build());
			
    }

	@Override
	public Long alterar(FaseDTO faseDTO) throws ExcecaoDeNegocio {
			return FaseRepositorio.alterar(Fase.builder()
											.id(faseDTO.getId())
											.numero(faseDTO.getNumero())                                
                                            .nome(faseDTO.getNome())
											.sigla(faseDTO.getSigla())	
                                            .idTipoPeriodo(faseDTO.getIdTipoPeriodo())
											.idPeriodo(faseDTO.getIdPeriodo()).build());
		
	}

	@Override
	public FaseDTO buscarPorId (long id) {
		return modelMapper.map(FaseRepositorio.buscarPorId(id), FaseDTO.class);
	}

	@Override
	public PaginacaoDTO<FaseDTO> listar(FiltroFase filtroFase) throws ExcecaoDeNegocio {
			int total = FaseRepositorio.contar(filtroFase);
			List<FaseDTO> Fases = modelMapper.map(FaseRepositorio.listar(filtroFase),
					new TypeToken<List<FaseDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<FaseDTO>builder().total(total).dados(Fases).build();
	}

	@Override
	public boolean excluir(Long id) {
		return FaseRepositorio.excluir(id);
	}

	@Override
	public List<FaseDTO> listarParaCombo(FiltroFase filtroFase) {
		return modelMapper.map(FaseRepositorio.listarParaCombo(filtroFase),
				new TypeToken<List<FaseDTO>>() {
				}.getType());
	}

}
