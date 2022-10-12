package br.com.kronos.backend.aplicacao.diario.api;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.diario.Diario;
import br.com.kronos.backend.aplicacao.diario.DiarioRepositorio;
import br.com.kronos.backend.aplicacao.diario.FiltroDiario;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DiarioServicoImpl implements DiarioServico {

	@NonNull
	private DiarioRepositorio diarioRepositorio;
	
	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(DiarioDTO diarioDTO) throws ExcecaoDeNegocio {
		verificarSeExisteDiario(diarioDTO);
		return diarioRepositorio.criar(Diario.builder()
				                        .procedimento(diarioDTO.getProcedimento())                                
                                        .recurso(diarioDTO.getRecurso())
                                        .observacao(diarioDTO.getObservacao())
                                        .idAtividade(diarioDTO.getIdAtividade())
                                        .idTipoPrograma(diarioDTO.getIdTipoPrograma()).build());	

    }
	
	@Override
	public Long alterar(DiarioDTO diarioDTO) throws ExcecaoDeNegocio {
		verificarSeExisteDiario(diarioDTO);
		return diarioRepositorio.alterar(Diario.builder()
										.id(diarioDTO.getId())
										.procedimento(diarioDTO.getProcedimento())                                
                                        .recurso(diarioDTO.getRecurso())
                                        .observacao(diarioDTO.getObservacao())
                                        .idAtividade(diarioDTO.getIdAtividade())
                                        .idTipoPrograma(diarioDTO.getIdTipoPrograma()).build());

		
	}
	
	@Override
	public void verificarSeExisteDiario(DiarioDTO diarioDTO) {
		boolean existe = false;
		if (diarioDTO.getId() == 0) {
			existe =  diarioRepositorio.contar(FiltroDiario.builder().idAtividade(diarioDTO.getIdAtividade()).idTipoPrograma(diarioDTO.getIdTipoPrograma()).build()) > 0;
		}
		DiarioDTO diarioBanco = diarioRepositorio.buscarPorId(diarioDTO.getId());
		if (diarioBanco != null && !diarioBanco.getIdTipoPrograma().equals(diarioDTO.getIdTipoPrograma())) {
			existe =  diarioRepositorio.contar(FiltroDiario.builder().idAtividade(diarioDTO.getIdAtividade()).idTipoPrograma(diarioDTO.getIdTipoPrograma()).build()) > 0;
		}
		if (existe) {
			throw new ExcecaoDeNegocio("Já existe um diário registrado para este tipo de programa");
		}
	}

	@Override
	public DiarioDTO buscarPorId (long id) {
		return diarioRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<DiarioDTO> listar(FiltroDiario filtroDiario) throws ExcecaoDeNegocio {
		int total = diarioRepositorio.contar(filtroDiario);
		
		return PaginacaoDTO.<DiarioDTO>builder().total(total).dados(diarioRepositorio.listar(filtroDiario)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return diarioRepositorio.excluir(id);
	}
	
}

