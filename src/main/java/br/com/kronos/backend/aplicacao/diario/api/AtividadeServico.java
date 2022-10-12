package br.com.kronos.backend.aplicacao.diario.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.diario.FiltroAtividade;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;


public interface AtividadeServico {
	
	void criar(AtividadeDTO atividadeDTO) throws ExcecaoDeNegocio;
	Long alterar(AtividadeDTO atividadeDTO)throws ExcecaoDeNegocio;
	AtividadeDTO buscarPorId(long id);
	PaginacaoDTO<AtividadeDTO>listar(FiltroAtividade filtroAtividade) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	void confirmar(AtividadeDTO atividadeDTO);
	List<AtividadeDisciplinaDireitoDTO> listarAtividadesDisciplinasDireitos(FiltroAtividade filtroAtividade);
	void criarAtividadeDisciplinaDireito(AtividadeDisciplinaDireitoDTO dto);
	boolean excluirAtividadeDisciplinaDireito(Long idDisciplinaDireito, Long idAtividade);
	List<AtividadeDisciplinaObjetivoDTO> listarAtividadesDisciplinasObjetivos(FiltroAtividade filtroAtividade);
	void criarAtividadeDisciplinaObjetivo(AtividadeDisciplinaObjetivoDTO dto);
	boolean excluirAtividadeDisciplinaObjetivo(Long idDisciplinaObjetivo, Long idAtividade);
	List<AtividadeDisciplinaCompetenciaDTO> listarAtividadesDisciplinasCompetencias(
			FiltroAtividade filtroAtividade);
	void criarAtividadeDisciplinaCompetencia(AtividadeDisciplinaCompetenciaDTO dto);
	boolean excluirAtividadeDisciplinaCompetencia(Long idDisciplinaCompetencia, Long idAtividade);
	List<AtividadeDisciplinaHabilidadeDTO> listarAtividadesDisciplinasHabilidades(
			FiltroAtividade filtroAtividade);
	void criarAtividadeDisciplinaHabilidade(AtividadeDisciplinaHabilidadeDTO dto);
	boolean excluirAtividadeDisciplinaHabilidade(Long idDisciplinaHabilidade, Long idAtividade);
	List<AtividadeDTO> listarDatasAtividades(FiltroAtividade filtroAtividade);
	List<String> listarHorasIniciaisAtividades(Long idAtividade);
	boolean excluirDiasNaoLetivos(FiltroAtividade filtroAtividade);
	boolean verificarSePodeAddAvaliacaoDeDesempenho(FiltroAtividade filtroAtividade);
	
}

