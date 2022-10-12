package br.com.kronos.backend.adaptadores.controlador.frequencia;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.kronos.backend.adaptadores.controlador.BaseControlador;
import br.com.kronos.backend.aplicacao.frequencia.FiltroFrequencia;
import br.com.kronos.backend.aplicacao.frequencia.api.FrequenciaDTO;
import br.com.kronos.backend.aplicacao.frequencia.api.FrequenciaServico;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/frequencias")
@CrossOrigin
public class FrequenciaControlador extends BaseControlador {

	@NonNull
	private FrequenciaServico frequenciaServico;
	
	@PostMapping("/tipos-atestados/todos/sub-fase-execucao/{idSubFaseExecucao}/atividade/{idAtividade}/disciplina/{idDisciplina}/ano-turma/{anoTurma}")
	@ApiOperation(value = "Altera frequencias para dos tipos de atestados - T, C, TF", notes = "Altera frequencias para dos tipos de atestados - T, C, TF")
	public ResponseEntity<String> criarTiposFrequenciasAtestados(@PathVariable(value = "idSubFaseExecucao") Long idSubFaseExecucao,
			@PathVariable(value = "idAtividade") Long idAtividade,
			@PathVariable(value = "idDisciplina") Long idDisciplina,
			@PathVariable(value = "anoTurma") Integer anoTurma) {
	
		frequenciaServico.criarTiposFrequenciasAtestados(FiltroFrequencia.builder()
										.idAtividade(idAtividade)
										.idDisciplina(idDisciplina)
										.idSubFaseExecucao(idSubFaseExecucao)
										.anoTurma(anoTurma)
										.build());
	
		return ResponseEntity.ok().body("Frequências alteradas para FJ/C/T");
	}
	
	@PostMapping("/tp")
	@ApiOperation(value = "Criar Tipo TP", notes = "Criar Tipo TP")
	public ResponseEntity<String> criarTipoTP(@RequestBody FrequenciaDTO dto) {
		dto.setCriarTipoTP(true);
		this.registrarDadosFrequencia(dto);
		return ResponseEntity.ok().body("Frequência TP enviada com sucesso");
	}
	
	@PostMapping("/tp/todos/sub-fase-execucao/{idSubFaseExecucao}/atividade/{idAtividade}/disciplina/{idDisciplina}/ano-turma/{anoTurma}")
	@ApiOperation(value = "Altera frequencias para TP", notes = "Altera frequencias para TP")
	public ResponseEntity<String> criarTipoTPTodos(@PathVariable(value = "idSubFaseExecucao") Long idSubFaseExecucao,
			@PathVariable(value = "idAtividade") Long idAtividade,
			@PathVariable(value = "idDisciplina") Long idDisciplina,
			@PathVariable(value = "anoTurma") Integer anoTurma) {
	
		frequenciaServico.criarTipoTPTodos(FiltroFrequencia.builder()
										.idAtividade(idAtividade)
										.idDisciplina(idDisciplina)
										.idSubFaseExecucao(idSubFaseExecucao)
										.anoTurma(anoTurma)
										.build());
	
		return ResponseEntity.ok().body("Frequências alteradas para TP");
	}
	
	@PostMapping("/tf")
	@ApiOperation(value = "Criar Tipo TF", notes = "Criar Tipo TF")
	public ResponseEntity<String> criarTipoTF(@RequestBody FrequenciaDTO dto) {
		dto.setCriarTipoTF(true);
		dto.setTipoTFIndividual(true);
		this.registrarDadosFrequencia(dto);
		return ResponseEntity.ok().body("Frequência TF enviada com sucesso");
	}
	
	@PostMapping("/tf/todos/sub-fase-execucao/{idSubFaseExecucao}/atividade/{idAtividade}/disciplina/{idDisciplina}/ano-turma/{anoTurma}")
	@ApiOperation(value = "Altera frequencias para TF", notes = "Altera frequencias para TF")
	public ResponseEntity<String> criarTipoTFTodos(@PathVariable(value = "idSubFaseExecucao") Long idSubFaseExecucao,
			@PathVariable(value = "idAtividade") Long idAtividade,
			@PathVariable(value = "idDisciplina") Long idDisciplina,
			@PathVariable(value = "anoTurma") Integer anoTurma) {
	
		frequenciaServico.criarTipoTFTodos(FiltroFrequencia.builder()
										.idAtividade(idAtividade)
										.idDisciplina(idDisciplina)
										.idSubFaseExecucao(idSubFaseExecucao)
										.anoTurma(anoTurma)
										.build());
	
		return ResponseEntity.ok().body("Frequências alteradas para TF");
	}
	
	@PostMapping("/ad/todos/sub-fase-execucao/{idSubFaseExecucao}/atividade/{idAtividade}/disciplina/{idDisciplina}/ano-turma/{anoTurma}")
	@ApiOperation(value = "Altera frequencias para AD", notes = "Altera frequencias para AD")
	public ResponseEntity<String> criarTipoADTodos(@PathVariable(value = "idSubFaseExecucao") Long idSubFaseExecucao,
			@PathVariable(value = "idAtividade") Long idAtividade,
			@PathVariable(value = "idDisciplina") Long idDisciplina,
			@PathVariable(value = "anoTurma") Integer anoTurma) {
	
		frequenciaServico.criarTipoADTodos(FiltroFrequencia.builder()
										.idAtividade(idAtividade)
										.idDisciplina(idDisciplina)
										.idSubFaseExecucao(idSubFaseExecucao)
										.anoTurma(anoTurma)
										.build());
	
		return ResponseEntity.ok().body("Frequências alteradas para AD");
	}
	
