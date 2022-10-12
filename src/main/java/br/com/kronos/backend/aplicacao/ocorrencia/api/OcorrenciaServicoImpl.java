package br.com.kronos.backend.aplicacao.ocorrencia.api;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.ocorrencia.FiltroOcorrencia;
import br.com.kronos.backend.aplicacao.ocorrencia.Ocorrencia;
import br.com.kronos.backend.aplicacao.ocorrencia.OcorrenciaRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.PessoaRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class OcorrenciaServicoImpl implements OcorrenciaServico {

	@NonNull
	private OcorrenciaRepositorio ocorrenciaRepositorio;
	
	@NonNull
	private PessoaRepositorio pessoaRepositorio; 

	@NonNull
	private ModelMapper modelMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long criar(OcorrenciaDTO ocorrenciaDTO) throws ExcecaoDeNegocio {

		Long id = ocorrenciaRepositorio.criar(Ocorrencia.builder()
				                        .data(ocorrenciaDTO.getData())                                
                                        .hora(ocorrenciaDTO.getHora())
                                        .registro(ocorrenciaDTO.getRegistro())
                                        .idTurma(ocorrenciaDTO.getIdTurma())
                                        .idTipoOcorrencia(ocorrenciaDTO.getIdTipoOcorrencia())
                                        .idMatricula(ocorrenciaDTO.getIdMatricula())                                
                                        .idFuncionarioRelator(ocorrenciaDTO.getIdFuncionarioRelator())
                                        .idFuncionarioRegistro(ocorrenciaDTO.getIdFuncionarioRegistro())
                                        .dataCiencia(ocorrenciaDTO.getDataCiencia())
                                        .idResponsavelCiencia(ocorrenciaDTO.getIdResponsavelCiencia())
                                        .anulado(ocorrenciaDTO.isAnulado())
                                        .dataAnulacao(ocorrenciaDTO.getDataAnulacao())
                                        .motivoAnulacao(ocorrenciaDTO.getMotivoAnulacao())
                                        .idFuncionarioAnulacao(ocorrenciaDTO.getIdFuncionarioAnulacao()).build());
		
		atualizarGrauComportamento(ocorrenciaDTO);
		
		return id;

    }

	private void atualizarGrauComportamento(OcorrenciaDTO ocorrenciaDTO) {
		Float grau = this.returnaValorGrauComportamento(ocorrenciaDTO.getIdPessoaSelecionada());
		if (grau != null) {
			pessoaRepositorio.alterarGrauComportamento(ocorrenciaDTO.getIdPessoaSelecionada(), this.returnaValorGrauComportamento(ocorrenciaDTO.getIdPessoaSelecionada()));
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long alterar(OcorrenciaDTO ocorrenciaDTO) throws ExcecaoDeNegocio {
		Long id = ocorrenciaRepositorio.alterar(Ocorrencia.builder()
											.id(ocorrenciaDTO.getId())
											.data(ocorrenciaDTO.getData())                                
                                            .hora(ocorrenciaDTO.getHora())
                                            .registro(ocorrenciaDTO.getRegistro())
                                            .idTurma(ocorrenciaDTO.getIdTurma())
                                            .idTipoOcorrencia(ocorrenciaDTO.getIdTipoOcorrencia())
                                            .idMatricula(ocorrenciaDTO.getIdMatricula())                                
                                            .idFuncionarioRelator(ocorrenciaDTO.getIdFuncionarioRelator())
                                            .idFuncionarioRegistro(ocorrenciaDTO.getIdFuncionarioRegistro())
                                            .dataCiencia(ocorrenciaDTO.getDataCiencia())
                                            .idResponsavelCiencia(ocorrenciaDTO.getIdResponsavelCiencia())
                                            .anulado(ocorrenciaDTO.isAnulado())
                                            .dataAnulacao(ocorrenciaDTO.getDataAnulacao())
                                            .motivoAnulacao(ocorrenciaDTO.getMotivoAnulacao())
                                            .idFuncionarioAnulacao(ocorrenciaDTO.getIdFuncionarioAnulacao()).build());
		
		atualizarGrauComportamento(ocorrenciaDTO);
			
		return id;
		
	}

	@Override
	public OcorrenciaDTO buscarPorId (long id) {
		return ocorrenciaRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<OcorrenciaDTO> listar(FiltroOcorrencia filtroOcorrencia) throws ExcecaoDeNegocio {
			int total = ocorrenciaRepositorio.contar(filtroOcorrencia);
			
			return PaginacaoDTO.<OcorrenciaDTO>builder().total(total).dados(ocorrenciaRepositorio.listar(filtroOcorrencia)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return ocorrenciaRepositorio.excluir(id);
	}

	@Override
	public Float returnaValorGrauComportamento(Long idPessoaSelecionada) {
		Float grau = ocorrenciaRepositorio.returnaValorGrauComportamento(idPessoaSelecionada);
		return grau != null ? grau : 0.0f;
	}

}

