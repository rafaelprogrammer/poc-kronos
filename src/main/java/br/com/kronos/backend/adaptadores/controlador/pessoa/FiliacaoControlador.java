package br.com.kronos.backend.adaptadores.controlador.pessoa;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.kronos.backend.adaptadores.controlador.BaseControlador;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroFiliacao;
import br.com.kronos.backend.aplicacao.pessoa.api.FiliacaoDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.FiliacaoServico;
import br.com.kronos.backend.aplicacao.pessoa.api.TipoFiliacaoDTO;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/filiacoes")
@CrossOrigin
public class FiliacaoControlador extends BaseControlador {

	@NonNull
	private FiliacaoServico filiacaoServico;


	@PostMapping
	@ApiOperation(value = "Cadastrar Filiacao", notes = "Cadastrar Filiacao")
	public ResponseEntity<String> criar(@RequestBody FiliacaoDTO dto) throws ExcecaoDeNegocio {
		filiacaoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro de filiação enviada com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Filiacao", notes = "Excluir Filiacao")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Integer id) throws ExcecaoDeNegocio {
		filiacaoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da filiacao enviada com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Filiacoes", notes = "Listar Filiacoes")
	public ResponseEntity<PaginacaoDTO<FiliacaoDTO>> listar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "idPessoaFilho", required = false) Long idPessoaFilho,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<FiliacaoDTO> pessoas = filiacaoServico
				.listar(FiltroFiliacao.builder()
									.id(id)
									.idPessoaFilho(idPessoaFilho)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (pessoas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(pessoas);
	}
	
	@GetMapping(value="/tipos")
	@ApiOperation(value = "Listar Tipos de Filiacoes", notes = "Listar Tipos de Filiacoes")
	public ResponseEntity<List<TipoFiliacaoDTO>> listarTipoFiliacoes()
			throws ExcecaoDeNegocio {
		return ResponseEntity.ok().body(TipoFiliacaoDTO.tipos());
	}
	
}
