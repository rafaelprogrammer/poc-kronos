package br.com.kronos.backend.aplicacao.matricula.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.atestado.Atestado;
import br.com.kronos.backend.aplicacao.atestado.AtestadoRepositorio;
import br.com.kronos.backend.aplicacao.atestado.EnumTipoAtestado;
import br.com.kronos.backend.aplicacao.atestado.EnumTipoFalta;
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import br.com.kronos.backend.aplicacao.contrato.ContratoRepositorio;
import br.com.kronos.backend.aplicacao.contrato.EnumTipoSituacaoContrato;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.funcionario.FuncionarioRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioDTO;
import br.com.kronos.backend.aplicacao.matricula.EnumTipoSituacaoMatricula;
import br.com.kronos.backend.aplicacao.matricula.FiltroMatricula;
import br.com.kronos.backend.aplicacao.matricula.Matricula;
import br.com.kronos.backend.aplicacao.matricula.MatriculaRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class MatriculaServicoImpl implements MatriculaServico {

	@NonNull
	private MatriculaRepositorio matriculaRepositorio;
	
	@NonNull
	private ContratoRepositorio contratoRepositorio;
	
	@NonNull
	private AtestadoRepositorio atestadoRepositorio;
	
	@NonNull
	private ServicoAutenticacao servicoAutenticacao;
	
	@NonNull
	private FuncionarioRepositorio funcionarioRepositorio;
	
	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(MatriculaDTO matriculaDTO) {
		verificarSeExisteMatricula(matriculaDTO.getId(), matriculaDTO.getIdPessoa(), matriculaDTO.getIdCurso());
	
		return matriculaRepositorio.criar(Matricula.builder()
				                        .data(matriculaDTO.getData())                                
                                        .anoInicioCurso(matriculaDTO.getAnoInicioCurso())
                                        .semestreInicioCurso(matriculaDTO.getSemestreInicioCurso())
                                        .dataInicioCurso(matriculaDTO.getDataInicioCurso())
                                        .anoConclusaoCurso(matriculaDTO.getAnoConclusaoCurso())
                                        .semestreConclusaoCurso(matriculaDTO.getSemestreConclusaoCurso())
                                        .dataConclusaoCurso(matriculaDTO.getDataConclusaoCurso())
                                        .dataColacaoGrau(matriculaDTO.getDataColacaoGrau())
                                        .idPessoa(matriculaDTO.getIdPessoa())
                                        .idCurso(matriculaDTO.getIdCurso())
                                        .idTipoSituacaoMatricula(EnumTipoSituacaoMatricula.PRE_MATRICULA.id())
                                        .idTipoResultado(matriculaDTO.getIdTipoResultado())
                                        .idEmpresaOrigem(matriculaDTO.getIdEmpresaOrigem()).build());	

    }


	@Override
	public Long alterar(MatriculaDTO matriculaDTO) {
		verificarSeExisteMatricula(matriculaDTO.getId(), matriculaDTO.getIdPessoa(), matriculaDTO.getIdCurso());
		return matriculaRepositorio.alterar(Matricula.builder()
											.id(matriculaDTO.getId())
											.data(matriculaDTO.getData())                                
                                            .anoInicioCurso(matriculaDTO.getAnoInicioCurso())
                                            .semestreInicioCurso(matriculaDTO.getSemestreInicioCurso())
                                            .dataInicioCurso(matriculaDTO.getDataInicioCurso())
                                            .anoConclusaoCurso(matriculaDTO.getAnoConclusaoCurso())
                                            .semestreConclusaoCurso(matriculaDTO.getSemestreConclusaoCurso())
                                            .dataConclusaoCurso(matriculaDTO.getDataConclusaoCurso())
                                            .dataColacaoGrau(matriculaDTO.getDataColacaoGrau())
                                            .idPessoa(matriculaDTO.getIdPessoa())
                                            .idCurso(matriculaDTO.getIdCurso())
                                            .idTipoSituacaoMatricula(matriculaDTO.getIdTipoSituacaoMatricula())
                                            .idTipoResultado(matriculaDTO.getIdTipoResultado())
                                            .idEmpresaOrigem(matriculaDTO.getIdEmpresaOrigem()).build());
	
	}
	
	@Override
	public MatriculaDTO buscarPorId (long id) {
		return matriculaRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<MatriculaDTO> listar(FiltroMatricula filtroMatricula) {
			int total = matriculaRepositorio.contar(filtroMatricula);
			return PaginacaoDTO.<MatriculaDTO>builder()
					.total(total)
					.dados(matriculaRepositorio.listar(filtroMatricula))
					.build();
	}

	@Override
	public boolean excluir(Long id) {
		return matriculaRepositorio.excluir(id);
	}

	private void verificarSeExisteMatricula(Long id, Long idPessoa, Long idCurso) {
		if (id != null && id > 0) {
			MatriculaDTO dto = matriculaRepositorio.buscarPorId(id);
			if ((!dto.getIdPessoa().equals(idPessoa) || !dto.getIdCurso().equals(idCurso)) && 
				matriculaRepositorio.verificarSeExisteMatricula(idPessoa, idCurso)) {
				throw new ExcecaoDeNegocio("Já existe uma matrícula ou pré-matrícula");
			}
		}
	}


	@Override
	public void validar(Long id) {
		MatriculaDTO matricula = matriculaRepositorio.buscarPorId(id);
		if (matricula.getIdTipoSituacaoMatricula().equals(EnumTipoSituacaoMatricula.PRE_MATRICULA.id())) {
			matriculaRepositorio.atualizarTipoSituacao(id, EnumTipoSituacaoMatricula.VALIDADA);
		}
	}
	
	@Override
	public void reativar(Long id) {
		MatriculaDTO matricula = matriculaRepositorio.buscarPorId(id);
		if (matricula.getIdTipoSituacaoMatricula().equals(EnumTipoSituacaoMatricula.TRANSFERIDA.id()) || 
			matricula.getIdTipoSituacaoMatricula().equals(EnumTipoSituacaoMatricula.CANCELADA.id())	) {
			matriculaRepositorio.atualizarTipoSituacao(id, EnumTipoSituacaoMatricula.PRE_MATRICULA);
		}
	}


	@Override
	public List<MatriculaContratoDTO> listarPendentesContrato(Long idInstituicao) {
		
		return matriculaRepositorio.listarPendentesContrato(idInstituicao, EnumTipoSituacaoContrato.ANALISE_FINANCEIRO.id(),
				EnumTipoSituacaoContrato.AGUARDANDO_APROVACAO.id(), EnumTipoSituacaoContrato.AGUARDANDO_ASSINATURA.id());
	}


	@Override
	public MatriculaOcorrenciaDTO buscarMatriculaParaOcorrencia(Long idPessoaSelecionada) {
		return matriculaRepositorio.buscarMatriculaParaOcorrencia(idPessoaSelecionada);
	}


	@Override
	@Cacheable(cacheNames = Matricula.COMBO_CACHE_NAME_OCORRENCIAS_POR_PESSOA, key="{#idPessoaSelecionada}")
	public List<MatriculaOcorrenciaDTO> listarMatriculaParaOcorrenciaCombo(Long idPessoaSelecionada) {
		return matriculaRepositorio.listarMatriculaParaOcorrenciaCombo(idPessoaSelecionada);
	}
	
	@Override
	public MatriculaCanceladaTransferidaDTO buscarPorIdParaCancelamentoOuTransferencia(Long id) {
		return matriculaRepositorio.buscarPorIdParaCancelamentoOuTransferencia(id);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void cancelar(MatriculaCanceladaTransferidaDTO dto) {
		cancelarOuTransferir(dto, EnumTipoSituacaoMatricula.CANCELADA, EnumTipoSituacaoContrato.CANCELADO, EnumTipoAtestado.CANCELAMENTO, EnumTipoFalta.CANCELAMENTO);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void transferir(MatriculaCanceladaTransferidaDTO dto) {
		cancelarOuTransferir(dto, EnumTipoSituacaoMatricula.TRANSFERIDA, EnumTipoSituacaoContrato.TRANSFERIDO, EnumTipoAtestado.TRANSFERIDO, EnumTipoFalta.TRANSFERIDO);
	}

	private void cancelarOuTransferir(MatriculaCanceladaTransferidaDTO dto, EnumTipoSituacaoMatricula situacaoMatricula, 
			EnumTipoSituacaoContrato situacaoContrato, EnumTipoAtestado tipoAtestado, EnumTipoFalta tipoFalta) {
		matriculaRepositorio.atualizarTipoSituacao(dto.getId(), situacaoMatricula);
		contratoRepositorio.alterarSituacaoContrato(dto.getIdContrato(), situacaoContrato);
		UsuarioDTO usuario = servicoAutenticacao.buscarUsuarioLogado();
		FuncionarioDTO funcionario = funcionarioRepositorio.buscarPorIdPessoa(usuario.getIdPessoa());
		atestadoRepositorio.criar(Atestado.builder()
							.idFuncionario(funcionario.getId())
							.idPessoa(dto.getIdPessoa())
							.idInstituicao(usuario.getIdInstituicao())
							.idTipoAtestado(tipoAtestado.id())
							.idTipoFalta(tipoFalta.valor())
							.idContrato(dto.getIdContrato())
							.dataInicioVigencia(dto.getDataInformada())
							.dataFinalVigencia(dto.getDataFinalAnoLetivo())
							.dataEntrega(dto.getDataInformada())
							.build());
	}

}

