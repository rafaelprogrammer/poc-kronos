package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.CampoExperiencia;
import br.com.kronos.backend.aplicacao.basecurricular.CampoExperienciaRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroCampoExperiencia;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class CampoExperienciaServicoImpl implements CampoExperienciaServico {

	@NonNull
	private CampoExperienciaRepositorio campoExperienciaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(CampoExperienciaDTO campoExperienciaDTO) throws ExcecaoDeNegocio {

			return campoExperienciaRepositorio.criar(CampoExperiencia.builder()
					                        .nome(campoExperienciaDTO.getNome()) 
					                        .idNivel(campoExperienciaDTO.getIdNivel()).build());	

    }

	@Override
	public Long alterar(CampoExperienciaDTO campoExperienciaDTO) throws ExcecaoDeNegocio {
			return campoExperienciaRepositorio.alterar(CampoExperiencia.builder()
											    .id(campoExperienciaDTO.getId())
											    .nome(campoExperienciaDTO.getNome()) 
						                        .idNivel(campoExperienciaDTO.getIdNivel()).build());		
	}

	@Override
	public CampoExperienciaDTO buscarPorId (long id) {
		return campoExperienciaRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<CampoExperienciaDTO> listar(FiltroCampoExperiencia filtroCampoExperiencia) throws ExcecaoDeNegocio {
		int total = campoExperienciaRepositorio.contar(filtroCampoExperiencia);
		return PaginacaoDTO.<CampoExperienciaDTO>builder().total(total).dados(campoExperienciaRepositorio.listar(filtroCampoExperiencia)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return campoExperienciaRepositorio.excluir(id);
	}

	@Override
	public List<CampoExperienciaDTO> listarParaCombo(FiltroCampoExperiencia filtro) {
		return campoExperienciaRepositorio.listar(filtro);
	}

}

