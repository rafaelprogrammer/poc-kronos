package br.com.kronos.backend.adaptadores.controlador.parcela;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.kronos.backend.adaptadores.controlador.BaseControlador;
import br.com.kronos.backend.aplicacao.contrato.FiltroParcela;
import br.com.kronos.backend.aplicacao.contrato.api.ParcelaDTO;
import br.com.kronos.backend.aplicacao.contrato.api.ParcelaServico;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/parcelas")
@CrossOrigin
public class ParcelaControlador extends BaseControlador {

	@NonNull
	private ParcelaServico parcelaServico;
	
	@PostMapping
	@ApiOperation(value = "Gerar Parcelas", notes = "Gerar Parcelas")
	public ResponseEntity<String> gerarParcelas(@RequestBody ParcelaDTO dto) {
		parcelaServico.gerarParcelas(dto);
		return ResponseEntity.ok().body("Geração de parcelas solicitadas com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Parcela", notes = "Alterar Parcela")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody ParcelaDTO dto) {
		dto.setId(id);
		parcelaServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da Parcela enviada com sucesso");
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Parcela por id", notes = "Buscar Parcela por id")
	public ResponseEntity<ParcelaDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		ParcelaDTO dto = parcelaServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	
	@GetMapping
	@ApiOperation(value = "Listar Parcelas", notes = "Listar Parcelas")
	public ResponseEntity<List<ParcelaDTO>> listar(@RequestParam(value = "idContrato", required = false) Long idContrato) {

		List<ParcelaDTO> parcelas = parcelaServico
				.listar(FiltroParcela.builder().idContrato(idContrato).build());
		
		if (parcelas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(parcelas);
	}
	
}
