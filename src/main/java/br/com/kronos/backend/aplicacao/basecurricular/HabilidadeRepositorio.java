package br.com.kronos.backend.aplicacao.basecurricular;

import java.util.List;

import br.com.kronos.backend.aplicacao.basecurricular.api.HabilidadeDTO;

public interface HabilidadeRepositorio {
	
	Long criar(Habilidade habilidade);
	Long alterar(Habilidade habilidade);
	HabilidadeDTO buscarPorId(Long id);
	List<HabilidadeDTO> listar(FiltroHabilidade filtroHabilidade);
	boolean excluir(Long id);
	int contar(FiltroHabilidade filtroHabilidade);
	Long criarHabilidadeCompetencia(Long idHabilidade, Long idCompetencia);
	List<Long> buscarIdsCompetencias(Long idHabilidade);
	boolean excluirHabilidadeCompetencia(long idHabilidade);
	Long criarHabilidadeFaixaAno(Long idHabilidade, Long idFaixaAno);
	List<Long> buscarIdsFaixasAnos(Long idHabilidade);
	boolean excluirHabilidadeFaixaAno(long idHabilidade);
	List<HabilidadeDTO> listarParaDisciplinaHabilidade(FiltroHabilidade filtroHabilidade);
	int contarParaDisciplinaHabilidade(FiltroHabilidade filtroHabilidade);

}