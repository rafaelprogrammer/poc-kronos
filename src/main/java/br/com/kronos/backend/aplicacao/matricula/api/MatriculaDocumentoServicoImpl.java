package br.com.kronos.backend.aplicacao.matricula.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.matricula.FiltroMatriculaDocumento;
import br.com.kronos.backend.aplicacao.matricula.MatriculaDocumento;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaDocumentoServico;
import br.com.kronos.backend.aplicacao.matricula.MatriculaDocumentoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class MatriculaDocumentoServicoImpl implements MatriculaDocumentoServico {

	@NonNull
	private MatriculaDocumentoRepositorio matriculaDocumentoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(MatriculaDocumentoDTO matriculaDocumentoDTO) throws ExcecaoDeNegocio {

			return matriculaDocumentoRepositorio.criar(MatriculaDocumento.builder()
											.data(matriculaDocumentoDTO.getData())
					                        .idMatricula(matriculaDocumentoDTO.getIdMatricula())  
					                        .idDocumento(matriculaDocumentoDTO.getIdDocumento()).build());	

    }

	@Override
	public Long alterar(MatriculaDocumentoDTO matriculaDocumentoDTO) throws ExcecaoDeNegocio {
			return matriculaDocumentoRepositorio.alterar(MatriculaDocumento.builder()
											.id(matriculaDocumentoDTO.getId())
											.data(matriculaDocumentoDTO.getData())
					                        .idMatricula(matriculaDocumentoDTO.getIdMatricula())  
					                        .idDocumento(matriculaDocumentoDTO.getIdDocumento()).build());	

		
	}

	@Override
	public MatriculaDocumentoDTO buscarPorId (long id) {
		return modelMapper.map(matriculaDocumentoRepositorio.buscarPorId(id), MatriculaDocumentoDTO.class);
	}

	@Override
	public PaginacaoDTO<MatriculaDocumentoDTO> listar(FiltroMatriculaDocumento filtroMatriculaDocumento) throws ExcecaoDeNegocio {
			int total = matriculaDocumentoRepositorio.contar(filtroMatriculaDocumento);
			List<MatriculaDocumentoDTO> matriculaDocumentos = modelMapper.map(matriculaDocumentoRepositorio.listar(filtroMatriculaDocumento),
					new TypeToken<List<MatriculaDocumentoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<MatriculaDocumentoDTO>builder().total(total).dados(matriculaDocumentos).build();
	}

	@Override
	public boolean excluir(Long id) {
		return matriculaDocumentoRepositorio.excluir(id);
	}

}

