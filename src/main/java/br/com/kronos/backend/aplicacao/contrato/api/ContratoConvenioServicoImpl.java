package br.com.kronos.backend.aplicacao.contrato.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.contrato.FiltroContratoConvenio;
import br.com.kronos.backend.aplicacao.contrato.ContratoConvenio;
import br.com.kronos.backend.aplicacao.contrato.ContratoConvenioRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class ContratoConvenioServicoImpl implements ContratoConvenioServico {

	@NonNull
	private ContratoConvenioRepositorio contratoConvenioRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void criar(List<ContratoConvenioDTO> dtos) throws ExcecaoDeNegocio {
		try {
			//TODO melhorar essa criacao utilizando in()
			dtos.stream().forEach(dto -> {
				if(contratoConvenioRepositorio.contar(FiltroContratoConvenio.builder().idContrato(dto.getIdContrato()).idConvenio(dto.getIdConvenio()).build()) == 0) {
					contratoConvenioRepositorio.criar(ContratoConvenio.builder()
						.idContrato(dto.getIdContrato())
						.idConvenio(dto.getIdConvenio())
						.percentualDesconto(dto.getPercentualDesconto())
						.build());
				}
			});
		} catch (RuntimeException e) {
			log.error("Erro ao atribuir convênio ao contrato", e);
			throw new ExcecaoDeNegocio("Erro ao atribuir convênio ao contrat", e);
		}
	}

	
	@Override
	public PaginacaoDTO<ContratoConvenioDTO> listar(FiltroContratoConvenio filtroContratoConvenio) throws ExcecaoDeNegocio {
		try {
			
			int total = contratoConvenioRepositorio.contar(filtroContratoConvenio);
			
			List<ContratoConvenioDTO> contratoConvenios = modelMapper.map(contratoConvenioRepositorio.listar(filtroContratoConvenio),
					new TypeToken<List<ContratoConvenioDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<ContratoConvenioDTO>builder().total(total).dados(contratoConvenios).build();
		} catch (RuntimeException e) {
			log.error("Erro ao listar convênios do contrato", e);
			throw new ExcecaoDeNegocio("Erro ao listar convênios do contrato", e);
		}
	}

	@Override
	public boolean excluir(long idContrato, long idConvenio) {
		try {
			return contratoConvenioRepositorio.excluir(idContrato, idConvenio);
		} catch (RuntimeException e) {
			log.error("Erro ao excluir convênio - " + idConvenio  + " do contrato - " + idContrato, e);
			throw new ExcecaoDeNegocio("Erro ao excluir convênio - " + idConvenio  + " do contrato - " + idContrato, e);
		}
	}


	@Override
	public Double somarPercentualDesconto(Long idContrato) {
		return contratoConvenioRepositorio.somarPercentualDesconto(idContrato);
	}

}
