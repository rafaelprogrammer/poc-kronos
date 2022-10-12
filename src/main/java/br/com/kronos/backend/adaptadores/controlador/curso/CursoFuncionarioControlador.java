package br.com.kronos.backend.adaptadores.controlador.curso;

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
import br.com.kronos.backend.aplicacao.curso.FiltroCursoFuncionario;
import br.com.kronos.backend.aplicacao.curso.api.CursoFuncionarioDTO;
import br.com.kronos.backend.aplicacao.curso.api.CursoFuncionarioServico;
import br.com.kronos.backend.aplicacao.curso.api.TipoFuncaoDTO;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cursos/funcionarios")
@CrossOrigin
public class CursoFuncionarioControlador extends BaseControlador {

	@NonNull
	private CursoFuncionarioServico cursoFuncionarioServico;

	@NonNull
	private DateUtil dateUtil;
	

	@PostMapping
	@ApiOperation(value = "Cadastrar Funcionarios do Curso", notes = "Cadastrar Funcionarios do Curso")
	public ResponseEntity<String> criar(@RequestBody CursoFuncionarioDTO dto) {
		cursoFuncionarioServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do Funcionário enviado com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Funcionarios do Curso", notes = "Alterar Funcionarios do Curso")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody CursoFuncionarioDTO dto) {
		dto.setId(id);
		cursoFuncionarioServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do Funcionário enviadp com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Funcionario do Curso", notes = "Excluir Funcionario do Curso")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		cursoFuncionarioServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do Funcionário enviado com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Funcionarios do Curso", notes = "Listar Funcionarios do Curso")
	public ResponseEntity<PaginacaoDTO<CursoFuncionarioDTO>> listar(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idCurso", required = false) Long idCurso,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<CursoFuncionarioDTO> cursos = cursoFuncionarioServico
				.listar(FiltroCursoFuncionario.builder()
									.id(id)
									.idCurso(idCurso)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (cursos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(cursos);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Funcionarios por id", notes = "Buscar Funcionarios por id")
	public ResponseEntity<CursoFuncionarioDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		CursoFuncionarioDTO dto = cursoFuncionarioServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/tipos")
	@ApiOperation(value = "Listar Tipos de Funcoes", notes = "Listar Tipos de Funcoes")
	public ResponseEntity<List<TipoFuncaoDTO>> tipos() {
		return ResponseEntity.ok().body(TipoFuncaoDTO.tipos());
	}

}