	@PostMapping("/ad")
	@ApiOperation(value = "Criar Tipo AD", notes = "Criar Tipo AD")
	public ResponseEntity<String> criarTipoAD(@RequestBody FrequenciaDTO dto) {
		dto.setCriarTipoAD(true);
		this.registrarDadosFrequencia(dto);
		return ResponseEntity.ok().body("Frequência TF enviada com sucesso");
	}
	
	@PostMapping("/limpar/todos/sub-fase-execucao/{idSubFaseExecucao}/atividade/{idAtividade}/disciplina/{idDisciplina}")
	@ApiOperation(value = "Limpar todas Frequencias", notes = "Limpar todas Frequencias")
	public ResponseEntity<String> limparTodos(@PathVariable(value = "idSubFaseExecucao") Long idSubFaseExecucao,
			@PathVariable(value = "idAtividade") Long idAtividade,
			@PathVariable(value = "idDisciplina") Long idDisciplina) {
	
		frequenciaServico.limparTodos(FiltroFrequencia.builder()
										.idAtividade(idAtividade)
										.idDisciplina(idDisciplina)
										.idSubFaseExecucao(idSubFaseExecucao)
										.build());
	
		return ResponseEntity.ok().body("Frequências redefinadas com sucesso");
	}
	
	@PostMapping("/limpar")
	@ApiOperation(value = "Limpar frequencia", notes = "Limpar frequencia")
	public ResponseEntity<String> limpar(@RequestBody FrequenciaDTO dto) {
		dto.setLimpar(true);
		this.registrarDadosFrequencia(dto);
		return ResponseEntity.ok().body("Solicitação de redefinição da frequência enviada com sucesso");
	}
	
	@PostMapping("/registra-reposicao")
	@ApiOperation(value = "Registrar reposicao da frequencia", notes = "Registrar reposicao da frequencia")
	public ResponseEntity<String> registrarReposicao(@RequestBody FrequenciaDTO dto) {
		dto.setRegistrarReposicao(true);
		this.registrarDadosFrequencia(dto);
		return ResponseEntity.ok().body("Registro de reposição da frequência enviado com sucesso");
	}
	
	@PostMapping("/anula-reposicao")
	@ApiOperation(value = "Anular reposicao da frequencia", notes = "Anular reposicao da frequencia")
	public ResponseEntity<String> anularReposicao(@RequestBody FrequenciaDTO dto) {
		dto.setAnularReposicao(true);
		this.registrarDadosFrequencia(dto);
		return ResponseEntity.ok().body("Anulação de reposição da frequência enviada com sucesso");
	}
	
	@PostMapping("/atualiza-frequencia-individual")
	@ApiOperation(value = "Alterar frequencia individual", notes = "Alterar frequencia individual")
	public ResponseEntity<String> alterarArrayFrequencia(@RequestBody FrequenciaDTO dto) {
		dto.setAtualizaIndividual(true);
		this.registrarDadosFrequencia(dto);
		return ResponseEntity.ok().body("Alteração da Frequência enviada com sucesso");
	}
	
	
	@GetMapping
	@ApiOperation(value = "Listar Frequencias", notes = "Listar Frequencias")
	public ResponseEntity<List<FrequenciaDTO>> listar(@RequestParam(value = "idSubFaseExecucao", required = false) Long idSubFaseExecucao,
			@RequestParam(value = "idAtividade", required = false) Long idAtividade,
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "anoTurma", required = false) Integer anoTurma,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		List<FrequenciaDTO> frequencias = frequenciaServico
				.listar(FiltroFrequencia.builder()
									.idAtividade(idAtividade)
									.idDisciplina(idDisciplina)
									.idSubFaseExecucao(idSubFaseExecucao)
									.anoTurma(anoTurma)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (frequencias == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(frequencias);
	}
	
	private void registrarDadosFrequencia(FrequenciaDTO dto) {
		if(dto.isCriarTipoAD()) {
			frequenciaServico.criarTipoAD(dto);
			return;
		}
		if(dto.isCriarTipoTP()) {
			frequenciaServico.criarTipoTP(dto);
			return;
		}
		if(dto.isCriarTipoTF()) {
			frequenciaServico.criarTipoTF(dto);
			return;
		}
		if(dto.isLimpar()) {
			frequenciaServico.limpar(dto);
			return;
		}
		if(dto.isRegistrarReposicao()) {
			frequenciaServico.registrarReposicao(dto);
			return;
		}
		if(dto.isAnularReposicao()) {
			frequenciaServico.anularReposicao(dto);
			return;
		}
		if(dto.isAtualizaIndividual()) {
			frequenciaServico.alterarArrayFrequencia(dto);
			return;
		}
		if(dto.getId() > 0 && dto.getIdAtividade() == null && dto.getIdCredito() == null) {
			frequenciaServico.excluir(dto.getId());
		}
	}

}
