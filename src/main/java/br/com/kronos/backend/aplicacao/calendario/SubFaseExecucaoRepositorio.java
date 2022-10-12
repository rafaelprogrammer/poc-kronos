package br.com.kronos.backend.aplicacao.calendario;

import java.time.LocalDate;
import java.util.List;

import br.com.kronos.backend.aplicacao.calendario.api.DadosCriaSubFaseExecucaoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoDTO;

public interface SubFaseExecucaoRepositorio {
	
	Long criar(SubFaseExecucao subFaseExecucao);
	Long alterar(SubFaseExecucao subFaseExecucao);
	SubFaseExecucaoDTO buscarPorId(Long id);
	List<SubFaseExecucaoDTO> listar(FiltroSubFaseExecucao filtroSubFaseExecucao);
	boolean excluir(Long id);
	int contar(FiltroSubFaseExecucao filtroSubFaseExecucao);
	List<DadosCriaSubFaseExecucaoDTO> listarParaCriar(FiltroSubFaseExecucao filtroSubFaseExecucao);
	List<SubFaseExecucaoDTO> listarParaDiarioEFrequencia(Long idTurma, Long idDisciplina, Long idPessoaUsuarioLogado);
	Long buscarIdParaCriarAtividade(Long idHorario, LocalDate data);

}