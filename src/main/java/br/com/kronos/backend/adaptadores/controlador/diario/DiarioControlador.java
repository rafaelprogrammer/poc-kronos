package br.com.kronos.backend.adaptadores.controlador.diario;

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
import br.com.kronos.backend.aplicacao.diario.FiltroDiario;
import br.com.kronos.backend.aplicacao.diario.api.DiarioDTO;
import br.com.kronos.backend.aplicacao.diario.api.DiarioServico;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/diarios")
@CrossOrigin
public class DiarioControlador extends BaseControlador {

	@NonNull
	private DiarioServico diarioServico;

	@PostMapping
	@ApiOperation(value = "Cadastrar Diario", notes = "Cadastrar Diario")
	public ResponseEntity<String> criar(@RequestBody DiarioDTO diarioDTO) {
		diarioServico.criar(diarioDTO);
		return ResponseEntity.ok().body("Cadastro do diário enviado com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Diario", notes = "Alterar Diario")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody DiarioDTO diarioDTO) {
		diarioDTO.setId(id);
		diarioServico.alterar(diarioDTO);
		return ResponseEntity.ok().body("Alteração da diário enviada com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Diario", notes = "Excluir Diario")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		diarioServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da diário enviada com sucesso - " + id);
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Diarios", notes = "Listar Diarios")
	public ResponseEntity<PaginacaoDTO<DiarioDTO>> listar(@RequestParam(value = "idAtividade", required = false) Long idAtividade,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<DiarioDTO> diarios = diarioServico
				.listar(FiltroDiario.builder()
									.idAtividade(idAtividade)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (diarios == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(diarios);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Diario por id", notes = "Buscar Diario por id")
	public ResponseEntity<DiarioDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		DiarioDTO dto = diarioServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	

}
