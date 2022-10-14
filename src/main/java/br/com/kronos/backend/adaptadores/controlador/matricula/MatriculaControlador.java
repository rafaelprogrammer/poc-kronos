package br.com.kronos.backend.adaptadores.controlador.matricula;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.kronos.backend.adaptadores.controlador.BaseControlador;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.matricula.FiltroMatricula;
import br.com.kronos.backend.aplicacao.matricula.api.CreditoContratoDTO;
import br.com.kronos.backend.aplicacao.matricula.api.CreditoServico;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaCanceladaTransferidaDTO;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaContratoDTO;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaDTO;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaOcorrenciaDTO;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaServico;
import br.com.kronos.backend.aplicacao.matricula.api.TipoCreditoDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TipoProgramaDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TipoResultadoDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TipoSituacaoMatriculaDTO;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/matriculas")
@CrossOrigin
public class MatriculaControlador extends BaseControlador {

//	@NonNull
//	private MatriculaServico matriculaServico;
	
//	@NonNull
//	private CreditoServico creditoServico;

	@NonNull
	private DateUtil dateUtil;
	
//	@PostMapping
//	@ApiOperation(value = "Cadastrar Matricula", notes = "Cadastrar Matricula")
//	public ResponseEntity<String> criar(@RequestBody MatriculaDTO dto) {
//		matriculaServico.criar(dto);
//		return ResponseEntity.ok().body("Cadastro da Matrícula enviada com sucesso");
//	}
//	
//	@PutMapping("/{id}")
//	@ApiOperation(value = "Alterar Matricula", notes = "Alterar Matricula")
//	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody MatriculaDTO dto) {
//		dto.setId(id);
//		matriculaServico.alterar(dto);
//		return ResponseEntity.ok().body("Alteração da matrícula enviada com sucesso");
//	}
//	
//	@DeleteMapping("/{id}")
//	@ApiOperation(value = "Excluir Matricula", notes = "Excluir Matricula")
//	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
//		matriculaServico.excluir(id);
//		return ResponseEntity.ok().body("Exclusão da matrícula enviada com sucesso");
//	}
//	
//	@GetMapping
//	@ApiOperation(value = "Listar Matriculas", notes = "Listar Matriculas")
//	public ResponseEntity<PaginacaoDTO<MatriculaDTO>> listar(@RequestParam(value = "id", required = false) Long id,
//			@RequestParam(value = "idPessoa", required = false) Long idPessoa,
//			@RequestParam(value = "nomePessoa", required = false) String nomePessoa,
//			@RequestParam(value = "cpfPessoa", required = false) String cpfPessoa,
//			@RequestParam(value = "numeroRegistroPessoa", required = false) Integer numeroRegistroPessoa,
//			@RequestParam(value = "anoInicioCurso", required = false) Integer anoInicioCurso,
//			@RequestParam(value = "idTipoSituacaoMatricula", required = false) Integer idTipoSituacaoMatricula,
//			@RequestParam(value = "idCurso", required = false) Long idCurso,
//			@RequestParam(value = "total", required = false) Integer total,
//			@RequestParam(value = "pagina", required = false) Integer pagina) {
//
//		PaginacaoDTO<MatriculaDTO> matriculas = matriculaServico
//				.listar(FiltroMatricula.builder()
//									.id(id)
//									.idPessoa(idPessoa)
//									.nomePessoa(nomePessoa)
//									.cpfPessoa(cpfPessoa)
//									.numeroRegistroPessoa(numeroRegistroPessoa)
//									.anoInicioCurso(anoInicioCurso)
//									.idCurso(idCurso)
//									.idTipoSituacaoMatricula(idTipoSituacaoMatricula)
//									.qtdTotal(total)
//									.numeroPagina(pagina)
//									.build());
//		
//		if (matriculas == null) {
//			return ResponseEntity.noContent().build();
//		}
//
//		return ResponseEntity.ok().body(matriculas);
//	}
//	
//	@GetMapping(value="/pendentes-contrato")
//	@ApiOperation(value = "Listar Matriculas Pendentes Contrato", notes = "Listar Matriculas Pendentes Contrato")
//	public ResponseEntity<List<MatriculaContratoDTO>> listarPendentesContrato(
//			@RequestParam(value = "idInstituicao", required = true) Long idInstituicao) {
//
//		List<MatriculaContratoDTO> matriculas = matriculaServico
//				.listarPendentesContrato(idInstituicao);
//		
//		if (matriculas == null) {
//			return ResponseEntity.noContent().build();
//		}
//
//		return ResponseEntity.ok().body(matriculas);
//	}
//	
//	@GetMapping(value = "/{id}")
//	@ApiOperation(value = "Buscar Matricula por id", notes = "Buscar Matricula por id")
//	public ResponseEntity<MatriculaDTO> buscarPorId(@PathVariable(value = "id") Long id) {
//
//		MatriculaDTO dto = matriculaServico.buscarPorId(id);
//		if (dto == null) {
//			return ResponseEntity.noContent().build();
//		}
//
//		return ResponseEntity.ok().body(dto);
//	}
	
//	@GetMapping(value="/tipos-situacoes")
//	@ApiOperation(value = "Listar Tipos de Situacao da Matricula", notes = "Listar Tipos de Situacao da Matricula")
//	public ResponseEntity<List<TipoSituacaoMatriculaDTO>> tiposSituacoesMatricula() {
//		return ResponseEntity.ok().body(TipoSituacaoMatriculaDTO.tipos());
//	}
//	
//	@GetMapping(value="/tipos-resultados")
//	@ApiOperation(value = "Listar Tipos de Resultado", notes = "Listar Tipos de Resultado")
//	public ResponseEntity<List<TipoResultadoDTO>> tiposResultados() {
//		return ResponseEntity.ok().body(TipoResultadoDTO.tipos());
//	}
//	
//	@PutMapping("/{id}/valida")
//	@ApiOperation(value = "Validar Matricula", notes = "Validar Matricula")
//	public ResponseEntity<String> validar(@PathVariable(value = "id") Long id) {
//		matriculaServico.validar(id);
//		return ResponseEntity.ok().body("Validação da matrícula enviada com sucesso");
//	}
//	
//	@PutMapping("/{id}/reativa")
//	@ApiOperation(value = "Reativar Matricula", notes = "Reativar Matricula")
//	public ResponseEntity<String> reativar(@PathVariable(value = "id") Long id) {
//		matriculaServico.reativar(id);
//		return ResponseEntity.ok().body("Matrícula reativada com sucesso");
//	}
//	
//	@PostMapping("/cancela")
//	@ApiOperation(value = "Cancelar Matricula", notes = "Cancelar Matricula")
//	public ResponseEntity<String> cancelar(@RequestBody MatriculaCanceladaTransferidaDTO dto) {
//		matriculaServico.cancelar(dto);
//		return ResponseEntity.ok().body("Matrícula cancelada com sucesso");
//	}
//	
//	@PostMapping("/tranfere")
//	@ApiOperation(value = "Transferir Matricula", notes = "Transferir Matricula")
//	public ResponseEntity<String> transferir(@RequestBody MatriculaCanceladaTransferidaDTO dto) {
//		matriculaServico.transferir(dto);
//		return ResponseEntity.ok().body("Matrícula transferida com sucesso");
//	}
//	
//	@GetMapping(value="/{id}/transferencias/cancelamentos")
//	@ApiOperation(value = "Buscar para transferencias e cancelamentos", notes = "Buscar para transferencias e cancelamentos")
//	public ResponseEntity<MatriculaCanceladaTransferidaDTO> buscarPorIdParaCancelamentoOuTransferencia(@PathVariable(value = "id") Long id) {
//
//		MatriculaCanceladaTransferidaDTO matricula = matriculaServico.buscarPorIdParaCancelamentoOuTransferencia(id);
//		
//		if (matricula == null) {
//			return ResponseEntity.noContent().build();
//		}
//
//		return ResponseEntity.ok().body(matricula);
//	}
//	
//	@GetMapping(value="/tipos-creditos")
//	@ApiOperation(value = "Listar Tipos de Creditos", notes = "Listar Tipos de Creditos")
//	public ResponseEntity<List<TipoCreditoDTO>> tiposCreditos() {
//		return ResponseEntity.ok().body(TipoCreditoDTO.tipos());
//	}
	
