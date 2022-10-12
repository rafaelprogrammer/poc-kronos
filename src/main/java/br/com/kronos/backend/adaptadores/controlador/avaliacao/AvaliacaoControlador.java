
package br.com.kronos.backend.adaptadores.controlador.avaliacao;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import br.com.kronos.backend.aplicacao.avaliacao.FiltroAvaliacao;
import br.com.kronos.backend.aplicacao.avaliacao.FiltroAvaliacaoHabilidade;
import br.com.kronos.backend.aplicacao.avaliacao.FiltroGrupoAvaliacao;
import br.com.kronos.backend.aplicacao.avaliacao.api.AvaliacaoDTO;
import br.com.kronos.backend.aplicacao.avaliacao.api.AvaliacaoHabilidadeDTO;
import br.com.kronos.backend.aplicacao.avaliacao.api.AvaliacaoHabilidadeServico;
import br.com.kronos.backend.aplicacao.avaliacao.api.AvaliacaoServico;
import br.com.kronos.backend.aplicacao.avaliacao.api.GrupoAvaliacaoDTO;
import br.com.kronos.backend.aplicacao.avaliacao.api.GrupoAvaliacaoServico;
import br.com.kronos.backend.aplicacao.avaliacao.api.TipoAbrangenciaDTO;
import br.com.kronos.backend.aplicacao.avaliacao.api.TipoAvaliacaoDTO;
import br.com.kronos.backend.aplicacao.avaliacao.api.TipoFormatoDTO;
import br.com.kronos.backend.aplicacao.avaliacao.api.TipoRegistroNotaDTO;
import br.com.kronos.backend.aplicacao.resultado.FiltroAvaliado;
import br.com.kronos.backend.aplicacao.resultado.api.AvaliadoDTO;
import br.com.kronos.backend.aplicacao.resultado.api.AvaliadoServico;
import br.com.kronos.backend.aplicacao.resultado.api.ResultadoHabilidadeDTO;
import br.com.kronos.backend.aplicacao.resultado.api.ResultadoHabilidadeServico;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/avaliacoes")
@CrossOrigin
public class AvaliacaoControlador extends BaseControlador {
	
	@NotNull
	private AvaliacaoServico avaliacaoServico;
	
	@NotNull
	private AvaliadoServico avaliadoServico;
	
	@NotNull
	private GrupoAvaliacaoServico grupoAvaliacaoServico;
	
	@NotNull
	private AvaliacaoHabilidadeServico avaliacaoHabilidadeServico;
	
	@NotNull
	private ResultadoHabilidadeServico resultadoHabilidadeServico;
	
	@PostMapping("/automatica")
	@ApiOperation(value = "Criar Avaliacao Automaticamente", notes = "Criar Avaliacao Automaticamente")
	public ResponseEntity<String> alterar(@RequestBody AvaliacaoDTO dto) {
		avaliacaoServico.criarAvaliacaoAutomatica(dto);
		return ResponseEntity.ok().body("Avaliação criada com sucesso");
	}
	
