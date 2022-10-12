package br.com.kronos.backend.adaptadores.controlador.curso;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
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
import br.com.kronos.backend.aplicacao.calendario.FiltroSubFaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.api.EstatisticaResultadoSubFaseDTO;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoServico;
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import br.com.kronos.backend.aplicacao.curso.FiltroFase;
import br.com.kronos.backend.aplicacao.curso.FiltroSubFase;
import br.com.kronos.backend.aplicacao.curso.api.CursoResumoDTO;
import br.com.kronos.backend.aplicacao.curso.api.SubFaseDTO;
import br.com.kronos.backend.aplicacao.curso.api.SubFaseResumoDTO;
import br.com.kronos.backend.aplicacao.curso.api.SubFaseServico;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaResumoDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaResumoDTO;
import br.com.kronos.backend.aplicacao.resultado.FiltroResultadoSubFase;
import br.com.kronos.backend.aplicacao.resultado.api.ResultadoAvaliacaoDTO;
import br.com.kronos.backend.aplicacao.resultado.api.ResultadoSubFaseDTO;
import br.com.kronos.backend.aplicacao.resultado.api.ResultadoSubFaseServico;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cursos/periodos/fases/sub-fases")
@CrossOrigin
public class SubFaseControlador extends BaseControlador {

	@NonNull
	private SubFaseServico subFaseServico;
	
	@NonNull
	private SubFaseExecucaoServico subFaseExecucaoServico;
	
	@NonNull
	private ResultadoSubFaseServico resultadoSubFaseServico;

	@NonNull
	private ServicoAutenticacao servicoAutenticacao; 
	

