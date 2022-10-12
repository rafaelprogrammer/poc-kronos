package br.com.kronos.backend.aplicacao.desempenho.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.desempenho.DesempenhoAtitude;
import br.com.kronos.backend.aplicacao.desempenho.DesempenhoAtitudeRepositorio;
import br.com.kronos.backend.aplicacao.desempenho.FiltroDesempenhoAtitude;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class DesempenhoAtitudeServicoImpl implements DesempenhoAtitudeServico {

	@NonNull
	private DesempenhoAtitudeRepositorio desempenhoAtitudeRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criar(List<DesempenhoAtitudeDTO> dtos) throws ExcecaoDeNegocio {
		try {
			//TODO melhorar essa criacao utilizando in()
			dtos.stream().forEach(dto -> {
				if(desempenhoAtitudeRepositorio.contar(FiltroDesempenhoAtitude.builder().idDesempenho(dto.getIdDesempenho()).idAtitude(dto.getIdAtitude()).build()) == 0) {
					desempenhoAtitudeRepositorio.criar(DesempenhoAtitude.builder()
						.idDesempenho(dto.getIdDesempenho())
						.idAtitude(dto.getIdAtitude())
						.nota(dto.getNota())
						.idMencao(dto.getIdMencao())
						.build());
				}
			});
		} catch (RuntimeException e) {
			log.error("Erro ao atribuir atitudes ao desempenho", e);
			throw new ExcecaoDeNegocio("Erro ao atribuir atitudes ao desempenho", e);
		}
	}

	
	@Override
	public PaginacaoDTO<DesempenhoAtitudeDTO> listar(FiltroDesempenhoAtitude filtroDesempenhoAtitude) throws ExcecaoDeNegocio {
		try {
			
			int total = desempenhoAtitudeRepositorio.contar(filtroDesempenhoAtitude);
			
			List<DesempenhoAtitudeDTO> desempenhoAtitudes = modelMapper.map(desempenhoAtitudeRepositorio.listar(filtroDesempenhoAtitude),
					new TypeToken<List<DesempenhoAtitudeDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<DesempenhoAtitudeDTO>builder().total(total).dados(desempenhoAtitudes).build();
		} catch (RuntimeException e) {
			log.error("Erro ao listar atitudes do desempenho", e);
			throw new ExcecaoDeNegocio("Erro ao listar atitudes do desempenho", e);
		}
	}

	@Override
	public boolean excluir(long idDesempenho, long idAtitude) {
		try {
			return desempenhoAtitudeRepositorio.excluir(idDesempenho, idAtitude);
		} catch (RuntimeException e) {
			log.error("Erro ao excluir atitude - " + idAtitude  + " do desempenho - " + idDesempenho, e);
			throw new ExcecaoDeNegocio("Erro ao excluir atitude - " + idAtitude  + " do desempenho - " + idDesempenho, e);
		}
	}

}

