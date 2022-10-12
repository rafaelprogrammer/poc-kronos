package br.com.kronos.backend.aplicacao.horario.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.horario.FiltroHorarioHoraAtividade;
import br.com.kronos.backend.aplicacao.horario.HorarioHoraAtividade;
import br.com.kronos.backend.aplicacao.horario.HorarioHoraAtividadeRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class HorarioHoraAtividadeServicoImpl implements HorarioHoraAtividadeServico {

	@NonNull
	private HorarioHoraAtividadeRepositorio horarioHoraAtividadeRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criar(List<HorarioHoraAtividadeDTO> dtos) throws ExcecaoDeNegocio {
		try {
			//TODO melhorar essa criacao utilizando in()
			dtos.stream().forEach(dto -> {
				if(horarioHoraAtividadeRepositorio.contar(FiltroHorarioHoraAtividade.builder().idHorario(dto.getIdHorario()).idHoraAtividade(dto.getIdHoraAtividade()).build()) == 0) {
					horarioHoraAtividadeRepositorio.criar(HorarioHoraAtividade.builder()
						.idHorario(dto.getIdHorario())
						.idHoraAtividade(dto.getIdHoraAtividade())
						.build());
				}
			});
		} catch (RuntimeException e) {
			log.error("Erro ao vincular curso ao calendário", e);
			throw new ExcecaoDeNegocio("Erro ao vincular hora atividade ao horario", e);
		}
	}

	
	@Override
	public PaginacaoDTO<HorarioHoraAtividadeDTO> listar(FiltroHorarioHoraAtividade filtroHorarioHoraAtividade) throws ExcecaoDeNegocio {
		try {
			
			int total = horarioHoraAtividadeRepositorio.contar(filtroHorarioHoraAtividade);
			
			List<HorarioHoraAtividadeDTO> horarioHoraAtividades = modelMapper.map(horarioHoraAtividadeRepositorio.listar(filtroHorarioHoraAtividade),
					new TypeToken<List<HorarioHoraAtividadeDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<HorarioHoraAtividadeDTO>builder().total(total).dados(horarioHoraAtividades).build();
		} catch (RuntimeException e) {
			log.error("Erro ao listar horas atividades do horário", e);
			throw new ExcecaoDeNegocio("Erro ao listar horas atividades do horário", e);
		}
	}

	@Override
	public boolean excluir(long idHorario, long idHoraAtividade) {
		try {
			return horarioHoraAtividadeRepositorio.excluir(idHorario, idHoraAtividade);
		} catch (RuntimeException e) {
			log.error("Erro ao desvincular hora atividade - " + idHoraAtividade  + " do horário - " + idHorario, e);
			throw new ExcecaoDeNegocio("Erro ao desvincular hora atividade - " + idHoraAtividade  + " do horário - " + idHorario, e);
		}
	}

}
