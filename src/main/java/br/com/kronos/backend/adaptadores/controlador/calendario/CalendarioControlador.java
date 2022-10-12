package br.com.kronos.backend.adaptadores.controlador.calendario;

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
import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.calendario.FiltroCalendario;
import br.com.kronos.backend.aplicacao.calendario.FiltroCalendarioCurso;
import br.com.kronos.backend.aplicacao.calendario.api.CalendarioDTO;
import br.com.kronos.backend.aplicacao.calendario.api.CalendarioResumidoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.CalendarioServico;
import br.com.kronos.backend.aplicacao.calendario.api.TipoSituacaoCalendarioDTO;
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/calendarios")
@CrossOrigin
public class CalendarioControlador extends BaseControlador {

	@NonNull
	private CalendarioServico calendarioServico;
	
	@NonNull
	private ServicoAutenticacao servicoAutenticacao;
	
	@PostMapping
	@ApiOperation(value = "Cadastrar Calendario", notes = "Cadastrar Calendario")
	public ResponseEntity<String> criar(@RequestBody CalendarioDTO dto) {
		adicionarDadosUsuarioLogado(dto);
		calendarioServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do calendário enviado com sucesso");
	}

	private void adicionarDadosUsuarioLogado(CalendarioDTO dto) {
		UsuarioDTO usuario = servicoAutenticacao.buscarUsuarioLogado();
		dto.setIdFuncionarioElaboracao(usuario.getId());
		dto.setIdInstituicao(usuario.getIdInstituicao());
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Calendario", notes = "Alterar Calendario")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody CalendarioDTO dto) {
		adicionarDadosUsuarioLogado(dto);
		calendarioServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do calendário enviado com sucesso");
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Calendario", notes = "Excluir Calendario")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		calendarioServico.verificarSeExistePeriodoExecucao(id);
		calendarioServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do calendário enviado com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Calendarios", notes = "Listar Calendarios")
	public ResponseEntity<PaginacaoDTO<CalendarioDTO>> listar(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam(value = "numero", required = false) Integer numero,
			@RequestParam(value = "idTipoSituacaoCalendario", required = false) Integer idTipoSituacaoCalendario,
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<CalendarioDTO> calendarios = calendarioServico
				.listar(FiltroCalendario.builder()
									.id(id)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.ano(ano)
									.numero(numero)
									.idTipoSituacaoCalendario(idTipoSituacaoCalendario)
									.descricao(descricao)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (calendarios == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(calendarios);
	}
	
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Calendario por id", notes = "Buscar Calendario por id")
	public ResponseEntity<CalendarioDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		CalendarioDTO dto = calendarioServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/tipos-situacoes")
	@ApiOperation(value = "Listar Tipos de Situacao do Calendario", notes = "Listar Tipos de Situacao do Calendario")
	public ResponseEntity<List<TipoSituacaoCalendarioDTO>> tipos() {
		return ResponseEntity.ok().body(TipoSituacaoCalendarioDTO.tipos());
	}
	
	@GetMapping(value="/anos")
	@ApiOperation(value = "Listar Todos os anos dos calendarios", notes = "Listar Tipos de Formas de Pagamento")
	public ResponseEntity<List<Integer>> listarAnos() {
		List<Integer> anos = calendarioServico.listarAnos();
		if (anos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(anos);
	}
	
	@GetMapping(value="/anos/{ano}/numeros")
	@ApiOperation(value = "Listar Todos os números do ano", notes = "Listar Tipos de Formas de Pagamento")
	public ResponseEntity<List<Integer>> listarNumerosPorAno(@PathVariable(value = "ano") Integer ano) {
		List<Integer> numeros = calendarioServico.listarNumerosPorAno(ano);
		if (numeros == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(numeros);
	}
	
	@GetMapping(value="/anos/curso/{idCurso}")
	@ApiOperation(value = "Listar Anos por curso", notes = "Listar Anos por curso")
	public ResponseEntity<List<Integer>> listarAnos(@PathVariable(value = "idCurso") Long idCurso) {
		List<Integer> anos = calendarioServico.listarAnos(FiltroCalendarioCurso.builder()
																.idCurso(idCurso)
																.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
																.build());
		if (anos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(anos);
	}
	
	@GetMapping(value="/combo")
	@ApiOperation(value = "Listar para combo", notes = "Listar para combo")
	public ResponseEntity<List<CalendarioResumidoDTO>> listarAnos(@RequestParam(value = "idCurso") Long idCurso,
			@RequestParam(value = "ano", required = false) Integer ano) {
		List<CalendarioResumidoDTO> calendarios = calendarioServico.listarParaCombo(FiltroCalendarioCurso.builder()
																.idCurso(idCurso)
																.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
																.ano(ano)
																.build());
		if (calendarios == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(calendarios);
	}
	
	@PutMapping(value="/{id}/conclui")
	@ApiOperation(value = "Concluir Calendário", notes = "Concluir Calendário")
	public ResponseEntity<String> concluir(@PathVariable(value = "id") Long id) {
		calendarioServico.concluir(id, servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		return ResponseEntity.ok().body("Calendário concluído com sucesso");
	}
	
	@PutMapping(value="/{id}/aprova")
	@ApiOperation(value = "Aprovar Calendário", notes = "Aprovar Calendário")
	public ResponseEntity<String> aprovar(@PathVariable(value = "id") Long id) {
		calendarioServico.aprovar(id, servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		return ResponseEntity.ok().body("Calendário aprovado com sucesso");
	}
	
	@PutMapping(value="/{id}/libera-edicao")
	@ApiOperation(value = "Liberar Edição Calendário", notes = "Liberar Edição Calendário")
	public ResponseEntity<String> liberarEdicao(@PathVariable(value = "id") Long id) {
		calendarioServico.liberarEdicao(id, servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		return ResponseEntity.ok().body("Calendário liberado para edição");
	}
	
	
}
