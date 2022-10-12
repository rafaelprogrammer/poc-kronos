package br.com.kronos.backend.aplicacao.calendario.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.calendario.FiltroCalendarioCurso;
import br.com.kronos.backend.aplicacao.calendario.CalendarioCurso;
import br.com.kronos.backend.aplicacao.calendario.CalendarioCursoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class CalendarioCursoServicoImpl implements CalendarioCursoServico {

	@NonNull
	private CalendarioCursoRepositorio calendarioCursoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criar(List<CalendarioCursoDTO> dtos) throws ExcecaoDeNegocio {
		try {
			//TODO melhorar essa criacao utilizando in()
			dtos.stream().forEach(dto -> {
				if(calendarioCursoRepositorio.contar(FiltroCalendarioCurso.builder().idCalendario(dto.getIdCalendario()).idCurso(dto.getIdCurso()).build()) == 0) {
					calendarioCursoRepositorio.criar(CalendarioCurso.builder()
						.idCalendario(dto.getIdCalendario())
						.idCurso(dto.getIdCurso())
						.build());
				}
			});
		} catch (RuntimeException e) {
			log.error("Erro ao vincular curso ao calendário", e);
			throw new ExcecaoDeNegocio("Erro ao vincular curso ao calendário", e);
		}
	}

	
	@Override
	public PaginacaoDTO<CalendarioCursoDTO> listar(FiltroCalendarioCurso filtroCalendarioCurso) throws ExcecaoDeNegocio {
		try {
			
			int total = calendarioCursoRepositorio.contar(filtroCalendarioCurso);
			
			List<CalendarioCursoDTO> calendarioCursos = modelMapper.map(calendarioCursoRepositorio.listar(filtroCalendarioCurso),
					new TypeToken<List<CalendarioCursoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<CalendarioCursoDTO>builder().total(total).dados(calendarioCursos).build();
		} catch (RuntimeException e) {
			log.error("Erro ao listar cursos do calendário", e);
			throw new ExcecaoDeNegocio("Erro ao listar cursos do calendário", e);
		}
	}

	@Override
	public boolean excluir(long idCalendario, long idCurso) {
		try {
			return calendarioCursoRepositorio.excluir(idCalendario, idCurso);
		} catch (RuntimeException e) {
			log.error("Erro ao desvincular curso - " + idCurso  + " do calendário - " + idCalendario, e);
			throw new ExcecaoDeNegocio("Erro ao desvincular curso - " + idCurso  + " do calendário - " + idCalendario, e);
		}
	}

}
