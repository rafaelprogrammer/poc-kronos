package br.com.kronos.backend.aplicacao.disciplina.api;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaHabilidade;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaHabilidadeRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaHabilidade;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DisciplinaHabilidadeServicoImpl implements DisciplinaHabilidadeServico {

	@NonNull
	private DisciplinaHabilidadeRepositorio disciplinaHabilidadeRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public void criar(DisciplinaHabilidadeDTO disciplinaHabilidadeDTO) throws ExcecaoDeNegocio {

		if (disciplinaHabilidadeDTO.getIdsHabilidades() != null && !disciplinaHabilidadeDTO.getIdsHabilidades().isEmpty()) {
			disciplinaHabilidadeDTO.getIdsHabilidades().stream().forEach(idHabilidade -> {
				disciplinaHabilidadeRepositorio.criar(DisciplinaHabilidade.builder()
                        .idDisciplina(disciplinaHabilidadeDTO.getIdDisciplina())                                
                        .idHabilidade(idHabilidade)
                        .idSubFase(disciplinaHabilidadeDTO.getIdSubFase())
                        .dataInicioVigencia(disciplinaHabilidadeDTO.getDataInicioVigencia()).build());	
			});
		}

    }

	@Override
	public Long alterar(DisciplinaHabilidadeDTO disciplinaHabilidadeDTO) throws ExcecaoDeNegocio {
			return disciplinaHabilidadeRepositorio.alterar(DisciplinaHabilidade.builder()
											.id(disciplinaHabilidadeDTO.getId())
											.idDisciplina(disciplinaHabilidadeDTO.getIdDisciplina())                                
                                            .idHabilidade(disciplinaHabilidadeDTO.getIdHabilidade())
                                            .idSubFase(disciplinaHabilidadeDTO.getIdSubFase())
                                            .dataInicioVigencia(disciplinaHabilidadeDTO.getDataInicioVigencia())
                                            .dataFinalVigencia(disciplinaHabilidadeDTO.getDataFinalVigencia()).build());

		
	}

	@Override
	public DisciplinaHabilidadeDTO buscarPorId (long id) {
		return disciplinaHabilidadeRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<DisciplinaHabilidadeDTO> listar(FiltroDisciplinaHabilidade filtroDisciplinaHabilidade) throws ExcecaoDeNegocio {
		int total = disciplinaHabilidadeRepositorio.contar(filtroDisciplinaHabilidade);
		
		return PaginacaoDTO.<DisciplinaHabilidadeDTO>builder().total(total).dados(disciplinaHabilidadeRepositorio.listar(filtroDisciplinaHabilidade)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return disciplinaHabilidadeRepositorio.excluir(id);
	}
	
	@Override
	public PaginacaoDTO<DisciplinaHabilidadeDTO> listarParaAtividadeDisciplinaHabilidade(FiltroDisciplinaHabilidade filtroDisciplinaHabilidade) {
		int total = disciplinaHabilidadeRepositorio.contarParaAtividadeDisciplinaHabilidade(filtroDisciplinaHabilidade);
		
		return PaginacaoDTO.<DisciplinaHabilidadeDTO>builder().total(total).dados(disciplinaHabilidadeRepositorio.listarParaAtividadeDisciplinaHabilidade(filtroDisciplinaHabilidade)).build();
	}

	@Override
	public PaginacaoDTO<DisciplinaHabilidadeDTO> listarParaAvaliacaoHabilidade(
			FiltroDisciplinaHabilidade filtroDisciplinaHabilidade) {
		int total = disciplinaHabilidadeRepositorio.contarParaParaAvaliacaoHabilidade(filtroDisciplinaHabilidade);
		return PaginacaoDTO.<DisciplinaHabilidadeDTO>builder().total(total).dados(disciplinaHabilidadeRepositorio.listarParaAvaliacaoHabilidade(filtroDisciplinaHabilidade)).build();
	}

}
