package br.com.kronos.backend.aplicacao.curso.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.curso.FiltroCurso;
import br.com.kronos.backend.aplicacao.curso.FiltroCursoEscala;

public interface CursoServico {
	
	Long criar(CursoDTO cursoDTO);
	Long alterar(CursoDTO cursoDTO);
	CursoDTO buscarPorId(long id);
	PaginacaoDTO<CursoDTO> listar(FiltroCurso filtroCurso);
	List<CursoDTO> listarParaCombo(FiltroCurso filtroCurso);
	boolean excluir(long id);
	
	void vincularCursoEscala(CursoEscalaDTO dto);
	void desvincularCursoEscala(CursoEscalaDTO dto);
	boolean verificarVinculoCursoEscala(FiltroCursoEscala filtro);
	List<EstruturaDTO> visualizarEstrutura(long idCurso);
	List<CursoResumoDTO> listarParaHorario(Integer ano);
	List<CursoResumoDTO> listarParaModulosDosProfessores(Long idInstituicao, Long idPessoaUsuarioLogado);
	List<TipoMatrizHorarioDTO> listarTipoMatrizHorario();

}
