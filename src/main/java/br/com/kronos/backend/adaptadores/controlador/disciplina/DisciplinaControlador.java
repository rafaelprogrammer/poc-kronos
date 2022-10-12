package br.com.kronos.backend.adaptadores.controlador.disciplina;

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
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplina;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaCompetencia;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaDireito;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaHabilidade;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaObjetivo;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaCompetenciaDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaCompetenciaServico;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaDireitoDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaDireitoServico;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaHabilidadeDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaHabilidadeServico;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaObjetivoDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaObjetivoServico;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaPreRequisitoDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaResumoDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaServico;
import br.com.kronos.backend.aplicacao.disciplina.api.TipoDisciplinaDTO;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/disciplinas")
@CrossOrigin
public class DisciplinaControlador extends BaseControlador {

	@NonNull
	private DisciplinaServico disciplinaServico;
	
	@NonNull
	private DisciplinaDireitoServico disciplinaDireitoServico;
	
	@NonNull
	private DisciplinaObjetivoServico disciplinaObjetivoServico;
	
	@NonNull
	private DisciplinaCompetenciaServico disciplinaCompetenciaServico;
	
	@NonNull
	private DisciplinaHabilidadeServico disciplinaHabilidadeServico;
	
	@NonNull
	private ServicoAutenticacao servicoAutenticacao;