	@GetMapping(value="/tipos-programas")
	@ApiOperation(value = "Listar Tipos de Programas", notes = "Listar Tipos de Programas")
	public ResponseEntity<List<TipoProgramaDTO>> tiposProgramas() {
		return ResponseEntity.ok().body(TipoProgramaDTO.tipos());
	}
	
//	@GetMapping(value="/gera-creditos")
//	@ApiOperation(value = "Gera Creditos", notes = "Gera Creditos")
//	public ResponseEntity<List<CreditoContratoDTO>> gerarCreditos(
//			@RequestParam(value = "idContrato", required = false) Long idContrato,
//			@RequestParam(value = "idPeriodoExecucao", required = true) Long idPeriodoExecucao,
//			@RequestParam(value = "idTurma", required = true) Long idTurma,
//			@RequestParam(value = "idTipoPrograma", required = true) Integer idTipoPrograma,
//			@RequestParam(value = "idTipoCredito", required = true) Integer idTipoCredito) {
//
//		List<CreditoContratoDTO> creditos = creditoServico
//				.gerarCreditos(CreditoContratoDTO.builder()
//								.idContrato(idContrato)
//								.idPeriodoExecucao(idPeriodoExecucao)
//								.idTurma(idTurma)
//								.idTipoPrograma(idTipoPrograma)
//								.idTipoCredito(idTipoCredito)
//								.build());
//		
//		if (creditos == null) {
//			return ResponseEntity.noContent().build();
//		}
//
//		return ResponseEntity.ok().body(creditos);
//	}
//	
//	@GetMapping(value="/gera-creditos-pendentes")
//	@ApiOperation(value = "Gera Creditos Pendentes", notes = "Gera Creditos Pendentes")
//	public ResponseEntity<List<CreditoContratoDTO>> gerarCreditosPendentes(
//			@RequestParam(value = "idContrato", required = false) Long idContrato,
//			@RequestParam(value = "idPeriodoExecucao", required = true) Long idPeriodoExecucao,
//			@RequestParam(value = "idMatricula", required = true) Long idMatricula,
//			@RequestParam(value = "idTurma", required = true) Long idTurma,
//			@RequestParam(value = "idTipoPrograma", required = true) Integer idTipoPrograma,
//			@RequestParam(value = "idTipoCredito", required = true) Integer idTipoCredito) {
//
//		List<CreditoContratoDTO> creditos = creditoServico
//				.gerarCreditosPendentes(CreditoContratoDTO.builder()
//								.idContrato(idContrato)
//								.idPeriodoExecucao(idPeriodoExecucao)
//								.idMatricula(idMatricula)
//								.idTurma(idTurma)
//								.idTipoPrograma(idTipoPrograma)
//								.idTipoCredito(idTipoCredito)
//								.build());
//		
//		if (creditos == null) {
//			return ResponseEntity.noContent().build();
//		}
//
//		return ResponseEntity.ok().body(creditos);
//	}
//	
//	@GetMapping(value = "/ocorrencia/pessoa/{id}")
//	@ApiOperation(value = "Buscar Matricula por id da pessoa selecionada para ocorrencia", notes = "Buscar Matricula por id da pessoa selecionada para ocorrencia")
//	public ResponseEntity<MatriculaOcorrenciaDTO> buscarMatriculaParaOcorrencia(@PathVariable(value = "id") Long idPessoa) {
//
//		MatriculaOcorrenciaDTO dto = matriculaServico.buscarMatriculaParaOcorrencia(idPessoa);
//		if (dto == null) {
//			return ResponseEntity.noContent().build();
//		}
//
//		return ResponseEntity.ok().body(dto);
//	}
//	
//	@GetMapping(value = "/ocorrencia/cursos/combo/pessoa/{id}")
//	@ApiOperation(value = "Listar Matriculas e nome dos cursos por id da pessoa selecionada para ocorrencia", notes = "Listar Matriculas e nome dos cursos por id da pessoa selecionada para ocorrencia")
//	public ResponseEntity<List<MatriculaOcorrenciaDTO>> listarMatriculaParaOcorrenciaCombo(@PathVariable(value = "id") Long idPessoa) {
//
//		List<MatriculaOcorrenciaDTO> dtos = matriculaServico.listarMatriculaParaOcorrenciaCombo(idPessoa);
//		if (dtos == null) {
//			return ResponseEntity.noContent().build();
//		}
//
//		return ResponseEntity.ok().body(dtos);
//	}
}
