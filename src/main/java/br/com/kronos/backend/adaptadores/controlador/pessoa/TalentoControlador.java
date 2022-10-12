package br.com.kronos.backend.adaptadores.controlador.pessoa;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.kronos.backend.adaptadores.controlador.BaseControlador;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroTalento;
import br.com.kronos.backend.aplicacao.pessoa.api.TalentoDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.TalentoServico;
import br.com.kronos.backend.aplicacao.pessoa.api.TipoTalentoDTO;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/talentos")
@CrossOrigin
public class TalentoControlador extends BaseControlador {

	@NonNull
	private TalentoServico talentoServico;

	@PostMapping
	@ApiOperation(value = "Cadastrar Talento", notes = "Cadastrar Talento")
	public ResponseEntity<String> criar(@RequestBody List<TalentoDTO> dtos) throws ExcecaoDeNegocio {
		talentoServico.criar(dtos);
		return ResponseEntity.ok().body("Cadastro de talento enviado com sucesso");
	}
	
	@DeleteMapping("/{id}/idtipo/{idTipo}")
	@ApiOperation(value = "Excluir Talento", notes = "Excluir Talento")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long idPessoa, @PathVariable(value = "idTipo") int idTipo) throws ExcecaoDeNegocio {
		talentoServico.excluir(idPessoa, idTipo);
		return ResponseEntity.ok().body("Exclusão do talento enviado com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Talentos", notes = "Listar Talentos")
	public ResponseEntity<PaginacaoDTO<TalentoDTO>> listar(
			@RequestParam(value = "idPessoa", required = false) Long idPessoa,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<TalentoDTO> talentos = talentoServico
				.listar(FiltroTalento.builder()
									.idPessoa(idPessoa)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (talentos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(talentos);
	}
	
	@GetMapping("/pessoa/{idPessoa}")
	@ApiOperation(value = "Buscar Talentos da Pessoa", notes = "Buscar Talentos da Pessoa")
	public ResponseEntity<List<TalentoDTO>> buscarTalentosDaPessoa(
			@PathVariable(value = "idPessoa", required = false) Long idPessoa) {

		List<TalentoDTO> talentos = talentoServico
				.buscarTalentosDaPessoa(idPessoa);
		
		if (talentos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(talentos);
	}
	
	@GetMapping(value="/tipos")
	@ApiOperation(value = "Listar Tipos de Talentos", notes = "Listar Tipos de Talentos")
	public ResponseEntity<List<TipoTalentoDTO>> listarTipoFiliacoes()
			throws ExcecaoDeNegocio {
		return ResponseEntity.ok().body(TipoTalentoDTO.tipos());
	}
	
}
