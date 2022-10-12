package br.com.kronos.backend.adaptadores.controlador.pessoa;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.kronos.backend.adaptadores.controlador.BaseControlador;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.arquivo.api.ArquivoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroTitulacao;
import br.com.kronos.backend.aplicacao.pessoa.api.TipoTituloDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.TitulacaoDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.TitulacaoServico;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/titulacoes")
@CrossOrigin
public class TitulacaoControlador extends BaseControlador {

	@NonNull
	private TitulacaoServico titulacaoServico;

	@NonNull
	private DateUtil dateUtil;
	
	@NonNull
	private ObjectMapper objectMapper;
	
	@PostMapping(value="/arquivo", consumes = {"multipart/form-data"})
	@ApiOperation(value = "Alterar Arquivo", notes = "Alterar Arquivo")
	public ResponseEntity<String> alterarArquivo(@RequestParam(value="file", required = true) MultipartFile file, @RequestParam("id") Integer id, 
			@RequestParam("metadado") String metadado) throws ExcecaoDeNegocio, JsonParseException, JsonMappingException, IOException {
		ArquivoDTO arquivoDTO = objectMapper.readValue(metadado, ArquivoDTO.class);
		arquivoDTO.setBytes(file.getBytes());
		arquivoDTO.setContentType(file.getContentType());
		arquivoDTO.setTamanho(file.getSize());
		titulacaoServico.alterarArquivo(TitulacaoDTO.builder()
											.id(id)
											.arquivoDTO(arquivoDTO)
											.build());
		return ResponseEntity.ok().body("Arquivo da titulação enviada com sucesso");
	}
	
	
	@PostMapping
	@ApiOperation(value = "Cadastrar Titulacao", notes = "Cadastrar Titulacao")
	public ResponseEntity<String> criar(@RequestBody TitulacaoDTO dto) throws ExcecaoDeNegocio {
		titulacaoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro de titulação enviada com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Titulacao", notes = "Alterar Titulacao")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Integer id, @RequestBody TitulacaoDTO dto) throws ExcecaoDeNegocio {
		dto.setId(id);
		titulacaoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da titulação enviada com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Titulacao", notes = "Excluir Titulacao")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Integer id) throws ExcecaoDeNegocio {
		titulacaoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da titulacao enviada com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Titulacoes", notes = "Listar Titulacoes")
	public ResponseEntity<PaginacaoDTO<TitulacaoDTO>> listar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "idPessoa", required = false) Long idPessoa,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<TitulacaoDTO> titulacoes = titulacaoServico
				.listar(FiltroTitulacao.builder()
									.id(id)
									.idPessoa(idPessoa)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (titulacoes == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(titulacoes);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Titulacao por id", notes = "Buscar Titulacao por id")
	public ResponseEntity<TitulacaoDTO> buscarPorId(@PathVariable(value = "id") Integer id)
			throws ExcecaoDeNegocio {

		TitulacaoDTO dto = titulacaoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/tipos")
	@ApiOperation(value = "Listar Tipos de Titulos", notes = "Tipos de Titulos")
	public ResponseEntity<List<TipoTituloDTO>> listarTipos()
			throws ExcecaoDeNegocio {
		return ResponseEntity.ok().body(TipoTituloDTO.tipos());
	}

}
