package br.com.kronos.backend.adaptadores.controlador.instituicao;

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
import br.com.kronos.backend.aplicacao.instituicao.FiltroEscala;
import br.com.kronos.backend.aplicacao.instituicao.api.EscalaDTO;
import br.com.kronos.backend.aplicacao.instituicao.api.EscalaServico;
import br.com.kronos.backend.aplicacao.instituicao.api.MensaoLimiteDTO;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/instituicoes/escalas")
@CrossOrigin
public class EscalaControlador extends BaseControlador {

	@NonNull
	private EscalaServico escalaServico;

	@NonNull
	private DateUtil dateUtil;
	
	@PostMapping
	@ApiOperation(value = "Cadastrar Escala", notes = "Cadastrar Escala")
	public ResponseEntity<String> criar(@RequestBody EscalaDTO dto) {
		escalaServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro da escala enviada com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Escala", notes = "Alterar Escala")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody EscalaDTO dto) {
		dto.setId(id);
		escalaServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da escala enviada com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Escala", notes = "Excluir Escala")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		escalaServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da escala enviada com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Escalas", notes = "Listar Escalas")
	public ResponseEntity<PaginacaoDTO<EscalaDTO>> listar(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idCurso", required = false) Long idCurso,
			@RequestParam(value = "idInstituicao", required = false) Long idInstituicao,
			@RequestParam(value = "idTipoAbrangencia", required = false) Long idTipoAbrangencia,
			@RequestParam(value = "idTipoAvaliacao", required = false) Long idTipoAvaliacao,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<EscalaDTO> escalas = escalaServico
				.listar(FiltroEscala.builder()
									.id(id)
									.idCurso(idCurso)
									.idInstituicao(idInstituicao)
									.idTipoAbrangencia(idTipoAbrangencia)
									.idTipoAvaliacao(idTipoAvaliacao)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (escalas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(escalas);
	}
	
	@GetMapping(value="/mensoes-limites")
	@ApiOperation(value = "Listar Mensoes e Limites", notes = "Listar Mensoes e Limites")
	public ResponseEntity<List<MensaoLimiteDTO>> listarMensoesLimites(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idCurso", required = false) Long idCurso) {

		List<MensaoLimiteDTO> mensoesELimites = escalaServico
				.listarMensaoELimite(FiltroEscala.builder()
									.id(id)
									.idCurso(idCurso)
									.build());
		
		if (mensoesELimites == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(mensoesELimites);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Escala por id", notes = "Buscar Escala por id")
	public ResponseEntity<EscalaDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		EscalaDTO dto = escalaServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}

}
