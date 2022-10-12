package br.com.kronos.backend.aplicacao.pessoa.api;

import java.time.LocalDate;
import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.pessoa.FiltroResponsavel;

public interface ResponsavelServico {
	
	int criar(ResponsavelDTO dto);
	PaginacaoDTO<ResponsavelDTO> listar(FiltroResponsavel filtro);
	boolean excluir(int id);
	int alterar(ResponsavelDTO dto);
	ResponsavelDTO buscarPorId(int id);
	List<ResponsavelDTO> listarResponsaveisFinanceiros(FiltroResponsavel filtro);
	List<ResponsavelDTO> listarParaOcorrenciaResponsavelCiencia(LocalDate dataOcorrencia, Long idPessoaSelecionada);

}