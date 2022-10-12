package br.com.kronos.backend.adaptadores.controlador.funcionario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import br.com.kronos.backend.aplicacao.api.dto.comum.MesDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.funcionario.FiltroConfiguracaoPonto;
import br.com.kronos.backend.aplicacao.funcionario.FiltroFuncionario;
import br.com.kronos.backend.aplicacao.funcionario.FiltroFuncionarioFuncao;
import br.com.kronos.backend.aplicacao.funcionario.FiltroPonto;
import br.com.kronos.backend.aplicacao.funcionario.api.AusenteDTO;
import br.com.kronos.backend.aplicacao.funcionario.api.ConfiguracaoPontoDTO;
import br.com.kronos.backend.aplicacao.funcionario.api.ConfiguracaoPontoServico;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioDTO;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioFuncaoDTO;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioFuncaoServico;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioServico;
import br.com.kronos.backend.aplicacao.funcionario.api.PontoDTO;
import br.com.kronos.backend.aplicacao.funcionario.api.PontoServico;
import br.com.kronos.backend.aplicacao.funcionario.api.TipoCategoriaFuncaoDTO;
import br.com.kronos.backend.aplicacao.funcionario.api.TipoFuncaoDTO;
import br.com.kronos.backend.aplicacao.funcionario.api.TipoPeriodoPontoDTO;
import br.com.kronos.backend.aplicacao.horario.EnumTipoDiaSemana;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/funcionarios")
@CrossOrigin
public class FuncionarioControlador extends BaseControlador {

	@NonNull
	private FuncionarioServico funcionarioServico;
	
	@NonNull
	private FuncionarioFuncaoServico funcionarioFuncaoServico;
	
	@NonNull
	private ConfiguracaoPontoServico configuracaoPontoServico;
	
	@NonNull
	private PontoServico pontoServico;
	
	@NonNull
	private ServicoAutenticacao servicoAutenticacao;
	
	@PostMapping
	@ApiOperation(value = "Cadastrar Funcionario", notes = "Cadastrar Funcionario")
	public ResponseEntity<String> criar(@RequestBody FuncionarioDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		funcionarioServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro de Funcionário enviado com sucesso");
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Funcionario", notes = "Alterar Funcionario")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody FuncionarioDTO dto) {
		dto.setId(id);
		funcionarioServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração de Funcionário enviado com sucesso");
	}


