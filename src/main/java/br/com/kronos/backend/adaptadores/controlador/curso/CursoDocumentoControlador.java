package br.com.kronos.backend.adaptadores.controlador.curso;

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
import br.com.kronos.backend.aplicacao.curso.FiltroCursoDocumento;
import br.com.kronos.backend.aplicacao.curso.api.CursoDocumentoDTO;
import br.com.kronos.backend.aplicacao.curso.api.CursoDocumentoServico;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cursos/documentos")
@CrossOrigin
public class CursoDocumentoControlador extends BaseControlador {

	@NonNull
	private CursoDocumentoServico cursoDocumentoServico;

	@NonNull
	private DateUtil dateUtil;
	

	@PostMapping
	@ApiOperation(value = "Cadastrar Curso Documento", notes = "Cadastrar Curso Documento")
	public ResponseEntity<String> criar(@RequestBody CursoDocumentoDTO dto) {
		cursoDocumentoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do documento enviado com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Curso Documento", notes = "Alterar Curso Documento")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody CursoDocumentoDTO dto) {
		dto.setId(id);
		cursoDocumentoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do documento enviado com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Curso Documento", notes = "Excluir Curso Documento")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		cursoDocumentoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do documento enviado com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Cursos Documentos", notes = "Listar Cursos Documentos")
	public ResponseEntity<PaginacaoDTO<CursoDocumentoDTO>> listar(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idCurso", required = false) Long idCurso,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<CursoDocumentoDTO> cursos = cursoDocumentoServico
				.listar(FiltroCursoDocumento.builder()
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
	@ApiOperation(value = "Buscar Curso Documento por id", notes = "Buscar Curso Documento por id")
	public ResponseEntity<CursoDocumentoDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		CursoDocumentoDTO dto = cursoDocumentoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}

}
