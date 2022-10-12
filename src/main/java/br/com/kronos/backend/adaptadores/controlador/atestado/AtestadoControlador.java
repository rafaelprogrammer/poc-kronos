package br.com.kronos.backend.adaptadores.controlador.atestado;

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
import br.com.kronos.backend.aplicacao.atestado.FiltroAtestado;
import br.com.kronos.backend.aplicacao.atestado.api.AtestadoDTO;
import br.com.kronos.backend.aplicacao.atestado.api.AtestadoServico;
import br.com.kronos.backend.aplicacao.atestado.api.TipoFaltaDTO;
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioDTO;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioServico;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/atestados")
@CrossOrigin
public class AtestadoControlador extends BaseControlador {
	
	@NonNull
	private AtestadoServico atestadoServico;
	
	@NonNull
	private FuncionarioServico funcionarioServico;
	
	@NonNull
	private ServicoAutenticacao servicoAutenticacao;
	
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
		atestadoServico.alterarArquivo(AtestadoDTO.builder()
												.id(id)
												.arquivoDTO(arquivoDTO)
												.build());
		return ResponseEntity.ok().body("Arquivo do documento enviado com sucesso");
	}
	
	@PostMapping()
	@ApiOperation(value = "Cadastrar Atestados", notes = "Cadastrar Atestados")
	public ResponseEntity<String> criar(@RequestBody AtestadoDTO dto) {
		incluirDados(dto);
		atestadoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro de Atestado enviado com sucesso");
	}

	private void incluirDados(AtestadoDTO dto) {
		FuncionarioDTO funcionarioDTO = funcionarioServico.buscarPorIdPessoa(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		dto.setIdFuncionario(funcionarioDTO.getId());
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Atestados", notes = "Alterar Atestados")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody AtestadoDTO dto) {
		dto.setId(id);
		atestadoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração de Atestado enviado com sucesso");
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Atestados", notes = "Excluir Atestados")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		atestadoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão de Atestado enviado com sucesso");
	}
	
	@GetMapping()
	@ApiOperation(value = "Listar Atestados", notes = "Listar Atestados")
	public ResponseEntity<PaginacaoDTO<AtestadoDTO>> listar(
			@RequestParam(value = "idPessoa", required = false) Long idPessoa,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<AtestadoDTO> atestados = atestadoServico
				.listar(FiltroAtestado.builder()
									.idPessoa(idPessoa)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (atestados == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(atestados);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Atestado por id", notes = "Buscar Atestado por id")
	public ResponseEntity<AtestadoDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		AtestadoDTO dto = atestadoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	
	@GetMapping(value="/combo-tipo-falta")
	@ApiOperation(value = "Listar Tipos Faltas para combo", notes = "Listar Tipos Faltas para combo")
	public ResponseEntity<List<TipoFaltaDTO>> listarTipoFalta() {
		return ResponseEntity.ok().body(TipoFaltaDTO.tipos());
	}
	
}
