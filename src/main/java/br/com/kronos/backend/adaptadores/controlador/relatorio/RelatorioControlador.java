package br.com.kronos.backend.adaptadores.controlador.relatorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.api.impl.comum.RelatorioServico;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoDTO;
import br.com.kronos.backend.aplicacao.comum.EnumRelatorios;
import br.com.kronos.backend.aplicacao.comum.FiltroRelatorio;
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import br.com.kronos.backend.aplicacao.curso.api.CursoResumoDTO;
import br.com.kronos.backend.aplicacao.curso.api.CursoServico;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaResumoDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaServico;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaResumoDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.PessoaDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.PessoaServico;
import br.com.kronos.backend.aplicacao.relatorio.api.RelatorioResultadosBimestraisDTO;
import br.com.kronos.backend.aplicacao.relatorio.api.RelatoriosPedagogicosServico;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/relatorios")
@CrossOrigin
public class RelatorioControlador extends BaseControlador {
	
	@NonNull
	private RelatorioServico relatorioServico;
	
	@NonNull
	private RelatoriosPedagogicosServico relatoriosPedagogicosServico;
	
	@NonNull
	private ServicoAutenticacao servicoAutenticacao;
	
	@NonNull
	private PessoaServico pessoaServico;
	
	@NonNull
	private DisciplinaServico disciplinaServico;
	
	@NonNull
	private CursoServico cursoServico;
	
	@GetMapping("/folha-ponto/pdf")
	public ResponseEntity<byte[]> gerarFolhaPontoPdf(@RequestParam(name = "idFuncionario", required = true) Long idFuncionario,
			@RequestParam(name = "ano", required = true) Integer ano,
			@RequestParam(name = "mes", required = true) Integer mes) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("ID_FUNCIONARIO", idFuncionario);
		PessoaDTO pessoaDTO = pessoaServico.buscarPorId(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		parameters.put("NR_REG_USUARIO_LOGADO", new Long(pessoaDTO.getNumeroRegistro()));
		parameters.put("NOME_USUARIO_LOGADO", pessoaDTO.getNome());
		parameters.put("ANO", ano);
		parameters.put("MES", mes);

		byte[] bytes = relatorioServico.exportarParaPdf(EnumRelatorios.REL_REG_FOLHA_PONTO, parameters);
		return responseRelatorioPDF(EnumRelatorios.REL_REG_FOLHA_PONTO, bytes);
	}
	
	@GetMapping("/diario/pdf")
	public ResponseEntity<byte[]> gerarDiarioPdf(@RequestParam(name = "idDisciplina", required = true) Long idDisciplina,
			@RequestParam(name = "idFuncionario", required = true) Long idFuncionario,
			@RequestParam(name = "idSubFaseExecucao", required = true) Long idSubFaseExecucao,
			@RequestParam(name = "idTurma", required = true) Long idTurma,
			@RequestParam(name = "cargaHorariaPrevista", required = false) Integer cargaHorariaPrevista,
			@RequestParam(name = "cargaHorariaMinistrada", required = false) Integer cargaHorariaMinistrada,
			@RequestParam(name = "nomeCurso", required = true) String nomeCurso,
			@RequestParam(name = "nomeDisciplina", required = true) String nomeDisciplina,
			@RequestParam(name = "nomePeriodo", required = true) String nomePeriodo,
			@RequestParam(name = "nomeTurma", required = true) String nomeTurma,
			@RequestParam(name = "numeroDias", required = true) String numeroDias,
			@RequestParam(name = "nomeProfessor", required = true) String nomeProfessor,
			@RequestParam(name = "dataInicio", required = true) String dataInicio,
			@RequestParam(name = "dataFim", required = true) String dataFim) {
		
		Map<String, Object> parameters = criarParametrosRelatorioDiario(idDisciplina, idFuncionario, idSubFaseExecucao,
				cargaHorariaPrevista, cargaHorariaMinistrada, nomeCurso, nomeDisciplina, nomePeriodo, nomeTurma,
				numeroDias, nomeProfessor, dataInicio, dataFim, idTurma);

		byte[] bytes = relatorioServico.exportarParaPdf(EnumRelatorios.REL_REG_DIARIO, parameters);
		return responseRelatorioPDF(EnumRelatorios.REL_REG_DIARIO, bytes);
	}

