package br.com.kronos.backend.adaptadores.controlador.atividade;

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
import br.com.kronos.backend.aplicacao.diario.FiltroAtividade;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeDTO;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeDisciplinaCompetenciaDTO;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeDisciplinaDireitoDTO;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeDisciplinaHabilidadeDTO;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeDisciplinaObjetivoDTO;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeServico;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/atividades")
@CrossOrigin
public class AtividadeControlador extends BaseControlador {

	@NonNull
	private AtividadeServico atividadeServico;
	
	@PostMapping
	@ApiOperation(value = "Gerar Atividades", notes = "Gerar Atividades")
	public ResponseEntity<String> criar(@RequestBody AtividadeDTO dto) {
		atividadeServico.criar(dto);
		return ResponseEntity.ok().body("Geração das Atividades solicitadas com sucesso");
	}

	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Atividade", notes = "Alterar Atividade")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody AtividadeDTO dto) {
		atividadeServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração atividade enviada com sucesso");
	}
	
	@PutMapping("/confirma/{id}")
	@ApiOperation(value = "Confirmar Atividade", notes = "Confirmar Atividade")
	public ResponseEntity<String> confirmar(@PathVariable(value = "id") Long id, @RequestBody AtividadeDTO dto) {
		atividadeServico.confirmar(dto);
		return ResponseEntity.ok().body("Confirmação da atividade enviada com sucesso");
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Atividade", notes = "Excluir Atividade")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		atividadeServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da Atividade enviada com sucesso");
	}
	
	@GetMapping("/{id}/combo-hora-inicio")
	@ApiOperation(value = "Listar Hora Inicial da Atividade", notes = "Listar Hora Inicial da Atividade")
	public ResponseEntity<List<String>> listarHorasIniciaisAtividades(@PathVariable(value = "id", required = false) Long id) {

		List<String> horas = atividadeServico.listarHorasIniciaisAtividades(id);
		
		if (horas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(horas);
	}
	
	@GetMapping("/combo-datas-previstas")
	@ApiOperation(value = "Listar Datas Previstas das Atividades para combo", notes = "Listar Datas Previstas das Atividades para combo")
	public ResponseEntity<List<AtividadeDTO>> listarDatasAtividades(
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idTurma", required = false) Long idTurma) {

		List<AtividadeDTO> atividades = atividadeServico
				.listarDatasAtividades(FiltroAtividade.builder()
									.idDisciplina(idDisciplina)
									.idTurma(idTurma)
									.build());
		
		if (atividades == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(atividades);
	}
	
	@DeleteMapping("/exclui-dias-nao-letivos/turma/{idTurma}/disciplina/{idDisciplina}/periodo-execucao/{idPeriodoExecucao}")
	@ApiOperation(value = "Excluir Atividades Dias Nao Letivos", notes = "Excluir Atividades Dias Nao Letivos")
	public ResponseEntity<?> excluirDiasNaoLetivos(
			@PathVariable(value = "idDisciplina", required = true) Long idDisciplina,
			@PathVariable(value = "idTurma", required = true) Long idTurma,
			@PathVariable(value = "idPeriodoExecucao", required = true) Long idPeriodoExecucao) {
		
		atividadeServico.excluirDiasNaoLetivos(FiltroAtividade.builder()
				.idDisciplina(idDisciplina)
				.idPeriodoExecucao(idPeriodoExecucao)
				.idTurma(idTurma).build());
		
		return ResponseEntity.ok().body("Exclusão dias não letivos enviado com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Atividades", notes = "Listar Atividades")
	public ResponseEntity<PaginacaoDTO<AtividadeDTO>> listar(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idTurma", required = false) Long idTurma,
			@RequestParam(value = "idSubFaseExecucao", required = false) Long idSubFaseExecucao,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<AtividadeDTO> atividades = atividadeServico
				.listar(FiltroAtividade.builder()
									.id(id)
									.idDisciplina(idDisciplina)
									.idTurma(idTurma)
									.idSubFaseExecucao(idSubFaseExecucao)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (atividades == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(atividades);
	}
	
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Atividade por id", notes = "Buscar Atividade por id")
	public ResponseEntity<AtividadeDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		AtividadeDTO dto = atividadeServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	// ### ATIVIDADES DISCIPLINAS DIREITOS
	@PostMapping("/disciplinas-direitos")
	@ApiOperation(value = "Criar Atividade Disciplina Direito", notes = "Criar Atividade Disciplina Direito")
	public ResponseEntity<String> criarAtividadeDisciplinaDireito(@RequestBody AtividadeDisciplinaDireitoDTO dto) {
		atividadeServico.criarAtividadeDisciplinaDireito(dto);
		return ResponseEntity.ok().body("Criação da Atividade Disciplina Direito enviada com sucesso");
	}
	
	@DeleteMapping("/disciplinas-direitos/{id}/disciplina-direito/{idDisciplinaDireito}")
	@ApiOperation(value = "Excluir Atividade Disciplina Direito", notes = "Excluir Atividade")
	public ResponseEntity<?> excluirAtividadeDisciplinaDireito(@PathVariable(value = "id") Long id, @PathVariable(value = "idDisciplinaDireito") Long idDisciplinaDireito) {
		atividadeServico.excluirAtividadeDisciplinaDireito(idDisciplinaDireito, id);
		return ResponseEntity.ok().body("Exclusão da Atividade Disciplina Direito enviada com sucesso");
	}
	
	@GetMapping("/disciplinas-direitos")
	@ApiOperation(value = "Listar Atividades Disciplinas Direitos", notes = "Listar Atividades Disciplinas Direitos")
	public ResponseEntity<List<AtividadeDisciplinaDireitoDTO>> listarAtividadesDisciplinasDireitos(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		List<AtividadeDisciplinaDireitoDTO> atividades = atividadeServico
				.listarAtividadesDisciplinasDireitos(FiltroAtividade.builder()
									.id(id)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (atividades == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(atividades);
	}
	// ### FIM ATIVIDADES DISCIPLINAS DIREITOS
	
	// ### ATIVIDADES DISCIPLINAS OBJETIVOS
	@PostMapping("/disciplinas-objetivos")
	@ApiOperation(value = "Criar Atividade Disciplina Objetivo", notes = "Criar Atividade Disciplina Objetivo")
	public ResponseEntity<String> criarAtividadeDisciplinaObjetivo(@RequestBody AtividadeDisciplinaObjetivoDTO dto) {
		atividadeServico.criarAtividadeDisciplinaObjetivo(dto);
		return ResponseEntity.ok().body("Criação da Atividade Disciplina Objetivo enviada com sucesso");
	}
	
	@DeleteMapping("/disciplinas-objetivos/{id}/disciplina-objetivo/{idDisciplinaObjetivo}")
	@ApiOperation(value = "Excluir Atividade Disciplina Direito", notes = "Excluir Atividade")
	public ResponseEntity<?> excluirAtividadeDisciplinaObjetivo(@PathVariable(value = "id") Long id, @PathVariable(value = "idDisciplinaObjetivo") Long idDisciplinaObjetivo) {
		atividadeServico.excluirAtividadeDisciplinaObjetivo(idDisciplinaObjetivo, id);
		return ResponseEntity.ok().body("Exclusão da Atividade Disciplina Objetivo enviada com sucesso");
	}
	
	@GetMapping("/disciplinas-objetivos")
	@ApiOperation(value = "Listar Atividades Disciplinas Objetivos", notes = "Listar Atividades Disciplinas Objetivos")
	public ResponseEntity<List<AtividadeDisciplinaObjetivoDTO>> listarAtividadesDisciplinasObjetivos(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		List<AtividadeDisciplinaObjetivoDTO> atividades = atividadeServico
				.listarAtividadesDisciplinasObjetivos(FiltroAtividade.builder()
									.id(id)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (atividades == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(atividades);
	}
	// ### FIM ATIVIDADES DISCIPLINAS OBJETIVOS
	
	// ### ATIVIDADES DISCIPLINAS COMPETENCIAS
	@PostMapping("/disciplinas-competencias")
	@ApiOperation(value = "Criar Atividade Disciplina Competencia", notes = "Criar Atividade Disciplina Competencia")
	public ResponseEntity<String> criarAtividadeDisciplinaCompetencia(@RequestBody AtividadeDisciplinaCompetenciaDTO dto) {
		atividadeServico.criarAtividadeDisciplinaCompetencia(dto);
		return ResponseEntity.ok().body("Criação da Atividade Disciplina Competência enviada com sucesso");
	}
	
	@DeleteMapping("/disciplinas-competencias/{id}/disciplina-competencia/{idDisciplinaCompetencia}")
	@ApiOperation(value = "Excluir Atividade Disciplina Competencia", notes = "Excluir Atividade Disciplina Competencia")
	public ResponseEntity<?> excluirAtividadeDisciplinaCompetencia(@PathVariable(value = "id") Long id, @PathVariable(value = "idDisciplinaCompetencia") Long idDisciplinaCompetencia) {
		atividadeServico.excluirAtividadeDisciplinaCompetencia(idDisciplinaCompetencia, id);
		return ResponseEntity.ok().body("Exclusão da Atividade Disciplina Competência enviada com sucesso");
	}
	
	@GetMapping("/disciplinas-competencias")
	@ApiOperation(value = "Listar Atividades Disciplinas Competencias", notes = "Listar Atividades Disciplinas Competencias")
	public ResponseEntity<List<AtividadeDisciplinaCompetenciaDTO>> listarAtividadesDisciplinasCompetencias(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		List<AtividadeDisciplinaCompetenciaDTO> atividades = atividadeServico
				.listarAtividadesDisciplinasCompetencias(FiltroAtividade.builder()
									.id(id)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (atividades == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(atividades);
	}
	// ### FIM ATIVIDADES DISCIPLINAS COMPETENCIAS
	
	// ### ATIVIDADES DISCIPLINAS HABILIDADES
	@PostMapping("/disciplinas-habilidades")
	@ApiOperation(value = "Criar Atividade Disciplina Habilidade", notes = "Criar Atividade Disciplina Habilidade")
	public ResponseEntity<String> criarAtividadeDisciplinaHabilidade(@RequestBody AtividadeDisciplinaHabilidadeDTO dto) {
		atividadeServico.criarAtividadeDisciplinaHabilidade(dto);
		return ResponseEntity.ok().body("Criação da Atividade Disciplina Habilidade enviada com sucesso");
	}
	
	@DeleteMapping("/disciplinas-habilidades/{id}/disciplina-habilidade/{idDisciplinaHabilidade}")
	@ApiOperation(value = "Excluir Atividade Disciplina Habilidade", notes = "Excluir Atividade Disciplina Habilidade")
	public ResponseEntity<?> excluirAtividadeDisciplinaHabilidade(@PathVariable(value = "id") Long id, @PathVariable(value = "idDisciplinaHabilidade") Long idDisciplinaHabilidade) {
		atividadeServico.excluirAtividadeDisciplinaHabilidade(idDisciplinaHabilidade, id);
		return ResponseEntity.ok().body("Exclusão da Atividade Disciplina Habilidade enviada com sucesso");
	}
	
	@GetMapping("/disciplinas-habilidades")
	@ApiOperation(value = "Listar Atividades Disciplinas Habilidades", notes = "Listar Atividades Disciplinas Habilidades")
	public ResponseEntity<List<AtividadeDisciplinaHabilidadeDTO>> listarAtividadesDisciplinasHabilidades(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		List<AtividadeDisciplinaHabilidadeDTO> atividades = atividadeServico
				.listarAtividadesDisciplinasHabilidades(FiltroAtividade.builder()
									.id(id)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (atividades == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(atividades);
	}
	
	@GetMapping("/{id}/avaliacao-desempenho")
	@ApiOperation(value = "Verificar se possui avaliacao de desempenho", notes = "Verificar se possui avaliacao de desempenho")
	public ResponseEntity<Boolean> verificarSePossuiAvaliacaoDeDesempenho(@PathVariable(value = "id", required = false) Long id) {

		return ResponseEntity.ok().body(atividadeServico.verificarSePodeAddAvaliacaoDeDesempenho(FiltroAtividade.builder()
				.id(id)
				.build()));
	}
	// ### FIM ATIVIDADES DISCIPLINAS HABILIDADES
	
}
