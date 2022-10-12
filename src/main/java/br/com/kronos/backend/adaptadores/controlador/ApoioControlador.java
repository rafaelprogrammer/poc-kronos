package br.com.kronos.backend.adaptadores.controlador;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.kronos.backend.adaptadores.rest.BuscaCepRestService;
import br.com.kronos.backend.adaptadores.rest.CepDTO;
import br.com.kronos.backend.adaptadores.seguranca.Authority;
import br.com.kronos.backend.aplicacao.api.dto.PerfilDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.CidadeDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.EstadoDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaisDTO;
import br.com.kronos.backend.aplicacao.api.impl.comum.CidadeServico;
import br.com.kronos.backend.aplicacao.api.impl.comum.EstadoServico;
import br.com.kronos.backend.aplicacao.api.impl.comum.PaisServico;
import br.com.kronos.backend.aplicacao.comum.FiltroCidade;
import br.com.kronos.backend.aplicacao.comum.FiltroEstado;
import br.com.kronos.backend.aplicacao.comum.FiltroPais;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/apoio")
@CrossOrigin
public class ApoioControlador extends BaseControlador {
	
	@NonNull
	private String versionSystem;

	@NonNull
	private CidadeServico cidadeServico;
	
	@NonNull
	private EstadoServico estadoServico;
	
	@NonNull
	private PaisServico paisServico;
	
	@NonNull
	private BuscaCepRestService buscaCepRestService;
	
	@GetMapping(value="/version-system")
	@ApiOperation(value = "Informar Versao do Sistema", notes = "Informar Versao do Sistema")
	public ResponseEntity<String> recuperarVersaoDoSistema()
			throws ExcecaoDeNegocio {
		return ResponseEntity.ok().body(versionSystem);
	}


	@GetMapping(value="/cidade")
	@ApiOperation(value = "Listar Cidades", notes = "Listar Cidades")
	public ResponseEntity<PaginacaoDTO<CidadeDTO>> listar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "idEstado", required = false) Integer idEstado,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<CidadeDTO> cidades = cidadeServico
				.listar(FiltroCidade.builder()
						.id(id)
						.nome(nome)
						.idEstado(idEstado)
						.qtdTotal(total)
						.numeroPagina(pagina)
						.build());
		if (cidades == null || cidades.getDados() == null || cidades.getDados().isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(cidades);
	}
	
	@GetMapping(value="/estado")
	@ApiOperation(value = "Listar Estados", notes = "Listar Estados")
	public ResponseEntity<List<EstadoDTO>> listar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "sigla", required = false) String sigla,
			@RequestParam(value = "idPais", required = false) Integer idPais)
			throws ExcecaoDeNegocio {

		List<EstadoDTO> estados = estadoServico
				.listar(FiltroEstado.builder()
						.id(id)
						.nome(nome)
						.idPais(idPais)
						.sigla(sigla)
						.build());
		if (estados == null || estados.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(estados);
	}
	
	@GetMapping(value="/pais")
	@ApiOperation(value = "Listar Paises", notes = "Listar Paises")
	public ResponseEntity<List<PaisDTO>> listar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "sigla", required = false) String sigla)
			throws ExcecaoDeNegocio {

		List<PaisDTO> paises = paisServico
				.listar(FiltroPais.builder()
						.id(id)
						.nome(nome)
						.sigla(sigla)
						.build());
		if (paises == null || paises.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(paises);
	}
	
	@GetMapping(value="/perfis")
	@ApiOperation(value = "Listar Perfis", notes = "Listar Perfis")
	public ResponseEntity<List<PerfilDTO>> listarPerfis()
			throws ExcecaoDeNegocio {
		return ResponseEntity.ok().body(Authority.autorities());
	}
	
	@GetMapping(value="/cep/{numero}")
	@ApiOperation(value = "Busca CEP", notes = "Busca CEP")
	public ResponseEntity<CepDTO> buscarCep(@PathVariable(value="numero") String numero)
			throws ExcecaoDeNegocio {

		CepDTO cepDTO = buscaCepRestService.buscar(numero);
		
		if (cepDTO == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(cepDTO);
	}

}
