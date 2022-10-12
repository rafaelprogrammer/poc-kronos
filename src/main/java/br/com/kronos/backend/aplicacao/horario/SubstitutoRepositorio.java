package br.com.kronos.backend.aplicacao.horario;

import java.util.List;

import br.com.kronos.backend.aplicacao.horario.api.DadosExcluiSubstitutoDTO;
import br.com.kronos.backend.aplicacao.horario.api.SubstitutoDTO;
import br.com.kronos.backend.aplicacao.horario.api.SubstitutoResumoDTO;

public interface SubstitutoRepositorio {
	
	Long criar(Substituto substituto);
	Long alterar(Substituto substituto);
	SubstitutoDTO buscarPorId(Long id);
	List<SubstitutoDTO> listar(FiltroSubstituto filtroSubstituto);
	int contar(FiltroSubstituto filtroSubstituto);
	List<SubstitutoResumoDTO> listarParaProfessoresHorario(Long idDisciplina, Long idFuncionarioDoHorario);
	long verificarSeExisteSubstituto(FiltroSubstituto filtroSubstituto);
	boolean excluir(DadosExcluiSubstitutoDTO dados);

}