	@GetMapping
	@ApiOperation(value = "Listar Funcinarios", notes = "Listar Funcinarios")
	public ResponseEntity<PaginacaoDTO<FuncionarioDTO>> listar(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "idPessoa", required = false) Long idPessoa,
			@RequestParam(value = "idInstituicao", required = false) Long idInstituicao,
			@RequestParam(value = "idTipoFuncao", required = false) Long idTipoFuncao,
			@RequestParam(value = "numeroRegistro", required = false) Integer numeroRegistro,
			@RequestParam(value = "idTipoCategoriaFuncao", required = false) Long idTipoCategoriaFuncao,
			@RequestParam(value = "ativo", required = false) Boolean ativo,
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<FuncionarioDTO> funcionarios = funcionarioServico
				.listar(FiltroFuncionario.builder()
									.id(id)
									.idPessoa(idPessoa)
									.idInstituicao(idInstituicao)
									.numeroRegistro(numeroRegistro)
									.idTipoFuncao(idTipoFuncao)
									.idTipoCategoriaFuncao(idTipoCategoriaFuncao)
									.cpf(cpf)
									.ativo(ativo)
									.nome(nome)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (funcionarios == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(funcionarios);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Funcionario por id", notes = "Buscar Funcionario por id")
	public ResponseEntity<FuncionarioDTO> buscarPorId(@PathVariable(value = "id") Long id) {

		FuncionarioDTO dto = funcionarioServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/categorias")
	@ApiOperation(value = "Listar Categorias", notes = "Listar Categorias")
	public ResponseEntity<List<TipoCategoriaFuncaoDTO>> listarCategorias() {
		return ResponseEntity.ok().body(TipoCategoriaFuncaoDTO.tipos());
	}
	
	@GetMapping(value="/funcoes/tipos")
	@ApiOperation(value = "Listar Funcoes", notes = "Listar Funcoes")
	public ResponseEntity<List<TipoFuncaoDTO>> listarFuncoes() {
		return ResponseEntity.ok().body(TipoFuncaoDTO.tipos());
	}
	
	@GetMapping("/combo/disciplina/{idDisciplina}")
	@ApiOperation(value = "Listar Funcionarios por disciplina", notes = "Listar Funcionarios por disciplina")
	public ResponseEntity<List<FuncionarioDTO>> listarPorPeriodoExecucao(@PathVariable(value = "idDisciplina", required = true) Long idDisciplina) {

		List<FuncionarioDTO> funcionarios = funcionarioServico.listarPorDisciplina(idDisciplina); 
		
		if (funcionarios == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(funcionarios);
	}
	
	@GetMapping("/combo/ocorrencia/relatores")
	@ApiOperation(value = "Listar Funcionarios (Relatores) por data ocorrencia", notes = "Listar Funcionarios (Relatores) por data ocorrencia")
	public ResponseEntity<List<FuncionarioDTO>> listarParaOcorrenciaRelatores(@RequestParam(value = "data", required = true) String data) {

		List<FuncionarioDTO> funcionarios = funcionarioServico.listarParaOcorrenciaRelatores(LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao()); 
		
		if (funcionarios == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(funcionarios);
	}
	
	@GetMapping("/combo/ocorrencia/responsaveis-anulacao")
	@ApiOperation(value = "Listar Funcionarios ((Resp. Anulacao) por data ocorrencia", notes = "Listar Funcionarios (Resp. Anulacao) por data ocorrencia")
	public ResponseEntity<List<FuncionarioDTO>> listarParaOcorrenciaResponsavelAnulacao(@RequestParam(value = "data", required = true) String data) {

		List<FuncionarioDTO> funcionarios = funcionarioServico.listarParaOcorrenciaResponsavelAnulacao(LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao()); 
		
		if (funcionarios == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(funcionarios);
	}
	
	@PostMapping("/funcoes")
	@ApiOperation(value = "Cadastrar Funcionario Funcao", notes = "Cadastrar Funcionario Funcao")
	public ResponseEntity<String> criar(@RequestBody List<FuncionarioFuncaoDTO> dtos) {
		funcionarioFuncaoServico.verificarSeExisteSomenteUmRegistroTrue(dtos);
		funcionarioFuncaoServico.criar(dtos);
		return ResponseEntity.ok().body("Cadastro da Função enviada com sucesso");
	}
	
	@GetMapping("/funcoes")
	@ApiOperation(value = "Listar Funcinarios Funcoes", notes = "Listar Funcinarios Funcoes")
	public ResponseEntity<List<FuncionarioFuncaoDTO>> listar(@RequestParam(value = "idFuncionario", required = true) Long idFuncionario) {

		List<FuncionarioFuncaoDTO> funcionariosFuncoes = funcionarioFuncaoServico
				.listar(FiltroFuncionarioFuncao.builder().idFuncionario(idFuncionario)
									.build());
		
		if (funcionariosFuncoes == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(funcionariosFuncoes);
	}
	
	//### Configuracao PontoDTO
	@PostMapping("/configuracoes-pontos")
	@ApiOperation(value = "Cadastrar Configuracao PontoDTO", notes = "Cadastrar Configuracao PontoDTO")
	public ResponseEntity<String> criarConfiguracaoPonto(@RequestBody ConfiguracaoPontoDTO dto) {
		validar(dto);
		configuracaoPontoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro da Configuração enviada com sucesso");
	}

	private void validar(ConfiguracaoPontoDTO dto) {
		FiltroConfiguracaoPonto filtro = FiltroConfiguracaoPonto.builder()
													.id(dto.getId())
													.dataInicialVigencia(dto.getDataInicialVigencia())
													.dataFinalVigencia(dto.getDataFinalVigencia())
													.idFuncionario(dto.getIdFuncionario())
													.build();
		configuracaoPontoServico.verificarSeEstaSobrepondoOutraConfiguracao(filtro);
		if (filtro.getId() != null) {
			configuracaoPontoServico.verificarSeExistePontosForaDaVigencia(filtro);
		}
	}
	
	@PutMapping("/configuracoes-pontos/{id}")
	@ApiOperation(value = "Alterar Configuracao PontoDTO", notes = "Alterar Configuracao PontoDTO")
	public ResponseEntity<String> alterarConfiguracaoPonto(@PathVariable(value = "id") Long id, @RequestBody ConfiguracaoPontoDTO dto) {
		dto.setId(id);
		validar(dto);
		configuracaoPontoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da Configuração enviada com sucesso");
	}
	
	@DeleteMapping("/configuracoes-pontos/{id}")
	@ApiOperation(value = "Excluir Configuracao PontoDTO", notes = "Excluir Configuracao PontoDTO")
	public ResponseEntity<?> excluirConfiguracaoPonto(@PathVariable(value = "id") Long id) throws ExcecaoDeNegocio {
		configuracaoPontoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da Configuração enviada enviada com sucesso - " + id);
	}


	@GetMapping("/configuracoes-pontos")
	@ApiOperation(value = "Listar Configuracoes Pontos", notes = "Listar Configuracoes Pontos")
	public ResponseEntity<PaginacaoDTO<ConfiguracaoPontoDTO>> listarConfiguracaoPonto(@RequestParam(value = "idFuncionario", required = false) Long idFuncionario,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<ConfiguracaoPontoDTO> dtos = configuracaoPontoServico
				.listar(FiltroConfiguracaoPonto.builder()
									.idFuncionario(idFuncionario)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (dtos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dtos);
	}
	
	@GetMapping("/configuracoes-pontos/verifica-registra-entrada")
	@ApiOperation(value = "Verificar se pode registar entrada no Ponto", notes = "Verificar se pode registar entrada no Ponto")
	public ResponseEntity<Boolean> verificarRegistraEntrada() {
		return ResponseEntity.ok().body(configuracaoPontoServico.verificarSeExistePeloMenosUmaConfiguracao(FiltroConfiguracaoPonto.builder().idFuncionario(recuperarIdFuncionario()).build()));
	}
	
	@GetMapping("/configuracoes-pontos/verifica-registra-tarefa-online")
	@ApiOperation(value = "Verificar se pode registar tarefa online", notes = "Verificar se pode registar tarefa online")
	public ResponseEntity<Boolean> verificarRegistraTarefaOnline() {
		return ResponseEntity.ok().body(configuracaoPontoServico.verificarSeExistePeloMenosUmaConfiguracaoOnline(FiltroConfiguracaoPonto.builder().idFuncionario(recuperarIdFuncionario()).build()));
	}
	
	@GetMapping(value = "/configuracoes-pontos/{id}")
	@ApiOperation(value = "Buscar Configuracao PontoDTO por id", notes = "Buscar Configuracao PontoDTO por id")
	public ResponseEntity<ConfiguracaoPontoDTO> buscarConfiguracaoPontoPorId(@PathVariable(value = "id") Long id) {

		ConfiguracaoPontoDTO dto = configuracaoPontoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	//### FIM Configuracao PontoDTO

	//### PONTO
	@PostMapping("/pontos")
	@ApiOperation(value = "Cadastrar Ponto", notes = "Cadastrar Ponto")
	public ResponseEntity<String> criarPonto(@RequestBody PontoDTO dto) {
		setIdFuncionarioLogado(dto);
		pontoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do Ponto enviado com sucesso");
	}
	
	private void setIdFuncionarioLogado(PontoDTO dto) {
		FuncionarioDTO funcionarioDTO = funcionarioServico.buscarPorIdPessoa(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		dto.setIdFuncionarioLogado(funcionarioDTO.getId());
	}

	@PutMapping("/pontos/{id}")
	@ApiOperation(value = "Alterar Ponto", notes = "Alterar Ponto")
	public ResponseEntity<String> alterarPonto(@PathVariable(value = "id") Long id, @RequestBody PontoDTO dto) {
		dto.setId(id);
		pontoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do Ponto enviado com sucesso");
	}
	
	@PostMapping("/pontos/tarefa-online")
	@ApiOperation(value = "Cadastrar Tarefa Online", notes = "Cadastrar Tarefa Online")
	public ResponseEntity<String> criarTarefaOnline(@RequestBody PontoDTO dto) {
		setIdFuncionarioLogado(dto);
		dto.setTarefaOnline(true);
		pontoServico.criarTarefaOnline(dto);
		return ResponseEntity.ok().body("Cadastro da tarefa online enviada com sucesso");
	}


	@GetMapping("/pontos")
	@ApiOperation(value = "Listar Pontos", notes = "Listar Pontos")
	public ResponseEntity<PaginacaoDTO<PontoDTO>> listarPontos(@RequestParam(value = "idFuncionario", required = false) Long idFuncionario,
			@RequestParam(value = "pendente", required = false) boolean pendente,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<PontoDTO> pontos = pontoServico
				.listar(FiltroPonto.builder()
									.idFuncionario(recuperarIdFuncionario())
									.pendente(pendente)
									.ano(ano)
									.mes(mes)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (pontos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(pontos);
	}

	private long recuperarIdFuncionario() {
		return funcionarioServico.buscarPorIdPessoa(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa()).getId();
	}
	
	@GetMapping(value="/pontos/tipos")
	@ApiOperation(value = "Listar Tipos de Periodos do Ponto", notes = "Listar Tipos de Periodos do Ponto")
	public ResponseEntity<List<TipoPeriodoPontoDTO>> tiposPeriodosPonto() {
		List<TipoPeriodoPontoDTO> dtos = pontoServico.listarTiposPeriodos(FiltroPonto.builder().idFuncionario(recuperarIdFuncionario()).build());
		if (dtos == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(dtos);
	}
	
	@GetMapping(value="/pontos/horas-previstas/tipos/{idTipoPeriodoPonto}")
	@ApiOperation(value = "Retornar Horas Previstas", notes = "Retornar Horas Previstas")
	public ResponseEntity<PontoDTO> recuperarHorasPrevistas(@PathVariable(value = "idTipoPeriodoPonto") Integer idTipoPeriodoPonto) {
		PontoDTO dto = pontoServico.recuperarHorasPrevistas(FiltroPonto.builder().idTipoPeriodoPonto(idTipoPeriodoPonto).idFuncionario(recuperarIdFuncionario()).build());
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/pontos/{id}")
	@ApiOperation(value = "Buscar Ponto por id", notes = "Buscar Ponto por id")
	public ResponseEntity<PontoDTO> buscarPontoPorId(@PathVariable(value = "id") Long id)
			throws ExcecaoDeNegocio {

		PontoDTO dto = pontoServico.buscarPorId(id, recuperarIdFuncionario());
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/pontos/anos")
	@ApiOperation(value = "Listar Anos dos pontos cadastrados", notes = "Listar Anos dos pontos cadastrados")
	public ResponseEntity<List<Integer>> listarAnos(@RequestParam(value = "idFuncionario", required = false) Long idFuncionario) {
		List<Integer> anos = pontoServico.listarAnos(idFuncionario);
		if (anos == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(anos);
	}
	
	@GetMapping(value="/pontos/meses")
	@ApiOperation(value = "Listar Meses dos pontos cadastrados", notes = "Listar Meses dos pontos cadastrados")
	public ResponseEntity<List<MesDTO>> listarMeses(@RequestParam(value = "idFuncionario", required = false) Long idFuncionario,
			@RequestParam(value = "ano", required = false) Integer ano) {
		List<MesDTO> meses = pontoServico.listarMeses(idFuncionario, ano);
		if (meses == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(meses);
	}
	
	@PostMapping("/pontos/ausentes")
	@ApiOperation(value = "Gerar Pontos Ausentes", notes = "Gerar Pontos Ausentes")
	public ResponseEntity<String> gerarPontosAusentes(@RequestBody List<AusenteDTO> dtos) {
		if(dtos != null && !dtos.isEmpty()) {
			FuncionarioDTO funcionarioDTO = funcionarioServico.buscarPorIdPessoa(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
			dtos.stream().forEach(ausente -> {
				ausente.setIdFuncionarioLogado(funcionarioDTO.getId());
			});
		}
		pontoServico.gerarAusentes(dtos);
		return ResponseEntity.ok().body("Solicitação para a geração dos pontos ausentes enviada com sucesso");
	}
	
	@GetMapping("/pontos/ausentes")
	@ApiOperation(value = "Listar Ausentes", notes = "Listar Ausentes")
	public ResponseEntity<List<AusenteDTO>> listarAusentes(@RequestParam(value = "idFuncionario", required = false) Long idFuncionario,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "sabadoSuprimido", required = false) Boolean sabadoSuprimido,
			@RequestParam(value = "domingoSuprimido", required = false) Boolean domingoSuprimido) {
		
		List<Integer> idsDiaDaSemanaSuprimidos = new ArrayList<Integer>();
		if(sabadoSuprimido) {
			idsDiaDaSemanaSuprimidos.add(EnumTipoDiaSemana.SABADO.id());
		}
		if(domingoSuprimido) {
			idsDiaDaSemanaSuprimidos.add(0);
		}

		List<AusenteDTO> ausentes = pontoServico
				.listarAusentes(FiltroPonto.builder()
									.idFuncionario(recuperarIdFuncionario())
									.idsDiasSubrimidos(idsDiaDaSemanaSuprimidos.toArray(new Integer[idsDiaDaSemanaSuprimidos.size()]))
									.ano(ano)
									.mes(mes)
									.build());
		
		if (ausentes == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(ausentes);
	}
	//### FIM PONTO
	
	//### PONTO HOMOLOGACAO
	
	@PostMapping("/pontos/homologacoes/libera")
	@ApiOperation(value = "Liberar Ponto", notes = "Liberar Ponto")
	public ResponseEntity<String> liberarPonto(@RequestBody PontoDTO dto) {
		setIdFuncionarioLogado(dto);
		pontoServico.liberar(dto);
		return ResponseEntity.ok().body("Solicitação de liberação do(s) ponto(s) enviado(s) com sucesso");
	}
	
	@PostMapping("/pontos/homologacoes/libera/cancela")
	@ApiOperation(value = "Cancelar Liberaracao Ponto", notes = "Cancelar Liberaracao Ponto")
	public ResponseEntity<String> cancelarLiberacaoPonto(@RequestBody PontoDTO dto) {
		setIdFuncionarioLogado(dto);
		pontoServico.cancelarHomologacao(dto);
		return ResponseEntity.ok().body("Solicitação de cancelamento da liberação do(s) ponto(s) enviado(s) com sucesso");
	}
	
	@PostMapping("/pontos/homologacoes/homologa")
	@ApiOperation(value = "Homologar Ponto", notes = "Homologar Ponto")
	public ResponseEntity<String> homologarPonto(@RequestBody PontoDTO dto) {
		setIdFuncionarioLogado(dto);
		pontoServico.homologar(dto);
		return ResponseEntity.ok().body("Solicitação de homologação do(s) ponto(s) enviado(s) com sucesso");
	}
	
	@PostMapping("/pontos/homologacoes/homologa/cancela")
	@ApiOperation(value = "Cancelar Homologacao Ponto", notes = "Cancelar Homologacao Ponto")
	public ResponseEntity<String> cancelarHomologacaoPonto(@RequestBody PontoDTO dto) {
		setIdFuncionarioLogado(dto);
		pontoServico.cancelarLiberacao(dto);
		return ResponseEntity.ok().body("Solicitação de homologação da liberação do(s) ponto(s) enviado(s) com sucesso");
	}
	
	@GetMapping("/pontos/homologacoes")
	@ApiOperation(value = "Listar Pontos para Homologacao", notes = "Listar Pontos para Homologacao")
	public ResponseEntity<PaginacaoDTO<PontoDTO>> listarPontosParaHomologacao(@RequestParam(value = "idFuncionario", required = false) Long idFuncionario,
			@RequestParam(value = "pendente", required = false) boolean pendente,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina) {

		PaginacaoDTO<PontoDTO> pontos = pontoServico
				.listarParaFolhaDePonto(FiltroPonto.builder()
									.idFuncionario(idFuncionario)
									.pendente(pendente)
									.ano(ano)
									.mes(mes)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (pontos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(pontos);
	}
	
	//### FIM PONTO HOMOLOGACAO
	
}
