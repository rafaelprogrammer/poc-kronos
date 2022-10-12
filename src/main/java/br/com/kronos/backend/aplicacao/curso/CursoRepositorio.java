package br.com.kronos.backend.aplicacao.curso;

import java.util.List;

import br.com.kronos.backend.aplicacao.curso.api.CursoDTO;
import br.com.kronos.backend.aplicacao.curso.api.CursoResumoDTO;

public interface CursoRepositorio {
	
	Long criar(Curso curso);
	Long alterar(Curso curso);
	CursoDTO buscarPorId(Long id);
	List<CursoDTO> listar(FiltroCurso filtroCurso);
	List<CursoDTO> listarParaCombo(FiltroCurso filtroCurso);
	boolean excluir(Long id);
	int contar(FiltroCurso filtroCurso);
	
	void vincularCursoEscala(CursoEscala cursoEscala);
	void desvincularCursoEscala(CursoEscala cursoEscala);
	boolean verificarVinculoCursoEscala(FiltroCursoEscala filtro);
	List<Estrutura> visualizarEstrutura(long idCurso);
	List<CursoResumoDTO> listarParaHorario(Integer ano);
	List<CursoResumoDTO> listarParaModulosDosProfessores(Long idInstituicao, Long idPessoaUsuarioLogado);
	List<TipoMatrizHorario> listarTipoMatrizHorario();

}