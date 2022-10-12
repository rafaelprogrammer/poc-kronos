package br.com.kronos.backend.aplicacao.funcionario.api;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.funcionario.FiltroFuncionario;
import br.com.kronos.backend.aplicacao.funcionario.Funcionario;
import br.com.kronos.backend.aplicacao.funcionario.FuncionarioRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class FuncionarioServicoImpl implements FuncionarioServico {

	@NonNull
	private FuncionarioRepositorio funcionarioRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	@CacheEvict(value = { Funcionario.CACHE_NAME }, allEntries=true)
	public Long criar(FuncionarioDTO dto) {
			return funcionarioRepositorio.criar(Funcionario.builder()
											.dataAdmissao(dto.getDataAdmissao())
											.dataDemissao(dto.getDataDemissao())	
											.idPessoa(dto.getIdPessoa())
											.idInstituicao(dto.getIdInstituicao())
											.build());
			
	}

	@Override
	@CacheEvict(value = { Funcionario.CACHE_NAME }, allEntries=true)
	public Long alterar(FuncionarioDTO dto) {
			return funcionarioRepositorio.alterar(Funcionario.builder()
					.id(dto.getId())
					.dataAdmissao(dto.getDataAdmissao())
					.dataDemissao(dto.getDataDemissao())	
					.idPessoa(dto.getIdPessoa())
					.idInstituicao(dto.getIdInstituicao())
					.build());
	}

	@Override
	public FuncionarioDTO buscarPorId(Long id) {
		return funcionarioRepositorio.buscarPorId(id);
	}

	@Override
	@Cacheable(cacheNames = Funcionario.CACHE_NAME, key="#filtroFuncionario.hashCode()")
	public PaginacaoDTO<FuncionarioDTO> listar(FiltroFuncionario filtroFuncionario) throws ExcecaoDeNegocio {
			int total = funcionarioRepositorio.contar(filtroFuncionario);
			
			return PaginacaoDTO.<FuncionarioDTO>builder().total(total).dados(funcionarioRepositorio.listar(filtroFuncionario)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return funcionarioRepositorio.excluir(id);
	}

	@Override
	public List<FuncionarioDTO> listarPorDisciplina(long idDisciplina) {
		return funcionarioRepositorio.listarPorDisciplina(idDisciplina);
	}

	@Override
	@Cacheable(cacheNames = Funcionario.COMBO_CACHE_NAME_OCORRENCIAS_POR_DATA_E_INSTITUICAO, key="{#dataOcorrencia, #idInstituicao}")
	public List<FuncionarioDTO> listarParaOcorrenciaRelatores(LocalDate dataOcorrencia, Long idInstituicao) {
		return funcionarioRepositorio.listarParaOcorrencia(dataOcorrencia, idInstituicao);
	}

	@Override
	public FuncionarioDTO buscarPorIdPessoa(Long idPessoa) {
		return funcionarioRepositorio.buscarPorIdPessoa(idPessoa);
	}

	@Override
	public List<FuncionarioDTO> listarParaOcorrenciaResponsavelAnulacao(LocalDate dataOcorrencia, Long idInstituicao) {
		return funcionarioRepositorio.listarParaOcorrenciaResponsavelAnulacao(dataOcorrencia, idInstituicao);
	}
	
}

