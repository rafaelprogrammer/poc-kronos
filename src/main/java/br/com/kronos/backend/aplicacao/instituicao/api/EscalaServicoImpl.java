package br.com.kronos.backend.aplicacao.instituicao.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.instituicao.Escala;
import br.com.kronos.backend.aplicacao.instituicao.EscalaRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroEscala;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class EscalaServicoImpl implements EscalaServico {

	@NonNull
	private EscalaRepositorio escalaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(EscalaDTO escalaDTO) throws ExcecaoDeNegocio {

			return escalaRepositorio.criar(Escala.builder()
					                        .nome(escalaDTO.getNome())                                
                                            .dataInicialVigencia(escalaDTO.getDataInicialVigencia())
                                            .dataFinalVigencia(escalaDTO.getDataFinalVigencia())
                                            .idInstituicao(escalaDTO.getIdInstituicao())
                                            .idTipoAbrangencia(escalaDTO.getIdTipoAbrangencia())
                                            .idTipoAvaliacao(escalaDTO.getIdTipoAvaliacao()).build());	

    }
	
	@Override
	public Long alterar(EscalaDTO escalaDTO) throws ExcecaoDeNegocio {
			return escalaRepositorio.alterar(Escala.builder()
											.id(escalaDTO.getId())
											.nome(escalaDTO.getNome())                                
                                            .dataInicialVigencia(escalaDTO.getDataInicialVigencia())
                                            .dataFinalVigencia(escalaDTO.getDataFinalVigencia())
                                            .idInstituicao(escalaDTO.getIdInstituicao())
                                            .idTipoAbrangencia(escalaDTO.getIdTipoAbrangencia())
                                            .idTipoAvaliacao(escalaDTO.getIdTipoAvaliacao()).build());	
	}

	@Override
	public EscalaDTO buscarPorId (long id) {
		return modelMapper.map(escalaRepositorio.buscarPorId(id), EscalaDTO.class);
	}

	@Override
	public PaginacaoDTO<EscalaDTO> listar(FiltroEscala filtroEscala) throws ExcecaoDeNegocio {
			int total = escalaRepositorio.contar(filtroEscala);
			List<EscalaDTO> escalas = modelMapper.map(escalaRepositorio.listar(filtroEscala),
					new TypeToken<List<EscalaDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<EscalaDTO>builder().total(total).dados(escalas).build();
	}

	@Override
	public boolean excluir(Long id) {
		return escalaRepositorio.excluir(id);
	}

	@Override
	public List<MensaoLimiteDTO> listarMensaoELimite(FiltroEscala filtro) {
		return escalaRepositorio.listarMensaoELimite(filtro);
	}


}

