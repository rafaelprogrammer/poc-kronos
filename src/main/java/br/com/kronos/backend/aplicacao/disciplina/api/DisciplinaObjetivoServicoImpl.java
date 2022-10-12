package br.com.kronos.backend.aplicacao.disciplina.api;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaObjetivo;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaObjetivoRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaObjetivo;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DisciplinaObjetivoServicoImpl implements DisciplinaObjetivoServico {

	@NonNull
	private DisciplinaObjetivoRepositorio disciplinaObjetivoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public void criar(DisciplinaObjetivoDTO disciplinaObjetivoDTO) throws ExcecaoDeNegocio {

		if (disciplinaObjetivoDTO.getIdsObjetivos() != null && !disciplinaObjetivoDTO.getIdsObjetivos().isEmpty()) {
			disciplinaObjetivoDTO.getIdsObjetivos().stream().forEach(idObjetivo -> {
				disciplinaObjetivoRepositorio.criar(DisciplinaObjetivo.builder()
                        .idDisciplina(disciplinaObjetivoDTO.getIdDisciplina())                                
                        .idObjetivo(idObjetivo)
                        .idSubFase(disciplinaObjetivoDTO.getIdSubFase())
                        .dataInicioVigencia(disciplinaObjetivoDTO.getDataInicioVigencia()).build());	
			});
		}
		
    }

	@Override
	public Long alterar(DisciplinaObjetivoDTO disciplinaObjetivoDTO) throws ExcecaoDeNegocio {
			return disciplinaObjetivoRepositorio.alterar(DisciplinaObjetivo.builder()
											.id(disciplinaObjetivoDTO.getId())
											.idDisciplina(disciplinaObjetivoDTO.getIdDisciplina())                                
                                            .idObjetivo(disciplinaObjetivoDTO.getIdObjetivo())
                                            .idSubFase(disciplinaObjetivoDTO.getIdSubFase())
                                            .dataInicioVigencia(disciplinaObjetivoDTO.getDataInicioVigencia())
                                            .dataFinalVigencia(disciplinaObjetivoDTO.getDataFinalVigencia()).build());

		
	}

	@Override
	public DisciplinaObjetivoDTO buscarPorId (long id) {
		return disciplinaObjetivoRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<DisciplinaObjetivoDTO> listar(FiltroDisciplinaObjetivo filtroDisciplinaObjetivo) throws ExcecaoDeNegocio {
		int total = disciplinaObjetivoRepositorio.contar(filtroDisciplinaObjetivo);
		
		return PaginacaoDTO.<DisciplinaObjetivoDTO>builder().total(total).dados(disciplinaObjetivoRepositorio.listar(filtroDisciplinaObjetivo)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return disciplinaObjetivoRepositorio.excluir(id);
	}
	
	@Override
	public PaginacaoDTO<DisciplinaObjetivoDTO> listarParaAtividadeDisciplinaObjetivo(FiltroDisciplinaObjetivo filtroDisciplinaObjetivo) {
		int total = disciplinaObjetivoRepositorio.contarParaAtividadeDisciplinaObjetivo(filtroDisciplinaObjetivo);
		
		return PaginacaoDTO.<DisciplinaObjetivoDTO>builder().total(total).dados(disciplinaObjetivoRepositorio.listarParaAtividadeDisciplinaObjetivo(filtroDisciplinaObjetivo)).build();
	}

}