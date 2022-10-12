package br.com.kronos.backend.adaptadores.controlador.empresa;

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
import br.com.kronos.backend.aplicacao.empresa.FiltroEmpresa;
import br.com.kronos.backend.aplicacao.empresa.api.EmpresaDTO;
import br.com.kronos.backend.aplicacao.empresa.api.EmpresaServico;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/empresas")
@CrossOrigin
public class EmpresaControlador extends BaseControlador {

	@NonNull
	private EmpresaServico empresaServico;

	@PostMapping
	@ApiOperation(value = "Cadastrar Empresa", notes = "Cadastrar Empresa")
	public ResponseEntity<String> criar(@RequestBody EmpresaDTO dto) {
		empresaServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro de empresa enviado com sucesso");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Empresa", notes = "Excluir Empresa")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Integer id) {
		empresaServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da empresa enviado com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Empresas", notes = "Listar Empresas")
	public ResponseEntity<PaginacaoDTO<EmpresaDTO>> listar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "cnpj", required = false) String cnpj,
			@RequestParam(value = "nomeFantasia", required = false) String nomeFantasia,
			@RequestParam(value = "razaoSocial", required = false) String razaoSocial,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<EmpresaDTO> empresas = empresaServico
				.listar(FiltroEmpresa.builder()
									.id(id)
									.cnpj(cnpj)
									.nomeFantasia(nomeFantasia)
									.razaoSocial(razaoSocial)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (empresas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(empresas);
	}
	
}
