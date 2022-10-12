package br.com.kronos.backend.aplicacao.parecer.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.parecer.FiltroParecer;
import br.com.kronos.backend.aplicacao.parecer.Parecer;
import br.com.kronos.backend.aplicacao.parecer.ParecerRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ParecerServicoImpl implements ParecerServico {

	@NonNull
	private ParecerRepositorio parecerRepositorio;
	
	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(ParecerDTO parecerDTO) throws ExcecaoDeNegocio {

			return parecerRepositorio.criar(Parecer.builder()
					                        .idSubFaseExecucao(parecerDTO.getIdSubFaseExecucao())                               
                                            .idMatricula(parecerDTO.getIdMatricula())
                                            .data(parecerDTO.getData())
                                            .idFuncionario(parecerDTO.getIdFuncionario())
                                            .texto(parecerDTO.getTexto())
                                            .rascunho(parecerDTO.isRascunho()).build());
    }

	@Override
	public Long alterar(ParecerDTO parecerDTO) throws ExcecaoDeNegocio {
			return parecerRepositorio.alterar(Parecer.builder()
											.id(parecerDTO.getId())
											.idSubFaseExecucao(parecerDTO.getIdSubFaseExecucao())                               
                                            .idMatricula(parecerDTO.getIdMatricula())
                                            .data(parecerDTO.getData())
                                            .idFuncionario(parecerDTO.getIdFuncionario())
                                            .texto(parecerDTO.getTexto())
                                            .rascunho(parecerDTO.isRascunho()).build());
	}

	@Override
	public ParecerDTO buscarPorId (long id) {
		return modelMapper.map(parecerRepositorio.buscarPorId(id), ParecerDTO.class);
	}

	@Override
	public PaginacaoDTO<ParecerDTO> listar(FiltroParecer filtroParecer) throws ExcecaoDeNegocio {
			int total = parecerRepositorio.contar(filtroParecer);
			List<ParecerDTO> pareceres = modelMapper.map(parecerRepositorio.listar(filtroParecer),
					new TypeToken<List<ParecerDTO>>() {
					}.getType());
			
			return  PaginacaoDTO.<ParecerDTO>builder().total(total).dados(pareceres).build();
	}

	@Override
	public boolean excluir(Long id) {
		return parecerRepositorio.excluir(id);
	}

}

