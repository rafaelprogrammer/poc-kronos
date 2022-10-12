package br.com.kronos.backend.aplicacao.funcionario.api;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.funcionario.ConfiguracaoPonto;
import br.com.kronos.backend.aplicacao.funcionario.ConfiguracaoPontoRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.FiltroConfiguracaoPonto;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ConfiguracaoPontoServicoImpl implements ConfiguracaoPontoServico {

	@NonNull
	private ConfiguracaoPontoRepositorio configuracaoPontoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	@CacheEvict(value = { ConfiguracaoPonto.CACHE_NAME }, allEntries=true)
	public Long criar(ConfiguracaoPontoDTO dto) {
		return configuracaoPontoRepositorio.criar(ConfiguracaoPonto.builder()
										.idFuncionario(dto.getIdFuncionario())
										.tarefaOnline(dto.isTarefaOnline())
										.horaInicialP1(dto.getHoraInicialP1())
										.horaFinalP1(dto.getHoraFinalP1())
										.horaInicialP2(dto.getHoraInicialP2())
										.horaFinalP2(dto.getHoraFinalP2())
										.horaInicialP3(dto.getHoraInicialP3())
										.horaFinalP3(dto.getHoraFinalP3())
										.cargaHorariaSemanal(dto.getCargaHorariaSemanal())
										.dataInicialVigencia(dto.getDataInicialVigencia())
										.dataFinalVigencia(dto.getDataFinalVigencia())
										.tolerancia(dto.getTolerancia())
										.build());
			
	}

	@Override
	@CacheEvict(value = { ConfiguracaoPonto.CACHE_NAME }, allEntries=true)
	public Long alterar(ConfiguracaoPontoDTO dto) {
		return configuracaoPontoRepositorio.alterar(ConfiguracaoPonto.builder()
				.id(dto.getId())
				.idFuncionario(dto.getIdFuncionario())
				.tarefaOnline(dto.isTarefaOnline())
				.horaInicialP1(dto.getHoraInicialP1())
				.horaFinalP1(dto.getHoraFinalP1())
				.horaInicialP2(dto.getHoraInicialP2())
				.horaFinalP2(dto.getHoraFinalP2())
				.horaInicialP3(dto.getHoraInicialP3())
				.horaFinalP3(dto.getHoraFinalP3())
				.cargaHorariaSemanal(dto.getCargaHorariaSemanal())
				.dataInicialVigencia(dto.getDataInicialVigencia())
				.dataFinalVigencia(dto.getDataFinalVigencia())
				.tolerancia(dto.getTolerancia())
				.build());
	}

	@Override
	public ConfiguracaoPontoDTO buscarPorId(Long id) {
		return configuracaoPontoRepositorio.buscarPorId(id);
	}

	@Override
	@Cacheable(cacheNames = ConfiguracaoPonto.CACHE_NAME, key="#filtro.hashCode()")
	public PaginacaoDTO<ConfiguracaoPontoDTO> listar(FiltroConfiguracaoPonto filtro) throws ExcecaoDeNegocio {
			int total = configuracaoPontoRepositorio.contar(filtro);
			
			return PaginacaoDTO.<ConfiguracaoPontoDTO>builder().total(total).dados(configuracaoPontoRepositorio.listar(filtro)).build();
	}

	@Override
	@CacheEvict(value = { ConfiguracaoPonto.CACHE_NAME }, allEntries=true)
	public boolean excluir(Long id) {
		return configuracaoPontoRepositorio.excluir(id);
	}

	@Override
	public void verificarSeEstaSobrepondoOutraConfiguracao(FiltroConfiguracaoPonto filtro) {
		int countConfiguracaoJaExistente = configuracaoPontoRepositorio.verificarSeEstaSobrepondoOutraConfiguracao(filtro);
		if (countConfiguracaoJaExistente > 0) {
			throw new ExcecaoDeNegocio("Configuração do Ponto está sobrepondo outra configuração já existente");
		}
	}

	@Override
	public void verificarSeExistePontosForaDaVigencia(FiltroConfiguracaoPonto filtro) {
		int countPontoForaDaVigencia = configuracaoPontoRepositorio.verificarSeExistePontosForaDaVigencia(filtro);
		if (countPontoForaDaVigencia > 0) {
			throw new ExcecaoDeNegocio("Já existem Pontos que estão fora da vigência");
		}
		
	}

	@Override
	public boolean verificarSeExistePeloMenosUmaConfiguracao(FiltroConfiguracaoPonto filtro) {
		return configuracaoPontoRepositorio.verificarSeExistePeloMenosUmaConfiguracao(filtro);
	}

	@Override
	public boolean verificarSeExistePeloMenosUmaConfiguracaoOnline(FiltroConfiguracaoPonto filtro) {
		return configuracaoPontoRepositorio.verificarSeExistePeloMenosUmaConfiguracaoOnline(filtro) > 0;
	}

}

