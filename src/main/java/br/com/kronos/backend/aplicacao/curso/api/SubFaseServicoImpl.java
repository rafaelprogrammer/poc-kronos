package br.com.kronos.backend.aplicacao.curso.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.curso.FiltroFase;
import br.com.kronos.backend.aplicacao.curso.FiltroSubFase;
import br.com.kronos.backend.aplicacao.curso.SubFase;
import br.com.kronos.backend.aplicacao.curso.api.SubFaseServico;
import br.com.kronos.backend.aplicacao.curso.SubFaseRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class SubFaseServicoImpl implements SubFaseServico {

	@NonNull
	private SubFaseRepositorio subFaseRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(SubFaseDTO subFaseDTO) throws ExcecaoDeNegocio {
			return subFaseRepositorio.criar(SubFase.builder()
                                            .numero(subFaseDTO.getNumero())                                
                                            .nome(subFaseDTO.getNome())
											.sigla(subFaseDTO.getSigla())	
                                            .idTipoPeriodo(subFaseDTO.getIdTipoPeriodo())
											.idFase(subFaseDTO.getIdFase()).build());
			
    }

	@Override
	public Long alterar(SubFaseDTO subFaseDTO) throws ExcecaoDeNegocio {
			return subFaseRepositorio.alterar(SubFase.builder()
											.id(subFaseDTO.getId())
											.numero(subFaseDTO.getNumero())                                
                                            .nome(subFaseDTO.getNome())
											.sigla(subFaseDTO.getSigla())	
                                            .idTipoPeriodo(subFaseDTO.getIdTipoPeriodo())
											.idFase(subFaseDTO.getIdFase()).build());
		
	}

	@Override
	public SubFaseDTO buscarPorId (Long id) {
		return modelMapper.map(subFaseRepositorio.buscarPorId(id), SubFaseDTO.class);
	}

	@Override
	public PaginacaoDTO<SubFaseDTO> listar(FiltroSubFase filtroSubFase) throws ExcecaoDeNegocio {
			int total = subFaseRepositorio.contar(filtroSubFase);
			List<SubFaseDTO> SubFases = modelMapper.map(subFaseRepositorio.listar(filtroSubFase),
					new TypeToken<List<SubFaseDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<SubFaseDTO>builder().total(total).dados(SubFases).build();
	}

	@Override
	public boolean excluir(Long id) {
		return subFaseRepositorio.excluir(id);
	}

	@Override
	public List<SubFaseResumoDTO> listarParaCombo(FiltroFase filtroFase) {
		return subFaseRepositorio.listarParaCombo(filtroFase);
	}

}
