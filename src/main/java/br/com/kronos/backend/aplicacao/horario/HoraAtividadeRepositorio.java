package br.com.kronos.backend.aplicacao.horario;

import java.util.List;

public interface HoraAtividadeRepositorio {
	
	Long criar(HoraAtividade horaAtividade);
	Long alterar(HoraAtividade horaAtividade);
	HoraAtividade buscarPorId(Long id);
	List<HoraAtividade> listar(FiltroHoraAtividade filtroHoraAtividade);
	boolean excluir(Long id);
	int contar(FiltroHoraAtividade filtroHoraAtividade);

}