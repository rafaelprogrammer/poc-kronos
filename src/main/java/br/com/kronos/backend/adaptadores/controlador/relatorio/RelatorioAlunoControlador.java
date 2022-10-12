package br.com.kronos.backend.adaptadores.controlador.relatorio;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.kronos.backend.adaptadores.controlador.BaseControlador;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.api.impl.comum.RelatorioServico;
import br.com.kronos.backend.aplicacao.comum.EnumRelatorios;
import br.com.kronos.backend.aplicacao.comum.FiltroRelatorio;
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import br.com.kronos.backend.aplicacao.contrato.EnumTipoSituacaoContrato;
import br.com.kronos.backend.aplicacao.curso.api.CursoServico;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaServico;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.api.PessoaDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.PessoaServico;
import br.com.kronos.backend.aplicacao.relatorio.api.AlunoRelatorioDTO;
import br.com.kronos.backend.aplicacao.relatorio.api.RelatoriosAlunosServico;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/relatorios/alunos")
@CrossOrigin
public class RelatorioAlunoControlador extends BaseControlador {
	
	@NonNull
	private RelatorioServico relatorioServico;
	
	@NonNull
	private RelatoriosAlunosServico relatoriosAlunosServico;
	
	@NonNull
	private ServicoAutenticacao servicoAutenticacao;
	
	@NonNull
	private PessoaServico pessoaServico;
	
	@NonNull
	private DisciplinaServico disciplinaServico;
	
