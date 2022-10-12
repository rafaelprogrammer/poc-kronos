package br.com.kronos.backend.adaptadores.controlador.horario;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import br.com.kronos.backend.aplicacao.horario.FiltroHorario;
import br.com.kronos.backend.aplicacao.horario.api.DadosVinculacaoHorariosHorasAtividadesDTO;
import br.com.kronos.backend.aplicacao.horario.api.HorarioDTO;
import br.com.kronos.backend.aplicacao.horario.api.HorarioServico;
import br.com.kronos.backend.aplicacao.horario.api.HorariosDiaDaSemanaDTO;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/horarios")
@CrossOrigin
public class HorarioControlador extends BaseControlador {

	@NonNull
	private HorarioServico horarioServico;
	
	@PostMapping
	@ApiOperation(value = "Cadastrar Horário", notes = "Cadastrar Horário")
	public ResponseEntity<String> criar(@RequestBody DadosVinculacaoHorariosHorasAtividadesDTO dto) {
		horarioServico.vincularHorarioHoraAtividade(dto);
		return ResponseEntity.ok().body("Horários enviados com sucesso");
	}
	
	@DeleteMapping("/{id}/hora-atividade/{idHoraAtividade}")
	@ApiOperation(value = "Excluir Horário", notes = "Excluir Horário")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id,
			@PathVariable(value = "idHoraAtividade") Long idHoraAtividade) {
		horarioServico.validarVinculoComAtividade(id);
		horarioServico.excluir(id, idHoraAtividade);
		return ResponseEntity.ok().body("Exclusão do horário enviado com sucesso");
	}
	
	
	@GetMapping
	@ApiOperation(value = "Listar Horarios", notes = "Listar Horarios")
	public ResponseEntity<PaginacaoDTO<HorarioDTO>> listar(@RequestParam(value = "idTurma", required = false) Long idTurma,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<HorarioDTO> horarios = horarioServico
				.listar(FiltroHorario.builder()
									.idTurma(idTurma)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (horarios == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(horarios);
	}
	
	@GetMapping(value="/horarios-dias-semana")
	@ApiOperation(value = "Listar Horarios", notes = "Listar Horarios")
	public ResponseEntity<HorariosDiaDaSemanaDTO> listarHorasAtividadesDiaDaSemana(
			@RequestParam(value = "idFuncionario", required = false) Long idFuncionario,
			@RequestParam(value = "idTurma", required = false) Long idTurma,
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idTipoDiaSemana", required = false) Integer idTipoDiaSemana,
			@RequestParam(value = "idTipoMatrizHorario", required = false) Integer idTipoMatrizHorario,
			@RequestParam(value = "dataInicial", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") @Valid LocalDate dataInicial,
			@RequestParam(value = "dataFinal", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") @Valid LocalDate dataFinal,
			@RequestParam(value = "inclusao", required = false) Boolean inclusao) {

		HorariosDiaDaSemanaDTO horarios = horarioServico
				.carregarHorariosDiaDaSemana(FiltroHorario.builder()
									.idFuncionario(idFuncionario)
									.idTurma(idTurma)
									.idDisciplina(idDisciplina)
									.idTipoDiaSemana(idTipoDiaSemana)
									.dataInicial(dataInicial)
									.dataFinal(dataFinal)
									.idTipoMatrizHorario(idTipoMatrizHorario)
									.inclusao(inclusao)
									.build());
		
		if (horarios == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(horarios);
	}
	
	
}