	@PostMapping
	@ApiOperation(value = "Cadastrar Disciplina", notes = "Cadastrar Disciplina")
	public ResponseEntity<String> criar(@RequestBody DisciplinaDTO dto) {
		disciplinaServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro da Disciplina enviada com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Disciplina", notes = "Alterar Disciplina")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody DisciplinaDTO dto) {
		dto.setId(id);
		disciplinaServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da disciplina enviada com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Disciplina", notes = "Excluir Disciplina")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		disciplinaServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da disciplina enviada com sucesso");
	}
	
	@GetMapping("/combo-pre-requisito")
	@ApiOperation(value = "Listar Disciplinas para combo", notes = "Listar Disciplinas para combo")
	public ResponseEntity<List<DisciplinaResumoDTO>> listarParaUsoEmPreRequisito(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "idCurso", required = true) Long idCurso,
			@RequestParam(value = "numeroPeriodo", required = true) Integer numeroPeriodo) {

		List<DisciplinaResumoDTO> disciplinas = disciplinaServico
				.listarParaUsoEmPreRequisito(idCurso, numeroPeriodo, id);
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	
	@GetMapping("/combo")
	@ApiOperation(value = "Listar Disciplinas para combo", notes = "Listar Disciplinas para combo")
	public ResponseEntity<List<DisciplinaResumoDTO>> listarParaCombo(@RequestParam(value = "idPeriodo", required = true) Long idPeriodo) {

		List<DisciplinaResumoDTO> disciplinas = disciplinaServico.listarParaCombo(FiltroDisciplina.builder().idPeriodo(idPeriodo).build());
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	
	@GetMapping("/combo-disciplinas-unificacoes")
	@ApiOperation(value = "Listar Disciplinas Unificacoes para combo", notes = "Listar Disciplinas Unificacoes para combo")
	public ResponseEntity<List<DisciplinaResumoDTO>> listarParaCombo(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idPeriodo", required = true) Long idPeriodo) {

		
		List<DisciplinaResumoDTO> disciplinas = disciplinaServico.listarParaComboUnificacao(FiltroDisciplina.builder().idPeriodo(idPeriodo).id(id).build());
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	
	
	@GetMapping
	@ApiOperation(value = "Listar Disciplinas", notes = "Listar Disciplinas")
	public ResponseEntity<PaginacaoDTO<DisciplinaDTO>> listar(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idPeriodo", required = false) Long idPeriodo,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<DisciplinaDTO> disciplinas = disciplinaServico
				.listar(FiltroDisciplina.builder()
									.id(id)
									.idPeriodo(idPeriodo)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Disciplina por id", notes = "Buscar Disciplina por id")
	public ResponseEntity<DisciplinaDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		DisciplinaDTO dto = disciplinaServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/tipos")
	@ApiOperation(value = "Listar Tipos de Disciplinas", notes = "Listar Tipos de Disciplinas")
	public ResponseEntity<List<TipoDisciplinaDTO>> tipos() {
		return ResponseEntity.ok().body(TipoDisciplinaDTO.tipos());
	}
	
	@GetMapping(value = "/pre-requisitos/{id}")
	@ApiOperation(value = "Buscar Pre-requisitos da Disciplina por id", notes = "Buscar Pre-requisitos da Disciplina por id")
	public ResponseEntity<List<Long>> buscarPreRequisitoRegistrado(@PathVariable(value = "id") Long id) {

		List<Long> ids = disciplinaServico.buscarPreRequisitosRegistrados(id);
		if (ids == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(ids);
	}
	
	@PostMapping("/pre-requisitos")
	@ApiOperation(value = "Cadastrar Pre-requisitos da disciplina", notes = "Cadastrar Pre-requisitos da disciplina")
	public ResponseEntity<String> registrarPreRequisitos(@RequestBody DisciplinaPreRequisitoDTO dto) {
		disciplinaServico.registrarPreRequisito(dto);
		return ResponseEntity.ok().body("Registro de Pré-requisitos enviado com sucesso");
	}

	@DeleteMapping("/pre-requisitos/{id}")
	@ApiOperation(value = "Excluir Pre-requisitos da disciplina", notes = "Excluir Pre-requisitos da disciplina")
	public ResponseEntity<?> excluirPreRequisitos(@PathVariable(value = "id") Long id) {
		disciplinaServico.excluirPreRequisitos(DisciplinaPreRequisitoDTO.builder().idDisciplina(id).build());
		return ResponseEntity.ok().body("Exclusão dos Pré-requisitos enviado com sucesso");
	}
	
	@GetMapping("/combo/periodo-execucao/{idPeriodoExecucao}")
	@ApiOperation(value = "Listar Disciplinas por periodo execucao", notes = "Listar Disciplinas por periodo execucao")
	public ResponseEntity<List<DisciplinaResumoDTO>> listarPorPeriodoExecucao(@PathVariable(value = "idPeriodoExecucao", required = true) Long idPeriodoExecucao) {

		List<DisciplinaResumoDTO> disciplinas = disciplinaServico
				.listarPorPeriodoExecucao(idPeriodoExecucao);
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	
	@GetMapping("/combo-modulos-professores/turma/{idTurma}")
	@ApiOperation(value = "Listar Disciplinas para combo dos modulos utilizados pelos professores", notes = "Listar Disciplinas para combo dos modulos utilizados pelos professores")
	public ResponseEntity<List<DisciplinaResumoDTO>> listarParaModulosDosProfessores(@PathVariable(value = "idTurma", required = true) Long idTurma) {

		List<DisciplinaResumoDTO> disciplinas = disciplinaServico
				.listarParaModulosDosProfessores(idTurma, servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	
	// ### DISCIPLINAS DIREITOS
	@PostMapping(value="/direitos")
	@ApiOperation(value = "Cadastrar Disciplina Direito", notes = "Cadastrar Disciplina Direito")
	public ResponseEntity<String> criarDisciplinaDireito(@RequestBody DisciplinaDireitoDTO dto) {
		disciplinaDireitoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro da Disciplina Direito enviada com sucesso");
	}
	
	@PutMapping("/direitos/{id}")
	@ApiOperation(value = "Alterar Disciplina Direito", notes = "Alterar Disciplina Direito")
	public ResponseEntity<String> alterarDisciplinaDireito(@PathVariable(value = "id") Long id, @RequestBody DisciplinaDireitoDTO dto) {
		dto.setId(id);
		disciplinaDireitoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da Disciplina Direito enviada com sucesso");
	}
	
	@DeleteMapping("/direitos/{id}")
	@ApiOperation(value = "Excluir Disciplina Direito", notes = "Excluir Disciplina Direito")
	public ResponseEntity<?> excluirDisciplinaDireito(@PathVariable(value = "id") Long id) {
		disciplinaDireitoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da Disciplina Direito enviada com sucesso");
	}
	
	@GetMapping("/direitos/atividades-disciplinas-direitos")
	@ApiOperation(value = "Listar Disciplinas Direitos das Atividades", notes = "Listar Disciplinas Direitos das Atividades")
	public ResponseEntity<PaginacaoDTO<DisciplinaDireitoDTO>> listarParaAtividadeDisciplinaDireito(
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idAtividade", required = false) Long idAtividade,
			@RequestParam(value = "idSubFase", required = false) Long idSubFase,
			@RequestParam(value = "codigo", required = false) String codigo,
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<DisciplinaDireitoDTO> disciplinas = disciplinaDireitoServico
				.listarParaAtividadeDisciplinaDireito(FiltroDisciplinaDireito.builder()
									.idDisciplina(idDisciplina)
									.idAtividade(idAtividade)
									.idSubFase(idSubFase)
									.codigo(codigo)
									.descricao(descricao)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	
	@GetMapping("/direitos")
	@ApiOperation(value = "Listar Disciplinas Direitos", notes = "Listar Disciplinas Direitos")
	public ResponseEntity<PaginacaoDTO<DisciplinaDireitoDTO>> listarDisciplinasDireitos(
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<DisciplinaDireitoDTO> disciplinas = disciplinaDireitoServico
				.listar(FiltroDisciplinaDireito.builder()
									.idDisciplina(idDisciplina)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	
	@GetMapping(value = "/direitos/{id}")
	@ApiOperation(value = "Buscar Disciplina Direito por id", notes = "Buscar Disciplina Direito por id")
	public ResponseEntity<DisciplinaDireitoDTO> buscarDireitoDisciplinaPorId(@PathVariable(value = "id") Long id) {

		DisciplinaDireitoDTO dto = disciplinaDireitoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	// ### FIM DISCIPLINAS DIREITOS
	
	// ### DISCIPLINAS OBJETIVOS
	@PostMapping(value="/objetivos")
	@ApiOperation(value = "Cadastrar Disciplina Objetivo", notes = "Cadastrar Disciplina Objetivo")
	public ResponseEntity<String> criarDisciplinaObjetivo(@RequestBody DisciplinaObjetivoDTO dto) {
		disciplinaObjetivoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro da Disciplina Objetivo enviada com sucesso");
	}
	
	@PutMapping("/objetivos/{id}")
	@ApiOperation(value = "Alterar Disciplina Objetivo", notes = "Alterar Disciplina Objetivo")
	public ResponseEntity<String> alterarDisciplinaObjetivo(@PathVariable(value = "id") Long id, @RequestBody DisciplinaObjetivoDTO dto) {
		dto.setId(id);
		disciplinaObjetivoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da Disciplina Objetivo enviada com sucesso");
	}
	
	@DeleteMapping("/objetivos/{id}")
	@ApiOperation(value = "Excluir Disciplina Objetivo", notes = "Excluir Disciplina Objetivo")
	public ResponseEntity<?> excluirDisciplinaObjetivo(@PathVariable(value = "id") Long id) {
		disciplinaObjetivoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da Disciplina Objetivo enviada com sucesso");
	}
	
	@GetMapping("/objetivos/atividades-disciplinas-objetivos")
	@ApiOperation(value = "Listar Disciplinas Objetivos das Atividades", notes = "Listar Disciplinas Objetivos das Atividades")
	public ResponseEntity<PaginacaoDTO<DisciplinaObjetivoDTO>> listarParaAtividadeDisciplinaObjetivo(
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idAtividade", required = false) Long idAtividade,
			@RequestParam(value = "idSubFase", required = false) Long idSubFase,
			@RequestParam(value = "codigo", required = false) String codigo,
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<DisciplinaObjetivoDTO> disciplinas = disciplinaObjetivoServico
				.listarParaAtividadeDisciplinaObjetivo(FiltroDisciplinaObjetivo.builder()
									.idDisciplina(idDisciplina)
									.idAtividade(idAtividade)
									.idSubFase(idSubFase)
									.codigo(codigo)
									.descricao(descricao)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	
	@GetMapping("/objetivos")
	@ApiOperation(value = "Listar Disciplinas Objetivos", notes = "Listar Disciplinas Objetivos")
	public ResponseEntity<PaginacaoDTO<DisciplinaObjetivoDTO>> listarDisciplinasObjetivos(
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<DisciplinaObjetivoDTO> disciplinas = disciplinaObjetivoServico
				.listar(FiltroDisciplinaObjetivo.builder()
									.idDisciplina(idDisciplina)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	
	@GetMapping(value = "/objetivos/{id}")
	@ApiOperation(value = "Buscar Disciplina Objetivo por id", notes = "Buscar Disciplina Objetivo por id")
	public ResponseEntity<DisciplinaObjetivoDTO> buscarDisciplinaObjetivoPorId(@PathVariable(value = "id") Long id) {

		DisciplinaObjetivoDTO dto = disciplinaObjetivoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	// ### FIM DISCIPLINAS OBJETIVOS
	
	// ### DISCIPLINAS COMPETENCIAS
	@PostMapping(value="/competencias")
	@ApiOperation(value = "Cadastrar Disciplina Competencia", notes = "Cadastrar Disciplina Competencia")
	public ResponseEntity<String> criarDisciplinaCompetencia(@RequestBody DisciplinaCompetenciaDTO dto) {
		disciplinaCompetenciaServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro da Disciplina Competencia enviada com sucesso");
	}
	
	@PutMapping("/competencias/{id}")
	@ApiOperation(value = "Alterar Disciplina Competencia", notes = "Alterar Disciplina Competencia")
	public ResponseEntity<String> alterarDisciplinaCompetencia(@PathVariable(value = "id") Long id, @RequestBody DisciplinaCompetenciaDTO dto) {
		dto.setId(id);
		disciplinaCompetenciaServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da Disciplina Competencia enviada com sucesso");
	}
	
	@DeleteMapping("/competencias/{id}")
	@ApiOperation(value = "Excluir Disciplina Competencia", notes = "Excluir Disciplina Competencia")
	public ResponseEntity<?> excluirDisciplinaCompetencia(@PathVariable(value = "id") Long id) {
		disciplinaCompetenciaServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da Disciplina Competencia enviada com sucesso");
	}
	
	@GetMapping("/competencias/atividades-disciplinas-competencias")
	@ApiOperation(value = "Listar Disciplinas Competencias das Atividades", notes = "Listar Disciplinas Competencias das Atividades")
	public ResponseEntity<PaginacaoDTO<DisciplinaCompetenciaDTO>> listarParaAtividadeDisciplinaCompetencia(
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idAtividade", required = false) Long idAtividade,
			@RequestParam(value = "codigo", required = false) String codigo,
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "idSubFase", required = false) Long idSubFase,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<DisciplinaCompetenciaDTO> disciplinas = disciplinaCompetenciaServico
				.listarParaAtividadeDisciplinaCompetencia(FiltroDisciplinaCompetencia.builder()
									.idDisciplina(idDisciplina)
									.idAtividade(idAtividade)
									.idSubFase(idSubFase)
									.codigo(codigo)
									.descricao(descricao)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	
	@GetMapping("/competencias")
	@ApiOperation(value = "Listar Disciplinas Competencias", notes = "Listar Disciplinas Competencias")
	public ResponseEntity<PaginacaoDTO<DisciplinaCompetenciaDTO>> listarDisciplinasCompetencias(
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<DisciplinaCompetenciaDTO> competencias = disciplinaCompetenciaServico
				.listar(FiltroDisciplinaCompetencia.builder()
									.idDisciplina(idDisciplina)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (competencias == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(competencias);
	}
	
	@GetMapping(value = "/competencias/{id}")
	@ApiOperation(value = "Buscar Disciplina Competencia por id", notes = "Buscar Disciplina Competencia por id")
	public ResponseEntity<DisciplinaCompetenciaDTO> buscarDisciplinaCompetenciaPorId(@PathVariable(value = "id") Long id) {

		DisciplinaCompetenciaDTO dto = disciplinaCompetenciaServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	// ### FIM DISCIPLINAS COMPETENCIAS
	
	// ### DISCIPLINAS HABILIDADES
	@PostMapping(value="/habilidades")
	@ApiOperation(value = "Cadastrar Disciplina Habilidade", notes = "Cadastrar Disciplina Habilidade")
	public ResponseEntity<String> criarDisciplinaHabilidade(@RequestBody DisciplinaHabilidadeDTO dto) {
		disciplinaHabilidadeServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro da Disciplina Competencia enviada com sucesso");
	}
	
	@PutMapping("/habilidades/{id}")
	@ApiOperation(value = "Alterar Disciplina Habilidade", notes = "Alterar Disciplina Habilidade")
	public ResponseEntity<String> alterarDisciplinaHabilidade(@PathVariable(value = "id") Long id, @RequestBody DisciplinaHabilidadeDTO dto) {
		dto.setId(id);
		disciplinaHabilidadeServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da Disciplina Habilidade enviada com sucesso");
	}
	
	@DeleteMapping("/habilidades/{id}")
	@ApiOperation(value = "Excluir Disciplina Habilidade", notes = "Excluir Disciplina Habilidade")
	public ResponseEntity<?> excluirDisciplinaHabilidade(@PathVariable(value = "id") Long id) {
		disciplinaHabilidadeServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da Disciplina Habilidade enviada com sucesso");
	}
	
	@GetMapping("/habilidades/atividades-disciplinas-habilidades")
	@ApiOperation(value = "Listar Disciplinas Habilidades das Atividades", notes = "Listar Disciplinas Habilidades das Atividades")
	public ResponseEntity<PaginacaoDTO<DisciplinaHabilidadeDTO>> listarParaAtividadeDisciplinaHabilidade(
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idAtividade", required = false) Long idAtividade,
			@RequestParam(value = "idSubFase", required = false) Long idSubFase,
			@RequestParam(value = "codigoHabilidade", required = false) String codigoHabilidade,
			@RequestParam(value = "descricaoHabilidade", required = false) String descricaoHabilidade,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<DisciplinaHabilidadeDTO> disciplinas = disciplinaHabilidadeServico
				.listarParaAtividadeDisciplinaHabilidade(FiltroDisciplinaHabilidade.builder()
									.idDisciplina(idDisciplina)
									.idAtividade(idAtividade)
									.idSubFase(idSubFase)
									.codigoHabilidade(codigoHabilidade)
									.descricaoHabilidade(descricaoHabilidade)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	
	@GetMapping("/habilidades/avaliacoes-habilidades")
	@ApiOperation(value = "Listar Avaliações Habilidades", notes = "Listar Avaliações Habilidades")
	public ResponseEntity<PaginacaoDTO<DisciplinaHabilidadeDTO>> listarParaAvaliacaoHabilidade(
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idAvaliacao", required = false) Long idAvaliacao,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<DisciplinaHabilidadeDTO> disciplinas = disciplinaHabilidadeServico
				.listarParaAvaliacaoHabilidade(FiltroDisciplinaHabilidade.builder()
									.idDisciplina(idDisciplina)
									.idAvaliacao(idAvaliacao)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (disciplinas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(disciplinas);
	}
	
	@GetMapping("/habilidades")
	@ApiOperation(value = "Listar Disciplinas Habilidades", notes = "Listar Disciplinas Habilidades")
	public ResponseEntity<PaginacaoDTO<DisciplinaHabilidadeDTO>> listarDisciplinasHabilidades(
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<DisciplinaHabilidadeDTO> habilidades = disciplinaHabilidadeServico
				.listar(FiltroDisciplinaHabilidade.builder()
									.idDisciplina(idDisciplina)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (habilidades == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(habilidades);
	}
	
	@GetMapping(value = "/habilidades/{id}")
	@ApiOperation(value = "Buscar Disciplina Habilidade por id", notes = "Buscar Disciplina Habilidade por id")
	public ResponseEntity<DisciplinaHabilidadeDTO> buscarDisciplinaHabilidadePorId(@PathVariable(value = "id") Long id) {

		DisciplinaHabilidadeDTO dto = disciplinaHabilidadeServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	// ### FIM DISCIPLINAS HABILIDADES
}
