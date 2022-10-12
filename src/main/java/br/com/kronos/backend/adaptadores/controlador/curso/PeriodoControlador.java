package br.com.kronos.backend.adaptadores.controlador.curso;

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
import br.com.kronos.backend.aplicacao.curso.FiltroPeriodo;
import br.com.kronos.backend.aplicacao.curso.api.PeriodoDTO;
import br.com.kronos.backend.aplicacao.curso.api.PeriodoResumoDTO;
import br.com.kronos.backend.aplicacao.curso.api.PeriodoServico;
import br.com.kronos.backend.aplicacao.curso.api.TipoPeriodoDTO;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cursos/periodos")
@CrossOrigin
public class PeriodoControlador extends BaseControlador {

	@NonNull
	private PeriodoServico periodoServico;

	@NonNull
	private DateUtil dateUtil;
	

	@PostMapping
	@ApiOperation(value = "Cadastrar Periodo", notes = "Cadastrar Periodo")
	public ResponseEntity<String> criar(@RequestBody PeriodoDTO dto) {
		periodoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do período enviado com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Periodo", notes = "Alterar Periodo")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody PeriodoDTO dto) {
		dto.setId(id);
		periodoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do período enviado com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Periodo", notes = "Periodo")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		periodoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do periodo enviado com sucesso");
	}
	
	@GetMapping("/combo-periodo-contrato")
	@ApiOperation(value = "Listar Periodos Para Combo Contrato", notes = "Listar Periodos Para Contrato")
	public ResponseEntity<List<PeriodoResumoDTO>> listarParaComboContrato(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idCurso", required = false) Long idCurso,
			@RequestParam(value = "anoPeriodoExecucao", required = false) Integer anoPeriodoExecucao,
			@RequestParam(value = "idMatricula", required = false) Long idMatricula) {

		List<PeriodoResumoDTO> periodos = periodoServico
				.listarParaComboContrato(FiltroPeriodo.builder()
									.id(id)
									.idCurso(idCurso)
									.idMatricula(idMatricula)
									.anoPeriodoExecucao(anoPeriodoExecucao)
									.build());
		
		if (periodos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(periodos);
	}
	
	@GetMapping("/combo-periodo-pendencia-contrato")
	@ApiOperation(value = "Listar Periodos Pendentes Para Combo Contrato", notes = "Listar Periodos Pendentes Para Contrato")
	public ResponseEntity<List<PeriodoResumoDTO>> listarParaComboPeriodoPendenciaContrato(
			@RequestParam(value = "idCurso", required = true) Long idCurso,
			@RequestParam(value = "anoPeriodoExecucao", required = true) Integer anoPeriodoExecucao,
			@RequestParam(value = "idMatricula", required = true) Long idMatricula,
			@RequestParam(value = "numero", required = true) Integer numero) {

		List<PeriodoResumoDTO> periodos = periodoServico
				.listarParaComboPeriodoPendenteContrato(FiltroPeriodo.builder()
									.idCurso(idCurso)
									.idMatricula(idMatricula)
									.anoPeriodoExecucao(anoPeriodoExecucao)
									.numero(numero)
									.build());
		
		if (periodos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(periodos);
	}
	
	@GetMapping("/combo")
	@ApiOperation(value = "Listar Periodos Para Combo", notes = "Listar Periodos Para Combo")
	public ResponseEntity<List<PeriodoDTO>> listarParaCombo(
			@RequestParam(value = "idCurso", required = false) Long idCurso,
			@RequestParam(value = "idMatricula", required = false) Long idMatricula,
			@RequestParam(value = "anoPeriodoExecucao", required = false) Integer anoPeriodoExecucao) {

		List<PeriodoDTO> periodos = periodoServico
				.listarParaCombo(FiltroPeriodo.builder()
									.idCurso(idCurso)
									.idMatricula(idMatricula)
									.anoPeriodoExecucao(anoPeriodoExecucao)
									.build());
		
		if (periodos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(periodos);
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Periodos", notes = "Listar Periodos")
	public ResponseEntity<PaginacaoDTO<PeriodoDTO>> listar(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idCurso", required = false) Long idCurso,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<PeriodoDTO> periodos = periodoServico
				.listar(FiltroPeriodo.builder()
									.id(id)
									.idCurso(idCurso)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (periodos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(periodos);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Periodo por id", notes = "Buscar Periodo por id")
	public ResponseEntity<PeriodoDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		PeriodoDTO dto = periodoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/tipos")
	@ApiOperation(value = "Listar Tipos de Periodos", notes = "Listar Tipos de Periodos")
	public ResponseEntity<List<TipoPeriodoDTO>> listarTipoRegimeLetivo() {
		return ResponseEntity.ok().body(TipoPeriodoDTO.tipos());
	}
	
	@GetMapping("/combo-periodo-horario")
	@ApiOperation(value = "Listar Periodos para Horario", notes = "Listar Periodos para Horario")
	public ResponseEntity<List<PeriodoResumoDTO>> listarParaHorario(
			@RequestParam(value = "idCurso", required = true) Long idCurso,
			@RequestParam(value = "anoPeriodoExecucao", required = true) Integer anoPeriodoExecucao) {

		List<PeriodoResumoDTO> periodos = periodoServico
				.listarParaHorario(FiltroPeriodo.builder()
									.idCurso(idCurso)
									.anoPeriodoExecucao(anoPeriodoExecucao)
									.build());
		
		if (periodos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(periodos);
	}
	
}
