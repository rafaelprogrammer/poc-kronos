package br.com.kronos.backend.aplicacao.contrato.api;

import static br.com.kronos.backend.aplicacao.util.DecimalUtil.converterDoubleDoisDecimais;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.contrato.ContratoRepositorio;
import br.com.kronos.backend.aplicacao.contrato.FiltroParcela;
import br.com.kronos.backend.aplicacao.contrato.Parcela;
import br.com.kronos.backend.aplicacao.contrato.ParcelaRepositorio;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ParcelaServicoImpl implements ParcelaServico {

	@NonNull
	private ParcelaRepositorio parcelaRepositorio;
	
	@NonNull
	private ContratoRepositorio contratoRepositorio;
	
	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(ParcelaDTO ParcelaDTO) throws ExcecaoDeNegocio {
			return parcelaRepositorio.criar(Parcela.builder()
											.numero(ParcelaDTO.getNumero())
											.dataVencimento(ParcelaDTO.getDataVencimento())
											.valorOriginal(ParcelaDTO.getValorOriginal())
											.dataPagamento(ParcelaDTO.getDataPagamento())
											.valorJuros(ParcelaDTO.getValorJuros())
											.valorMulta(ParcelaDTO.getValorMulta())
											.valorDesconto(ParcelaDTO.getValorDesconto())
											.valorPagamento(ParcelaDTO.getValorPagamento())
											.observacao(ParcelaDTO.getObservacao())
											.idTipoFormaPagamento(ParcelaDTO.getIdTipoFormaPagamento())
											.idContrato(ParcelaDTO.getIdContrato()).build());
			
    }
	 
	@Override
	public Long alterar(ParcelaDTO dto) throws ExcecaoDeNegocio {
			return parcelaRepositorio.alterar(Parcela.builder()
											.id(dto.getId())
											.numero(dto.getNumero())
											.dataVencimento(dto.getDataVencimento())
											.valorOriginal(dto.getValorOriginal())
											.dataPagamento(dto.getDataPagamento())
											.valorJuros(dto.getValorJuros())
											.valorMulta(dto.getValorMulta())
											.valorDesconto(dto.getValorDesconto())
											.valorPagamento(dto.getValorPagamento())
											.observacao(dto.getObservacao())
											.idTipoFormaPagamento(dto.getIdTipoFormaPagamento())
											.idContrato(dto.getIdContrato()).build());
		
	}

	@Override
	public ParcelaDTO buscarPorId(long id) {
		return modelMapper.map(parcelaRepositorio.buscarPorId(id), ParcelaDTO.class);
	}

	@Override
	public List<ParcelaDTO> listar(FiltroParcela filtroParcela) throws ExcecaoDeNegocio {
//			int total = parcelaRepositorio.contar(filtroParcela);
			List<ParcelaDTO> parcelas = modelMapper.map(parcelaRepositorio.listar(filtroParcela),
					new TypeToken<List<ParcelaDTO>>() {
					}.getType());
			
			return parcelas;
			
//			return PaginacaoDTO.<ParcelaDTO>builder().total(total).dados(parcelas).build();
	}

	@Override
	public boolean excluir(long id) {
		return parcelaRepositorio.excluir(id);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void gerarParcelas(ParcelaDTO parcelaDTO) {
		//TODO gera somente uma vez. Verificar esta regra depois
		int totalParcelas = parcelaRepositorio.contar(FiltroParcela.builder().idContrato(parcelaDTO.getIdContrato()).build());
		ContratoDTO dto = contratoRepositorio.buscarPorId(parcelaDTO.getIdContrato());
		if (totalParcelas == 0 && dto.getValorTotalFinal() != null && dto.getValorTotalFinal() > 0 ) {
			double valorCalculado = converterDoubleDoisDecimais(dto.getValorTotalFinal() / parcelaDTO.getNumeroParcelas()); 
			double valorPrimeiraParcela = converterDoubleDoisDecimais(dto.getValorTotalFinal() - ((dto.getNumeroParcelas() - 1) * valorCalculado));
			Parcela primeiraParcela = criarParcela(dto, 1, valorPrimeiraParcela, dto.getDataVencimentoPrimeiraParcela());
			List<Parcela> parcelas = new ArrayList<Parcela>();
			parcelas.add(primeiraParcela);
			IntStream.range(1, parcelaDTO.getNumeroParcelas()).forEach(index -> {
				parcelas.add(criarParcela(dto, index + 1, valorCalculado, dto.getDataVencimentoPrimeiraParcela().plusMonths(index)));
			});
			
			parcelas.stream().forEach(parcela -> {
				parcelaRepositorio.criar(parcela);
			});
		}
		
	}
	
	private Parcela criarParcela(ContratoDTO dto, int numero, double valor, LocalDate dataVencimento) {
		return Parcela.builder()
					.idContrato(dto.getId())
					.dataVencimento(dataVencimento)
					.idTipoFormaPagamento(dto.getIdTipoFormaPagamento())
					.valorOriginal(valor)
					.valorJuros(dto.getPercentualJurosAtraso() * valor / 100)
					.valorMulta(dto.getPercentualMultaAtraso() * valor / 100)
					.valorDesconto(dto.getPercentualDescontoConvenio() * valor / 100)
					.numero(numero)
					.build();
	}

}
