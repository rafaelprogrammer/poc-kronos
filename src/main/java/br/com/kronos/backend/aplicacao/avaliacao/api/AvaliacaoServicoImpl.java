package br.com.kronos.backend.aplicacao.avaliacao.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.avaliacao.Avaliacao;
import br.com.kronos.backend.aplicacao.avaliacao.AvaliacaoHabilidadeRepositorio;
import br.com.kronos.backend.aplicacao.avaliacao.AvaliacaoRepositorio;
import br.com.kronos.backend.aplicacao.avaliacao.FiltroAvaliacao;
import br.com.kronos.backend.aplicacao.avaliacao.FiltroAvaliacaoHabilidade;
import br.com.kronos.backend.aplicacao.avaliacao.FiltroGrupoAvaliacao;
import br.com.kronos.backend.aplicacao.avaliacao.GrupoAvaliacaoRepositorio;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.resultado.AvaliadoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class AvaliacaoServicoImpl implements AvaliacaoServico {

	@NonNull
	private AvaliacaoRepositorio avaliacaoRepositorio;
	
	@NonNull
	private GrupoAvaliacaoRepositorio grupoAvaliacaoRepositorio;
	
	@NonNull
	private AvaliacaoHabilidadeRepositorio avaliacaoHabilidadeRepositorio;
	
	@NonNull
	private AvaliadoRepositorio avaliadoRepositorio;
	
	@NonNull
	private ModelMapper modelMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criarAvaliacaoAutomatica(AvaliacaoDTO avaliacaoDTO) {
		avaliacaoRepositorio.criarAvaliacaoAutomatica(avaliacaoDTO.getIdAtividade(), avaliacaoDTO.getIdTurma(), avaliacaoDTO.getIdDisciplina());
		avaliacaoHabilidadeRepositorio.criarAvaliacaoHabilidadeAutomatica(avaliacaoDTO.getIdAtividade());
		avaliadoRepositorio.criarAvaliadoAutomatico(avaliacaoDTO.getIdAtividade());
	}

	@Override
	@CacheEvict(value = {Avaliacao.COMBO_CACHE_NAME}, allEntries=true)
	public void criar(AvaliacaoDTO avaliacaoDTO) throws ExcecaoDeNegocio {

		avaliacaoRepositorio.criar(Avaliacao.builder()
				                        .horaInicio(avaliacaoDTO.getHoraInicio())                                
                                        .grupo(avaliacaoDTO.isGrupo())
                                        .numeroMaxParticipante(avaliacaoDTO.getNumeroMaxParticipante())
                                        .peso(avaliacaoDTO.getPeso())
                                        .observacao(avaliacaoDTO.getObservacao())
                                        .anulada(avaliacaoDTO.isAnulada())
                                        .motivoAnulacao(avaliacaoDTO.getMotivoAnulacao())
                                        .idTipoFormato(avaliacaoDTO.getIdTipoFormato())
                                        .idTipoRegistroNota(avaliacaoDTO.getIdTipoRegistroNota())
                                        .idTipoAbrangencia(avaliacaoDTO.getIdTipoAbrangencia())
                                        .idAtividade(avaliacaoDTO.getIdAtividade())
                                        .idTipoAvaliacao(avaliacaoDTO.getIdTipoAvaliacao())
                                        .tempoDuracao(avaliacaoDTO.getTempoDuracao())
                                        .idTurma(avaliacaoDTO.getIdTurma())
                                        .idDisciplina(avaliacaoDTO.getIdDisciplina())
                                        .build());	
		

    }

	@Override
	@CacheEvict(value = {Avaliacao.COMBO_CACHE_NAME}, allEntries=true)
	public void alterar(AvaliacaoDTO avaliacaoDTO) throws ExcecaoDeNegocio {
		 avaliacaoRepositorio.alterar(Avaliacao.builder()
											.id(avaliacaoDTO.getId())
											.horaInicio(avaliacaoDTO.getHoraInicio())                                
                                            .grupo(avaliacaoDTO.isGrupo())
                                            .numeroMaxParticipante(avaliacaoDTO.getNumeroMaxParticipante())
                                            .peso(avaliacaoDTO.getPeso())
                                            .observacao(avaliacaoDTO.getObservacao())
                                            .anulada(avaliacaoDTO.isAnulada())
                                            .motivoAnulacao(avaliacaoDTO.getMotivoAnulacao())
                                            .idTipoFormato(avaliacaoDTO.getIdTipoFormato())
                                            .idTipoRegistroNota(avaliacaoDTO.getIdTipoRegistroNota())
                                            .idTipoAbrangencia(avaliacaoDTO.getIdTipoAbrangencia())
                                            .idAtividade(avaliacaoDTO.getIdAtividade())
                                            .idTipoAvaliacao(avaliacaoDTO.getIdTipoAvaliacao())
                                            .tempoDuracao(avaliacaoDTO.getTempoDuracao())
                                            .idTurma(avaliacaoDTO.getIdTurma())
                                            .idDisciplina(avaliacaoDTO.getIdDisciplina())
                                            .build());	
		 
	}
	
	@Override
	public AvaliacaoDTO buscarPorId (long id) {
		AvaliacaoDTO avaliacaoDTO = avaliacaoRepositorio.buscarPorId(id);
		avaliacaoDTO.setGruposAvaliacoes(grupoAvaliacaoRepositorio.listar(FiltroGrupoAvaliacao.builder().idAvaliacao(id).build()));
		avaliacaoDTO.setAvaliacoesHabilidades(avaliacaoHabilidadeRepositorio.listar(FiltroAvaliacaoHabilidade.builder().idAvaliacao(id).build()));
		return avaliacaoDTO;
	}

	@Override
	public PaginacaoDTO<AvaliacaoDTO> listar(FiltroAvaliacao filtroAvaliacao) throws ExcecaoDeNegocio {
		int total = avaliacaoRepositorio.contar(filtroAvaliacao);
		return PaginacaoDTO.<AvaliacaoDTO>builder().total(total).dados(avaliacaoRepositorio.listar(filtroAvaliacao)).build();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(value = {Avaliacao.COMBO_CACHE_NAME}, allEntries=true)
	public boolean excluir(Long id) {
		grupoAvaliacaoRepositorio.excluirPorAvaliacao(id);
		avaliacaoHabilidadeRepositorio.excluirPorAvaliacao(id);
		avaliacaoRepositorio.excluir(id);
		return true;
	}

	@Override
	@Cacheable(cacheNames = Avaliacao.COMBO_CACHE_NAME, key="#filtro.hashCode()")
	public List<AvaliacaoDTO> listarParaCombo(FiltroAvaliacao filtro) {
		return avaliacaoRepositorio.listar(filtro);
	}

}
