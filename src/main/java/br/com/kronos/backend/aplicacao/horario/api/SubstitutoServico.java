package br.com.kronos.backend.aplicacao.horario.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.horario.FiltroSubstituto;


public interface SubstitutoServico {
	
	void criar(SubstitutoDTO substitutoDTO);
	Long alterar(SubstitutoDTO substitutoDTO);
	SubstitutoDTO buscarPorId(long id);
	PaginacaoDTO<SubstitutoDTO>listar(FiltroSubstituto filtroSubstituto);
	public List<SubstitutoResumoDTO> listarParaProfessoresHorario(Long idDisciplina, Long idFuncionarioDoHorario);
	void validarIntervaloHorario(SubstitutoDTO dto);
	void validarDuplicidade(SubstitutoDTO substitutoDTO);
	boolean excluir(DadosExcluiSubstitutoDTO dados);
	
}
