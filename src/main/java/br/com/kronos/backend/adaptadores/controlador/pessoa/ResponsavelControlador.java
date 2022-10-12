package br.com.kronos.backend.adaptadores.controlador.pessoa;

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
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroResponsavel;
import br.com.kronos.backend.aplicacao.pessoa.api.ResponsavelDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.ResponsavelServico;
import br.com.kronos.backend.aplicacao.pessoa.api.TipoResponsavelDTO;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/responsaveis")
@CrossOrigin
public class ResponsavelControlador extends BaseControlador {

	@NonNull
	private ResponsavelServico responsavelServico;

	@PostMapping
	@ApiOperation(value = "Cadastrar Responsavel", notes = "Cadastrar Responsavel")
	public ResponseEntity<String> criar(@RequestBody ResponsavelDTO dto) {
		responsavelServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro de Responsável enviado com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Responsavel", notes = "Alterar Responsavel")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Integer id, @RequestBody ResponsavelDTO dto) {
		dto.setId(id);
		responsavelServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do Responsável enviado com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Responsavel", notes = "Excluir Responsavel")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Integer id) throws ExcecaoDeNegocio {
		responsavelServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do Responsável enviado com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Responsavel", notes = "Listar Responsavel")
	public ResponseEntity<PaginacaoDTO<ResponsavelDTO>> listar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "idPessoa", required = false) Long idPessoa,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<ResponsavelDTO> pessoas = responsavelServico
				.listar(FiltroResponsavel.builder()
									.idPessoa(idPessoa)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (pessoas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(pessoas);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Responsavel por id", notes = "Buscar Responsavel por id")
	public ResponseEntity<ResponsavelDTO> buscarPorId(@PathVariable(value = "id") Integer id) {

		ResponsavelDTO dto = responsavelServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/tipos")
	@ApiOperation(value = "Listar Tipos de Responsaveis", notes = "Listar Tipos de Responsaveis")
	public ResponseEntity<List<TipoResponsavelDTO>> listarTipoResponsaveis()
			throws ExcecaoDeNegocio {
		return ResponseEntity.ok().body(TipoResponsavelDTO.tipos());
	}
	
	@GetMapping(value="/responsaveis-financeiros")
	@ApiOperation(value = "Listar Responsaveis Financeiros", notes = "Listar Responsaveis Financeiros")
	public ResponseEntity<List<ResponsavelDTO>> listar(
			@RequestParam(value = "idMatricula", required = true) Long idMatricula)
			throws ExcecaoDeNegocio {

		List<ResponsavelDTO> responsaveis = responsavelServico
				.listarResponsaveisFinanceiros(FiltroResponsavel.builder()
									.idMatricula(idMatricula)
									.build());
		
		if (responsaveis == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(responsaveis);
	}
	
	@GetMapping(value="/ocorrencia/responsaveis-ciencia")
	@ApiOperation(value = "Listar Responsaveis Financeiros", notes = "Listar Responsaveis Financeiros")
	public ResponseEntity<List<ResponsavelDTO>> listar(@RequestParam(value = "data", required = true) String data,
			@RequestParam(value = "idPessoa", required = true) Long idPessoa)
			throws ExcecaoDeNegocio {

		List<ResponsavelDTO> responsaveis = responsavelServico
				.listarParaOcorrenciaResponsavelCiencia(LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")), idPessoa);
		
		if (responsaveis == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(responsaveis);
	}
	
}
