package br.com.kronos.backend.adaptadores.controlador.periodoexecucao;

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
import br.com.kronos.backend.aplicacao.calendario.FiltroFaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.FiltroPeriodoExecucao;
import br.com.kronos.backend.aplicacao.calendario.FiltroSubFaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.api.FaseExecucaoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.FaseExecucaoServico;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoEstruturaAnoLetivoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoServico;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoServico;
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import br.com.kronos.backend.aplicacao.curso.FiltroFase;
import br.com.kronos.backend.aplicacao.curso.api.EstruturaDTO;
import br.com.kronos.backend.aplicacao.curso.api.FaseResumoDTO;
import br.com.kronos.backend.aplicacao.curso.api.SubFaseResumoDTO;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/periodos-execucoes")
@CrossOrigin
public class PeriodoExecucaoControlador extends BaseControlador {

	@NonNull
	private PeriodoExecucaoServico periodoExecucaoServico;
	
	@NonNull
	private FaseExecucaoServico faseExecucaoServico;
	
	@NonNull
	private SubFaseExecucaoServico subFaseExecucaoServico;
	
	@NonNull
	private ServicoAutenticacao servicoAutenticacao;
	
	@PostMapping
	@ApiOperation(value = "Cadastrar Periodo Execucao", notes = "Cadastrar Periodo Execucao")
	public ResponseEntity<String> criar(@RequestBody PeriodoExecucaoDTO dto) {
		periodoExecucaoServico.criar(dto);
		return ResponseEntity.ok().body("Geração dos períodos solicitada com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Periodo Execucao para estrutura ano letivo", notes = "Alterar Periodo Execucao para estrutura ano letivo")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody PeriodoExecucaoDTO dto) {
		dto.setId(id);
		periodoExecucaoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do período de execução enviado com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Execucao para estrutura ano letivo", notes = "Excluir Execucao para estrutura ano letivo")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		periodoExecucaoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do período de execução enviado com sucesso");
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Periodo Execucao por id", notes = "Buscar Periodo Execucao por id")
	public ResponseEntity<PeriodoExecucaoEstruturaAnoLetivoDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		PeriodoExecucaoEstruturaAnoLetivoDTO dto = periodoExecucaoServico.buscarPorIdParaEstruturaAnoLetivo(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}


