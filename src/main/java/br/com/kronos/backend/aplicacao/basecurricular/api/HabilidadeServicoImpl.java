package br.com.kronos.backend.aplicacao.basecurricular.api;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroHabilidade;
import br.com.kronos.backend.aplicacao.basecurricular.Habilidade;
import br.com.kronos.backend.aplicacao.basecurricular.HabilidadeRepositorio;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class HabilidadeServicoImpl implements HabilidadeServico {

	@NonNull
	private HabilidadeRepositorio habilidadeRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long criar(HabilidadeDTO habilidadeDTO) throws ExcecaoDeNegocio {

		Long id = habilidadeRepositorio.criar(Habilidade.builder()
											.idComponente(habilidadeDTO.getIdComponente())
					                        .descricao(habilidadeDTO.getDescricao()) 
					                        .codigo(habilidadeDTO.getCodigo())					                        
					                        .ativo(habilidadeDTO.getAtivo()) 
					                        .bncc(habilidadeDTO.getBncc()) 
					                        .eixo(habilidadeDTO.getEixo()) 
					                        .campoAtuacao(habilidadeDTO.getCampoAtuacao()) 
					                        .praticaLinguagem(habilidadeDTO.getPraticaLinguagem()) 
					                        .objetoConhecimento(habilidadeDTO.getObjetoConhecimento()) 
					                        .unidadeTematica(habilidadeDTO.getUnidadeTematica()) 
					                        .idInstituicao(habilidadeDTO.getIdInstituicao()).build());
		
		if(habilidadeDTO.getIdsCompetencias() != null && !habilidadeDTO.getIdsCompetencias().isEmpty()) {
			habilidadeDTO.getIdsCompetencias().stream().forEach(idCompetencia -> {
				habilidadeRepositorio.criarHabilidadeCompetencia(id, idCompetencia);
			});
		}
		
		if(habilidadeDTO.getIdsFaixasAnos() != null && !habilidadeDTO.getIdsFaixasAnos().isEmpty()) {
			habilidadeDTO.getIdsFaixasAnos().stream().forEach(idFaixaAno -> {
				habilidadeRepositorio.criarHabilidadeFaixaAno(id, idFaixaAno);
			});
		}
		
		return id;
    }
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long alterar(HabilidadeDTO habilidadeDTO) throws ExcecaoDeNegocio {
		habilidadeRepositorio.alterar(Habilidade.builder()
										    .id(habilidadeDTO.getId())
										    .idComponente(habilidadeDTO.getIdComponente())
					                        .descricao(habilidadeDTO.getDescricao()) 
					                        .codigo(habilidadeDTO.getCodigo())					                        
					                        .ativo(habilidadeDTO.getAtivo()) 
					                        .bncc(habilidadeDTO.getBncc()) 
					                        .eixo(habilidadeDTO.getEixo()) 
					                        .campoAtuacao(habilidadeDTO.getCampoAtuacao()) 
					                        .praticaLinguagem(habilidadeDTO.getPraticaLinguagem()) 
					                        .objetoConhecimento(habilidadeDTO.getObjetoConhecimento()) 
					                        .unidadeTematica(habilidadeDTO.getUnidadeTematica()) 
					                        .idInstituicao(habilidadeDTO.getIdInstituicao()).build());		
		
		if(habilidadeDTO.getIdsCompetencias() != null && !habilidadeDTO.getIdsCompetencias().isEmpty()) {
			habilidadeRepositorio.excluirHabilidadeCompetencia(habilidadeDTO.getId());
			habilidadeDTO.getIdsCompetencias().stream().forEach(idCompetencia -> {
				habilidadeRepositorio.criarHabilidadeCompetencia(habilidadeDTO.getId(), idCompetencia);
			});
		} else {
			// se tiver vazio sera excluido o que existir no banco
			habilidadeRepositorio.excluirHabilidadeCompetencia(habilidadeDTO.getId());
		}
		
		if(habilidadeDTO.getIdsFaixasAnos() != null && !habilidadeDTO.getIdsFaixasAnos().isEmpty()) {
			habilidadeRepositorio.excluirHabilidadeFaixaAno(habilidadeDTO.getId());
			habilidadeDTO.getIdsFaixasAnos().stream().forEach(idFaixaAno -> {
				habilidadeRepositorio.criarHabilidadeFaixaAno(habilidadeDTO.getId(), idFaixaAno);
			});
		}
		
		return habilidadeDTO.getId();
	}

	@Override
	public HabilidadeDTO buscarPorId (long id) {
		HabilidadeDTO dto = habilidadeRepositorio.buscarPorId(id);
		dto.setIdsCompetencias(habilidadeRepositorio.buscarIdsCompetencias(id));
		dto.setIdsFaixasAnos(habilidadeRepositorio.buscarIdsFaixasAnos(id));
		return dto;
	}

	@Override
	public PaginacaoDTO<HabilidadeDTO> listar(FiltroHabilidade filtroHabilidade) throws ExcecaoDeNegocio {
		int total = habilidadeRepositorio.contar(filtroHabilidade);
		return PaginacaoDTO.<HabilidadeDTO>builder().total(total).dados(habilidadeRepositorio.listar(filtroHabilidade)).build();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean excluir(Long id) {
		habilidadeRepositorio.excluirHabilidadeCompetencia(id);
		habilidadeRepositorio.excluirHabilidadeFaixaAno(id);
		return habilidadeRepositorio.excluir(id);
	}

	@Override
	public PaginacaoDTO<HabilidadeDTO> listarParaDisciplinaHabilidade(FiltroHabilidade filtroHabilidade) {
		int total = habilidadeRepositorio.contarParaDisciplinaHabilidade(filtroHabilidade);
		return PaginacaoDTO.<HabilidadeDTO>builder().total(total).dados(habilidadeRepositorio.listarParaDisciplinaHabilidade(filtroHabilidade)).build();
	}
}
