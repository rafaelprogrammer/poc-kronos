package br.com.kronos.backend.adaptadores.controlador;

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

import br.com.kronos.backend.aplicacao.FiltroUsuario;
import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;
import br.com.kronos.backend.aplicacao.api.dto.UsuarioPessoaDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.api.impl.UsuarioServico;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/usuarios")
@CrossOrigin
public class UsuarioControlador extends BaseControlador {

	@NonNull
	private UsuarioServico usuarioServico;

	@NonNull
	private DateUtil dateUtil;

	@PostMapping
	@ApiOperation(value = "Cadastrar Usuário", notes = "Cadastrar Usuário")
	public ResponseEntity<String> criar(@RequestBody UsuarioDTO usuarioDTO) throws ExcecaoDeNegocio {
		usuarioServico.criar(usuarioDTO);
		return ResponseEntity.ok().body("Cadastro do usuário enviado com sucesso - " + usuarioDTO.getIdPessoa());
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Usuário", notes = "Alterar Usuário")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Integer id, @RequestBody UsuarioDTO usuarioDTO) throws ExcecaoDeNegocio {
		usuarioDTO.setId(id);
		usuarioServico.alterar(usuarioDTO);
		return ResponseEntity.ok().body("Alteração do usuário enviada com sucesso - " + usuarioDTO.getIdPessoa());
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Usuário", notes = "Excluir Usuário")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) throws ExcecaoDeNegocio {
		usuarioServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do usuário enviada com sucesso - " + id);
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Usuários", notes = "Listar Usuários")
	public ResponseEntity<PaginacaoDTO<UsuarioPessoaDTO>> listar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "numeroRegistro", required = false) Integer numeroRegistro,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<UsuarioPessoaDTO> usuarios = usuarioServico
				.listar(FiltroUsuario.builder()
						.id(id)
						.nome(nome)
						.cpf(cpf)
						.numeroRegistro(numeroRegistro)
						.nome(nome)
						.qtdTotal(total)
						.numeroPagina(pagina)
						.build());
		if (usuarios == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(usuarios);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Usuário por id", notes = "Buscar Usuário por id")
	public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable(value = "id") Long id)
			throws ExcecaoDeNegocio {

		UsuarioDTO usuarioDTO = usuarioServico.buscarPorId(id);
		if (usuarioDTO == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(usuarioDTO);
	}
	

}
