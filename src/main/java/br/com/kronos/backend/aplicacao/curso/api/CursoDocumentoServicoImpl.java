package br.com.kronos.backend.aplicacao.curso.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.curso.CursoDocumento;
import br.com.kronos.backend.aplicacao.curso.CursoDocumentoRepositorio;
import br.com.kronos.backend.aplicacao.curso.FiltroCursoDocumento;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class CursoDocumentoServicoImpl implements CursoDocumentoServico {

	@NonNull
	private CursoDocumentoRepositorio cursoDocumentoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(CursoDocumentoDTO dto) {

		return cursoDocumentoRepositorio.criar(CursoDocumento.builder()
				                        .idCurso(dto.getIdCurso())
				                        .autenticacao(dto.isAutenticacao())
				                        .original(dto.isOriginal())
				                        .idTipoDocumento(dto.getIdTipoDocumento())
				                        .numeroCopia(dto.getNumeroCopia())
				                        .build());	

    }
	

	@Override
	public Long alterar(CursoDocumentoDTO dto) throws ExcecaoDeNegocio {
		return cursoDocumentoRepositorio.alterar(CursoDocumento.builder()
				.id(dto.getId())
                .idCurso(dto.getIdCurso())
                .autenticacao(dto.isAutenticacao())
                .original(dto.isOriginal())
                .idTipoDocumento(dto.getIdTipoDocumento())
                .numeroCopia(dto.getNumeroCopia())
                .build());	

		
	}

	@Override
	public CursoDocumentoDTO buscarPorId (long id) {
		return modelMapper.map(cursoDocumentoRepositorio.buscarPorId(id), CursoDocumentoDTO.class);
	}

	@Override
	public PaginacaoDTO<CursoDocumentoDTO> listar(FiltroCursoDocumento filtro) throws ExcecaoDeNegocio {
			int total = cursoDocumentoRepositorio.contar(filtro);
			
			List<CursoDocumentoDTO> cursosDocumentos = modelMapper.map(cursoDocumentoRepositorio.listar(filtro),
					new TypeToken<List<CursoDocumentoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<CursoDocumentoDTO>builder().total(total).dados(cursosDocumentos).build();
	}

	@Override
	public boolean excluir(long id) {
		return cursoDocumentoRepositorio.excluir(id);
	}

}
