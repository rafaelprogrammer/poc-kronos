package br.com.kronos.backend.aplicacao.matricula.api;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplina;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaCreditoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.matricula.Credito;
import br.com.kronos.backend.aplicacao.matricula.CreditoRepositorio;
import br.com.kronos.backend.aplicacao.matricula.FiltroCredito;
import br.com.kronos.backend.aplicacao.matricula.TurmaRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class CreditoServicoImpl implements CreditoServico {

	@NonNull
	private CreditoRepositorio creditoRepositorio;
	
	@NonNull
	private DisciplinaRepositorio disciplinaRepositorio;
	
	@NonNull
	private TurmaRepositorio turmaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(CreditoDTO creditoDTO) throws ExcecaoDeNegocio {

			return creditoRepositorio.criar(Credito.builder()
					                        .valor(creditoDTO.getValor())                                
                                            .cargaHorariaTotal(creditoDTO.getCargaHorariaTotal())
                                            .cargaHorariaPresencial(creditoDTO.getCargaHorariaPresencial())
                                            .cargaHorariaDistancia(creditoDTO.getCargaHorariaDistancia())
                                            .totalMinutosAusencia(creditoDTO.getTotalMinutosAusencia())
                                            .percentualAusencia(creditoDTO.getPercentualAusencia())
                                            .idContrato(creditoDTO.getIdContrato())
                                            .idDisciplina(creditoDTO.getIdDisciplina())
                                            .idTurma(creditoDTO.getIdTurma())
                                            .idTipoCredito(creditoDTO.getIdTipoCredito())
                                            .idTipoPrograma(creditoDTO.getIdTipoPrograma())
                                            .notaFinalNormal(creditoDTO.getNotaFinalNormal())
                                            .notaFinalExame(creditoDTO.getNotaFinalExame())
                                            .notaConselho(creditoDTO.getNotaConselho())
                                            .notaResultadoFinal(creditoDTO.getNotaResultadoFinal())
                                            .idTipoMencaoFinal(creditoDTO.getIdTipoMencaoFinal())
                                            .pendencia(creditoDTO.isPendencia()).build());	

    }

	@Override
	public Long alterar(CreditoDTO creditoDTO) throws ExcecaoDeNegocio {
			return creditoRepositorio.alterar(Credito.builder()
											.id(creditoDTO.getId())
											.valor(creditoDTO.getValor())                                
                                            .cargaHorariaTotal(creditoDTO.getCargaHorariaTotal())
                                            .cargaHorariaPresencial(creditoDTO.getCargaHorariaPresencial())
                                            .cargaHorariaDistancia(creditoDTO.getCargaHorariaDistancia())
                                            .totalMinutosAusencia(creditoDTO.getTotalMinutosAusencia())
                                            .percentualAusencia(creditoDTO.getPercentualAusencia())
                                            .idContrato(creditoDTO.getIdContrato())
                                            .idDisciplina(creditoDTO.getIdDisciplina())
                                            .idTurma(creditoDTO.getIdTurma())
                                            .idTipoCredito(creditoDTO.getIdTipoCredito())
                                            .idTipoPrograma(creditoDTO.getIdTipoPrograma())
                                            .notaFinalNormal(creditoDTO.getNotaFinalNormal())
                                            .notaFinalExame(creditoDTO.getNotaFinalExame())
                                            .notaConselho(creditoDTO.getNotaConselho())
                                            .notaResultadoFinal(creditoDTO.getNotaResultadoFinal())
                                            .idTipoMencaoFinal(creditoDTO.getIdTipoMencaoFinal())
                                            .pendencia(creditoDTO.isPendencia()).build());	

		
	}

	@Override
	public CreditoDTO buscarPorId (long id) {
		return modelMapper.map(creditoRepositorio.buscarPorId(id), CreditoDTO.class);
	}

	@Override
	public PaginacaoDTO<CreditoDTO> listar(FiltroCredito filtroCredito) throws ExcecaoDeNegocio {
			int total = creditoRepositorio.contar(filtroCredito);
			List<CreditoDTO> Creditos = modelMapper.map(creditoRepositorio.listar(filtroCredito),
					new TypeToken<List<CreditoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<CreditoDTO>builder().total(total).dados(Creditos).build();
	}

	@Override
	public boolean excluir(Long id) {
		return creditoRepositorio.excluir(id);
	}

	@Override
	public List<CreditoContratoDTO> listarParaContrato(FiltroCredito filtroCredito) {
		return creditoRepositorio.listarParaContrato(filtroCredito);
	}

	@Override
	public List<CreditoContratoDTO> gerarCreditos(CreditoContratoDTO dto) {
		return gerarCreditosDasDisciplinas(dto, 
				disciplinaRepositorio.listarParaGeracaoDeCreditos(FiltroDisciplina.builder()
																	.idContrato(dto.getIdContrato())
																	.idPeriodoExecucao(dto.getIdPeriodoExecucao())
																	.build()));
	}
	
	@Override
	public List<CreditoContratoDTO> gerarCreditosPendentes(CreditoContratoDTO dto) {
		return gerarCreditosDasDisciplinas(dto, 
				 disciplinaRepositorio.listarParaGeracaoDeCreditosPendentes(FiltroDisciplina.builder()
																	.idPeriodoExecucao(dto.getIdPeriodoExecucao())
																	.idMatricula(dto.getIdMatricula())
																	.build()));
	}

	private List<CreditoContratoDTO> gerarCreditosDasDisciplinas(CreditoContratoDTO dto,
			List<DisciplinaCreditoDTO> disciplinas) {
		TurmaDTO turma = turmaRepositorio.buscarPorId(dto.getIdTurma());
		List<CreditoContratoDTO> creditos = new ArrayList<CreditoContratoDTO>();
		disciplinas.stream().forEach(disciplina -> {
			creditos.add(CreditoContratoDTO.builder()
											.idContrato(dto.getIdContrato())
											.idDisciplina(disciplina.getId())
											.idPeriodoExecucao(dto.getIdPeriodoExecucao())
											.idMatricula(dto.getIdMatricula())
											.idTurma(dto.getIdTurma())
											.pendencia(disciplina.getObrigatoria())
											.idTipoPrograma(dto.getIdTipoPrograma())
											.idTipoCredito(dto.getIdTipoCredito())
											.cargaHorariaDistancia(disciplina.getCargaHorariaDistancia())
											.cargaHorariaPresencial(disciplina.getCargaHorariaPresencial())
											.cargaHorariaTotal(disciplina.getCargaHorariaTotal())
											.valor(disciplina.getValor())
											.nomeDisciplina(disciplina.getNome())
											.anoTurma(String.valueOf(turma.getAno()))
											.siglaTurma(turma.getSigla())
											.siglaPeriodo(disciplina.getSiglaPeriodo())
											.build());
		});
		return creditos;
	}

}

