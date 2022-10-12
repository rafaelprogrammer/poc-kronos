package br.com.kronos.backend.aplicacao.disciplina.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaFuncionario;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaFuncionario;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaFuncionarioRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class DisciplinaFuncionarioServicoImpl implements DisciplinaFuncionarioServico {

	@NonNull
	private DisciplinaFuncionarioRepositorio disciplinaFuncionarioRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void criar(List<DisciplinaFuncionarioDTO> dtos) throws ExcecaoDeNegocio {
		try {
			//TODO melhorar essa criacao utilizando in()
			dtos.stream().forEach(dto -> {
				if(disciplinaFuncionarioRepositorio.contar(FiltroDisciplinaFuncionario.builder().idDisciplina(dto.getIdDisciplina()).idFuncionario(dto.getIdFuncionario()).build()) == 0) {
					disciplinaFuncionarioRepositorio.criar(DisciplinaFuncionario.builder()
						.idDisciplina(dto.getIdDisciplina())
						.idFuncionario(dto.getIdFuncionario())
						.build());
				}
			});
		} catch (RuntimeException e) {
			log.error("Erro ao criar funcionário na disciplina", e);
			throw new ExcecaoDeNegocio("Erro ao criar funcionário na disciplina", e);
		}
	}

	
	@Override
	public PaginacaoDTO<DisciplinaFuncionarioDTO> listar(FiltroDisciplinaFuncionario filtroDisciplinaFuncionario) throws ExcecaoDeNegocio {
		try {
			
			int total = disciplinaFuncionarioRepositorio.contar(filtroDisciplinaFuncionario);
			
			List<DisciplinaFuncionarioDTO> disciplinaFuncionarios = modelMapper.map(disciplinaFuncionarioRepositorio.listar(filtroDisciplinaFuncionario),
					new TypeToken<List<DisciplinaFuncionarioDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<DisciplinaFuncionarioDTO>builder().total(total).dados(disciplinaFuncionarios).build();
		} catch (RuntimeException e) {
			log.error("Erro ao listar funcionarios relacionandoa a disciplina", e);
			throw new ExcecaoDeNegocio("Erro ao listar funcionarios relacionandoa a disciplina", e);
		}
	}

	@Override
	public boolean excluir(long idDisciplina, long idFuncionario) {
		try {
			return disciplinaFuncionarioRepositorio.excluir(idDisciplina, idFuncionario);
		} catch (RuntimeException e) {
			log.error("Erro ao excluir funcionário - " + idFuncionario  + " da disciplina - " + idDisciplina, e);
			throw new ExcecaoDeNegocio("Erro ao excluir funcionário - " + idFuncionario  + " da disciplina - " + idDisciplina, e);
		}
	}

	}
