package br.com.kronos.backend.aplicacao.pessoa.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroTalento;
import br.com.kronos.backend.aplicacao.pessoa.Talento;
import br.com.kronos.backend.aplicacao.pessoa.TalentoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class TalentoServicoImpl implements TalentoServico {

	@NonNull
	private TalentoRepositorio talentoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void criar(List<TalentoDTO> dtos) throws ExcecaoDeNegocio {
		try {
			if(dtos != null && !dtos.isEmpty()) {
				talentoRepositorio.excluirTodosDaPessoa(dtos.get(0).getIdPessoa());
				dtos.stream().forEach(dto -> {
					talentoRepositorio.criar(Talento.builder()
						.idPessoa(dto.getIdPessoa())
						.idTipoTalento(dto.getIdTipoTalento())
						.build());
				});
			}
		} catch (RuntimeException e) {
			log.error("Erro ao criar talentos", e);
			throw new ExcecaoDeNegocio("Erro ao criar talentos", e);
		}
	}

	@Override
	public PaginacaoDTO<TalentoDTO> listar(FiltroTalento filtro) throws ExcecaoDeNegocio {
		try {
			
			int total = talentoRepositorio.contar(filtro);
			
			List<TalentoDTO> talentos = modelMapper.map(talentoRepositorio.listar(filtro),
					new TypeToken<List<TalentoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<TalentoDTO>builder().total(total).dados(talentos).build();
		} catch (RuntimeException e) {
			log.error("Erro ao listar filiacao", e);
			throw new ExcecaoDeNegocio("Erro ao listar talentos", e);
		}
	}

	@Override
	public boolean excluir(long id, int idTipoTalento) {
		try {
			return talentoRepositorio.excluir(id, idTipoTalento);
		} catch (RuntimeException e) {
			log.error("Erro ao excluir a talento de idPessoa - " + id + " idTipoTalento - " + idTipoTalento, e);
			throw new ExcecaoDeNegocio("Erro ao excluir a talento de idPessoa - " + id + " idTipoTalento - " + idTipoTalento, e);
		}
	}

	@Override
	public List<TalentoDTO> buscarTalentosDaPessoa(long idPessoa) {
		return  modelMapper.map(talentoRepositorio.buscarTalentosDaPessoa(idPessoa),
				new TypeToken<List<TalentoDTO>>() {
				}.getType());
	}

}
