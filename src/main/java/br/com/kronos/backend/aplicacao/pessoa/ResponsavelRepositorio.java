package br.com.kronos.backend.aplicacao.pessoa;

import java.time.LocalDate;
import java.util.List;

import br.com.kronos.backend.aplicacao.pessoa.api.ResponsavelDTO;

public interface ResponsavelRepositorio {
	
	int criar(Responsavel responsavel);
	List<ResponsavelDTO> listar(FiltroResponsavel filtro);
	int contar(FiltroResponsavel filtro);
	boolean excluir(int id);
	int alterar(Responsavel responsavel);
	ResponsavelDTO buscarPorId(int id);
	List<ResponsavelDTO> listarParaOcorrenciaResponsavelCiencia(LocalDate dataOcorrencia, Long idPessoaSelecionada);

}