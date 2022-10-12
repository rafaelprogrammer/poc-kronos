package br.com.kronos.backend.aplicacao.funcionario.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.funcionario.FiltroConfiguracaoPonto;


public interface ConfiguracaoPontoServico {
	
	Long criar(ConfiguracaoPontoDTO dto);
	Long alterar(ConfiguracaoPontoDTO dto)throws ExcecaoDeNegocio;
	ConfiguracaoPontoDTO buscarPorId(Long id);
	PaginacaoDTO<ConfiguracaoPontoDTO>listar(FiltroConfiguracaoPonto filtro);
	boolean excluir(Long id);
	void verificarSeEstaSobrepondoOutraConfiguracao(FiltroConfiguracaoPonto filtro);
	void verificarSeExistePontosForaDaVigencia(FiltroConfiguracaoPonto filtro);
	boolean verificarSeExistePeloMenosUmaConfiguracao(FiltroConfiguracaoPonto filtro);
	boolean verificarSeExistePeloMenosUmaConfiguracaoOnline(FiltroConfiguracaoPonto filtro);
}
