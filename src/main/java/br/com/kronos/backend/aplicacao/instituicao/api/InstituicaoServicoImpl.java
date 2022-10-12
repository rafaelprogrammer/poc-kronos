package br.com.kronos.backend.aplicacao.instituicao.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroInstituicao;
import br.com.kronos.backend.aplicacao.instituicao.Instituicao;
import br.com.kronos.backend.aplicacao.instituicao.InstituicaoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class InstituicaoServicoImpl implements InstituicaoServico {

	
	@NonNull
	private InstituicaoRepositorio instituicaoRepositorio;

	@NonNull
	private ModelMapper modelMapper;
	

	@Override
	public Long criar(InstituicaoDTO instituicaoDTO) throws ExcecaoDeNegocio {

			return instituicaoRepositorio.criar(Instituicao.builder()
					                        .nome(instituicaoDTO.getNome())                                
                                            .ativo(instituicaoDTO.isAtivo())
                                            .idArquivoAnexo(instituicaoDTO.getIdArquivoAnexo())  
                                            .idEmpresa(instituicaoDTO.getIdEmpresa())
                                            .mantenedora(instituicaoDTO.isMantenedora())
                                            .idComposicaoValor(instituicaoDTO.getIdComposicaoValor())
                                            .percentualJurosAtraso(instituicaoDTO.getPercentualJurosAtraso())
                                            .percentualMultaAtraso(instituicaoDTO.getPercentualMultaAtraso())
                                            .percentualBomPagador(instituicaoDTO.getPercentualBomPagador()).build());	

    }
	
	@Override
	public Long alterar(InstituicaoDTO instituicaoDTO) throws ExcecaoDeNegocio {
			return instituicaoRepositorio.alterar(Instituicao.builder()
											.id(instituicaoDTO.getId())
											.nome(instituicaoDTO.getNome())                                
                                            .ativo(instituicaoDTO.isAtivo())
                                            .idArquivoAnexo(instituicaoDTO.getIdArquivoAnexo())  
                                            .idEmpresa(instituicaoDTO.getIdEmpresa())
                                            .mantenedora(instituicaoDTO.isMantenedora())
                                            .idComposicaoValor(instituicaoDTO.getIdComposicaoValor())
                                            .percentualJurosAtraso(instituicaoDTO.getPercentualJurosAtraso())
                                            .percentualMultaAtraso(instituicaoDTO.getPercentualMultaAtraso())
                                            .percentualBomPagador(instituicaoDTO.getPercentualBomPagador()).build());	

		
	}

	@Override
	public InstituicaoDTO buscarPorId (long id) {
		return modelMapper.map(instituicaoRepositorio.buscarPorId(id), InstituicaoDTO.class);
	}

	@Override
	public PaginacaoDTO<InstituicaoDTO> listar(FiltroInstituicao filtroInstituicao) throws ExcecaoDeNegocio {
			int total = instituicaoRepositorio.contar(filtroInstituicao);
			List<InstituicaoDTO> instituicaos = modelMapper.map(instituicaoRepositorio.listar(filtroInstituicao),
					new TypeToken<List<InstituicaoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<InstituicaoDTO>builder().total(total).dados(instituicaos).build();
	}

	@Override
	public boolean excluir(Long id) {
		return instituicaoRepositorio.excluir(id);
	}


}

