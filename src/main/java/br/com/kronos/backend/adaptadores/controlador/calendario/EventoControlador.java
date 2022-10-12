package br.com.kronos.backend.adaptadores.controlador.calendario;

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
import br.com.kronos.backend.aplicacao.calendario.FiltroEvento;
import br.com.kronos.backend.aplicacao.calendario.api.CategoriaEventoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.EventoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.EventoServico;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/eventos")
@CrossOrigin
public class EventoControlador extends BaseControlador {

	@NonNull
	private EventoServico eventoServico;

	@PostMapping
	@ApiOperation(value = "Cadastrar Evento", notes = "Cadastrar Evento")
	public ResponseEntity<String> criar(@RequestBody EventoDTO dto) {
		eventoServico.validarIntervaloCalendario(dto);
		eventoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do evento enviado com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Evento", notes = "Alterar Evento")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody EventoDTO dto) {
		eventoServico.validarIntervaloCalendario(dto);
		dto.setId(id);
		eventoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do evento enviado com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Evento", notes = "Excluir Evento")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		eventoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da evento enviado com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Eventos", notes = "Listar Eventos")
	public ResponseEntity<List<EventoDTO>> listar(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idCalendario", required = false) Long idCalendario) {

		List<EventoDTO> eventos = eventoServico
				.listar(FiltroEvento.builder()
									.id(id)
									.idCalendario(idCalendario)
									.build());
		
		if (eventos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(eventos);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Evento por id", notes = "Buscar Evento por id")
	public ResponseEntity<EventoDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		EventoDTO dto = eventoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/categorias")
	@ApiOperation(value = "Listar Categorias", notes = "Listar Categorias")
	public ResponseEntity<List<CategoriaEventoDTO>> listarCategoria() {

		List<CategoriaEventoDTO> categorias = eventoServico.listarCatetoria();
		
		if (categorias == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(categorias);
	}
	
}
