package br.com.kronos.backend.aplicacao.horario.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.horario.FiltroHorario;


public interface HorarioServico {
	
//	Long criar(HorarioDTO horarioDTO) throws ExcecaoDeNegocio;
//	Long alterar(HorarioDTO horarioDTO)throws ExcecaoDeNegocio;
	PaginacaoDTO<HorarioDTO>listar(FiltroHorario filtroHorario) throws ExcecaoDeNegocio;
	boolean excluir(Long idHorario, Long idHoraAtividade) throws ExcecaoDeNegocio;
	HorariosDiaDaSemanaDTO carregarHorariosDiaDaSemana(FiltroHorario filtroHorario);
	void vincularHorarioHoraAtividade(DadosVinculacaoHorariosHorasAtividadesDTO dto);
	void validarVinculoComAtividade(Long idHorario);
	
}
