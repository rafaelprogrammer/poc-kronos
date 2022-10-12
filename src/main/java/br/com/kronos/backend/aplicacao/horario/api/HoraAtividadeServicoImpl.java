package br.com.kronos.backend.aplicacao.horario.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.horario.FiltroHoraAtividade;
import br.com.kronos.backend.aplicacao.horario.HoraAtividadeRepositorio;
import br.com.kronos.backend.aplicacao.horario.HoraAtividade;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class HoraAtividadeServicoImpl implements HoraAtividadeServico {

	@NonNull
	private HoraAtividadeRepositorio horaAtividadeRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(HoraAtividadeDTO horaAtividadeDTO) throws ExcecaoDeNegocio {

			return horaAtividadeRepositorio.criar(HoraAtividade.builder()
					                        .horaInicial(horaAtividadeDTO.getHoraInicial())                                
                                            .horaFinal(horaAtividadeDTO.getHoraFinal())
                                            .tempoCargaHoraria(horaAtividadeDTO.getTempoCargaHoraria())
                                            .tempoTrabalhoComputado(horaAtividadeDTO.getTempoTrabalhoComputado())
                                            .idTipoTurno(horaAtividadeDTO.getIdTipoTurno())
                                            .idInstituicao(horaAtividadeDTO.getIdInstituicao()).build());	

    }

	@Override
	public Long alterar(HoraAtividadeDTO horaAtividadeDTO) throws ExcecaoDeNegocio {
			return horaAtividadeRepositorio.alterar(HoraAtividade.builder()
											.id(horaAtividadeDTO.getId())
											.horaInicial(horaAtividadeDTO.getHoraInicial())                                
                                            .horaFinal(horaAtividadeDTO.getHoraFinal())
                                            .tempoCargaHoraria(horaAtividadeDTO.getTempoCargaHoraria())
                                            .tempoTrabalhoComputado(horaAtividadeDTO.getTempoTrabalhoComputado())
                                            .idTipoTurno(horaAtividadeDTO.getIdTipoTurno())
                                            .idInstituicao(horaAtividadeDTO.getIdInstituicao()).build());	

		
	}

	@Override
	public HoraAtividadeDTO buscarPorId (long id) {
		return modelMapper.map(horaAtividadeRepositorio.buscarPorId(id), HoraAtividadeDTO.class);
	}

	@Override
	public PaginacaoDTO<HoraAtividadeDTO> listar(FiltroHoraAtividade filtroHoraAtividade) throws ExcecaoDeNegocio {
			int total = horaAtividadeRepositorio.contar(filtroHoraAtividade);
			List<HoraAtividadeDTO> horasAtividades = modelMapper.map(horaAtividadeRepositorio.listar(filtroHoraAtividade),
					new TypeToken<List<HoraAtividadeDTO>>() {
					}.getType());
			
			return  PaginacaoDTO.<HoraAtividadeDTO>builder().total(total).dados(horasAtividades).build();
	}

	@Override
	public boolean excluir(Long id) {
		return horaAtividadeRepositorio.excluir(id);
	}

}
