package br.com.kronos.backend.adaptadores.controlador;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroInstituicao;
import br.com.kronos.backend.aplicacao.instituicao.FiltroMencao;
import br.com.kronos.backend.aplicacao.instituicao.api.InstituicaoDTO;
import br.com.kronos.backend.aplicacao.instituicao.api.InstituicaoServico;
import br.com.kronos.backend.aplicacao.instituicao.api.MencaoDTO;
import br.com.kronos.backend.aplicacao.instituicao.api.MencaoServico;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/instituicao")
@CrossOrigin
public class InstituicaoControlador extends BaseControlador {

	@NonNull
	private InstituicaoServico instituicaoServico;
	
	@NonNull
	private MencaoServico mencaoServico;
	
	@GetMapping(value="")
	@ApiOperation(value = "Listar Instituicoes", notes = "Listar Instituicoes")
	public ResponseEntity<PaginacaoDTO<InstituicaoDTO>> listar()
			throws ExcecaoDeNegocio {

		PaginacaoDTO<InstituicaoDTO> instituicoes = instituicaoServico
				.listar(FiltroInstituicao.builder().build());
		if (instituicoes == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(instituicoes);
	}
	
	@GetMapping(value="/mencoes")
	@ApiOperation(value = "Listar Mencoes", notes = "Listar Mencoes")
	public ResponseEntity<List<MencaoDTO>> listarMensoes()
			throws ExcecaoDeNegocio {

		List<MencaoDTO> mencoes = mencaoServico
				.listarParaCombo(FiltroMencao.builder().build());
		if (mencoes == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(mencoes);
	}

}
