package br.com.kronos.backend.adaptadores.controlador.contrato;

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
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import br.com.kronos.backend.aplicacao.contrato.FiltroContrato;
import br.com.kronos.backend.aplicacao.contrato.api.ContratoConvenioServico;
import br.com.kronos.backend.aplicacao.contrato.api.ContratoDTO;
import br.com.kronos.backend.aplicacao.contrato.api.ContratoServico;
import br.com.kronos.backend.aplicacao.contrato.api.DiaParcelaDTO;
import br.com.kronos.backend.aplicacao.contrato.api.TipoFormaPagamentoDTO;
import br.com.kronos.backend.aplicacao.contrato.api.TipoSituacaoContratoDTO;
import br.com.kronos.backend.aplicacao.instituicao.FiltroConvenio;
import br.com.kronos.backend.aplicacao.instituicao.api.ConvenioContratoDTO;
import br.com.kronos.backend.aplicacao.instituicao.api.ConvenioServico;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/contratos")
@CrossOrigin
public class ContratoControlador extends BaseControlador {

	@NonNull
	private ContratoServico contratoServico;
	
	@NonNull
	private ConvenioServico convenioServico;
	
	@NonNull
	private ContratoConvenioServico contratoConvenioServico;
	
	@NonNull
	private ServicoAutenticacao servicoAutenticacao;
	

