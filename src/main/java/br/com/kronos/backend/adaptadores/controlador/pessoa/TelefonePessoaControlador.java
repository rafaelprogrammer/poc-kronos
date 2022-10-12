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
import br.com.kronos.backend.aplicacao.pessoa.FiltroTelefonePessoa;
import br.com.kronos.backend.aplicacao.pessoa.api.OperadoraDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.TelefonePessoaDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.TelefonePessoaServico;
import br.com.kronos.backend.aplicacao.pessoa.api.TipoTelefoneDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.TipoUsoDTO;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/telefone-pessoa")
@CrossOrigin
public class TelefonePessoaControlador extends BaseControlador {

	@NonNull
	private TelefonePessoaServico telefonePessoaServico;

	@PostMapping
	@ApiOperation(value = "Cadastrar Telefone Pessoa", notes = "Cadastrar Telefone Pessoa")
	public ResponseEntity<String> criar(@RequestBody TelefonePessoaDTO telefonePessoaDTO) throws ExcecaoDeNegocio {
		telefonePessoaServico.criar(telefonePessoaDTO);
		return ResponseEntity.ok().body("Cadastro de telefone da pessoa enviado com sucesso - " + telefonePessoaDTO.getNumero());
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Telefone Pessoa", notes = "Excluir Telefone Pessoa")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Integer id) throws ExcecaoDeNegocio {
		telefonePessoaServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do telefone da pessoa enviada com sucesso - " + id);
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Telefones Pessoas", notes = "Listar Telefones Pessoas")
	public ResponseEntity<PaginacaoDTO<TelefonePessoaDTO>> listar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "numero", required = false) Integer numero,
			@RequestParam(value = "idPessoa", required = false) Long idPessoa,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<TelefonePessoaDTO> pessoas = telefonePessoaServico
				.listar(FiltroTelefonePessoa.builder()
									.id(id)
									.numero(numero)
									.idPessoa(idPessoa)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (pessoas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(pessoas);
	}
	
	@GetMapping(value="/tipos")
	@ApiOperation(value = "Listar Tipos de Telefones", notes = "Listar Tipos de Telefones")
	public ResponseEntity<List<TipoTelefoneDTO>> listarTipoTelefones()
			throws ExcecaoDeNegocio {
		return ResponseEntity.ok().body(TipoTelefoneDTO.tipos());
	}
	
	@GetMapping(value="/operadoras")
	@ApiOperation(value = "Listar Operadoras", notes = "Listar Operadoras")
	public ResponseEntity<List<OperadoraDTO>> listarOperadoras()
			throws ExcecaoDeNegocio {
		return ResponseEntity.ok().body(OperadoraDTO.tipos());
	}
	
	@GetMapping(value="/tipos-usos")
	@ApiOperation(value = "Listar Tipos de Usos", notes = "Listar Tipos de Usos")
	public ResponseEntity<List<TipoUsoDTO>> listarTiposUsos()
			throws ExcecaoDeNegocio {
		return ResponseEntity.ok().body(TipoUsoDTO.tipos());
	}
	
}
