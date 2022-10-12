package br.com.kronos.backend.aplicacao.funcionario;

import java.util.List;

import br.com.kronos.backend.aplicacao.funcionario.api.ConfiguracaoPontoDTO;

public interface ConfiguracaoPontoRepositorio {
	
	Long criar(ConfiguracaoPonto configuracaoPonto);
	Long alterar(ConfiguracaoPonto configuracaoPonto);
	ConfiguracaoPontoDTO buscarPorId(Long id);
	List<ConfiguracaoPontoDTO> listar(FiltroConfiguracaoPonto filtro);
	boolean excluir(Long id);
	int contar(FiltroConfiguracaoPonto filtro);
	int verificarSeEstaSobrepondoOutraConfiguracao(FiltroConfiguracaoPonto filtro);
	int verificarSeExistePontosForaDaVigencia(FiltroConfiguracaoPonto filtro);
	Long recuperarIdPorIdFuncionario(Long idFuncionario);
	boolean verificarSeExistePeloMenosUmaConfiguracao(FiltroConfiguracaoPonto filtro);
	int verificarSeExistePeloMenosUmaConfiguracaoOnline(FiltroConfiguracaoPonto filtro);

}