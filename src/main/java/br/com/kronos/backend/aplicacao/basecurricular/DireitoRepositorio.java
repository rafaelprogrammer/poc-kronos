package br.com.kronos.backend.aplicacao.basecurricular;

import java.util.List;

import br.com.kronos.backend.aplicacao.basecurricular.api.DireitoDTO;

public interface DireitoRepositorio {
	
	Long criar(Direito direito);
	Long alterar(Direito direito);
	DireitoDTO buscarPorId(Long id);
	List<DireitoDTO> listar(FiltroDireito filtroDireito);
	boolean excluir(Long id);
	int contar(FiltroDireito filtroDireito);
	boolean excluirCamposExperienciasDireito(long idDireito);
	Long criarCampoExperienciaDireito(Long idDireito, Long idCampoExperiencia);
	List<Long> buscarIdsCamposExperienciasDireito(Long idDireito);
	List<DireitoDTO> listarParaDisciplinaDireito(FiltroDireito filtroDireito);
	int contarParaDisciplinaDireito(FiltroDireito filtroDireito);

}