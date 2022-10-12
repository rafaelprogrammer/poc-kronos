package br.com.kronos.backend.aplicacao.instituicao.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroConvenio;
import br.com.kronos.backend.aplicacao.instituicao.Convenio;
import br.com.kronos.backend.aplicacao.instituicao.api.ConvenioServico;
import br.com.kronos.backend.aplicacao.instituicao.ConvenioRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ConvenioServicoImpl implements ConvenioServico {

	@NonNull
	private ConvenioRepositorio convenioRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(ConvenioDTO convenioDTO) throws ExcecaoDeNegocio {

			return convenioRepositorio.criar(Convenio.builder()
					                        .percentualDesconto(convenioDTO.getPercentualDesconto())                                
                                            .dataInicioVigencia(convenioDTO.getDataInicioVigencia())
                                            .dataFinalVigencia(convenioDTO.getDataFinalVigencia())
                                            .idTipoDesconto(convenioDTO.getIdTipoDesconto())
                                            .idEmpresa(convenioDTO.getIdEmpresa())
                                            .idInstitucicao(convenioDTO.getIdInstitucicao()).build());	

    }
	
	@Override
	public Long alterar(ConvenioDTO convenioDTO) throws ExcecaoDeNegocio {
			return convenioRepositorio.alterar(Convenio.builder()
											.id(convenioDTO.getId())
											.percentualDesconto(convenioDTO.getPercentualDesconto())                                
                                            .dataInicioVigencia(convenioDTO.getDataInicioVigencia())
                                            .dataFinalVigencia(convenioDTO.getDataFinalVigencia())
                                            .idTipoDesconto(convenioDTO.getIdTipoDesconto())
                                            .idEmpresa(convenioDTO.getIdEmpresa())
                                            .idInstitucicao(convenioDTO.getIdInstitucicao()).build());

		
	}

	@Override
	public ConvenioDTO buscarPorId (long id) {
		return modelMapper.map(convenioRepositorio.buscarPorId(id), ConvenioDTO.class);
	}

	@Override
	public PaginacaoDTO<ConvenioDTO> listar(FiltroConvenio filtroConvenio) throws ExcecaoDeNegocio {
			int total = convenioRepositorio.contar(filtroConvenio);
			List<ConvenioDTO> convenios = modelMapper.map(convenioRepositorio.listar(filtroConvenio),
					new TypeToken<List<ConvenioDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<ConvenioDTO>builder().total(total).dados(convenios).build();
	}

	@Override
	public boolean excluir(Long id) {
		return convenioRepositorio.excluir(id);
	}

	@Override
	public List<ConvenioContratoDTO> listarParaContrato(FiltroConvenio filtroConvenio) {
		return convenioRepositorio.listarParaContrato(filtroConvenio);
	}

}

