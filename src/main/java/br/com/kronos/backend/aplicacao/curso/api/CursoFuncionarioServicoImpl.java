package br.com.kronos.backend.aplicacao.curso.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.curso.CursoFuncionario;
import br.com.kronos.backend.aplicacao.curso.CursoFuncionarioRepositorio;
import br.com.kronos.backend.aplicacao.curso.FiltroCursoFuncionario;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class CursoFuncionarioServicoImpl implements CursoFuncionarioServico {

	@NonNull
	private CursoFuncionarioRepositorio cursoFuncionarioRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(CursoFuncionarioDTO dto) throws ExcecaoDeNegocio {

			return cursoFuncionarioRepositorio.criar(CursoFuncionario.builder()
											.idCurso(dto.getIdCurso())
											.idFuncionario(dto.getIdFuncionario())
											.idTipoFuncao(dto.getIdTipoFuncao())
											.dataInicio(dto.getDataInicio())
											.dataFinal(dto.getDataFinal())
					                        .build());	

    }
	

	@Override
	public Long alterar(CursoFuncionarioDTO dto) throws ExcecaoDeNegocio {
			return cursoFuncionarioRepositorio.alterar(CursoFuncionario.builder()
																.id(dto.getId())
																.idCurso(dto.getIdCurso())
																.idFuncionario(dto.getIdFuncionario())
																.idTipoFuncao(dto.getIdTipoFuncao())
																.dataInicio(dto.getDataInicio())
																.dataFinal(dto.getDataFinal())
											                    .build());

		
	}

	@Override
	public CursoFuncionarioDTO buscarPorId (long id) {
		return modelMapper.map(cursoFuncionarioRepositorio.buscarPorId(id), CursoFuncionarioDTO.class);
	}

	@Override
	public PaginacaoDTO<CursoFuncionarioDTO> listar(FiltroCursoFuncionario filtro) throws ExcecaoDeNegocio {
			int total = cursoFuncionarioRepositorio.contar(filtro);
			List<CursoFuncionarioDTO> cursosFuncionarios = modelMapper.map(cursoFuncionarioRepositorio.listar(filtro),
					new TypeToken<List<CursoFuncionarioDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<CursoFuncionarioDTO>builder().total(total).dados(cursosFuncionarios).build();
	}

	@Override
	public boolean excluir(long id) {
		return cursoFuncionarioRepositorio.excluir(id);
	}

}