	@GetMapping
	@ApiOperation(value = "Listar Períodos Execuções", notes = "Listar Períodos Execuções")
	public ResponseEntity<PaginacaoDTO<PeriodoExecucaoEstruturaAnoLetivoDTO>> listarParaEstruturaAnoLetivo(
			@RequestParam(value = "idCurso", required = false) Long idCurso,
			@RequestParam(value = "idCalendario", required = false) Long idCalendario,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<PeriodoExecucaoEstruturaAnoLetivoDTO> periodos = periodoExecucaoServico
				.listarParaEstruturaAnoLetivo(FiltroPeriodoExecucao.builder()
									.idCalendario(idCalendario)
									.idCurso(idCurso)
									.ano(ano)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (periodos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(periodos);
	}
	
	@GetMapping(value = "/fases-execucao/curso/{idCurso}/geracao")
	@ApiOperation(value = "Listar para geracao de fases", notes = "Listar para geracao de fases")
	public ResponseEntity<List<FaseResumoDTO>> listarParaGeracaoFaseExecucao(@PathVariable(value = "idCurso") Long idCurso) {

		List<FaseResumoDTO> fases = faseExecucaoServico.listarParaGeracaoFaseExecucao(FiltroFase.builder().idCurso(idCurso).build());
		
		if (fases == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(fases);
	}
	
	
	
	@PostMapping(value="/fases-execucao")
	@ApiOperation(value = "Cadastrar Fase Execucao", notes = "Cadastrar Fase Execucao")
	public ResponseEntity<String> criarFaseExecucao(@RequestBody List<FaseExecucaoDTO> dtos) {
		faseExecucaoServico.criar(dtos);
		return ResponseEntity.ok().body("Geração das fases solicitada com sucesso");
	}
	
	@GetMapping(value="/fases-execucao")
	@ApiOperation(value = "Listar Fases Execuções", notes = "Listar Fases Execuções")
	public ResponseEntity<PaginacaoDTO<FaseExecucaoDTO>> listarFasesExecucao(
			@RequestParam(value = "idCurso", required = false) Long idCurso,
			@RequestParam(value = "idPeriodoExecucao", required = false) Long idPeriodoExecucao,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<FaseExecucaoDTO> fases = faseExecucaoServico
				.listar(FiltroFaseExecucao.builder()
									.idCurso(idCurso)
									.idPeriodoExecucao(idPeriodoExecucao)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (fases == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(fases);
	}
	
	@PutMapping("/fases-execucao/{id}")
	@ApiOperation(value = "Alterar Fase Execucao", notes = "Alterar Fase Execucao")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody FaseExecucaoDTO dto) {
		dto.setId(id);
		faseExecucaoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do período de execução enviado com sucesso");
	}
	
	@GetMapping(value = "/fases-execucao/{id}")
	@ApiOperation(value = "Buscar Fase Execucao por id", notes = "Buscar Fase Execucao por id")
	public ResponseEntity<FaseExecucaoDTO> buscarFaseExecucaoPorId(@PathVariable(value = "id") Long id) {

		FaseExecucaoDTO dto = faseExecucaoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping("/fases-execucao/{id}")
	@ApiOperation(value = "Excluir Fase Execucao", notes = "Excluir Fase Execucao")
	public ResponseEntity<?> excluirFaseExecucao(@PathVariable(value = "id") Long id) {
		faseExecucaoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da fase de execução enviada com sucesso");
	}
	
	@GetMapping(value = "/sub-fases-execucao/curso/{idCurso}/geracao")
	@ApiOperation(value = "Listar para geracao de sub-fases", notes = "Listar para geracao de sub-fases")
	public ResponseEntity<List<SubFaseResumoDTO>> listarParaGeracaoSubFaseExecucao(@PathVariable(value = "idCurso") Long idCurso) {

		List<SubFaseResumoDTO> subFases = subFaseExecucaoServico.listarParaGeracaoSubFaseExecucao(FiltroFase.builder().idCurso(idCurso).build());
		
		if (subFases == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(subFases);
	}
	
	@PostMapping(value="/sub-fases-execucao")
	@ApiOperation(value = "Cadastrar Sub Fase Execucao", notes = "Cadastrar Sub Fase Execucao")
	public ResponseEntity<String> criarSubFaseExecucao(@RequestBody List<SubFaseExecucaoDTO> dtos) {
		subFaseExecucaoServico.criar(dtos);
		return ResponseEntity.ok().body("Geração das sub-fases execução solicitada com sucesso");
	}
	
	@PutMapping("/sub-fases-execucao/{id}")
	@ApiOperation(value = "Alterar Fase Execucao", notes = "Alterar Fase Execucao")
	public ResponseEntity<String> alterarSubFaseExecucao(@PathVariable(value = "id") Long id, @RequestBody SubFaseExecucaoDTO dto) {
		dto.setId(id);
		subFaseExecucaoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do período de execução enviado com sucesso");
	}
	
	@GetMapping(value = "/sub-fases-execucao/{id}")
	@ApiOperation(value = "Buscar Sub-Fase Execucao por id", notes = "Buscar Sub-Fase Execucao por id")
	public ResponseEntity<SubFaseExecucaoDTO> buscarSubFaseExecucaoPorId(@PathVariable(value = "id") Long id) {

		SubFaseExecucaoDTO dto = subFaseExecucaoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping("/sub-fases-execucao/{id}")
	@ApiOperation(value = "Excluir Sub-Fase Execucao", notes = "Excluir Sub-Fase Execucao")
	public ResponseEntity<?> excluirSubFaseExecucao(@PathVariable(value = "id") Long id) {
		subFaseExecucaoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da sub-fase de execução enviada com sucesso");
	}
	
	@GetMapping(value="/sub-fases-execucao")
	@ApiOperation(value = "Listar Sub-Fases Execuções", notes = "Listar Sub-Fases Execuções")
	public ResponseEntity<PaginacaoDTO<SubFaseExecucaoDTO>> listarSubFasesExecucao(
			@RequestParam(value = "idCurso", required = false) Long idCurso,
			@RequestParam(value = "idPeriodoExecucao", required = false) Long idPeriodoExecucao,
			@RequestParam(value = "idFaseExecucao", required = false) Long idFaseExecucao,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<SubFaseExecucaoDTO> subFases = subFaseExecucaoServico
				.listar(FiltroSubFaseExecucao.builder()
									.idCurso(idCurso)
									.idPeriodoExecucao(idPeriodoExecucao)
									.idFaseExecucao(idFaseExecucao)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (subFases == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(subFases);
	}
	
	@GetMapping(value = "/visualiza-estrutura/curso/{idCurso}/ano/{ano}")
	@ApiOperation(value = "Visualizar Estrutura do Curso", notes = "Visualizar Estrutura do Curso")
	public ResponseEntity<List<EstruturaDTO>> visualizarEstrutura(@PathVariable(value = "idCurso") Long idCurso, 
			@PathVariable(value = "ano") Integer ano) {

		List<EstruturaDTO> dtos = periodoExecucaoServico.visualizarEstrutura(FiltroPeriodoExecucao.builder().idCurso(idCurso).ano(ano).build());
		if (dtos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dtos);
	}
	
	@GetMapping(value = "/combo-modulos-professores/curso/{idCurso}")
	@ApiOperation(value = "Listar Periodos Execucoes para combo dos modulos utilizados pelos professores", notes = "Listar Periodos Execucoes para combo dos modulos utilizados pelos professores")
	public ResponseEntity<List<PeriodoExecucaoResumoDTO>> listarParaModulosDosProfessores(@PathVariable(value = "idCurso") Long idCurso) {

		List<PeriodoExecucaoResumoDTO> periodos = periodoExecucaoServico.listarParaModulosDosProfessores(idCurso, servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		
		if (periodos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(periodos);
	}
}
