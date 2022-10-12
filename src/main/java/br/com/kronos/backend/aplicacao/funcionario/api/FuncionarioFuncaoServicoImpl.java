package br.com.kronos.backend.aplicacao.funcionario.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.funcionario.FiltroFuncionarioFuncao;
import br.com.kronos.backend.aplicacao.funcionario.FuncionarioFuncao;
import br.com.kronos.backend.aplicacao.funcionario.FuncionarioFuncaoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class FuncionarioFuncaoServicoImpl implements FuncionarioFuncaoServico {

	@NonNull
	private FuncionarioFuncaoRepositorio funcionarioFuncaoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(value = { FuncionarioFuncao.CACHE_NAME }, allEntries=true)
	public void criar(List<FuncionarioFuncaoDTO> dtos) throws ExcecaoDeNegocio {
		
		if (dtos !=null && !dtos.isEmpty()) {
			FuncionarioFuncaoDTO funcionarioFuncaoDTO = dtos.stream().filter(dto -> dto.getIdFuncionario() != null && dto.getIdFuncionario() > 0).findFirst().orElse(null);
			if (funcionarioFuncaoDTO !=null) {
				funcionarioFuncaoRepositorio.excluirPorIdFuncionario(funcionarioFuncaoDTO.getIdFuncionario());
			}
			dtos.stream().forEach(dto -> {
				if (dto.getIdFuncionario() != null && dto.getIdFuncionario() > 0) {
					funcionarioFuncaoRepositorio.criar(FuncionarioFuncao.builder()
							.idFuncionario(dto.getIdFuncionario())
							.idTipoFuncao(dto.getIdTipoFuncao())
							.funcaoRegistro(dto.getFuncaoRegistro())
							.build());
				}
			});
		}
	}
	
	@Override
	@Cacheable(cacheNames = FuncionarioFuncao.CACHE_NAME, key="#filtroFuncionarioFuncao.hashCode()")
	public List<FuncionarioFuncaoDTO> listar(FiltroFuncionarioFuncao filtroFuncionarioFuncao) throws ExcecaoDeNegocio {
		return funcionarioFuncaoRepositorio.listar(filtroFuncionarioFuncao);
	}
	
	@Override
	public void verificarSeExisteSomenteUmRegistroTrue(List<FuncionarioFuncaoDTO> dtos) {
		long qtdRegistros = dtos.stream().filter(dto -> dto.getFuncaoRegistro()).count();
		if (qtdRegistros > 1) {
			throw new ExcecaoDeNegocio("Somente pode haver uma função marcada como registro");
		}
	}
	
}
