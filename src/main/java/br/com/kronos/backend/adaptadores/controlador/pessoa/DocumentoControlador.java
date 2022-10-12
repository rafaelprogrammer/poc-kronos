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
import br.com.kronos.backend.aplicacao.pessoa.FiltroDocumento;
import br.com.kronos.backend.aplicacao.pessoa.api.DocumentoDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.DocumentoServico;
import br.com.kronos.backend.aplicacao.pessoa.api.TipoDocumentoDTO;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/documentos")
@CrossOrigin
public class DocumentoControlador extends BaseControlador {

	@NonNull
	private DocumentoServico documentoServico;

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
		documentoServico.alterarArquivo(DocumentoDTO.builder()
												.id(id)
												.arquivoDTO(arquivoDTO)
												.build());
		return ResponseEntity.ok().body("Arquivo do documento enviado com sucesso");
	}
	
	@PostMapping
	@ApiOperation(value = "Cadastrar Documento", notes = "Cadastrar Documento")
	public ResponseEntity<String> criar(@RequestBody DocumentoDTO dto) throws ExcecaoDeNegocio {
		documentoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do documento enviado com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Documento", notes = "Alterar Documento")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Integer id, @RequestBody DocumentoDTO dto) throws ExcecaoDeNegocio {
		dto.setId(id);
		documentoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do documento enviado com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Documento", notes = "Excluir Documento")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Integer id) throws ExcecaoDeNegocio {
		documentoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do documento enviado com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Documentos", notes = "Listar Documentos")
	public ResponseEntity<PaginacaoDTO<DocumentoDTO>> listar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "idPessoa", required = false) Long idPessoa,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<DocumentoDTO> pessoas = documentoServico
				.listar(FiltroDocumento.builder()
									.id(id)
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
	@ApiOperation(value = "Buscar Documento por id", notes = "Buscar Documento por id")
	public ResponseEntity<DocumentoDTO> buscarPorId(@PathVariable(value = "id") Integer id)
			throws ExcecaoDeNegocio {

		DocumentoDTO dto = documentoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/tipos")
	@ApiOperation(value = "Listar Tipos de Documentos", notes = "Tipos de Documentos")
	public ResponseEntity<List<TipoDocumentoDTO>> listarTipos()
			throws ExcecaoDeNegocio {
		return ResponseEntity.ok().body(TipoDocumentoDTO.tipos());
	}

}
