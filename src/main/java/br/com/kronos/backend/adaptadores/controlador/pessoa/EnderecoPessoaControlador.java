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
import br.com.kronos.backend.aplicacao.pessoa.FiltroEnderecoPessoa;
import br.com.kronos.backend.aplicacao.pessoa.api.EnderecoPessoaDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.EnderecoPessoaServico;
import br.com.kronos.backend.aplicacao.pessoa.api.TipoEnderecoDTO;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/endereco-pessoa")
@CrossOrigin
public class EnderecoPessoaControlador extends BaseControlador {

	@NonNull
	private EnderecoPessoaServico enderecoPessoaServico;

	@PostMapping
	@ApiOperation(value = "Cadastrar Endereco Pessoa", notes = "Cadastrar Endereco Pessoa")
	public ResponseEntity<String> criar(@RequestBody EnderecoPessoaDTO enderecoPessoaDTO) throws ExcecaoDeNegocio {
		enderecoPessoaServico.criar(enderecoPessoaDTO);
		return ResponseEntity.ok().body("Cadastro de endereço da pessoa enviado com sucesso - " + enderecoPessoaDTO.getCep());
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Endereco Pessoa", notes = "Excluir Endereco Pessoa")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Integer id) throws ExcecaoDeNegocio {
		enderecoPessoaServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do endereço da pessoa enviada com sucesso - " + id);
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Enderecos Pessoas", notes = "Listar Enderecos Pessoas")
	public ResponseEntity<PaginacaoDTO<EnderecoPessoaDTO>> listar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "cep", required = false) String cep,
			@RequestParam(value = "idPessoa", required = false) Integer idPessoa,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<EnderecoPessoaDTO> pessoas = enderecoPessoaServico
				.listar(FiltroEnderecoPessoa.builder()
									.id(id)
									.cep(cep)
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
	@ApiOperation(value = "Listar Tipos de Endereços", notes = "Listar Tipos de Endereços")
	public ResponseEntity<List<TipoEnderecoDTO>> listarTipoGeneros()
			throws ExcecaoDeNegocio {
		return ResponseEntity.ok().body(TipoEnderecoDTO.tipos());
	}
	
}
