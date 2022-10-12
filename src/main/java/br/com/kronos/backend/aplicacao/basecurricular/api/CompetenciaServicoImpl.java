package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.Competencia;
import br.com.kronos.backend.aplicacao.basecurricular.CompetenciaRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroCompetencia;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class CompetenciaServicoImpl implements CompetenciaServico {

	@NonNull
	private CompetenciaRepositorio competenciaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(CompetenciaDTO competenciaDTO) throws ExcecaoDeNegocio {

			return competenciaRepositorio.criar(Competencia.builder()
					                        .descricao(competenciaDTO.getDescricao()) 
					                        .idNivel(competenciaDTO.getIdNivel())
					                        .idComponente(competenciaDTO.getIdComponente())
					                        .codigo(competenciaDTO.getCodigo())					                        
					                        .ativo(competenciaDTO.getAtivo()) 
					                        .bncc(competenciaDTO.getBncc()) 
					                        .geral(competenciaDTO.getGeral()) 
					                        .idInstituicao(competenciaDTO.getIdInstituicao()).build());	

    }

	@Override
	public Long alterar(CompetenciaDTO competenciaDTO) throws ExcecaoDeNegocio {
			return competenciaRepositorio.alterar(Competencia.builder()
											    .id(competenciaDTO.getId())
											    .descricao(competenciaDTO.getDescricao()) 
						                        .idNivel(competenciaDTO.getIdNivel())
						                        .idComponente(competenciaDTO.getIdComponente())
						                        .codigo(competenciaDTO.getCodigo())					                        
						                        .ativo(competenciaDTO.getAtivo()) 
						                        .bncc(competenciaDTO.getBncc()) 
						                        .geral(competenciaDTO.getGeral()) 
						                        .idInstituicao(competenciaDTO.getIdInstituicao()).build());			
	}

	@Override
	public CompetenciaDTO buscarPorId (long id) {
		return competenciaRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<CompetenciaDTO> listar(FiltroCompetencia filtroCompetencia) throws ExcecaoDeNegocio {
		int total = competenciaRepositorio.contar(filtroCompetencia);
		return PaginacaoDTO.<CompetenciaDTO>builder().total(total).dados(competenciaRepositorio.listar(filtroCompetencia)).build();
	}
	
	@Override
	public List<CompetenciaDTO> listarPorIds(List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		return competenciaRepositorio.listarPorIds(ids);
	}

	@Override
	public boolean excluir(Long id) {
		return competenciaRepositorio.excluir(id);
	}

	@Override
	public PaginacaoDTO<CompetenciaDTO> listarParaDisciplinaCompetencia(FiltroCompetencia filtroCompetencia) {
		int total = competenciaRepositorio.contarParaDisciplinaCompetencia(filtroCompetencia);
		return PaginacaoDTO.<CompetenciaDTO>builder().total(total).dados(competenciaRepositorio.listarParaDisciplinaCompetencia(filtroCompetencia)).build();
	}

}
