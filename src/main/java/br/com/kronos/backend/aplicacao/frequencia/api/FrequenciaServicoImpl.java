package br.com.kronos.backend.aplicacao.frequencia.api;

import java.util.List;
import java.util.stream.IntStream;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.kronos.backend.aplicacao.frequencia.EnumTipoFrequencia;
import br.com.kronos.backend.aplicacao.frequencia.FiltroFrequencia;
import br.com.kronos.backend.aplicacao.frequencia.Frequencia;
import br.com.kronos.backend.aplicacao.frequencia.FrequenciaRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class FrequenciaServicoImpl implements FrequenciaServico {

	@NonNull
	private FrequenciaRepositorio frequenciaRepositorio;

	@NonNull
	private ModelMapper modelMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criarTipoTPTodos(FiltroFrequencia filtroFrequencia) {
		List<FrequenciaDTO> frequencias = frequenciaRepositorio.listar(filtroFrequencia);
		if (frequencias !=null && !frequencias.isEmpty()) {
			frequencias.stream().forEach(f -> {
				criarTipoTP(f);
			});
		}
	}
	
	@Override
	public void criarTipoTP(FrequenciaDTO frequenciaDTO)  {
		frequenciaDTO.setNumeroPresenca(frequenciaDTO.getNumeroAtividade().intValue());
		this.registrar(EnumTipoFrequencia.P, frequenciaDTO);
    }
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criarTipoTFTodos(FiltroFrequencia filtroFrequencia) {
		List<FrequenciaDTO> frequencias = frequenciaRepositorio.listar(filtroFrequencia);
		if (frequencias !=null && !frequencias.isEmpty()) {
			frequencias.stream().forEach(f -> {
				criarTipoTF(f);
			});
		}
	}
	
	@Override
	public void criarTipoTF(FrequenciaDTO frequenciaDTO)  {
		frequenciaDTO.setNumeroFalta(frequenciaDTO.getNumeroAtividade().intValue());
		if(frequenciaDTO.isTipoTFIndividual() && frequenciaDTO.getTipoFrequenciaAtestado() != null  && frequenciaDTO.getTipoFrequenciaAtestado().equals(EnumTipoFrequencia.AD.name())) {
			frequenciaDTO.setTipoFrequenciaAtestado(null);
		}
		this.registrar(EnumTipoFrequencia.F, frequenciaDTO);
    }
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criarTipoADTodos(FiltroFrequencia filtroFrequencia) {
		List<FrequenciaDTO> frequencias = frequenciaRepositorio.listar(filtroFrequencia);
		if (frequencias !=null && !frequencias.isEmpty()) {
			frequencias.stream().forEach(f -> {
				criarTipoAD(f);
			});
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criarTiposFrequenciasAtestados(FiltroFrequencia filtroFrequencia) {
		List<FrequenciaDTO> frequencias = frequenciaRepositorio.listar(filtroFrequencia);
		if (frequencias !=null && !frequencias.isEmpty()) {
			frequencias.stream().forEach(f -> {
				if (!StringUtils.isEmpty(f.getTipoFrequenciaAtestado())
						&& (f.getTipoFrequenciaAtestado().equals(EnumTipoFrequencia.C.name())
						|| f.getTipoFrequenciaAtestado().equals(EnumTipoFrequencia.T.name())
						|| f.getTipoFrequenciaAtestado().equals(EnumTipoFrequencia.FJ.name()))) {
					this.registrar(EnumTipoFrequencia.tipo(f.getTipoFrequenciaAtestado()), f);
				}
			});
		}
	}
	
	@Override
	public void criarTipoAD(FrequenciaDTO frequenciaDTO)  {
		frequenciaDTO.setNumeroFalta(frequenciaDTO.getNumeroAtividade().intValue());
		this.registrar(EnumTipoFrequencia.AD, frequenciaDTO);
    }
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void limparTodos(FiltroFrequencia filtroFrequencia) {
		List<FrequenciaDTO> frequencias = frequenciaRepositorio.listar(filtroFrequencia);
		if (frequencias !=null && !frequencias.isEmpty()) {
			frequencias.stream().forEach(f -> {
				limpar(f);
			});
		}
	}
	
	@Override
	public void limpar(FrequenciaDTO frequenciaDTO)  {
		frequenciaDTO.setNumeroPresenca(0);
		frequenciaDTO.setNumeroFalta(0);
		frequenciaDTO.setNumeroFaltaJustificada(0);
		this.registrar(EnumTipoFrequencia.I, frequenciaDTO);
    }
	
	private void registrar(EnumTipoFrequencia tipo, FrequenciaDTO frequenciaDTO)  {
		String[] frequencia = new String[frequenciaDTO.getNumeroAtividade().intValue()];
		IntStream.range(0, frequenciaDTO.getNumeroAtividade().intValue()).forEach(index -> {
			frequencia[index] = StringUtils.isEmpty(frequenciaDTO.getTipoFrequenciaAtestado()) && tipo !=null ? tipo.name() : frequenciaDTO.getTipoFrequenciaAtestado();
		});
		
		Long idFrequencia = verificarSeExisteFrequencia(frequenciaDTO);
		frequenciaDTO.setFrequencia(frequencia);
		atualizarDados(frequenciaDTO);
		if (idFrequencia !=null && idFrequencia > 0) {
			log.info("### ALTERANDO FREQUENCIA - ID_ATIVIDADE: " + frequenciaDTO.getIdAtividade() + " - ID_CREDITO: " + frequenciaDTO.getIdCredito());
			alterar(frequenciaDTO);
		} else {
			log.info("### CRIANDO FREQUENCIA - ID_ATIVIDADE: " + frequenciaDTO.getIdAtividade() + " - ID_CREDITO: " + frequenciaDTO.getIdCredito());
			criar(frequenciaDTO);
		}
    }

	private void criar(FrequenciaDTO frequenciaDTO) {
		frequenciaRepositorio.criar(Frequencia.builder()
				.dataReposicao(frequenciaDTO.getDataReposicao())
				.idAtividade(frequenciaDTO.getIdAtividade())
				.idCredito(frequenciaDTO.getIdCredito())
				.frequencia(frequenciaDTO.getFrequencia())
				.numeroFalta(frequenciaDTO.getNumeroFalta())
				.numeroFaltaJustificada(frequenciaDTO.getNumeroFaltaJustificada())
				.numeroPresenca(frequenciaDTO.getNumeroPresenca())
				.build());
	}

	private void alterar(FrequenciaDTO frequenciaDTO) {
		frequenciaRepositorio.alterar(Frequencia.builder()
				.id(frequenciaDTO.getId())
				.dataReposicao(frequenciaDTO.getDataReposicao())
				.idAtividade(frequenciaDTO.getIdAtividade())
				.idCredito(frequenciaDTO.getIdCredito())
				.frequencia(frequenciaDTO.getFrequencia())
				.numeroFalta(frequenciaDTO.getNumeroFalta())
				.numeroFaltaJustificada(frequenciaDTO.getNumeroFaltaJustificada())
				.numeroPresenca(frequenciaDTO.getNumeroPresenca())
				.build());
	}

	private Long verificarSeExisteFrequencia(FrequenciaDTO frequenciaDTO) {
		Long idFrequencia;
		if (frequenciaDTO.getId() == 0) {
			idFrequencia = frequenciaRepositorio.buscarFrequenciaPorIdAtividadeEIdCredito(frequenciaDTO.getIdAtividade(), frequenciaDTO.getIdCredito());
		} else {
			idFrequencia = frequenciaDTO.getId();
		}
		return idFrequencia;
	}

	@Override
	public void registrarReposicao(FrequenciaDTO frequenciaDTO) {
		Long idFrequencia = frequenciaRepositorio.buscarFrequenciaPorIdAtividadeEIdCredito(frequenciaDTO.getIdAtividade(), frequenciaDTO.getIdCredito());
		if (idFrequencia > 0) {
			log.info("### ATUALIZANDO DA REPOSICAO FREQUENCIA - ID_ATIVIDADE: " + frequenciaDTO.getIdAtividade() + " - ID_CREDITO: " + frequenciaDTO.getIdCredito());
			Frequencia frequencia = frequenciaRepositorio.buscarPorId(idFrequencia);
			frequencia.atualizarDataReposicao(frequenciaDTO.getDataReposicao());
			frequenciaRepositorio.alterar(frequencia);
		} else {
			log.info("### CRIANDO FREQUENCIA COM DATA REPOSICAO - ID_ATIVIDADE: " + frequenciaDTO.getIdAtividade() + " - ID_CREDITO: " + frequenciaDTO.getIdCredito());
			frequenciaRepositorio.criar(Frequencia.builder().dataReposicao(frequenciaDTO.getDataReposicao())
					.idAtividade(frequenciaDTO.getIdAtividade()).idCredito(frequenciaDTO.getIdCredito())
					.frequencia(frequenciaDTO.getFrequencia()).build());
		}
			
	}
	
	@Override
	public void anularReposicao(FrequenciaDTO frequenciaDTO) {
		Long idFrequencia = frequenciaRepositorio.buscarFrequenciaPorIdAtividadeEIdCredito(frequenciaDTO.getIdAtividade(), frequenciaDTO.getIdCredito());
		if (idFrequencia > 0) {
			log.info("### ANULANDO REPOSICAO FREQUENCIA - ID_ATIVIDADE: " + frequenciaDTO.getIdAtividade() + " - ID_CREDITO: " + frequenciaDTO.getIdCredito());
			Frequencia frequencia = frequenciaRepositorio.buscarPorId(frequenciaDTO.getId());
			frequencia.atualizarDataReposicao(null);
			frequenciaRepositorio.alterar(frequencia);	
		}
	}

	@Override
	public FrequenciaDTO buscarPorId (long id) {
		return modelMapper.map(frequenciaRepositorio.buscarPorId(id), FrequenciaDTO.class);
	}

	@Override
	public List<FrequenciaDTO> listar(FiltroFrequencia filtroFrequencia)  {
		return  frequenciaRepositorio.listar(filtroFrequencia);
	}

	@Override
	public boolean excluir(Long id) {
		return frequenciaRepositorio.excluir(id);
	}

	@Override
	public void alterarArrayFrequencia(FrequenciaDTO frequenciaDTO) {
		atualizarDados(frequenciaDTO);
		
		if (frequenciaDTO.getId() > 0) {
			Frequencia frequencia = frequenciaRepositorio.buscarPorId(frequenciaDTO.getId());
			frequencia.atualizarArrayFrequencia(frequenciaDTO.getFrequencia());
			frequencia.atualizarNumeroFalta(frequenciaDTO.getNumeroFalta());
			frequencia.atualizarNumeroFaltaJustificada(frequenciaDTO.getNumeroFaltaJustificada());
			frequencia.atualizarNumeroPresenca(frequenciaDTO.getNumeroPresenca());
			frequenciaRepositorio.alterar(frequencia);
		} else {
			criar(frequenciaDTO);
		}
	}

	private void atualizarDados(FrequenciaDTO frequenciaDTO) {
		frequenciaDTO.setNumeroFalta(0);
		frequenciaDTO.setNumeroFaltaJustificada(0);
		frequenciaDTO.setNumeroPresenca(0);
		String[] valoresFrequencia = frequenciaDTO.getFrequencia();
		IntStream.range(0, frequenciaDTO.getNumeroAtividade().intValue()).forEach(index -> {
			if (valoresFrequencia[index].equals(EnumTipoFrequencia.P.name())) {
				frequenciaDTO.setNumeroPresenca(frequenciaDTO.getNumeroPresenca() + 1);
			}
			if (valoresFrequencia[index].equals(EnumTipoFrequencia.AD.name())) {
				frequenciaDTO.setNumeroPresenca(frequenciaDTO.getNumeroPresenca() + 1);
			}
			if (valoresFrequencia[index].equals(EnumTipoFrequencia.F.name())) {
				frequenciaDTO.setNumeroFalta(frequenciaDTO.getNumeroFalta() + 1);
			}
			if (valoresFrequencia[index].equals(EnumTipoFrequencia.FJ.name())) {
				frequenciaDTO.setNumeroFaltaJustificada(frequenciaDTO.getNumeroFaltaJustificada() + 1);
			}
		});
	}

}
