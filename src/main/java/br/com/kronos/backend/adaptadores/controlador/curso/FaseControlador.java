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
import br.com.kronos.backend.aplicacao.calendario.api.FaseExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.FaseExecucaoServico;
import br.com.kronos.backend.aplicacao.curso.FiltroFase;
import br.com.kronos.backend.aplicacao.curso.api.FaseDTO;
import br.com.kronos.backend.aplicacao.curso.api.FaseServico;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cursos/periodos/fases")
@CrossOrigin
public class FaseControlador extends BaseControlador {

	@NonNull
	private FaseServico faseServico;
	
	@NonNull
	private FaseExecucaoServico faseExecucaoServico;

	@NonNull
	private DateUtil dateUtil;
	

	@PostMapping
	@ApiOperation(value = "Cadastrar Fase", notes = "Cadastrar Fase")
	public ResponseEntity<String> criar(@RequestBody FaseDTO dto) {
		faseServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro de fase enviada com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Fase", notes = "Alterar Fase")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody FaseDTO dto) {
		dto.setId(id);
		faseServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da fase enviada com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Fase", notes = "Excluir Fase")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		faseServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da fase enviada com sucesso");
	}
	
	@GetMapping("/combo")
	@ApiOperation(value = "Listar Fases Para Combo", notes = "Listar Fases Para Combo")
	public ResponseEntity<List<FaseDTO>> listarParaCombo(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idPeriodo", required = false) Long idPeriodo,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		List<FaseDTO> fases = faseServico
				.listarParaCombo(FiltroFase.builder()
									.id(id)
									.idPeriodo(idPeriodo)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (fases == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(fases);
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Fases", notes = "Listar Fases")
	public ResponseEntity<PaginacaoDTO<FaseDTO>> listar(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idPeriodo", required = false) Long idPeriodo,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<FaseDTO> fases = faseServico
				.listar(FiltroFase.builder()
									.id(id)
									.idPeriodo(idPeriodo)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (fases == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(fases);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Fase por id", notes = "Buscar Fase por id")
	public ResponseEntity<FaseDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		FaseDTO dto = faseServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping("/combo-fases-execucao")
	@ApiOperation(value = "Listar Fases Execucao Para Combo", notes = "Listar Fases Execucao Para Combo")
	public ResponseEntity<List<FaseExecucaoResumoDTO>> listarParaComboFaseExecucao(
			@RequestParam(value = "idPeriodoExecucao", required = false) Long idPeriodoExecucao) {

		List<FaseExecucaoResumoDTO> fases = faseExecucaoServico.listaParaCombo(idPeriodoExecucao);
		
		if (fases == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(fases);
	}
}
