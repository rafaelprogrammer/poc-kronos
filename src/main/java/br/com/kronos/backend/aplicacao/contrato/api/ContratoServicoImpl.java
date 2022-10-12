package br.com.kronos.backend.aplicacao.contrato.api;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.contrato.Contrato;
import br.com.kronos.backend.aplicacao.contrato.ContratoRepositorio;
import br.com.kronos.backend.aplicacao.contrato.EnumTipoSituacaoContrato;
import br.com.kronos.backend.aplicacao.contrato.FiltroContrato;
import br.com.kronos.backend.aplicacao.contrato.FiltroParcela;
import br.com.kronos.backend.aplicacao.curso.PeriodoRepositorio;
import br.com.kronos.backend.aplicacao.curso.api.PeriodoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.instituicao.ConvenioRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.EnumTipoComposicaoValor;
import br.com.kronos.backend.aplicacao.instituicao.FiltroConvenio;
import br.com.kronos.backend.aplicacao.instituicao.Instituicao;
import br.com.kronos.backend.aplicacao.instituicao.InstituicaoRepositorio;
import br.com.kronos.backend.aplicacao.matricula.Credito;
import br.com.kronos.backend.aplicacao.matricula.CreditoRepositorio;
import br.com.kronos.backend.aplicacao.matricula.FiltroCredito;
import br.com.kronos.backend.aplicacao.matricula.api.CreditoContratoDTO;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ContratoServicoImpl implements ContratoServico {

	@NonNull
	private ContratoRepositorio contratoRepositorio;
	
	@NonNull
	private CreditoRepositorio creditoRepositorio;
	
	@NonNull
	private InstituicaoRepositorio instituicaoRepositorio;
	
	@NonNull
	private PeriodoRepositorio periodoRepositorio;
	
	@NonNull
	private ConvenioRepositorio convenioRepositorio;
	
	@NonNull
	private ContratoConvenioServico contratoConvenioServico;
	
	@NonNull
	private ParcelaServico parcelaServico;

	@NonNull
	private ModelMapper modelMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long criar(ContratoDTO contratoDTO) throws ExcecaoDeNegocio {
		    Instituicao instituicao = instituicaoRepositorio.buscarPorId(contratoDTO.getIdInstituicao());
			Long idContrato = contratoRepositorio.criar(Contrato.builder()				
											.ano(contratoDTO.getAno())
											.data(contratoDTO.getData())
                                            .idInstituicao(contratoDTO.getIdInstituicao())
                                            .idPeriodoExecucao(contratoDTO.getIdPeriodoExecucao())
                                            .idMatricula(contratoDTO.getIdMatricula())
                                            .idTipoComposicaoValor(instituicao.getIdComposicaoValor())
                                            .build());
			
			criarCreditosContrato(idContrato, contratoDTO.getCreditosContratos());
			
			return idContrato;
			
    }

	@Transactional(propagation = Propagation.REQUIRED)
	private void criarCreditosContrato(Long idContrato, List<CreditoContratoDTO> creditosContratosDTOs) {
		creditosContratosDTOs.stream().forEach(credito -> {
			if(credito.getId() == null) {
				creditoRepositorio.criar(Credito.builder()
											.idContrato(idContrato)
											.idDisciplina(credito.getIdDisciplina())
											.idTurma(credito.getIdTurma())
											.idTipoPrograma(credito.getIdTipoPrograma())
											.idTipoCredito(credito.getIdTipoCredito())
											.valor(credito.getValor())
											.cargaHorariaDistancia(credito.getCargaHorariaDistancia())
											.cargaHorariaPresencial(credito.getCargaHorariaPresencial())
											.cargaHorariaTotal(credito.getCargaHorariaTotal())
											.build());
			}
		});
	}
    
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long alterar(ContratoDTO contratoDTO) {
		Instituicao instituicao = instituicaoRepositorio.buscarPorId(contratoDTO.getIdInstituicao());
		contratoRepositorio.alterar(Contrato.builder()
				.id(contratoDTO.getId())
				.ano(contratoDTO.getAno())
				.data(contratoDTO.getData())
				.idTipoComposicaoValor(instituicao.getIdComposicaoValor())
                .idInstituicao(contratoDTO.getIdInstituicao())
                .idPeriodoExecucao(contratoDTO.getIdPeriodoExecucao())
                .idMatricula(contratoDTO.getIdMatricula())
                .build());

		criarCreditosContrato(contratoDTO.getId(), contratoDTO.getCreditosContratos());
		
		return contratoDTO.getId();
		
	}

	@Override
	public ContratoDTO buscarPorId(long id) {
		ContratoDTO dto = contratoRepositorio.buscarPorId(id);
		dto.setCreditosContratos(creditoRepositorio.listarParaContrato(FiltroCredito.builder()
																		.idContrato(id).build()));
		
		dto.setConveniosContratos(convenioRepositorio.listarParaContrato(FiltroConvenio.builder().idContrato(id).build()));
		dto.setParcelas(parcelaServico.listar(FiltroParcela.builder().idContrato(id).build()));
		return dto;
	}
	
	@Override
	public ContratoDTO buscarContratoMatriculaPorId(long id) {
		ContratoDTO dto = contratoRepositorio.buscarPorId(id);
		dto.setCreditosContratos(creditoRepositorio.listarParaContrato(FiltroCredito.builder()
																		.idContrato(id).build()));
		return dto;
	}

	@Override
	public PaginacaoDTO<ContratoDTO> listar(FiltroContrato filtroContrato) throws ExcecaoDeNegocio {
			int total = contratoRepositorio.contar(filtroContrato);
			
			return PaginacaoDTO.<ContratoDTO>builder().total(total).dados(contratoRepositorio.listar(filtroContrato)).build();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean excluir(long id) {
		creditoRepositorio.excluirPorContrato(id);
		return contratoRepositorio.excluir(id);
	}

	@Override
	public void validar(Long id) {
		ContratoDTO contrato = contratoRepositorio.buscarPorId(id);
		if (contrato.getIdTipoSituacaoContrato().equals(EnumTipoSituacaoContrato.PRE_MATRICULA.id())) {
			contratoRepositorio.alterarSituacaoContrato(id, EnumTipoSituacaoContrato.VALIDADO);
		}
	}
	
	@Override
	public void enviarFinanceiro(Long id) {
		ContratoDTO contrato = contratoRepositorio.buscarPorId(id);
		if (contrato.getIdTipoSituacaoContrato().equals(EnumTipoSituacaoContrato.VALIDADO.id())) {
			contratoRepositorio.alterarSituacaoContrato(id, EnumTipoSituacaoContrato.ANALISE_FINANCEIRO);
		}
	}
	
	@Override
	public void finalizarAnalise(Long id) {
		contratoRepositorio.alterarSituacaoContrato(id, EnumTipoSituacaoContrato.AGUARDANDO_APROVACAO);
	}
	
	@Override
	public void aprovar(Long id) {
		contratoRepositorio.alterarSituacaoContrato(id, EnumTipoSituacaoContrato.AGUARDANDO_ASSINATURA);
	}
	
	@Override
	public void confirmarAssinatura(Long id) {
		contratoRepositorio.alterarSituacaoContrato(id, EnumTipoSituacaoContrato.CONFIRMADO);
	}
	
	@Override
	public void reativar(Long id) {
		ContratoDTO contrato = contratoRepositorio.buscarPorId(id);
		if (contrato.getIdTipoSituacaoContrato().equals(EnumTipoSituacaoContrato.CANCELADO.id()) ||
			contrato.getIdTipoSituacaoContrato().equals(EnumTipoSituacaoContrato.TRANSFERIDO.id())) {
			contratoRepositorio.alterarSituacaoContrato(id, EnumTipoSituacaoContrato.PRE_MATRICULA);
		}
	}

	@Override
	public DiaParcelaDTO calcularDiaVencimento(Integer numeroParcelas, LocalDate dataPrimeiraParcela) {
		LocalDate dataUltimaParcela = dataPrimeiraParcela.plusMonths(numeroParcelas);
		return DiaParcelaDTO.builder()
				.diaVencimento(dataPrimeiraParcela.getDayOfMonth())
				.dataUltimaParcela(dataUltimaParcela)
				.build();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void atualizarContratoAnalise(ContratoDTO dto) {
		ContratoDTO dtoBanco = contratoRepositorio.buscarPorId(dto.getId());
		Instituicao instituicao = instituicaoRepositorio.buscarPorId(dto.getIdInstituicao());
		
		List<ContratoConvenioDTO> contratosConvenios = dto.getConveniosContratos().stream().map(c -> {
			return ContratoConvenioDTO.builder().idContrato(dto.getId()).idConvenio(c.getId()).percentualDesconto(c.getPercentualDesconto()).build();
		}).collect(Collectors.toList());
		
		contratoConvenioServico.criar(contratosConvenios);
		
		if (dtoBanco.getIdTipoComposicaoValor().equals(EnumTipoComposicaoValor.POR_PERIODO.id())) {
			PeriodoDTO periodo = periodoRepositorio.buscarPorIdPeriodoExecucao(dtoBanco.getIdPeriodoExecucao());
			dtoBanco.setValorTotalPadrao(periodo.getValor());
		} else {
			Double somaValoresCreditosNaoPendentes = creditoRepositorio.somarValores(dtoBanco.getId(), false);
			dtoBanco.setValorTotalPadrao(somaValoresCreditosNaoPendentes != null ? somaValoresCreditosNaoPendentes : 0.0);
		}
		
		Double somaValoresCreditosPendentes = creditoRepositorio.somarValores(dtoBanco.getId(), true);
		dtoBanco.setValorTotalPendencia(somaValoresCreditosPendentes != null ? somaValoresCreditosPendentes : 0.0);
		dtoBanco.setValorTotalOriginal(dtoBanco.getValorTotalPadrao() + dtoBanco.getValorTotalPendencia());
		
		Double somaPercentuaisDescontos = contratoConvenioServico.somarPercentualDesconto(dtoBanco.getId());
		
		contratoRepositorio.alterar(Contrato.builder()
				.id(dto.getId())
				.numero(dto.getNumero())
				.ano(dto.getAno())
				.data(dto.getData())
				.idTipoSituacaoContrato(dto.getIdTipoSituacaoContrato())
				.idTipoComposicaoValor(instituicao.getIdComposicaoValor())
                .idInstituicao(dto.getIdInstituicao())
                .idPeriodoExecucao(dto.getIdPeriodoExecucao())
                .idMatricula(dto.getIdMatricula())
                .idResponsavelFinanceiro(dto.getIdResponsavelFinanceiro())
                .idTipoFormaPagamento(dto.getIdTipoFormaPagamento())
                .numeroParcelas(dto.getNumeroParcelas())
                .dataVencimentoPrimeiraParcela(dto.getDataVencimentoPrimeiraParcela())
                .dataVencimentoUltimaParcela(dto.getDataVencimentoUltimaParcela())
                .idEmpresaOrigem(dto.getIdEmpresaOrigem())
                .valorTotalPadrao(dtoBanco.getValorTotalPadrao())
                .valorTotalPendencia(dtoBanco.getValorTotalPendencia())
                .valorTotalOriginal(dtoBanco.getValorTotalOriginal())
                .percentualBomPagador(instituicao.getPercentualBomPagador())
                .percentualJurosAtraso(instituicao.getPercentualJurosAtraso())
                .percentualMultaAtraso(instituicao.getPercentualMultaAtraso())
                .percentualDescontoConvenio(somaPercentuaisDescontos)
                .diaVencimentoParcela(dto.getDiaVencimentoParcela())
                .observacao(dto.getObservacao())
                .build());
		
	}

}

