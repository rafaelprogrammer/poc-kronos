package br.com.kronos.backend.aplicacao.disciplina;

import java.util.List;

import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaHabilidadeDTO;

public interface DisciplinaHabilidadeRepositorio {
	
	Long criar(DisciplinaHabilidade DisciplinaHabilidade);
	Long alterar(DisciplinaHabilidade DisciplinaHabilidade);
	DisciplinaHabilidadeDTO buscarPorId(Long id);
	List<DisciplinaHabilidadeDTO> listar(FiltroDisciplinaHabilidade filtroDisciplinaHabilidade);
	boolean excluir(Long id);
	int contar(FiltroDisciplinaHabilidade filtroDisciplinaHabilidade);
	List<DisciplinaHabilidadeDTO> listarParaAtividadeDisciplinaHabilidade(
			FiltroDisciplinaHabilidade filtroDisciplinaHabilidade);
	int contarParaAtividadeDisciplinaHabilidade(FiltroDisciplinaHabilidade filtroDisciplinaHabilidade);
	List<DisciplinaHabilidadeDTO> listarParaAvaliacaoHabilidade(FiltroDisciplinaHabilidade filtroDisciplinaHabilidade);
	int contarParaParaAvaliacaoHabilidade(FiltroDisciplinaHabilidade filtroDisciplinaHabilidade);

}