	@PostMapping
	@ApiOperation(value = "Cadastrar Sub-Fase", notes = "Cadastrar Sub-Fase")
	public ResponseEntity<String> criar(@RequestBody SubFaseDTO dto) {
		subFaseServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro de sub-fase enviada com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Sub-Fase", notes = "Alterar Sub-Fase")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody SubFaseDTO dto) {
		dto.setId(id);
		subFaseServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da sub-fase enviada com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Sub-Fase", notes = "Excluir Sub-Fase")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		subFaseServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da sub-fase enviada com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Sub-Fases", notes = "Listar Sub-Fases")
	public ResponseEntity<PaginacaoDTO<SubFaseDTO>> listar(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idFase", required = false) Long idFase,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<SubFaseDTO> fases = subFaseServico
				.listar(FiltroSubFase.builder()
									.id(id)
									.idFase(idFase)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (fases == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(fases);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Sub-Fase por id", notes = "Buscar Sub-Fase por id")
	public ResponseEntity<SubFaseDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		SubFaseDTO dto = subFaseServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/combo-sub-fases-execucao/turma/{idTurma}/disciplina/{idDisciplina}")
	@ApiOperation(value = "Listar Sub-Fases Execuções para combo", notes = "Listar Sub-Fases Execuções para combo")
	public ResponseEntity<List<SubFaseExecucaoDTO>> listarParaDiarioEFrequencia(@PathVariable(value = "idTurma", required = false) Long idTurma,
			@PathVariable(value = "idDisciplina", required = false) Long idDisciplina) {

		List<SubFaseExecucaoDTO> subFases = subFaseExecucaoServico
				.listarParaDiarioEFrequencia(idTurma, idDisciplina, servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		
		if (subFases == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(subFases);
	}
	
	@GetMapping(value="/combo/curso/{idCurso}/periodo/{idPeriodo}")
	@ApiOperation(value = "Listar Sub-Fases para combo", notes = "Listar Sub-Fases para combo")
	public ResponseEntity<List<SubFaseResumoDTO>> listarParaCombo(@PathVariable(value = "idCurso", required = false) Long idCurso,
			@PathVariable(value = "idPeriodo", required = false) Long idPeriodo) {

		List<SubFaseResumoDTO> subFases = subFaseServico.listarParaCombo(FiltroFase.builder().idCurso(idCurso).idPeriodo(idPeriodo).build());
		
		if (subFases == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(subFases);
	}
	
	//### RESULTADOS
	@PostMapping(value="/resultados")
	@ApiOperation(value = "Cadastrar Resultados Sub-Fases", notes = "Cadastrar Resultados Sub-Fases")
	public ResponseEntity<String> criarResultadosSubFases(@RequestBody List<ResultadoSubFaseDTO> dtos) {
		resultadoSubFaseServico.criar(dtos);
		return ResponseEntity.ok().body("Registro de resultados de bimestre enviado com sucesso");
	}
	@GetMapping(value="/resultados/cursos")
	@ApiOperation(value = "Listar Cursos para combo dos resultados", notes = "Listar Cursos para combo dos resultados")
	public ResponseEntity<List<CursoResumoDTO>> listarParaComboCurso() {

		List<CursoResumoDTO> cursos = resultadoSubFaseServico.listarComboCurso(FiltroSubFaseExecucao.builder()
																		.idPessoaUsuarioLogado(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa())
																		.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
																		.build());
		
		if (cursos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(cursos);
	}
	@GetMapping(value="/resultados/cursos/{idCurso}/periodos")
	@ApiOperation(value = "Listar Periodos para combo dos resultados", notes = "Listar Periodos para combo dos resultados")
	public ResponseEntity<List<PeriodoExecucaoResumoDTO>> listarParaComboPeriodo(@PathVariable(value = "idCurso", required = false) Long idCurso) {

		List<PeriodoExecucaoResumoDTO> periodos = resultadoSubFaseServico.listarComboPeriodoExecucao(FiltroSubFaseExecucao.builder()
																		.idCurso(idCurso)
																		.idPessoaUsuarioLogado(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa())
																		.build());
		
		if (periodos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(periodos);
	}
	@GetMapping(value="/resultados/periodos/{idPeriodoExecucao}/turmas")
	@ApiOperation(value = "Listar Turmas para combo dos resultados", notes = "Listar Turmas para combo dos resultados")
	public ResponseEntity<List<TurmaResumoDTO>> listarParaComboTurma(@PathVariable(value = "idPeriodoExecucao", required = false) Long idPeriodoExecucao) {

		List<TurmaResumoDTO> turmas = resultadoSubFaseServico.listarComboTurma(FiltroSubFaseExecucao.builder()
																		.idPeriodoExecucao(idPeriodoExecucao)
																		.idPessoaUsuarioLogado(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa())
																		.build());
		
		if (turmas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(turmas);
	}
	@GetMapping(value="/resultados/turmas/{idTurma}/disciplinas")
	@ApiOperation(value = "Listar Disciplinas para combo dos resultados", notes = "Listar Disciplinas para combo dos resultados")
	public ResponseEntity<List<DisciplinaResumoDTO>> listarParaComboDisciplina(@PathVariable(value = "idTurma", required = false) Long idTurma) {

		List<DisciplinaResumoDTO> disciplinas = resultadoSubFaseServico.listarComboDisciplina(FiltroSubFaseExecucao.builder()
																		.idTurma(idTurma)
																		.idPessoaUsuarioLogado(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa())
																		.build());
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	@GetMapping(value="/resultados/periodos/{idPeriodoExecucao}/sub-fases-execucoes")
	@ApiOperation(value = "Listar Sub-Fases-Execucoes para combo dos resultados", notes = "Listar Sub-Fases-Execucoes para combo dos resultados")
	public ResponseEntity<List<SubFaseExecucaoResumoDTO>> listarParaComboSubFaseExecucao(@PathVariable(value = "idPeriodoExecucao", required = false) Long idPeriodoExecucao) {

		List<SubFaseExecucaoResumoDTO> subFasesExecucoes = resultadoSubFaseServico.listarComboSubFaseExecucao(FiltroSubFaseExecucao.builder()
																		.idPeriodoExecucao(idPeriodoExecucao)
																		.idPessoaUsuarioLogado(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa())
																		.build());
		
		if (subFasesExecucoes == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(subFasesExecucoes);
	}
	@GetMapping(value="/resultados/estatisticas")
	@ApiOperation(value = "Recuperar estatisticas dos resultados", notes = "Recuperar estatisticas dos resultados")
	public ResponseEntity<EstatisticaResultadoSubFaseDTO> recuperarEstatisticasResultados(
			@RequestParam(value = "idSubFaseExecucao", required = false) Long idSubFaseExecucao,
			@RequestParam(value = "idTurma", required = false) Long idTurma,
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "dataInicial", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") @Valid LocalDate dataInicial,
			@RequestParam(value = "dataFinal", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") @Valid LocalDate dataFinal) {

		EstatisticaResultadoSubFaseDTO estatistica = resultadoSubFaseServico.recuperarEstatistasResultado(FiltroSubFaseExecucao.builder()
																		.id(idSubFaseExecucao)
																		.idTurma(idTurma)
																		.idDisciplina(idDisciplina)
																		.dataInicio(dataInicial)
																		.dataFim(dataFinal)
																		.build());
		
		if (estatistica == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(estatistica);
	}
	@GetMapping("/resultados/alunos")
	@ApiOperation(value = "Listar Resultados Sub-Fases", notes = "Listar Resultados Sub-Fases")
	public ResponseEntity<List<ResultadoSubFaseDTO>> listarAlunosResultados(@RequestParam(value = "idTurma", required = false) Long idTurma,
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idSubFaseExecucao", required = false) Long idSubFaseExecucao,
			@RequestParam(value = "dataFinal", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") @Valid LocalDate dataFinal,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		List<ResultadoSubFaseDTO> resultados = resultadoSubFaseServico
				.listarAlunosResultados(FiltroResultadoSubFase.builder()
									.idTurma(idTurma)
									.idDisciplina(idDisciplina)
									.idSubFaseExecucao(idSubFaseExecucao)
									.dataFim(dataFinal)
									.numeroPagina(pagina)
									.qtdTotal(total)
									.build());
		
		if (resultados == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(resultados);
	}
	@GetMapping("/resultados/avaliacoes")
	@ApiOperation(value = "Listar Resultados Avaliacoes", notes = "Listar Resultados Avaliacoes")
	public ResponseEntity<List<ResultadoAvaliacaoDTO>> listarResultadosAvaliacoes(
			@RequestParam(value = "idCredito", required = false) Long idCredito,
			@RequestParam(value = "idTurma", required = false) Long idTurma,
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "dataInicial", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") @Valid LocalDate dataInicial,
			@RequestParam(value = "dataFinal", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") @Valid LocalDate dataFinal) {

		List<ResultadoAvaliacaoDTO> resultados = resultadoSubFaseServico
				.listarResultadosAvaliacoes(FiltroResultadoSubFase.builder()
									.idTurma(idTurma)
									.idDisciplina(idDisciplina)
									.idCredito(idCredito)
									.dataInicio(dataInicial)
									.dataFim(dataFinal)
									.build());
		
		if (resultados == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(resultados);
	}
	//### FIM RESULTADOS
	
}