	@NonNull
	private CursoServico cursoServico;
	
	
	@GetMapping("/turmas-situacoes/pdf")
	public ResponseEntity<byte[]> gerarPorTurmSituacaoPDF(@RequestParam(name = "idTurma", required = true) Long idTurma,
			@RequestParam(name = "idsTipoSituacaoContrato", required = true) Integer[] idsTipoSituacaoContrato,
			@RequestParam(name = "ano", required = true) Integer ano,
			@RequestParam(name = "nomeCurso", required = true) String nomeCurso,
			@RequestParam(name = "nomePeriodo", required = true) String nomePeriodo,
			@RequestParam(name = "nomeTurma", required = true) String nomeTurma) {
		
		Map<String, Object> parameters = new HashMap<>();
		
		PessoaDTO pessoaDTO = pessoaServico.buscarPorId(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		
		parameters.put("ANO", ano);
		parameters.put("NOME_PERIODO", nomePeriodo);
		parameters.put("NOME_CURSO", nomeCurso);
		parameters.put("SIGLA_NOME_TURMA", nomeTurma);
		
		parameters.put("NR_REG_USUARIO_LOGADO", new Long(pessoaDTO.getNumeroRegistro()));
		parameters.put("NOME_USUARIO_LOGADO", pessoaDTO.getNome());
		
		parameters.put("ID_TURMA", idTurma);
		
		criarParametrosTiposSituacoesContratos(idsTipoSituacaoContrato, parameters);
		
		parameters.put("DATA_HORA", DateUtil.dataAtual().toString());

		byte[] bytes = relatorioServico.exportarParaPdf(EnumRelatorios.REL_REG_ALUNOS_POR_TURMA_E_SITUACAO, parameters);
		return responseRelatorio(EnumRelatorios.REL_REG_ALUNOS_POR_TURMA_E_SITUACAO, bytes);
	}
	
	@GetMapping("/periodos-situacoes/pdf")
	public ResponseEntity<byte[]> gerarPorPeriodoSituacaoPDF(@RequestParam(name = "idPeriodoExecucao", required = true) Long idPeriodoExecucao,
			@RequestParam(name = "idsTipoSituacaoContrato", required = true) Integer[] idsTipoSituacaoContrato,
			@RequestParam(name = "ano", required = true) Integer ano,
			@RequestParam(name = "nomeCurso", required = true) String nomeCurso,
			@RequestParam(name = "nomePeriodo", required = true) String nomePeriodo) {
		
		Map<String, Object> parameters = new HashMap<>();
		
		PessoaDTO pessoaDTO = pessoaServico.buscarPorId(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		
		parameters.put("ANO", ano);
		parameters.put("NOME_PERIODO", nomePeriodo);
		parameters.put("NOME_CURSO", nomeCurso);
		
		parameters.put("NR_REG_USUARIO_LOGADO", new Long(pessoaDTO.getNumeroRegistro()));
		parameters.put("NOME_USUARIO_LOGADO", pessoaDTO.getNome());
		
		parameters.put("ID_PERIODO_EXECUCAO", idPeriodoExecucao);
		
		criarParametrosTiposSituacoesContratos(idsTipoSituacaoContrato, parameters);
		
		parameters.put("DATA_HORA", DateUtil.dataAtual().toString());

		byte[] bytes = relatorioServico.exportarParaPdf(EnumRelatorios.REL_REG_ALUNOS_POR_PERIODO_E_SITUACAO, parameters);
		return responseRelatorio(EnumRelatorios.REL_REG_ALUNOS_POR_PERIODO_E_SITUACAO, bytes);
	}

	private void criarParametrosTiposSituacoesContratos(Integer[] idsTipoSituacaoContrato,
			Map<String, Object> parameters) {
		StringBuilder strIds = new StringBuilder("");
		StringBuilder strNomesSituacoesContratos = new StringBuilder("");
		List<Integer> ids = Arrays.asList(idsTipoSituacaoContrato);
		ids.stream().forEach(id -> {
			strIds.append(id);
			strIds.append(",");
			strNomesSituacoesContratos.append(EnumTipoSituacaoContrato.porId(id).label());
			strNomesSituacoesContratos.append(", ");
		});
		strIds.replace(strIds.length()-1, strIds.length(), "");
		strNomesSituacoesContratos.replace(strNomesSituacoesContratos.length()-2, strNomesSituacoesContratos.length(), "");
		
		parameters.put("IDS_TIPO_SITUACAO_CONTRATO", strIds.toString());
		parameters.put("NOMES_TIPO_SITUACAO_CONTRATO", strNomesSituacoesContratos.toString());
	}
	
	
	@GetMapping("/turmas-situacoes")
	@ApiOperation(value = "Listar Alunos por Turma e Situacoes", notes = "Listar Alunos por Turma e Situacoes")
	public ResponseEntity<PaginacaoDTO<AlunoRelatorioDTO>> listarPorTurmaESituacoes(
			@RequestParam(name = "idTurma", required = true) Long idTurma,
			@RequestParam(name = "idsTipoSituacaoContrato", required = true) Integer[] idsTipoSituacaoContrato,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<AlunoRelatorioDTO> alunos = relatoriosAlunosServico
				.listarPorTurmaR(FiltroRelatorio.builder()
									.idTurma(idTurma)
									.idsTipoSituacaoContrato(idsTipoSituacaoContrato)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (alunos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(alunos);
	}
	
	@GetMapping("/periodos-situacoes")
	@ApiOperation(value = "Listar Alunos por Periodo e Situacoes", notes = "Listar Alunos por Periodo e Situacoes")
	public ResponseEntity<PaginacaoDTO<AlunoRelatorioDTO>> listarPorPeriodoESituacoes(
			@RequestParam(name = "idPeriodoExecucao", required = true) Long idPeriodoExecucao,
			@RequestParam(name = "idsTipoSituacaoContrato", required = true) Integer[] idsTipoSituacaoContrato,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<AlunoRelatorioDTO> alunos = relatoriosAlunosServico
				.listarPorPeriodoR(FiltroRelatorio.builder()
									.idPeriodoExecucao(idPeriodoExecucao)
									.idsTipoSituacaoContrato(idsTipoSituacaoContrato)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (alunos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(alunos);
	}
	
	private ResponseEntity<byte[]> responseRelatorio(EnumRelatorios relatorio, byte[] bytes) {
		return ResponseEntity.ok()
				.header("Content-Type", "application/pdf; charset=UTF-8")
				.header("Content-Disposition", "attachment; filename=\"" + relatorio.nome() + ".pdf\"").body(bytes);
	}
}
