package br.com.kronos.backend.adaptadores.controlador.turma;

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
import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import br.com.kronos.backend.aplicacao.curso.api.EstruturaDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.matricula.FiltroTurma;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaDiarioFrequenciaDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaResumoDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaServico;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/turmas")
@CrossOrigin
public class TurmaControlador extends BaseControlador {
	
	@NonNull
	private TurmaServico turmaServico;
	
	@NonNull
	private ServicoAutenticacao servicoAutenticacao;
	
	@PostMapping
	@ApiOperation(value = "Cadastrar Turma", notes = "Cadastrar Turma")
	public ResponseEntity<String> criar(@RequestBody TurmaDTO dto) {
		turmaServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro da turma enviada com sucesso");
	}
	
	@PostMapping(value="/geracao")
	@ApiOperation(value = "Gerar Turmas", notes = "Gerar Turmas")
	public ResponseEntity<String> gerar(@RequestBody TurmaDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		turmaServico.gerar(dto);
		return ResponseEntity.ok().body("Geração das turmas solicitada com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Turma", notes = "Alterar Turma")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody TurmaDTO dto) {
		dto.setId(id);
		turmaServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da Turma enviada com sucesso");
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Turma", notes = "Excluir Turma")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		turmaServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da turma enviada com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Turmas", notes = "Listar Turmas")
	public ResponseEntity<PaginacaoDTO<TurmaDTO>> listar(
			@RequestParam(value = "idPeriodoExecucao", required = false) Long idPeriodoExecucao,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<TurmaDTO> turmas = turmaServico
				.listar(FiltroTurma.builder()
									.idPeriodoExecucao(idPeriodoExecucao)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (turmas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(turmas);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Turma por id", notes = "Buscar Turma por id")
	public ResponseEntity<TurmaDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		TurmaDTO dto = turmaServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value="/combo-contratos")
	@ApiOperation(value = "Listar Turmas para combo", notes = "Listar Turmas para combo")
	public ResponseEntity<List<TurmaResumoDTO>> listarTurmasParaContrato(
			@RequestParam(value = "idPeriodoExecucao", required = true) Long idPeriodoExecucao)
			throws ExcecaoDeNegocio {

		List<TurmaResumoDTO> turmas = turmaServico
				.listarParaTurmaContratoCombo(idPeriodoExecucao);
		
		if (turmas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(turmas);
	}
	
	@GetMapping(value="/combo-anos-horario")
	@ApiOperation(value = "Listar Anos da turma para horario", notes = "Listar Anos da turma para horario")
	public ResponseEntity<List<Integer>> listarAnosParaHorario()
			throws ExcecaoDeNegocio {

		List<Integer> anos = turmaServico.listarAnosParaHorario();
		
		if (anos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(anos);
	}
	
	@GetMapping(value="/combo-turma-horario")
	@ApiOperation(value = "Listar Turmas para horario", notes = "Listar Turmas para horario")
	public ResponseEntity<List<TurmaResumoDTO>> listarAnosParaHorario(
			@RequestParam(value = "idPeriodoExecucao", required = true) Long idPeriodoExecucao) {

		List<TurmaResumoDTO> turmas = turmaServico.listarParaHorario(idPeriodoExecucao);
		
		if (turmas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(turmas);
	}
	
	@GetMapping("/combo-turmas-diario-frequencia")
	@ApiOperation(value = "Listar Turmas para diario/frequencia para combo", notes = "Listar Turmas para diario/frequencia para combo")
	public ResponseEntity<List<TurmaDiarioFrequenciaDTO>> listarParaDiarioFrequencia() {

		UsuarioDTO dto = servicoAutenticacao.buscarUsuarioLogado();
		List<TurmaDiarioFrequenciaDTO> turmas = turmaServico.listarParaDiarioFrequencia(dto.getIdInstituicao(), dto.getIdPessoa());
		if (turmas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(turmas);
	}
	
	@GetMapping(value = "/visualiza-estrutura/curso/{idCurso}/ano/{ano}")
	@ApiOperation(value = "Visualizar Estrutura do Curso", notes = "Visualizar Estrutura do Curso")
	public ResponseEntity<List<EstruturaDTO>> visualizarEstrutura(@PathVariable(value = "idCurso") Long idCurso, 
			@PathVariable(value = "ano") Integer ano) {

		List<EstruturaDTO> dtos = turmaServico.visualizarEstrutura(FiltroTurma.builder().idCurso(idCurso).ano(ano).build());
		if (dtos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dtos);
	}
	
	@GetMapping(value="/combo-modulos-professores/periodo-execucao/{idPeriodoExecucao}")
	@ApiOperation(value = "Listar Turmas para combo para modulos utilizados por professores", notes = "Listar Turmas para combo para modulos utilizados por professores")
	public ResponseEntity<List<TurmaResumoDTO>> listarParaModulosDosProfessores(@PathVariable(value = "idPeriodoExecucao") Long idPeriodoExecucao) {

		List<TurmaResumoDTO> turmas = turmaServico.listarParaModulosDosProfessores(idPeriodoExecucao, servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		
		if (turmas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(turmas);
	}
	
	@GetMapping(value="/combo-anos/curso/{idCurso}")
	@ApiOperation(value = "Listar Anos da turma por curso", notes = "Listar Anos da turma por curso")
	public ResponseEntity<List<Integer>> listarAnosFiltroIdCurso(@PathVariable(value = "idCurso") Long idCurso)
			throws ExcecaoDeNegocio {

		List<Integer> anos = turmaServico.listarAnosFiltroIdCurso(idCurso);
		
		if (anos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(anos);
	}
	
	@GetMapping(value="/combo-ocorrencia/curso/{idCurso}/ano/{ano}")
	@ApiOperation(value = "Listar Turmas para a funcionalidade de ocorrencias para selecionar aluno", notes = "Listar Turmas para a funcionalidade de ocorrencias para selecionar aluno")
	public ResponseEntity<List<TurmaResumoDTO>> listarPorIdCursoEAno(@PathVariable(value = "idCurso") Long idCurso,
			@PathVariable(value = "ano") Integer ano) {

		List<TurmaResumoDTO> turmas = turmaServico.listarPorIdCursoEAno(idCurso, ano);
		
		if (turmas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(turmas);
	}
	
	@GetMapping(value="/combo-ocorrencia/matricula/{idMatricula}")
	@ApiOperation(value = "Listar Turmas para a funcionalidade de ocorrencias ", notes = "Listar Turmas para a funcionalidade de ocorrencia")
	public ResponseEntity<List<TurmaResumoDTO>> listarParaOcorrenciaCombo(@PathVariable(value = "idMatricula") Long idMatricula) {

		List<TurmaResumoDTO> turmas = turmaServico.listarParaOcorrenciaCombo(idMatricula);
		
		if (turmas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(turmas);
	}

}
