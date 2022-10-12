package br.com.kronos.backend.adaptadores.controlador.ocorrencia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioDTO;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioServico;
import br.com.kronos.backend.aplicacao.ocorrencia.FiltroOcorrencia;
import br.com.kronos.backend.aplicacao.ocorrencia.FiltroTipoOcorrencia;
import br.com.kronos.backend.aplicacao.ocorrencia.api.FatorDTO;
import br.com.kronos.backend.aplicacao.ocorrencia.api.OcorrenciaDTO;
import br.com.kronos.backend.aplicacao.ocorrencia.api.OcorrenciaServico;
import br.com.kronos.backend.aplicacao.ocorrencia.api.TipoOcorrenciaDTO;
import br.com.kronos.backend.aplicacao.ocorrencia.api.TipoOcorrenciaServico;
import br.com.kronos.backend.aplicacao.ocorrencia.api.VigenteDTO;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ocorrencias")
@CrossOrigin
public class OcorrenciaControlador extends BaseControlador {

	@NonNull
	private TipoOcorrenciaServico tipoOcorrenciaServico;

	@NonNull
	private FuncionarioServico funcionarioService;

	@NonNull
	private OcorrenciaServico ocorrenciaServico;

	@NonNull
	private ServicoAutenticacao servicoAutenticacao;

	@PostMapping()
	@ApiOperation(value = "Cadastrar Ocorrencias", notes = "Cadastrar Ocorrencias")
	public ResponseEntity<String> criar(@RequestBody OcorrenciaDTO dto) {
		incluirFuncionarioRegistro(dto);
		ocorrenciaServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro da Ocorrência enviada com sucesso");
	}

	private void incluirFuncionarioRegistro(OcorrenciaDTO dto) {
		FuncionarioDTO funcionarioDTO = funcionarioService
				.buscarPorIdPessoa(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		dto.setIdFuncionarioRegistro(funcionarioDTO.getId());
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Ocorrencias", notes = "Alterar Ocorrencias")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody OcorrenciaDTO dto) {
		dto.setId(id);
		ocorrenciaServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da Ocorrência enviada com sucesso");
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Ocorrencias", notes = "Excluir Ocorrencias")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		ocorrenciaServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da Ocorrência enviada com sucesso");
	}
	
	@GetMapping()
	@ApiOperation(value = "Listar Ocorrencias", notes = "Listar Ocorrencias")
	public ResponseEntity<PaginacaoDTO<OcorrenciaDTO>> listar(
			@RequestParam(value = "idTipoOcorrencia", required = false) Long idTipoOcorrencia,
			@RequestParam(value = "idMatricula", required = false) Long idMatricula,
			@RequestParam(value = "idPessoa", required = false) Long idPessoa,
			@RequestParam(value = "idTurma", required = false) Long idTurma,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<OcorrenciaDTO> ocorrencias = ocorrenciaServico
				.listar(FiltroOcorrencia.builder().idTipoOcorrencia(idTipoOcorrencia).idMatricula(idMatricula)
						.idPessoa(idPessoa).idTurma(idTurma).qtdTotal(total).numeroPagina(pagina).build());

		if (ocorrencias == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(ocorrencias);
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Ocorrencias por id", notes = "Buscar Ocorrencias por id")
	public ResponseEntity<OcorrenciaDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		OcorrenciaDTO dto = ocorrenciaServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/grauComportamento/pessoa/{id}")
	@ApiOperation(value = "Retornar o grau do comportamento de acordo com as ocorrencias", notes = "Retornar o grau do comportamento de acordo com as ocorrencias")
	public ResponseEntity<Float> re(@PathVariable(value = "id") Long id) {

		Float grau = ocorrenciaServico.returnaValorGrauComportamento(id);
		if (grau == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(grau);
	}

	@PostMapping("/tipos")
	@ApiOperation(value = "Cadastrar Tipos Ocorrencias", notes = "Cadastrar Tipos Ocorrencias")
	public ResponseEntity<String> criarTipo(@RequestBody TipoOcorrenciaDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		tipoOcorrenciaServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do Tipo da Ocorrência enviado com sucesso");
	}

	@PutMapping("/tipos/{id}")
	@ApiOperation(value = "Alterar Tipos Ocorrencias", notes = "Alterar Tipos Ocorrencias")
	public ResponseEntity<String> alterarTipo(@PathVariable(value = "id") Long id, @RequestBody TipoOcorrenciaDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		dto.setId(id);
		tipoOcorrenciaServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do Tipo da Ocorrência enviado com sucesso");
	}

	@DeleteMapping("/tipos/{id}")
	@ApiOperation(value = "Excluir Tipos Ocorrencias", notes = "Excluir Tipos Ocorrencias")
	public ResponseEntity<?> excluirTipo(@PathVariable(value = "id") Long id) {
		tipoOcorrenciaServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do Tipo da Ocorrência enviado com sucesso");
	}
	
	@GetMapping("/tipos")
	@ApiOperation(value = "Listar Tipos Ocorrencias", notes = "Listar Tipos Ocorrencias")
	public ResponseEntity<PaginacaoDTO<TipoOcorrenciaDTO>> listarTipos(
			@RequestParam(value = "codigo", required = false) String codigo,
			@RequestParam(value = "fator", required = false) Integer fator,
			@RequestParam(value = "vigente", required = false) String vigente,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<TipoOcorrenciaDTO> tipos = tipoOcorrenciaServico.listar(FiltroTipoOcorrencia.builder()
				.codigo(codigo).fator(fator).vigente(vigente).qtdTotal(total).numeroPagina(pagina).build());

		if (tipos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(tipos);
	}

	@GetMapping(value = "/tipos/{id}")
	@ApiOperation(value = "Buscar Tipos Ocorrencias por id", notes = "Buscar Tipos Ocorrencias por id")
	public ResponseEntity<TipoOcorrenciaDTO> buscarTipoPorId(@PathVariable(value = "id") Long id) {

		TipoOcorrenciaDTO dto = tipoOcorrenciaServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/tipos/combo-vigentes")
	@ApiOperation(value = "Listar Opcoes Vigentes para combo", notes = "Listar Opcoes Vigentes para combo")
	public ResponseEntity<List<VigenteDTO>> listarVigentes() {

		return ResponseEntity.ok().body(VigenteDTO.fatores());
	}

	@GetMapping(value = "/tipos/combo-fatores")
	@ApiOperation(value = "Listar Fatores para combo", notes = "Listar Fatores para combo")
	public ResponseEntity<List<FatorDTO>> listarFatores() {
		return ResponseEntity.ok().body(FatorDTO.fatores());
	}

	@GetMapping(value = "/tipos/combo")
	@ApiOperation(value = "Listar Tipos Ocorrencias para combo por data", notes = "Listar Tipos Ocorrencias para combo por data")
	public ResponseEntity<List<TipoOcorrenciaDTO>> listarParaOcorrencia(
			@RequestParam(value = "dataOcorrencia") String dataOcorrencia) {

		List<TipoOcorrenciaDTO> dtos = tipoOcorrenciaServico
				.listarParaOcorrencia(LocalDate.parse(dataOcorrencia, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		if (dtos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dtos);

	}

}
