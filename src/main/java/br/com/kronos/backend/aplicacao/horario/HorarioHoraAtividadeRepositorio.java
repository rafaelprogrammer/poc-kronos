package br.com.kronos.backend.aplicacao.horario;

import java.util.List;

public interface HorarioHoraAtividadeRepositorio {
    
    int contarPorIdHorarioEHorAtividade(FiltroHorarioHoraAtividade filtroHorarioHoraAtividade);
	int criar(HorarioHoraAtividade horarioHoraAtividade);
	List<HorarioHoraAtividade> listar(FiltroHorarioHoraAtividade filtroHorarioHoraAtividade);
	int contar(FiltroHorarioHoraAtividade filtroHorarioHoraAtividade);
	boolean excluir(long idHorario, long idHoraAtividade);

}
