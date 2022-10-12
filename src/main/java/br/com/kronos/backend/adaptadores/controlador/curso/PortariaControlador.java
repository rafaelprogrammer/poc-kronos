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
import br.com.kronos.backend.aplicacao.curso.FiltroPortaria;
import br.com.kronos.backend.aplicacao.curso.api.PortariaDTO;
import br.com.kronos.backend.aplicacao.curso.api.PortariaServico;
import br.com.kronos.backend.aplicacao.curso.api.TipoPortariaDTO;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cursos/portarias")
@CrossOrigin
public class PortariaControlador extends BaseControlador {

	@NonNull
	private PortariaServico portariaServico;

	@NonNull
	private DateUtil dateUtil;
	
	@PostMapping
	@ApiOperation(value = "Cadastrar Portaria", notes = "Cadastrar Portaria")
	public ResponseEntity<String> criar(@RequestBody PortariaDTO dto) {
		portariaServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro da Portaria enviado com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Portaria", notes = "Alterar Portaria")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody PortariaDTO dto) {
		dto.setId(id);
		portariaServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da portaria enviada com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Portaria", notes = "Excluir Portaria")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		portariaServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da portaria enviada com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Portarias", notes = "Listar Portarias")
	public ResponseEntity<PaginacaoDTO<PortariaDTO>> listar(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idCurso", required = false) Long idCurso,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<PortariaDTO> cursos = portariaServico
				.listar(FiltroPortaria.builder()
									.id(id)
									.idCurso(idCurso)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (cursos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(cursos);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Portaria por id", notes = "Buscar Portaria por id")
	public ResponseEntity<PortariaDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		PortariaDTO dto = portariaServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/tipos")
	@ApiOperation(value = "Listar Tipos de Portarias", notes = "Listar Tipos de Portarias")
	public ResponseEntity<List<TipoPortariaDTO>> tipos() {
		return ResponseEntity.ok().body(TipoPortariaDTO.tipos());
	}

}