	@PostMapping
	@ApiOperation(value = "Cadastrar Contrato", notes = "Cadastrar Contrato")
	public ResponseEntity<String> criar(@RequestBody ContratoDTO dto) {
		contratoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do Contrato enviado com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Contrato", notes = "Alterar Contrato")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody ContratoDTO dto) {
		dto.setId(id);
		contratoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do Contrato enviado com sucesso");
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Contrato", notes = "Excluir Contrato")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		contratoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do contrato enviado com sucesso");
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Contratos", notes = "Listar Contratos")
	public ResponseEntity<PaginacaoDTO<ContratoDTO>> listar(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idMatricula", required = false) Long idMatricula,
			@RequestParam(value = "idInstituicao", required = false) Long idInstituicao,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<ContratoDTO> contratos = contratoServico
				.listar(FiltroContrato.builder()
									.id(id)
									.idInstituicao(idInstituicao)
									.idMatricula(idMatricula)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (contratos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(contratos);
	}
	
	@GetMapping(value="/calcula-dia-vencimento")
	@ApiOperation(value = "Calcula o dia do vencimento e a data da ultima parcela", notes = "Calcula o dia do vencimento e a data da ultima parcela")
	public ResponseEntity<DiaParcelaDTO> calcularDiaVencimento(@RequestParam(value = "numerosParcelas", required = true) Integer numerosParcelas,
			@RequestParam(value = "dataUltimaParcela", required = true) String dataUltimaParcela) {

		DiaParcelaDTO diaParcela = contratoServico.calcularDiaVencimento(
				numerosParcelas, 
				LocalDate.parse(dataUltimaParcela, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		
		if (diaParcela == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(diaParcela);
	}
	
	@PutMapping("/{id}/valida")
	@ApiOperation(value = "Validar Contrato", notes = "Validar Contrato")
	public ResponseEntity<String> validar(@PathVariable(value = "id") Long id) {
		contratoServico.validar(id);
		return ResponseEntity.ok().body("Validação do contrato enviada com sucesso");
	}
	
	@PutMapping("/{id}/envia-financeiro")
	@ApiOperation(value = "Enviar Contrato para Analise do Financeiro", notes = "Enviar Contrato para Analise do Financeiro")
	public ResponseEntity<String> enviarParaFinanceiro(@PathVariable(value = "id") Long id) {
		contratoServico.enviarFinanceiro(id);
		return ResponseEntity.ok().body("Enviado para o financeiro com sucesso");
	}
	
	@PutMapping("/{id}/finaliza-analise")
	@ApiOperation(value = "Finaliza Análise do Contrato", notes = "Finaliza Análise do Contrato")
	public ResponseEntity<String> finalizarAnalise(@PathVariable(value = "id") Long id) {
		contratoServico.finalizarAnalise(id);
		return ResponseEntity.ok().body("Análise confirmada com sucesso");
	}
	
	@PutMapping("/{id}/aprova")
	@ApiOperation(value = "Aprovar Contrato", notes = "Aprovar Contrato")
	public ResponseEntity<String> aprovar(@PathVariable(value = "id") Long id) {
		contratoServico.aprovar(id);
		return ResponseEntity.ok().body("Contrato aprovado com sucesso");
	}
	
	@PutMapping("/{id}/confirma-assinatura")
	@ApiOperation(value = "Confirmar Assinatura", notes = "Confirmar Assinatura")
	public ResponseEntity<String> confirmarAssinatura(@PathVariable(value = "id") Long id) {
		contratoServico.confirmarAssinatura(id);
		return ResponseEntity.ok().body("Assinatura confirmada com sucesso");
	}
	
	@PutMapping("/{id}/reativa")
	@ApiOperation(value = "Reativar Contrato", notes = "Reativar Contrato")
	public ResponseEntity<String> reativar(@PathVariable(value = "id") Long id) {
		contratoServico.reativar(id);
		return ResponseEntity.ok().body("Contrato reativado com sucesso");
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Contrato por id", notes = "Buscar Contrato por id")
	public ResponseEntity<ContratoDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		ContratoDTO dto = contratoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/{id}/matricula")
	@ApiOperation(value = "Buscar Contrato Matricula por id", notes = "Buscar Contrato Matricula por id")
	public ResponseEntity<ContratoDTO> buscarContratoMatriculaPorId(@PathVariable(value = "id") Long id) {

		ContratoDTO dto = contratoServico.buscarContratoMatriculaPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/tipos")
	@ApiOperation(value = "Listar Tipos de Situacao do Contrato", notes = "Listar Tipos de Situacao do Contrato")
	public ResponseEntity<List<TipoSituacaoContratoDTO>> tipos() {
		return ResponseEntity.ok().body(TipoSituacaoContratoDTO.tipos());
	}
	
	@GetMapping(value="/tipos-formas-pagamento")
	@ApiOperation(value = "Listar Tipos de Formas de Pagamento", notes = "Listar Tipos de Formas de Pagamento")
	public ResponseEntity<List<TipoFormaPagamentoDTO>> tiposFormasPagamento() {
		return ResponseEntity.ok().body(TipoFormaPagamentoDTO.tipos());
	}
	
	@GetMapping(value="/convenios")
	@ApiOperation(value = "Listar Convênios para o contrato", notes = "Listar Convênios para o contrato")
	public ResponseEntity<List<ConvenioContratoDTO>> listarParaContrato(@RequestParam(value = "idContrato", required = false) Long idContrato,
			@RequestParam(value = "dataContrato", required = false) String dataContrato) {

		List<ConvenioContratoDTO> convenios = convenioServico.listarParaContrato(FiltroConvenio.builder()
													.idContrato(idContrato)
													.dataContrato(LocalDate.parse(dataContrato, DateTimeFormatter.ofPattern("dd/MM/yyyy")))
													.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
													.build());
		
		if (convenios == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(convenios);
	}
	
	@DeleteMapping("/{id}/convenio/{idConvenio}")
	@ApiOperation(value = "Excluir Contrato Convenio", notes = "Excluir Contrato Convenio")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") long id, @PathVariable(value = "idConvenio") long idConvenio) {
		contratoConvenioServico.excluir(id, idConvenio);
		return ResponseEntity.ok().body("Exclusão da convenio enviado com sucesso");
	}
	
	@PutMapping("/{id}/atualiza-analise")
	@ApiOperation(value = "Atualizar o contrato durante a análise", notes = "Atualizar o contrato durante a análise")
	public ResponseEntity<String> atualizarContratoAnalise(@PathVariable(value = "id") Long id, @RequestBody ContratoDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		contratoServico.atualizarContratoAnalise(dto);
		return ResponseEntity.ok().body("Enviado para atualização com sucesso");
	}
	
}
