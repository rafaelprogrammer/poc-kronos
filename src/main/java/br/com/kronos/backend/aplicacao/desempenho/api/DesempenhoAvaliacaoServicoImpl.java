package br.com.kronos.backend.aplicacao.desempenho.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.desempenho.DesempenhoAvaliacao;
import br.com.kronos.backend.aplicacao.desempenho.DesempenhoAvaliacaoRepositorio;
import br.com.kronos.backend.aplicacao.desempenho.FiltroDesempenhoAvaliacao;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class DesempenhoAvaliacaoServicoImpl implements DesempenhoAvaliacaoServico {

	@NonNull
	private DesempenhoAvaliacaoRepositorio desempenhoAvaliacaoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criar(List<DesempenhoAvaliacaoDTO> dtos) throws ExcecaoDeNegocio {
		try {
			//TODO melhorar essa criacao utilizando in()
			dtos.stream().forEach(dto -> {
				if(desempenhoAvaliacaoRepositorio.contar(FiltroDesempenhoAvaliacao.builder().idDesempenho(dto.getIdDesempenho()).idAvaliacao(dto.getIdAvaliacao()).build()) == 0) {
					desempenhoAvaliacaoRepositorio.criar(DesempenhoAvaliacao.builder()
						.idDesempenho(dto.getIdDesempenho())
						.idAvaliacao(dto.getIdAvaliacao())
						.build());
				}
			});
		} catch (RuntimeException e) {
			log.error("Erro ao atribuir avaliações ao desempenho", e);
			throw new ExcecaoDeNegocio("Erro ao atribuir avaliações ao desempenho", e);
		}
	}

	
	@Override
	public PaginacaoDTO<DesempenhoAvaliacaoDTO> listar(FiltroDesempenhoAvaliacao filtroDesempenhoAvaliacao) throws ExcecaoDeNegocio {
		try {
			
			int total = desempenhoAvaliacaoRepositorio.contar(filtroDesempenhoAvaliacao);
			
			List<DesempenhoAvaliacaoDTO> desempenhoAvaliacaos = modelMapper.map(desempenhoAvaliacaoRepositorio.listar(filtroDesempenhoAvaliacao),
					new TypeToken<List<DesempenhoAvaliacaoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<DesempenhoAvaliacaoDTO>builder().total(total).dados(desempenhoAvaliacaos).build();
		} catch (RuntimeException e) {
			log.error("Erro ao listar avaliações do desempenho", e);
			throw new ExcecaoDeNegocio("Erro ao listar avaliações do desempenho", e);
		}
	}

	@Override
	public boolean excluir(long idDesempenho, long idAvaliacao) {
		try {
			return desempenhoAvaliacaoRepositorio.excluir(idDesempenho, idAvaliacao);
		} catch (RuntimeException e) {
			log.error("Erro ao excluir avaliação - " + idAvaliacao  + " do desempenho - " + idDesempenho, e);
			throw new ExcecaoDeNegocio("Erro ao excluir avaliação - " + idAvaliacao  + " do desempenho - " + idDesempenho, e);
		}
	}

}

