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
import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import br.com.kronos.backend.aplicacao.curso.FiltroCurso;
import br.com.kronos.backend.aplicacao.curso.FiltroCursoEscala;
import br.com.kronos.backend.aplicacao.curso.api.CursoDTO;
import br.com.kronos.backend.aplicacao.curso.api.CursoEscalaDTO;
import br.com.kronos.backend.aplicacao.curso.api.CursoResumoDTO;
import br.com.kronos.backend.aplicacao.curso.api.CursoServico;
import br.com.kronos.backend.aplicacao.curso.api.EstruturaDTO;
import br.com.kronos.backend.aplicacao.curso.api.TipoMatrizHorarioDTO;
import br.com.kronos.backend.aplicacao.curso.api.TipoRegimeLetivoDTO;
import br.com.kronos.backend.aplicacao.curso.api.TipoTurnoDTO;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cursos")
@CrossOrigin
public class CursoControlador extends BaseControlador {

	@NonNull
	private CursoServico cursoServico;
	
	@NonNull
	private DateUtil dateUtil;
	
	@NonNull
	private ServicoAutenticacao servicoAutenticacao;
	

	@PostMapping
	@ApiOperation(value = "Cadastrar Curso", notes = "Cadastrar Curso")
	public ResponseEntity<String> criar(@RequestBody CursoDTO dto) {
		cursoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do curso enviado com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Curso", notes = "Alterar Curso")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody CursoDTO dto) {
		dto.setId(id);
		cursoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do curso enviado com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Curso", notes = "Excluir Curso")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		cursoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do curso enviado com sucesso");
	}
	
	@GetMapping("/combo")
	@ApiOperation(value = "Listar Cursos Para Combo", notes = "Listar Cursos para Combo")
	public ResponseEntity<List<CursoDTO>> listarParaCombo(@RequestParam(value = "id", required = false) Long id) {

		List<CursoDTO> cursos = cursoServico
				.listarParaCombo(FiltroCurso.builder()
									.id(id)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.build());
		
		if (cursos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(cursos);
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Cursos", notes = "Listar Cursos")
	public ResponseEntity<PaginacaoDTO<CursoDTO>> listar(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<CursoDTO> cursos = cursoServico
				.listar(FiltroCurso.builder()
									.id(id)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (cursos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(cursos);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Curso por id", notes = "Buscar Curso por id")
	public ResponseEntity<CursoDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		CursoDTO dto = cursoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/tiposRegimesLetivos")
	@ApiOperation(value = "Listar Tipos de Regimes Letivos", notes = "Listar Tipos de Regimes Letivos")
	public ResponseEntity<List<TipoRegimeLetivoDTO>> listarTipoRegimeLetivo() {
		return ResponseEntity.ok().body(TipoRegimeLetivoDTO.tipos());
	}
	
	@GetMapping(value="/tiposTurnos")
	@ApiOperation(value = "Listar Tipos de Turnos", notes = "Listar Tipos de Turnos")
	public ResponseEntity<List<TipoTurnoDTO>> listarTipoTurno() {
		return ResponseEntity.ok().body(TipoTurnoDTO.tipos());
	}
	
	@PostMapping(value="/vincula-cursos-escalas")
	@ApiOperation(value = "Vincular Curso Escala", notes = "Vincular Curso")
	public ResponseEntity<String> vincularCursoEscala(@RequestBody CursoEscalaDTO dto) {
		dto.setCriacao(true);
		cursoServico.vincularCursoEscala(dto);
		return ResponseEntity.ok().body("Vinculação enviada com sucesso");
	}
	
	@PostMapping(value="/desvincula-cursos-escalas")
	@ApiOperation(value = "Desvincular Curso Escala", notes = "Desvincular Curso")
	public ResponseEntity<String> desvincularCursoEscala(@RequestBody CursoEscalaDTO dto) {
		cursoServico.desvincularCursoEscala(dto);
		return ResponseEntity.ok().body("Desvinculação enviada com sucesso");
	}
	
	@GetMapping(value = "/vinculo/curso/{idCurso}/escala/{idEscala}")
	@ApiOperation(value = "Buscar Curso por id", notes = "Buscar Curso por id")
	public ResponseEntity<Boolean> verificarVinculoCursoEscala(@PathVariable(value = "idCurso") Long idCurso, @PathVariable(value = "idEscala") Long idEscala) {

		return ResponseEntity.ok().body(cursoServico.verificarVinculoCursoEscala(FiltroCursoEscala.builder()
																					.idCurso(idCurso)
																					.idEscala(idEscala).build()));
	}
	
	@GetMapping(value = "/visualiza-estrutura/curso/{idCurso}")
	@ApiOperation(value = "Visualizar Estrutura do Curso", notes = "Visualizar Estrutura do Curso")
	public ResponseEntity<List<EstruturaDTO>> visualizarEstrutura(@PathVariable(value = "idCurso") Long idCurso) {

		List<EstruturaDTO> dtos = cursoServico.visualizarEstrutura(idCurso);
		if (dtos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dtos);
	}
	
	@GetMapping(value = "/combo-horario/ano/{ano}")
	@ApiOperation(value = "Cursos para o horario", notes = "Cursos para o horario")
	public ResponseEntity<List<CursoResumoDTO>> visualizarEstrutura(@PathVariable(value = "ano") Integer ano) {

		List<CursoResumoDTO> dtos = cursoServico.listarParaHorario(ano);
		if (dtos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dtos);
	}
	
	@GetMapping(value = "/combo-modulos-professores")
	@ApiOperation(value = "Cursos para a combo dos modulos utilizados pelos professores", notes = "Cursos para a combo dos modulos utilizados pelos professores")
	public ResponseEntity<List<CursoResumoDTO>> listarParaModulosDosProfessores() {

		UsuarioDTO usuario = servicoAutenticacao.buscarUsuarioLogado();
		List<CursoResumoDTO> dtos = cursoServico.listarParaModulosDosProfessores(usuario.getIdInstituicao(), usuario.getIdPessoa());
		if (dtos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dtos);
	}
	
	@GetMapping(value="/combo-tipos-matrizes-horarios")
	@ApiOperation(value = "Listar Tipos de Matrizes Horários", notes = "Listar Tipos de Matrizes Horários")
	public ResponseEntity<List<TipoMatrizHorarioDTO>> listarTiposMatrizesHorarios() {
		return ResponseEntity.ok().body(cursoServico.listarTipoMatrizHorario());
	}
	

}
