package br.com.kronos.backend.aplicacao.curso.api;

import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.curso.FiltroPeriodo;
import br.com.kronos.backend.aplicacao.curso.Periodo;
import br.com.kronos.backend.aplicacao.curso.PeriodoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class PeriodoServicoImpl implements PeriodoServico {

	@NonNull
	private PeriodoRepositorio periodoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(PeriodoDTO periodoDTO) {
			return periodoRepositorio.criar(Periodo.builder()
                                            .numero(periodoDTO.getNumero())                                
                                            .nome(periodoDTO.getNome())
                                            .sigla(periodoDTO.getSigla())
                                            .idCurso(periodoDTO.getIdCurso())	
                                            .idTipoPeriodo(periodoDTO.getIdTipoPeriodo())
											.idFaixaAno(periodoDTO.getIdFaixaAno())
											.valor(periodoDTO.getValor()).build());
			
    }

	@Override
	public Long alterar(PeriodoDTO periodoDTO) {
			return periodoRepositorio.alterar(Periodo.builder()
											.id(periodoDTO.getId())
											.numero(periodoDTO.getNumero())                                
                                            .nome(periodoDTO.getNome())
                                            .sigla(periodoDTO.getSigla())
                                            .idCurso(periodoDTO.getIdCurso())	
                                            .idTipoPeriodo(periodoDTO.getIdTipoPeriodo())
											.idFaixaAno(periodoDTO.getIdFaixaAno())
											.valor(periodoDTO.getValor()).build());
		
	}

	@Override
	public PeriodoDTO buscarPorId (long id) {
		return periodoRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<PeriodoDTO> listar(FiltroPeriodo filtroPeriodo) {
			int total = periodoRepositorio.contar(filtroPeriodo);
			
			return PaginacaoDTO.<PeriodoDTO>builder().total(total).dados(periodoRepositorio.listar(filtroPeriodo)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return periodoRepositorio.excluir(id);
	}

	@Override
	public List<PeriodoDTO> listarParaCombo(FiltroPeriodo filtroPeriodo) {
		return periodoRepositorio.listarParaCombo(filtroPeriodo);
	}

	@Override
	public List<PeriodoResumoDTO> listarParaComboContrato(FiltroPeriodo filtroPeriodo) {
		return periodoRepositorio.listarParaComboContrato(filtroPeriodo);
	}

	@Override
	public List<PeriodoResumoDTO> listarParaComboPeriodoPendenteContrato(FiltroPeriodo filtroPeriodo) {
		return periodoRepositorio.listarParaComboPeriodoPendenteContrato(filtroPeriodo);
	}

	@Override
	public List<PeriodoResumoDTO> listarParaHorario(FiltroPeriodo filtroPeriodo) {
		return periodoRepositorio.listarParaHorario(filtroPeriodo);
	}

}
