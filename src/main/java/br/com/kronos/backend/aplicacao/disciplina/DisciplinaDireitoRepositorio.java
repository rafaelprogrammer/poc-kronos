package br.com.kronos.backend.aplicacao.disciplina;

import java.util.List;

import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaDireitoDTO;

public interface DisciplinaDireitoRepositorio {
	
	Long criar(DisciplinaDireito DisciplinaDireito);
	Long alterar(DisciplinaDireito DisciplinaDireito);
	DisciplinaDireitoDTO buscarPorId(Long id);
	List<DisciplinaDireitoDTO> listar(FiltroDisciplinaDireito filtroDisciplinaDireito);
	boolean excluir(Long id);
	int contar(FiltroDisciplinaDireito filtroDisciplinaDireito);
	List<DisciplinaDireitoDTO> listarParaAtividadeDisciplinaDireito(FiltroDisciplinaDireito filtroDisciplinaDireito);
	int contarParaAtividadeDisciplinaDireito(FiltroDisciplinaDireito filtroDisciplinaDireito);

}
