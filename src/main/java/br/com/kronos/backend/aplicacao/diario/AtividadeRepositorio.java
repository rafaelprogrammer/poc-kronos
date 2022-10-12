package br.com.kronos.backend.aplicacao.diario;

import java.util.List;

import br.com.kronos.backend.aplicacao.diario.api.AtividadeDTO;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeDisciplinaCompetenciaDTO;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeDisciplinaDireitoDTO;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeDisciplinaHabilidadeDTO;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeDisciplinaObjetivoDTO;

public interface AtividadeRepositorio {
	
	boolean excluirAtividadeDisciplinaDireito(Long idDisciplinaDireito, Long idAtividade);
	Long criar(Atividade atividade);
	Long alterar(Atividade atividade);
	AtividadeDTO buscarPorId(Long id);
	List<AtividadeDTO> listar(FiltroAtividade filtroAtividade);
	boolean excluir(Long id);
	int contar(FiltroAtividade filtroAtividade);
	List<String> recuperarHorasAtividades(Long idAtividade);
	void criarAtividadeDisciplinaDireito(AtividadeDisciplinaDireito atividadeDisciplinaDireito);
	List<AtividadeDisciplinaDireitoDTO> listarAtividadesDisciplinasDireitos(FiltroAtividade filtroAtividade);
	int contarAtividadesDisciplinasDireitos(FiltroAtividade filtroAtividade);
	List<AtividadeDisciplinaObjetivoDTO> listarAtividadesDisciplinasObjetivos(FiltroAtividade filtroAtividade);
	int contarAtividadesDisciplinasObjetivos(FiltroAtividade filtroAtividade);
	void criarAtividadeDisciplinaObjetivo(AtividadeDisciplinaObjetivo atividadeDisciplinaObjetivo);
	boolean excluirAtividadeDisciplinaObjetivo(Long idDisciplinaObjetivo, Long idAtividade);
	List<AtividadeDisciplinaCompetenciaDTO> listarAtividadesDisciplinasCompetencias(FiltroAtividade filtroAtividade);
	int contarAtividadesDisciplinasCompetencias(FiltroAtividade filtroAtividade);
	void criarAtividadeDisciplinaCompetencia(AtividadeDisciplinaCompetencia atividadeDisciplinaCompetencia);
	boolean excluirAtividadeDisciplinaCompetencia(Long idDisciplinaCompetencia, Long idAtividade);
	List<AtividadeDisciplinaHabilidadeDTO> listarAtividadesDisciplinasHabilidades(FiltroAtividade filtroAtividade);
	int contarAtividadesDisciplinasHabilidades(FiltroAtividade filtroAtividade);
	void criarAtividadeDisciplinaHabilidade(AtividadeDisciplinaHabilidade atividadeDisciplinaHabilidade);
	boolean excluirAtividadeDisciplinaHabilidade(Long idDisciplinaHabilidade, Long idAtividade);
	List<AtividadeDTO> listarDatasAtividades(FiltroAtividade filtroAtividade);
	List<String> listarHorasIniciaisAtividades(Long idAtividade);
	boolean excluirDiasNaoLetivos(FiltroAtividade filtroAtividade);
	int verificarSeAtividadeExiste(FiltroAtividade filtroAtividade);
	boolean verificarDiarioCadastrado(FiltroAtividade filtroAtividade);
	boolean verificarFrequenciaCadastrada(FiltroAtividade filtroAtividade);
	boolean verificarHabilidadesCadastradas(FiltroAtividade filtroAtividade);
	boolean verificarSePodeAddAvaliacaoDeDesempenho(FiltroAtividade filtroAtividade);

}