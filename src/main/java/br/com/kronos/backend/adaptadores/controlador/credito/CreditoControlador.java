package br.com.kronos.backend.adaptadores.controlador.credito;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kronos.backend.adaptadores.controlador.BaseControlador;
import br.com.kronos.backend.aplicacao.matricula.api.CreditoServico;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/creditos")
@CrossOrigin
public class CreditoControlador extends BaseControlador {

	@NonNull
	private CreditoServico creditoServico;

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Creditos", notes = "Excluir Creditos")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		creditoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do crédito enviado com sucesso");
	}
	
}
