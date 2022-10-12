package br.com.kronos.backend.adaptadores.controlador.horario;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.kronos.backend.adaptadores.controlador.BaseControlador;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.horario.FiltroSubstituto;
import br.com.kronos.backend.aplicacao.horario.api.DadosExcluiSubstitutoDTO;
import br.com.kronos.backend.aplicacao.horario.api.SubstitutoDTO;
import br.com.kronos.backend.aplicacao.horario.api.SubstitutoResumoDTO;
import br.com.kronos.backend.aplicacao.horario.api.SubstitutoServico;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/horarios/substitutos")
@CrossOrigin
public class SubstitutoControlador extends BaseControlador {

	@NonNull
	private SubstitutoServico substitutoServico;
	
	@PostMapping
	@ApiOperation(value = "Cadastrar Substituto", notes = "Cadastrar Substituto")
	public ResponseEntity<String> criar(@RequestBody SubstitutoDTO dto) {
		substitutoServico.validarIntervaloHorario(dto);
		substitutoServico.validarDuplicidade(dto);
		substitutoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do Substituto enviado com sucesso");
	}
	
	@PostMapping(value="/exclui")
	@ApiOperation(value = "Excluir Substituto", notes = "Excluir Substituto")
	public ResponseEntity<?> excluir(@RequestBody DadosExcluiSubstitutoDTO dto) {
		substitutoServico.excluir(dto);
		return ResponseEntity.ok().body("Exclusão do substituto enviado com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Substitutos", notes = "Listar Substitutos")
	public ResponseEntity<PaginacaoDTO<SubstitutoDTO>> listar(@RequestParam(value = "idHorario", required = false) Long idHorario,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<SubstitutoDTO> substitutos = substitutoServico
				.listar(FiltroSubstituto.builder()
									.idHorario(idHorario)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (substitutos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(substitutos);
	}
	
	
	
	@GetMapping(value="/combo-professores-horario")
	@ApiOperation(value = "Listar Professores", notes = "Listar Professores")
	public ResponseEntity<List<SubstitutoResumoDTO>> listarComboProfessoresHorario(@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idFuncionarioDoHorario", required = false) Long idFuncionarioDoHorario) {
		
		List<SubstitutoResumoDTO> professores = substitutoServico.listarParaProfessoresHorario(idDisciplina, idFuncionarioDoHorario);
		
		if (professores == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(professores);
	}
	
	
}
