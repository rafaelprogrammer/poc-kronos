package br.com.kronos.backend.aplicacao.horario;

import java.util.List;

import br.com.kronos.backend.aplicacao.horario.api.HorarioDTO;

public interface HorarioRepositorio {
	
	Long criar(Horario horario);
	Long alterar(Horario horario);
	Horario buscarPorId(Long id);
	List<HorarioDTO> listar(FiltroHorario filtroHorario);
	boolean excluir(Long id);
	int contar(FiltroHorario filtroHorario);
	List<Long> listarIdsHorario(FiltroHorario filtroHorario);
	List<Horario> listarParaGerarAtividades(Long idTurma, Long idDisciplina, Long idPessoaUsuarioLogado);
	boolean verificarSeExisteHorario(FiltroHorario filtroHorario);

}