package br.com.kronos.backend.aplicacao.funcionario.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import br.com.kronos.backend.aplicacao.api.dto.comum.MesDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.funcionario.ConfiguracaoPontoRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.EnumTipoPeriodoPonto;
import br.com.kronos.backend.aplicacao.funcionario.FiltroPonto;
import br.com.kronos.backend.aplicacao.funcionario.Ponto;
import br.com.kronos.backend.aplicacao.funcionario.PontoRepositorio;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class PontoServicoImpl implements PontoServico {

	@NonNull
	private PontoRepositorio pontoRepositorio;
	
	@NonNull
	private ConfiguracaoPontoRepositorio configuracaoPontoRepositorio;
	
	@NonNull
	private ModelMapper modelMapper;

	@Override
	@CacheEvict(value = { Ponto.CACHE_NAME }, allEntries=true)
	public Long criar(PontoDTO dto) {
		return pontoRepositorio.criar(Ponto.builder()
										.data(DateUtil.dataAtual())
										.descricao(dto.getDescricao())
										.horaInicialInformado(dto.getHoraInicialInformado())
										.horaFinalInformado(dto.getHoraFinalInformado())
										.horaInicialRegistro(DateUtil.horaAtual())
										.horaFinalRegistro(dto.getHoraFinalRegistro())
										.idConfiguracaoPonto(recuperarIdConfiguracaoPonto(dto.getIdFuncionarioLogado()))
										.idTipoPeriodoPonto(dto.getIdTipoPeriodoPonto())
										.tarefaOnline(dto.isTarefaOnline())
										.build());
			
	}
	
	@Override
	@CacheEvict(value = { Ponto.CACHE_NAME }, allEntries=true)
	public Long criarTarefaOnline(PontoDTO dto) {
		dto.setIdTipoPeriodoPonto(EnumTipoPeriodoPonto.TAREFA_ONLINE.id());
		dto.setTarefaOnline(true);
		return this.criar(dto);
	}

	@Override
	@CacheEvict(value = { Ponto.CACHE_NAME }, allEntries=true)
	public Long alterar(PontoDTO dto) {
		return pontoRepositorio.alterar(Ponto.builder()
				.id(dto.getId())
				.data(dto.getData())
				.descricao(dto.getDescricao())
				.horaInicialInformado(dto.getHoraInicialInformado())
				.horaFinalInformado(dto.getHoraFinalInformado())
				.horaInicialRegistro(dto.getHoraInicialRegistro())
				.horaFinalRegistro(dto.getHoraFinalInformado() != null ?  DateUtil.horaAtual() : null)
				.idConfiguracaoPonto(dto.getIdConfiguracaoPonto())
				.idTipoPeriodoPonto(dto.getIdTipoPeriodoPonto())
				.tarefaOnline(dto.isTarefaOnline())
				.build());
	}

	private Long recuperarIdConfiguracaoPonto(Long idFuncionario) {
		return configuracaoPontoRepositorio.recuperarIdPorIdFuncionario(idFuncionario);
	}

	@Override
	@Cacheable(cacheNames = Ponto.CACHE_NAME, key="#filtro.hashCode()")
	public PaginacaoDTO<PontoDTO> listar(FiltroPonto filtro) throws ExcecaoDeNegocio {
			int total = pontoRepositorio.contar(filtro);
			
			return PaginacaoDTO.<PontoDTO>builder().total(total).dados(pontoRepositorio.listar(filtro)).build();
	}

	@Override
	public List<TipoPeriodoPontoDTO> listarTiposPeriodos(FiltroPonto filtro) {
		return pontoRepositorio.listarTiposPeriodos(filtro);
	}

	@Override
	public PontoDTO recuperarHorasPrevistas(FiltroPonto filtro) {
		return pontoRepositorio.recuperarHorasPrevistas(filtro);
	}

	@Override
	public PontoDTO buscarPorId(Long id, Long idFuncionario) {
		PontoDTO dto = pontoRepositorio.buscarPorId(id);
		PontoDTO horasPrevistas = pontoRepositorio.recuperarHorasPrevistas(FiltroPonto.builder().idTipoPeriodoPonto(dto.getIdTipoPeriodoPonto()).idFuncionario(idFuncionario).build());
		dto.setHoraInicialPrevista(horasPrevistas.getHoraInicialPrevista());
		dto.setHoraFinalPrevista(horasPrevistas.getHoraFinalPrevista());
		return dto;
	}

	@Override
	public List<Integer> listarAnos(Long idFuncionario) {
		return pontoRepositorio.listarAnos(idFuncionario);
	}


	@Override
	public PaginacaoDTO<PontoDTO> listarParaFolhaDePonto(FiltroPonto filtro) {
		int total = pontoRepositorio.contarParaFolhaDePonto(filtro);
		
		return PaginacaoDTO.<PontoDTO>builder().total(total).dados(pontoRepositorio.listarParaFolhaDePonto(filtro)).build();
	}

	@Override
	public List<MesDTO> listarMeses(Long idFuncionario, Integer ano) {
		return pontoRepositorio.listarMeses(idFuncionario, ano);
	}
	
	@Override
	@CacheEvict(value = { Ponto.CACHE_NAME }, allEntries=true)
	public void liberar(PontoDTO dto) {
		if (dto.getIdsPontos() !=null &&  !dto.getIdsPontos().isEmpty()) {
			dto.getIdsPontos().stream().forEach(id -> {
				pontoRepositorio.alterarDadosLiberacao(PontoDTO.builder().dataLiberacao(DateUtil.dataAtual()).idFuncionarioLiberacao(dto.getIdFuncionarioLogado()).id(id).build());
			});
		}
	}
	
	@Override
	@CacheEvict(value = { Ponto.CACHE_NAME }, allEntries=true)
	public void cancelarLiberacao(PontoDTO dto) {
		if (dto.getIdsPontos() !=null &&  !dto.getIdsPontos().isEmpty()) {
			dto.getIdsPontos().stream().forEach(id -> {
				pontoRepositorio.alterarDadosLiberacao(PontoDTO.builder().dataLiberacao(null).idFuncionarioLiberacao(null).id(id).build());
			});
		}
	}
	
	@Override
	@CacheEvict(value = { Ponto.CACHE_NAME }, allEntries=true)
	public void homologar(PontoDTO dto) {
		if (dto.getIdsPontos() !=null &&  !dto.getIdsPontos().isEmpty()) {
			dto.getIdsPontos().stream().forEach(id -> {
				pontoRepositorio.alterarDadosHomologacao(PontoDTO.builder().dataHomologacao(DateUtil.dataAtual()).idFuncionarioHomologacao(dto.getIdFuncionarioLogado()).id(id).build());
			});
		}
	}
	
	@Override
	@CacheEvict(value = { Ponto.CACHE_NAME }, allEntries=true)
	public void cancelarHomologacao(PontoDTO dto) {
		if (dto.getIdsPontos() !=null &&  !dto.getIdsPontos().isEmpty()) {
			dto.getIdsPontos().stream().forEach(id -> {
				pontoRepositorio.alterarDadosHomologacao(PontoDTO.builder().dataHomologacao(null).idFuncionarioHomologacao(null).id(id).build());
			});
		}
	}

	@Override
	@Cacheable(cacheNames = Ponto.CACHE_NAME_AUSENTES, key="#filtro.hashCode()")
	public List<AusenteDTO> listarAusentes(FiltroPonto filtro) {
		return pontoRepositorio.listarAusentes(filtro);
	}
	
	@Override
	@CacheEvict(value = { Ponto.CACHE_NAME_AUSENTES }, allEntries=true)
	public void gerarAusentes(List<AusenteDTO> dtos) {
		if(dtos !=null && !dtos.isEmpty()) {
			dtos.stream().forEach(ausente -> {
				pontoRepositorio.criar(Ponto.builder()
						.data(ausente.getDataGeracao())
						.idConfiguracaoPonto(recuperarIdConfiguracaoPonto(ausente.getIdFuncionarioLogado()))
						.idTipoPeriodoPonto(ausente.getIdTipoPeriodoPonto())
						.idFuncionarioLiberacao(ausente.getIdFuncionarioLogado())
						.dataLiberacao(DateUtil.dataAtual())
						.tarefaOnline(false)
						.build());
			});
		}
	}

}

