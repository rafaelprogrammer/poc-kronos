package br.com.kronos.backend.adaptadores.controlador;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.arquivo.FiltroArquivo;
import br.com.kronos.backend.aplicacao.arquivo.api.ArquivoDTO;
import br.com.kronos.backend.aplicacao.arquivo.api.ArquivoServico;
import br.com.kronos.backend.aplicacao.arquivo.api.TipoArquivoDTO;
import br.com.kronos.backend.aplicacao.arquivo.api.TipoConteudoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/arquivos")
@CrossOrigin
public class ArquivoControlador extends BaseControlador {
	
	@NonNull
	private ArquivoServico arquivoServico;
	
	@NonNull
	private ObjectMapper objectMapper;
	
	@PostMapping(value="", consumes = {"multipart/form-data"})
	@ApiOperation(value = "Upload de Arquivo", notes = "Upload de Arquivo")
	public ResponseEntity<String> upload(@RequestParam(value="file", required = true) MultipartFile file, @RequestParam("json") String metadado) throws ExcecaoDeNegocio, JsonParseException, JsonMappingException, IOException {
		ArquivoDTO dto = objectMapper.readValue(metadado, ArquivoDTO.class);
		dto.setBytes(file.getBytes());
		dto.setContentType(file.getContentType());
		dto.setTamanho(file.getSize());
		arquivoServico.excluir(dto.getId());
		return ResponseEntity.ok().body("Arquivo enviado com sucesso");
	}
	
	@GetMapping(value="/{id}")
	@ApiOperation(value = "Buscar Dados Por id", notes = "Buscar Dados Por id")
	public ResponseEntity<ArquivoDTO> buscarPorid(@PathVariable(value="id") int id)
			throws ExcecaoDeNegocio {

		return ResponseEntity.ok().body(arquivoServico.buscarPorId(id));
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Arquivo", notes = "Excluir Arquivo")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Integer id) throws ExcecaoDeNegocio {
		arquivoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do arquivo enviada com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Arquivos", notes = "Listar Arquivos")
	public ResponseEntity<PaginacaoDTO<ArquivoDTO>> listar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "idPessoa", required = false) Long idPessoa,
			@RequestParam(value = "idTipoArquivo", required = false) Integer idTipoArquivo,
			@RequestParam(value = "idTipoConteudo", required = false) Integer idTipoConteudo,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<ArquivoDTO> pessoas = arquivoServico
				.listar(FiltroArquivo.builder()
									.id(id)
									.idPessoa(idPessoa)
									.idTipoArquivo(idTipoArquivo)
									.idTipoConteudo(idTipoConteudo)
									.nome(nome)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (pessoas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(pessoas);
	}
	
	@GetMapping(value="/tipos")
	@ApiOperation(value = "Listar Tipo de Arquivos", notes = "Listar Tipo de Arquivos")
	public ResponseEntity<List<TipoArquivoDTO>> listarTipos()
			throws ExcecaoDeNegocio {
		return ResponseEntity.ok().body(TipoArquivoDTO.tipos());
	}
	
	@GetMapping(value="/tipos-conteudos")
	@ApiOperation(value = "Listar Tipo de Conteudos", notes = "Listar Tipo de Conteudos")
	public ResponseEntity<List<TipoConteudoDTO>> listarTiposConteudos()
			throws ExcecaoDeNegocio {
		return ResponseEntity.ok().body(TipoConteudoDTO.tipos());
	}
	
	@GetMapping(value = "/foto/{idPessoa}")
	@ApiOperation(value = "Buscar Foto da pessoa", notes = "Buscar Foto da pessoa")
	public ResponseEntity<ArquivoDTO> buscarFoto(@PathVariable(value = "idPessoa") Long idPessoa)
			throws ExcecaoDeNegocio {

		ArquivoDTO dto = arquivoServico.buscarFotoPessoa(idPessoa);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}

}
