package br.com.kronos.backend.aplicacao.disciplina.api;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaCompetencia;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaCompetenciaRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaCompetencia;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DisciplinaCompetenciaServicoImpl implements DisciplinaCompetenciaServico {

	@NonNull
	private DisciplinaCompetenciaRepositorio disciplinaCompetenciaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public void criar(DisciplinaCompetenciaDTO disciplinaCompetenciaDTO) throws ExcecaoDeNegocio {
			
		if (disciplinaCompetenciaDTO.getIdsCompetencias() != null && !disciplinaCompetenciaDTO.getIdsCompetencias().isEmpty()) {
			disciplinaCompetenciaDTO.getIdsCompetencias().stream().forEach(idCompetencia -> {
				disciplinaCompetenciaRepositorio.criar(DisciplinaCompetencia.builder()
                        .idDisciplina(disciplinaCompetenciaDTO.getIdDisciplina())                                
                        .idCompetencia(idCompetencia)
                        .idSubFase(disciplinaCompetenciaDTO.getIdSubFase())
                        .dataInicioVigencia(disciplinaCompetenciaDTO.getDataInicioVigencia()).build());	
			});
		}

    }

	@Override
	public Long alterar(DisciplinaCompetenciaDTO disciplinaCompetenciaDTO) throws ExcecaoDeNegocio {
			return disciplinaCompetenciaRepositorio.alterar(DisciplinaCompetencia.builder()
											.id(disciplinaCompetenciaDTO.getId())
											.idDisciplina(disciplinaCompetenciaDTO.getIdDisciplina())                                
                                            .idCompetencia(disciplinaCompetenciaDTO.getIdCompetencia())
                                            .idSubFase(disciplinaCompetenciaDTO.getIdSubFase())
                                            .dataInicioVigencia(disciplinaCompetenciaDTO.getDataInicioVigencia())
                                            .dataFinalVigencia(disciplinaCompetenciaDTO.getDataFinalVigencia()).build());

		
	}

	@Override
	public DisciplinaCompetenciaDTO buscarPorId (long id) {
		return disciplinaCompetenciaRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<DisciplinaCompetenciaDTO> listar(FiltroDisciplinaCompetencia filtroDisciplinaCompetencia) throws ExcecaoDeNegocio {
		int total = disciplinaCompetenciaRepositorio.contar(filtroDisciplinaCompetencia);
		
		return PaginacaoDTO.<DisciplinaCompetenciaDTO>builder().total(total).dados(disciplinaCompetenciaRepositorio.listar(filtroDisciplinaCompetencia)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return disciplinaCompetenciaRepositorio.excluir(id);
	}
	
	@Override
	public PaginacaoDTO<DisciplinaCompetenciaDTO> listarParaAtividadeDisciplinaCompetencia(FiltroDisciplinaCompetencia filtroDisciplinaCompetencia) {
		int total = disciplinaCompetenciaRepositorio.contarParaAtividadeDisciplinaCompetencia(filtroDisciplinaCompetencia);
		
		return PaginacaoDTO.<DisciplinaCompetenciaDTO>builder().total(total).dados(disciplinaCompetenciaRepositorio.listarParaAtividadeDisciplinaCompetencia(filtroDisciplinaCompetencia)).build();
	}

}
