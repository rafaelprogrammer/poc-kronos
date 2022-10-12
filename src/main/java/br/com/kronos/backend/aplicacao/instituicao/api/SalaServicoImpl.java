package br.com.kronos.backend.aplicacao.instituicao.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroSala;
import br.com.kronos.backend.aplicacao.instituicao.Sala;
import br.com.kronos.backend.aplicacao.instituicao.api.SalaServico;
import br.com.kronos.backend.aplicacao.instituicao.SalaRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class SalaServicoImpl implements SalaServico {

	@NonNull
	private SalaRepositorio salaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(SalaDTO salaDTO) throws ExcecaoDeNegocio {

			return salaRepositorio.criar(Sala.builder()
					                        .bloco(salaDTO.getBloco()) 
					                        .ala(salaDTO.getAla()) 
					                        .andar(salaDTO.getAndar()) 
					                        .numero(salaDTO.getNumero()) 
					                        .lotacao(salaDTO.getLotacao()) 
					                        .poolReserva(salaDTO.isPollReserva()) 
					                        .ativo(salaDTO.isAtivo()) 
					                        .idInstitucicao(salaDTO.getIdInstitucicao()) 
                                            .idTipoSala(salaDTO.getIdTipoSala()).build());	

    }
	
	@Override
	public Long alterar(SalaDTO salaDTO) throws ExcecaoDeNegocio {
			return salaRepositorio.alterar(Sala.builder()
											    .id(salaDTO.getId())
											    .bloco(salaDTO.getBloco()) 
						                        .ala(salaDTO.getAla()) 
						                        .andar(salaDTO.getAndar()) 
						                        .numero(salaDTO.getNumero()) 
						                        .lotacao(salaDTO.getLotacao()) 
						                        .poolReserva(salaDTO.isPollReserva()) 
						                        .ativo(salaDTO.isAtivo()) 
						                        .idInstitucicao(salaDTO.getIdInstitucicao()) 
	                                            .idTipoSala(salaDTO.getIdTipoSala()).build());	
	}

	@Override
	public SalaDTO buscarPorId (long id) {
		return modelMapper.map(salaRepositorio.buscarPorId(id), SalaDTO.class);
	}

	@Override
	public PaginacaoDTO<SalaDTO> listar(FiltroSala filtroSala) throws ExcecaoDeNegocio {
			int total = salaRepositorio.contar(filtroSala);
			List<SalaDTO> salas = modelMapper.map(salaRepositorio.listar(filtroSala),
					new TypeToken<List<SalaDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<SalaDTO>builder().total(total).dados(salas).build();
	}

	@Override
	public boolean excluir(Long id) {
		return salaRepositorio.excluir(id);
	}

}