	@GetMapping("/diario/xlsx")
	public ResponseEntity<byte[]> gerarDiarioXLSX(@RequestParam(name = "idDisciplina", required = true) Long idDisciplina,
			@RequestParam(name = "idFuncionario", required = true) Long idFuncionario,
			@RequestParam(name = "idSubFaseExecucao", required = true) Long idSubFaseExecucao,
			@RequestParam(name = "idTurma", required = true) Long idTurma,
			@RequestParam(name = "cargaHorariaPrevista", required = false) Integer cargaHorariaPrevista,
			@RequestParam(name = "cargaHorariaMinistrada", required = false) Integer cargaHorariaMinistrada,
			@RequestParam(name = "nomeCurso", required = true) String nomeCurso,
			@RequestParam(name = "nomeDisciplina", required = true) String nomeDisciplina,
			@RequestParam(name = "nomePeriodo", required = true) String nomePeriodo,
			@RequestParam(name = "nomeTurma", required = true) String nomeTurma,
			@RequestParam(name = "numeroDias", required = true) String numeroDias,
			@RequestParam(name = "nomeProfessor", required = true) String nomeProfessor,
			@RequestParam(name = "dataInicio", required = true) String dataInicio,
			@RequestParam(name = "dataFim", required = true) String dataFim) {
		
		Map<String, Object> parameters = criarParametrosRelatorioDiario(idDisciplina, idFuncionario, idSubFaseExecucao,
				cargaHorariaPrevista, cargaHorariaMinistrada, nomeCurso, nomeDisciplina, nomePeriodo, nomeTurma,
				numeroDias, nomeProfessor, dataInicio, dataFim, idTurma);

		byte[] bytes = relatorioServico.exportarParaXLSX(EnumRelatorios.REL_REG_DIARIO, parameters);
		return responseRelatorioXLSX(EnumRelatorios.REL_REG_DIARIO, bytes);
	}

	
	private Map<String, Object> criarParametrosRelatorioDiario(Long idDisciplina, Long idFuncionario,
			Long idSubFaseExecucao, Integer cargaHorariaPrevista, Integer cargaHorariaMinistrada, String nomeCurso,
			String nomeDisciplina, String nomePeriodo, String nomeTurma, String numeroDias, String nomeProfessor,
			String dataInicio, String dataFim, Long idTurma) {
		Map<String, Object> parameters = new HashMap<>();
		
		PessoaDTO pessoaDTO = pessoaServico.buscarPorId(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		
		parameters.put("NOME_DISCIPLINA", nomeDisciplina);
		parameters.put("NOME_PERIODO", nomePeriodo);
		parameters.put("NOME_CURSO", nomeCurso);
		parameters.put("NOME_PROFESSOR", nomeProfessor);
		parameters.put("SIGLA_NOME_TURMA", nomeTurma);
		parameters.put("NR_DIAS", numeroDias);
		parameters.put("TOTAL_HORAS_PREVISTAS", cargaHorariaPrevista);
		parameters.put("TOTAL_HORAS_MINISTRADAS", cargaHorariaMinistrada);
		
		parameters.put("NR_REG_USUARIO_LOGADO", new Long(pessoaDTO.getNumeroRegistro()));
		parameters.put("NOME_USUARIO_LOGADO", pessoaDTO.getNome());
		
		parameters.put("ID_FUNCIONARIO", idFuncionario);
		parameters.put("ID_DISCIPLINA", idDisciplina);
		parameters.put("ID_SUB_FASE_EXECUCAO", idSubFaseExecucao);
		parameters.put("ID_TURMA", idTurma);
		
		parameters.put("DATA_INICIAL", DateUtil.paraLocalDate(dataInicio).toString());
		parameters.put("DATA_FINAL", DateUtil.paraLocalDate(dataFim).toString());
		return parameters;
	}
	
	@GetMapping("/frequencia/pdf")
	public ResponseEntity<byte[]> gerarFrequenciaPdf(@RequestParam(name = "idDisciplina", required = true) Long idDisciplina,
			@RequestParam(name = "idFuncionario", required = true) Long idFuncionario,
			@RequestParam(name = "idTurma", required = true) Long idTurma,
			@RequestParam(name = "idSubFaseExecucao", required = true) Long idSubFaseExecucao,
			@RequestParam(name = "cargaHorariaPrevista", required = false) Integer cargaHorariaPrevista,
			@RequestParam(name = "cargaHorariaMinistrada", required = false) Integer cargaHorariaMinistrada,
			@RequestParam(name = "nomeCurso", required = true) String nomeCurso,
			@RequestParam(name = "nomeDisciplina", required = true) String nomeDisciplina,
			@RequestParam(name = "nomePeriodo", required = true) String nomePeriodo,
			@RequestParam(name = "nomeTurma", required = true) String nomeTurma,
			@RequestParam(name = "numeroDias", required = true) String numeroDias,
			@RequestParam(name = "nomeProfessor", required = true) String nomeProfessor,
			@RequestParam(name = "dataInicio", required = true) String dataInicio,
			@RequestParam(name = "dataFim", required = true) String dataFim) {
		
		Map<String, Object> parameters = criarParametrosRelatorio(idDisciplina, idFuncionario, idTurma,
				idSubFaseExecucao, cargaHorariaPrevista, cargaHorariaMinistrada, nomeCurso, nomeDisciplina, nomePeriodo,
				nomeTurma, numeroDias, nomeProfessor, dataInicio, dataFim, null, 2022);

		byte[] bytes = relatorioServico.exportarParaPdf(EnumRelatorios.REL_REG_FREQUENCIA, parameters);
		return responseRelatorioPDF(EnumRelatorios.REL_REG_FREQUENCIA, bytes);
	}
	
	@GetMapping("/frequencia/xlsx")
	public ResponseEntity<byte[]> gerarFrequenciaXLSX(@RequestParam(name = "idDisciplina", required = true) Long idDisciplina,
			@RequestParam(name = "idFuncionario", required = true) Long idFuncionario,
			@RequestParam(name = "idTurma", required = true) Long idTurma,
			@RequestParam(name = "idSubFaseExecucao", required = true) Long idSubFaseExecucao,
			@RequestParam(name = "cargaHorariaPrevista", required = false) Integer cargaHorariaPrevista,
			@RequestParam(name = "cargaHorariaMinistrada", required = false) Integer cargaHorariaMinistrada,
			@RequestParam(name = "nomeCurso", required = true) String nomeCurso,
			@RequestParam(name = "nomeDisciplina", required = true) String nomeDisciplina,
			@RequestParam(name = "nomePeriodo", required = true) String nomePeriodo,
			@RequestParam(name = "nomeTurma", required = true) String nomeTurma,
			@RequestParam(name = "numeroDias", required = true) String numeroDias,
			@RequestParam(name = "nomeProfessor", required = true) String nomeProfessor,
			@RequestParam(name = "dataInicio", required = true) String dataInicio,
			@RequestParam(name = "dataFim", required = true) String dataFim) {
		
		Map<String, Object> parameters = criarParametrosRelatorio(idDisciplina, idFuncionario, idTurma,
				idSubFaseExecucao, cargaHorariaPrevista, cargaHorariaMinistrada, nomeCurso, nomeDisciplina, nomePeriodo,
				nomeTurma, numeroDias, nomeProfessor, dataInicio, dataFim, null, 2022);

		byte[] bytes = relatorioServico.exportarParaXLSX(EnumRelatorios.REL_REG_FREQUENCIA, parameters);
		return responseRelatorioXLSX(EnumRelatorios.REL_REG_FREQUENCIA, bytes);
	}
	
	@GetMapping("/resultado-bimestre/pdf")
	public ResponseEntity<byte[]> gerarResultadoBimestrePdf(@RequestParam(name = "idDisciplina", required = true) Long idDisciplina,
			@RequestParam(name = "idFuncionario", required = true) Long idFuncionario,
			@RequestParam(name = "idTurma", required = true) Long idTurma,
			@RequestParam(name = "idSubFaseExecucao", required = true) Long idSubFaseExecucao,
			@RequestParam(name = "cargaHorariaPrevista", required = false) Integer cargaHorariaPrevista,
			@RequestParam(name = "cargaHorariaMinistrada", required = false) Integer cargaHorariaMinistrada,
			@RequestParam(name = "nomeCurso", required = true) String nomeCurso,
			@RequestParam(name = "nomeDisciplina", required = true) String nomeDisciplina,
			@RequestParam(name = "nomePeriodo", required = true) String nomePeriodo,
			@RequestParam(name = "nomeTurma", required = true) String nomeTurma,
			@RequestParam(name = "numeroDias", required = true) String numeroDias,
			@RequestParam(name = "nomeProfessor", required = true) String nomeProfessor,
			@RequestParam(name = "nomeBimestre", required = true) String nomeBimestre,
			@RequestParam(name = "dataInicio", required = true) String dataInicio,
			@RequestParam(name = "dataFim", required = true) String dataFim) {
		
		Map<String, Object> parameters = criarParametrosRelatorio(idDisciplina, idFuncionario, idTurma,
				idSubFaseExecucao, cargaHorariaPrevista, cargaHorariaMinistrada, nomeCurso, nomeDisciplina, nomePeriodo,
				nomeTurma, numeroDias, nomeProfessor, dataInicio, dataFim, nomeBimestre, 2022);

		byte[] bytes = relatorioServico.exportarParaPdf(EnumRelatorios.REL_REG_RESULTADO_BIMESTRE, parameters);
		return responseRelatorioPDF(EnumRelatorios.REL_REG_RESULTADO_BIMESTRE, bytes);
	}
	
	@GetMapping("/resultado-bimestre/xlsx")
	public ResponseEntity<byte[]> gerarResultadoBimestreXLSX(@RequestParam(name = "idDisciplina", required = true) Long idDisciplina,
			@RequestParam(name = "idFuncionario", required = true) Long idFuncionario,
			@RequestParam(name = "idTurma", required = true) Long idTurma,
			@RequestParam(name = "idSubFaseExecucao", required = true) Long idSubFaseExecucao,
			@RequestParam(name = "cargaHorariaPrevista", required = false) Integer cargaHorariaPrevista,
			@RequestParam(name = "cargaHorariaMinistrada", required = false) Integer cargaHorariaMinistrada,
			@RequestParam(name = "nomeCurso", required = true) String nomeCurso,
			@RequestParam(name = "nomeDisciplina", required = true) String nomeDisciplina,
			@RequestParam(name = "nomePeriodo", required = true) String nomePeriodo,
			@RequestParam(name = "nomeTurma", required = true) String nomeTurma,
			@RequestParam(name = "numeroDias", required = true) String numeroDias,
			@RequestParam(name = "nomeProfessor", required = true) String nomeProfessor,
			@RequestParam(name = "nomeBimestre", required = true) String nomeBimestre,
			@RequestParam(name = "dataInicio", required = true) String dataInicio,
			@RequestParam(name = "dataFim", required = true) String dataFim) {
		
		Map<String, Object> parameters = criarParametrosRelatorio(idDisciplina, idFuncionario, idTurma,
				idSubFaseExecucao, cargaHorariaPrevista, cargaHorariaMinistrada, nomeCurso, nomeDisciplina, nomePeriodo,
				nomeTurma, numeroDias, nomeProfessor, dataInicio, dataFim, nomeBimestre, 2022);

		byte[] bytes = relatorioServico.exportarParaXLSX(EnumRelatorios.REL_REG_RESULTADO_BIMESTRE, parameters);
		return responseRelatorioXLSX(EnumRelatorios.REL_REG_RESULTADO_BIMESTRE, bytes);
	}

	private Map<String, Object> criarParametrosRelatorio(Long idDisciplina, Long idFuncionario, Long idTurma,
			Long idSubFaseExecucao, Integer cargaHorariaPrevista, Integer cargaHorariaMinistrada, String nomeCurso,
			String nomeDisciplina, String nomePeriodo, String nomeTurma, String numeroDias, String nomeProfessor,
			String dataInicio, String dataFim, String nomeBimestre, Integer anoTurma) {
		Map<String, Object> parameters = new HashMap<>();
		
		PessoaDTO pessoaDTO = pessoaServico.buscarPorId(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		
		parameters.put("NOME_DISCIPLINA", nomeDisciplina);
		parameters.put("NOME_PERIODO", nomePeriodo);
		parameters.put("NOME_CURSO", nomeCurso);
		parameters.put("NOME_PROFESSOR", nomeProfessor);
		parameters.put("NOME_BIMESTRE", nomeBimestre);
		parameters.put("SIGLA_NOME_TURMA", nomeTurma);
		parameters.put("NR_DIAS", numeroDias);
		parameters.put("TOTAL_HORAS_PREVISTAS", cargaHorariaPrevista);
		parameters.put("TOTAL_HORAS_MINISTRADAS", cargaHorariaMinistrada);
		
		parameters.put("NR_REG_USUARIO_LOGADO", new Long(pessoaDTO.getNumeroRegistro()));
		parameters.put("NOME_USUARIO_LOGADO", pessoaDTO.getNome());
		
		parameters.put("ID_FUNCIONARIO", idFuncionario);
		parameters.put("ID_DISCIPLINA", idDisciplina);
		parameters.put("ID_SUB_FASE_EXECUCAO", idSubFaseExecucao);
		parameters.put("ID_TURMA", idTurma);
		parameters.put("ANO_TURMA", anoTurma);
		
		parameters.put("DATA_INICIAL", DateUtil.paraLocalDate(dataInicio).toString());
		parameters.put("DATA_FINAL", DateUtil.paraLocalDate(dataFim).toString());
		return parameters;
	}
	
	@GetMapping("/anos")
	public ResponseEntity<List<Integer>> listarAnos() {
		return ResponseEntity.ok(relatorioServico.listarAnos());
	}
	
	@GetMapping("/cursos/{ano}")
	public ResponseEntity<List<CursoResumoDTO>> listarCursos(@PathVariable(name = "ano") Integer ano) {
		return ResponseEntity.ok(relatorioServico.listarCursos(FiltroRelatorio.builder().ano(ano).build()));
	}
	
	@GetMapping("/periodos/curso/{idCurso}/ano/{ano}")
	public ResponseEntity<List<PeriodoExecucaoResumoDTO>> listarPeriodos(@PathVariable(name = "idCurso") Long idCurso,
			@PathVariable(name = "ano") Integer ano) {
		return ResponseEntity.ok(relatorioServico.listarPeriodos(FiltroRelatorio.builder().idCurso(idCurso).ano(ano).build()));
	}
	
	@GetMapping("/turmas/{idPeriodoExecucao}")
	public ResponseEntity<List<TurmaResumoDTO>> listarTurmas(@PathVariable(name = "idPeriodoExecucao") Long idPeriodoExecucao) {
		return ResponseEntity.ok(relatorioServico.listarTurmas(FiltroRelatorio.builder().idPeriodoExecucao(idPeriodoExecucao).build()));
	}
	
	@GetMapping("/bimestres/{idPeriodoExecucao}")
	public ResponseEntity<List<SubFaseExecucaoDTO>> listarBimestres(@PathVariable(name = "idPeriodoExecucao") Long idPeriodoExecucao) {
		return ResponseEntity.ok(relatoriosPedagogicosServico.listarBimestres(FiltroRelatorio.builder().idPeriodoExecucao(idPeriodoExecucao).build()));
	}
	
	@GetMapping("/pedagogicos")
	@ApiOperation(value = "Listar Disciplinas Professores", notes = "Listar Disciplinas Professores")
	public ResponseEntity<PaginacaoDTO<DisciplinaResumoDTO>> listarDisciplinasProfessores(
			@RequestParam(value = "idSubFaseExecucao", required = false) Long idSubFaseExecucao,
			@RequestParam(value = "idTurma", required = false) Long idTurma,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<DisciplinaResumoDTO> disciplinas = relatoriosPedagogicosServico
				.listarDisciplinasProfessores(FiltroRelatorio.builder()
									.idSubFaseExecucao(idSubFaseExecucao)
									.idTurma(idTurma)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	
	@GetMapping("/pedagogicos/resultados-bimestrais")
	@ApiOperation(value = "Listar Disciplinas Professores", notes = "Listar Disciplinas Professores")
	public ResponseEntity<PaginacaoDTO<DisciplinaResumoDTO>> listarDisciplinasProfessoresResultadosBimestrais(
			@RequestParam(value = "idSubFaseExecucao", required = false) Long idSubFaseExecucao,
			@RequestParam(value = "idTurma", required = false) Long idTurma,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<DisciplinaResumoDTO> disciplinas = relatoriosPedagogicosServico
				.listarDisciplinasProfessoresResultadosBimestrais(FiltroRelatorio.builder()
									.idSubFaseExecucao(idSubFaseExecucao)
									.idTurma(idTurma)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	
	@PostMapping("/diarios/encerra/disciplina/{idDisciplina}/subFaseExecucao/{idSubFaseExecucao}")
	@ApiOperation(value = "Encerrar Diário", notes = "Encerrar Diário")
	public ResponseEntity<String> encerrarDiario(@PathVariable("idSubFaseExecucao") Long idSubFaseExecucao, @PathVariable("idDisciplina") Long idDisciplina) throws ExcecaoDeNegocio {
		relatoriosPedagogicosServico.encerrarDiario(idSubFaseExecucao, idDisciplina);
		return ResponseEntity.ok().body("Solicitação de encerramento do diário enviada com sucesso");
	}
	
	@PostMapping("/diarios/reabre/disciplina/{idDisciplina}/subFaseExecucao/{idSubFaseExecucao}")
	@ApiOperation(value = "Reabrir Diário", notes = "Reabrir Diário")
	public ResponseEntity<String> reabrirDiario(@PathVariable("idSubFaseExecucao") Long idSubFaseExecucao, @PathVariable("idDisciplina") Long idDisciplina) throws ExcecaoDeNegocio {
		relatoriosPedagogicosServico.reabrirDiario(idSubFaseExecucao, idDisciplina);
		return ResponseEntity.ok().body("Solicitação de reabertura do diário enviada com sucesso");
	}
	
	@PutMapping("/pedagogicos/resultados-bimestrais")
	@ApiOperation(value = "Encerrar Diário", notes = "Atualizar resultados bimestrais")
	public ResponseEntity<String> atualizarResultadosBimestrais(@RequestBody RelatorioResultadosBimestraisDTO relatorioResultadosBimestraisDTO) throws ExcecaoDeNegocio {
		relatoriosPedagogicosServico.atualizarResultadosBimestrais(FiltroRelatorio.builder()
									.idTurma(relatorioResultadosBimestraisDTO.getIdTurma())
									.ano(relatorioResultadosBimestraisDTO.getAno())
									.idDisciplina(relatorioResultadosBimestraisDTO.getIdDisciplina())
									.idFuncionario(relatorioResultadosBimestraisDTO.getIdFuncionario())
									.idSubFaseExecucao(relatorioResultadosBimestraisDTO.getIdSubFaseExecucao()).build());
		return ResponseEntity.ok().body("Atualização executada com sucesso");
	}
	
	private ResponseEntity<byte[]> responseRelatorioPDF(EnumRelatorios relatorio, byte[] bytes) {
		return ResponseEntity.ok()
				.header("Content-Type", "application/pdf; charset=UTF-8")
				.header("Content-Disposition", "attachment; filename=\"" + relatorio.nome() + ".pdf\"").body(bytes);
	}
	
	private ResponseEntity<byte[]> responseRelatorioXLSX(EnumRelatorios relatorio, byte[] bytes) {
		return ResponseEntity.ok()
				.header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8")
				.header("Content-Disposition", "attachment; filename=\"" + relatorio.nome() + ".xlsx\"").body(bytes);
	}
}