	@PostMapping
	@ApiOperation(value = "Cadastrar Avaliacao", notes = "Cadastrar Avaliacao")
	public ResponseEntity<String> criar(@RequestBody AvaliacaoDTO dto) {
		avaliacaoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro da avaliação enviada com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Avaliacao", notes = "Alterar Avaliacao")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody AvaliacaoDTO dto) {
		dto.setId(id);
		avaliacaoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da avaliação enviada com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Avaliacao", notes = "Excluir Avaliacao")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		avaliacaoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da avaliação enviada com sucesso");
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Avaliacao por id", notes = "Buscar Avaliacao por id")
	public ResponseEntity<AvaliacaoDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		AvaliacaoDTO dto = avaliacaoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/combo")
	@ApiOperation(value = "Listar Avaliacoes para combo", notes = "Listar Avaliacoes para combo")
	public ResponseEntity<List<AvaliacaoDTO>> listarParaCombo(@RequestParam(value = "idTurma", required = false) Long idTurma,
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina) {

		List<AvaliacaoDTO> avaliacoes = avaliacaoServico
				.listarParaCombo(FiltroAvaliacao.builder()
									.idTurma(idTurma)
									.idDisciplina(idDisciplina)
									.build());
		
		if (avaliacoes == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(avaliacoes);
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Avaliacoes", notes = "Listar Avaliacoes")
	public ResponseEntity<PaginacaoDTO<AvaliacaoDTO>> listar(@RequestParam(value = "idTurma", required = false) Long idTurma,
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<AvaliacaoDTO> avaliacoes = avaliacaoServico
				.listar(FiltroAvaliacao.builder()
									.idTurma(idTurma)
									.idDisciplina(idDisciplina)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (avaliacoes == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(avaliacoes);
	}
	

	@GetMapping(value="/tipos")
	@ApiOperation(value = "Listar Tipos de Avaliacoes", notes = "Listar tipos de Avaliacoes")
	public ResponseEntity<List<TipoAvaliacaoDTO>> tipos() {
		return ResponseEntity.ok().body(TipoAvaliacaoDTO.tipos());
	}
	
	@GetMapping(value="/tipos-abrangencias")
	@ApiOperation(value = "Listar Tipos de Abrangencias", notes = "Listar tipos de Abrangencias")
	public ResponseEntity<List<TipoAbrangenciaDTO>> tiposAbrangencias() {
		return ResponseEntity.ok().body(TipoAbrangenciaDTO.tipos());
	}
	
	@GetMapping(value="/tipos-formatos")
	@ApiOperation(value = "Listar Tipos de Formatos", notes = "Listar tipos de Formatos")
	public ResponseEntity<List<TipoFormatoDTO>> tiposFormatos() {
		return ResponseEntity.ok().body(TipoFormatoDTO.tipos());
	}
	
	@GetMapping(value="/tipos-registros-notas")
	@ApiOperation(value = "Listar Tipos de Registro Notas", notes = "Listar tipos de Registro Notas")
	public ResponseEntity<List<TipoRegistroNotaDTO>> tiposRegistrosNotas() {
		return ResponseEntity.ok().body(TipoRegistroNotaDTO.tipos());
	}
	
	@PostMapping(value="/avaliados")
	@ApiOperation(value = "Cadastrar Avaliados", notes = "Cadastrar Avaliados")
	public ResponseEntity<String> criar(@RequestBody List<AvaliadoDTO> dtos) {
		avaliadoServico.criarAvaliados(dtos);
		return ResponseEntity.ok().body("Cadastro de avaliados enviado com sucesso");
	}
	
	@GetMapping(value="/avaliados")
	@ApiOperation(value = "Listar Avaliados", notes = "Listar Avaliados")
	public ResponseEntity<List<AvaliadoDTO>> listarParaSelecaoNaAvaliacao(
			@RequestParam(value = "idAvaliacao", required = false) Long idAvaliacao,
			@RequestParam(value = "idTurma", required = false) Long idTurma,
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "anoTurma", required = false) Integer anoTurma,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		List<AvaliadoDTO> avaliados = avaliadoServico
				.listarParaSelecaoNaAvaliacao(FiltroAvaliado.builder()
									.idAvaliacao(idAvaliacao)
									.idTurma(idTurma)
									.anoTurma(anoTurma)
									.idDisciplina(idDisciplina)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (avaliados == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(avaliados);
	}
	
	@GetMapping(value="/avaliados-mencoes")
	@ApiOperation(value = "Listar Avaliados com mencoes", notes = "Listar Avaliados com mencoes")
	public ResponseEntity<List<AvaliadoDTO>> listarResultadoHabiliadeDoAvaliado(@RequestParam(value = "idAvaliacao", required = false) Long idAvaliacao,
			@RequestParam(value = "dataAvaliacao", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") @Valid LocalDate dataAvaliacao,
			@RequestParam(value = "anoTurma", required = false) Integer anoTurma,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		List<AvaliadoDTO> avaliados = avaliadoServico
				.listarResultadoHabiliadeDoAvaliado(FiltroAvaliado.builder()
									.idAvaliacao(idAvaliacao)
									.dataAvaliacao(dataAvaliacao)
									.anoTurma(anoTurma)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (avaliados == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(avaliados);
	}

	
	@PostMapping(value="/habilidades/resultados")
	@ApiOperation(value = "Cadastrar Resultado Habilidade", notes = "Cadastrar Resultado Habilidade")
	public ResponseEntity<String> criarResultadosHabilidades(@RequestBody ResultadoHabilidadeDTO dto) {
		resultadoHabilidadeServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do Resultado Habilidade enviado com sucesso");
	}
	
	// ### GRUPOS AVALIACAOES
	@PostMapping("/grupos-avaliacoes")
	@ApiOperation(value = "Cadastrar Grupos Avaliacoes", notes = "Cadastrar Grupos Avaliacoes")
	public ResponseEntity<String> criarGruposAvaliacoes(@RequestBody GrupoAvaliacaoDTO dto) {
		grupoAvaliacaoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do grupo avaliação enviado com sucesso");
	}
	
	@PutMapping("/grupos-avaliacoes/{id}")
	@ApiOperation(value = "Alterar Grupos Avaliacoes", notes = "Alterar Grupos Avaliacoes")
	public ResponseEntity<String> alterarGruposAvaliacoes(@PathVariable(value = "id") Long id, @RequestBody GrupoAvaliacaoDTO dto) {
		dto.setId(id);
		grupoAvaliacaoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da grupo avaliação enviado com sucesso");
	}
	
	@DeleteMapping("/grupos-avaliacoes/{id}")
	@ApiOperation(value = "Excluir Grupo Avaliacao", notes = "Excluir Grupo Avaliacao")
	public ResponseEntity<?> excluirGrupoAvaliacao(@PathVariable(value = "id") Long id) {
		grupoAvaliacaoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do grupo avaliação enviado com sucesso");
	}
	
	@GetMapping(value = "/grupos-avaliacoes/{id}")
	@ApiOperation(value = "Buscar Avaliacao por id", notes = "Buscar Avaliacao por id")
	public ResponseEntity<GrupoAvaliacaoDTO> buscarGrupoAvaliacaoPorId(@PathVariable(value = "id") Long id) {

		GrupoAvaliacaoDTO dto = grupoAvaliacaoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/grupos-avaliacoes/existe/avaliacao/{idAvaliacao}")
	@ApiOperation(value = "Verificar se existe grupo avaliacao registrado para uma avaliacao", notes = "Verificar se existe grupo avaliacao registrado para uma avaliacao")
	public ResponseEntity<Boolean> verificarSeExisteGrupoAvaliacaoCadastrado(@PathVariable(value = "idAvaliacao") Long idAvaliacao) {

		return ResponseEntity.ok().body(grupoAvaliacaoServico.verificarSeExisteGrupoAvaliacaoCadastrado(FiltroGrupoAvaliacao.builder()
				.idAvaliacao(idAvaliacao).build()));
	}
	
	@GetMapping(value = "/grupos-avaliacoes")
	@ApiOperation(value = "Listar Grupos Avaliacoes", notes = "Listar Grupos Avaliacoes")
	public ResponseEntity<PaginacaoDTO<GrupoAvaliacaoDTO>> listar(
			@RequestParam(value = "idAvaliacao", required = false) Long idAvaliacao,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<GrupoAvaliacaoDTO> gruposAvaliacoes = grupoAvaliacaoServico
				.listar(FiltroGrupoAvaliacao.builder()
									.idAvaliacao(idAvaliacao)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (gruposAvaliacoes == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(gruposAvaliacoes);
	}
	
	@GetMapping(value="/grupos-avaliacoes/combo")
	@ApiOperation(value = "Listar Grupos Avaliacoes", notes = "Listar Grupos Avaliacoes")
	public ResponseEntity<List<GrupoAvaliacaoDTO>> listarGruposAvaliacoesCombo(
			@RequestParam(value = "idAvaliacao", required = false) Long idAvaliacao) {

		List<GrupoAvaliacaoDTO> grupos = grupoAvaliacaoServico
				.listarParaCombo(FiltroGrupoAvaliacao.builder()
									.idAvaliacao(idAvaliacao)
									.build());
		
		if (grupos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(grupos);
	}
	// ### FINAL GRUPOS AVALIACAOES
	
	// ### AVALIACAOES HABILIDADES
	@PostMapping("/habilidades")
	@ApiOperation(value = "Cadastrar Avaliacoes Habilidades", notes = "Cadastrar Avaliacoes Habilidades")
	public ResponseEntity<String> criarAvaliacaoHabilidade(@RequestBody List<AvaliacaoHabilidadeDTO> dtos) {
		avaliacaoHabilidadeServico.criarAvaliacoesHabilidades(dtos);
		return ResponseEntity.ok().body("Cadastro da avaliação habilidade enviada com sucesso");
	}
	
	@DeleteMapping("/habilidades/{id}")
	@ApiOperation(value = "Excluir Avaliacoes Habilidades", notes = "Excluir Avaliacoes Habilidades")
	public ResponseEntity<?> excluirAvaliacaoHabilidade(@PathVariable(value = "id") Long id) {
		avaliacaoHabilidadeServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da avaliação habilidade enviada com sucesso");
	}
	
	@GetMapping(value="/habilidades")
	@ApiOperation(value = "Listar Avaliacoes Habilidades", notes = "Listar Avaliacoes Habilidades")
	public ResponseEntity<List<AvaliacaoHabilidadeDTO>> listarAvaliacaoesHabilidades(@RequestParam(value = "idAvaliacao", required = false) Long idAvaliacao,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		List<AvaliacaoHabilidadeDTO> avaliacoes = avaliacaoHabilidadeServico
				.listar(FiltroAvaliacaoHabilidade.builder()
									.idAvaliacao(idAvaliacao)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (avaliacoes == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(avaliacoes);
   }
	
	// ### FIM AVALIACAOES HABILIDADES
}
