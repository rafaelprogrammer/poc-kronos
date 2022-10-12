package br.com.kronos.backend.aplicacao.curso.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.curso.CursoEscala;
import br.com.kronos.backend.aplicacao.curso.CursoEscalaRepositorio;
import br.com.kronos.backend.aplicacao.curso.FiltroCursoEscala;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class CursoEscalaServicoImpl implements CursoEscalaServico {

	@NonNull
	private CursoEscalaRepositorio cursoEscalaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void criar(List<CursoEscalaDTO> dtos) throws ExcecaoDeNegocio {
		try {
			//TODO melhorar essa criacao utilizando in()
			dtos.stream().forEach(dto -> {
				if(cursoEscalaRepositorio.contar(FiltroCursoEscala.builder().idCurso(dto.getIdCurso()).idEscala(dto.getIdEscala()).build()) == 0) {
					cursoEscalaRepositorio.criar(CursoEscala.builder()
						.idCurso(dto.getIdCurso())
						.idEscala(dto.getIdEscala())
						.build());
				}
			});
		} catch (RuntimeException e) {
			log.error("Erro ao criar CursoPortarias", e);
			throw new ExcecaoDeNegocio("Erro ao criar CursoPortarias", e);
		}
	}

	
	@Override
	public PaginacaoDTO<CursoEscalaDTO> listar(FiltroCursoEscala filtroCursoEscala) throws ExcecaoDeNegocio {
		try {
			
			int total = cursoEscalaRepositorio.contar(filtroCursoEscala);
			
			List<CursoEscalaDTO> cursoEscalas = modelMapper.map(cursoEscalaRepositorio.listar(filtroCursoEscala),
					new TypeToken<List<CursoEscalaDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<CursoEscalaDTO>builder().total(total).dados(cursoEscalas).build();
		} catch (RuntimeException e) {
			log.error("Erro ao listar escalas do curso", e);
			throw new ExcecaoDeNegocio("Erro ao listar escalas do curso", e);
		}
	}

	@Override
	public boolean excluir(long idCurso, long idEscala) {
		try {
			return cursoEscalaRepositorio.excluir(idCurso, idEscala);
		} catch (RuntimeException e) {
			log.error("Erro ao excluir escala - " + idEscala  + " do curso - " + idCurso, e);
			throw new ExcecaoDeNegocio("Erro ao excluir escala - " + idEscala  + " do curso - " + idCurso, e);
		}
	}

}
