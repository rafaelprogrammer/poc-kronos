package br.com.kronos.backend.adaptadores.controlador.basecurricular;

import java.util.Arrays;
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
import br.com.kronos.backend.aplicacao.basecurricular.FiltroAtitude;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroCampoExperiencia;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroCompetencia;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroComponente;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroDireito;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroFaixaAno;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroHabilidade;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroNivel;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroObjetivo;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroValor;
import br.com.kronos.backend.aplicacao.basecurricular.api.AtitudeDTO;
import br.com.kronos.backend.aplicacao.basecurricular.api.AtitudeServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.CampoExperienciaDTO;
import br.com.kronos.backend.aplicacao.basecurricular.api.CampoExperienciaServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.CompetenciaDTO;
import br.com.kronos.backend.aplicacao.basecurricular.api.CompetenciaServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.ComponenteDTO;
import br.com.kronos.backend.aplicacao.basecurricular.api.ComponenteServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.DireitoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.api.DireitoServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.FaixaAnoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.api.FaixaAnoServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.HabilidadeDTO;
import br.com.kronos.backend.aplicacao.basecurricular.api.HabilidadeServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.NivelDTO;
import br.com.kronos.backend.aplicacao.basecurricular.api.NivelServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.ObjetivoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.api.ObjetivoServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.ValorDTO;
import br.com.kronos.backend.aplicacao.basecurricular.api.ValorServico;
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/basescurriculares")
@CrossOrigin
public class BaseCurricularControlador extends BaseControlador {
	
	@NonNull
	private NivelServico nivelServico;
	
	@NonNull
	private FaixaAnoServico faixaAnoServico;
	
	@NonNull
	private ComponenteServico componenteServico;
	
	@NonNull
	private CampoExperienciaServico campoExperienciaServico;
	
	@NonNull
	private DireitoServico direitoServico;
	
	@NonNull
	private ServicoAutenticacao servicoAutenticacao;
	
	@NonNull
	private ObjetivoServico objetivoServico;
	
	@NonNull
	private CompetenciaServico competenciaServico;
	
	@NonNull
	private HabilidadeServico habilidadeServico;
	
	@NonNull
	private ValorServico valorServico;
	
	@NonNull
	private AtitudeServico atitudeServico;
	
	//#### NIVEIS
	@PostMapping("/niveis")
	@ApiOperation(value = "Cadastrar Nivel", notes = "Cadastrar Nivel")
	public ResponseEntity<String> criarNivel(@RequestBody NivelDTO dto) {
		nivelServico.excluir(dto.getId());
		return ResponseEntity.ok().body("Cadastro do Nível enviado com sucesso");
	}
	
	@PutMapping("/niveis/{id}")
	@ApiOperation(value = "Alterar Nivel", notes = "Alterar Nivel")
	public ResponseEntity<String> alterarNivel(@PathVariable(value = "id") Integer id, @RequestBody NivelDTO dto) {
		dto.setId(id);
		nivelServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do Nível enviado com sucesso");
	}
	
	@DeleteMapping("/niveis/{id}")
	@ApiOperation(value = "Excluir Nivel", notes = "Excluir Nivel")
	public ResponseEntity<?> excluirNivel(@PathVariable(value = "id") Integer id) {
		nivelServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do Nível enviado com sucesso");
	}
	
	@GetMapping(value = "/niveis/{id}")
	@ApiOperation(value = "Buscar Nivel por id", notes = "Buscar Nivel por id")
	public ResponseEntity<NivelDTO> buscarNivelPorId(@PathVariable(value = "id") Integer id) {

		NivelDTO dto = nivelServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/niveis")
	@ApiOperation(value = "Listar Niveis", notes = "Listar Niveis")
	public ResponseEntity<PaginacaoDTO<NivelDTO>> listarNiveis(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<NivelDTO> niveis = nivelServico
				.listar(FiltroNivel.builder()
									.id(id)
									.nome(nome)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (niveis == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(niveis);
	}

	@GetMapping(value="/niveis/combo")
	@ApiOperation(value = "Listar Niveis", notes = "Listar Niveis")
	public ResponseEntity<List<NivelDTO>> listarNiveisParaCombo() {
		List<NivelDTO> niveis = nivelServico
				.listarParaCombo();
		
		if (niveis == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(niveis);
	}
	
	//#### FIM NIVEIS
	
	//#### FAIXAS ANO
	@PostMapping("/faixas-anos")
	@ApiOperation(value = "Cadastrar Faixa Ano", notes = "Cadastrar Faixa Ano")
	public ResponseEntity<String> criarFaixaAno(@RequestBody FaixaAnoDTO dto) {
		faixaAnoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro da Faixa/Ano enviado com sucesso");
	}
	
	@PutMapping("/faixas-anos/{id}")
	@ApiOperation(value = "Alterar Faixa Ano", notes = "Alterar Faixa Ano")
	public ResponseEntity<String> alterarFaixaAno(@PathVariable(value = "id") Integer id, @RequestBody FaixaAnoDTO dto) {
		dto.setId(id);
		faixaAnoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da Faixa/Ano enviado com sucesso");
	}
	
	@DeleteMapping("/faixas-anos/{id}")
	@ApiOperation(value = "Excluir Faixa Ano", notes = "Excluir Faixa Ano")
	public ResponseEntity<?> excluirFaixaAno(@PathVariable(value = "id") Integer id) {
		faixaAnoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da Faixa/Ano enviado com sucesso");
	}
	
	@GetMapping(value = "/faixas-anos/{id}")
	@ApiOperation(value = "Buscar Faixa Ano por id", notes = "Buscar Faixa Ano por id")
	public ResponseEntity<FaixaAnoDTO> buscarFaixaAnoPorId(@PathVariable(value = "id") Integer id) {

		FaixaAnoDTO dto = faixaAnoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping("/faixas-anos/combo")
	@ApiOperation(value = "Listar Faixas Ano para combo", notes = "Listar Faixas Ano para combo")
	public ResponseEntity<List<FaixaAnoDTO>> listarFaixasAnosParaCombo(@RequestParam(value = "idNivel", required = false) Integer idNivel) {

		List<FaixaAnoDTO> faixas = faixaAnoServico.listarParaCombo(FiltroFaixaAno.builder().idNivel(idNivel).build());
		
		if (faixas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(faixas);
	}
	
	@GetMapping(value="/faixas-anos")
	@ApiOperation(value = "Listar Faixas de Ano", notes = "Listar Faixas de Ano")
	public ResponseEntity<PaginacaoDTO<FaixaAnoDTO>> listar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "idNivel", required = false) Integer idNivel,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<FaixaAnoDTO> faixas = faixaAnoServico
				.listar(FiltroFaixaAno.builder()
									.id(id)
									.nome(nome)
									.idNivel(idNivel)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (faixas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(faixas);
	}
	//#### FIM FAIXAS ANO
	
	//#### COMPONENTES
	@PostMapping("/componentes")
	@ApiOperation(value = "Cadastrar Componente", notes = "Cadastrar Faixa Ano")
	public ResponseEntity<String> criarComponente(@RequestBody ComponenteDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		componenteServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do Componente enviado com sucesso");
	}
	
	@PutMapping("/componentes/{id}")
	@ApiOperation(value = "Alterar Componente", notes = "Alterar Componente")
	public ResponseEntity<String> alterarComponente(@PathVariable(value = "id") Long id, @RequestBody ComponenteDTO dto) {
		dto.setId(id);
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		componenteServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do Componente enviado com sucesso");
	}
	
	@DeleteMapping("/componentes/{id}")
	@ApiOperation(value = "Excluir Faixa Ano", notes = "Excluir Componente")
	public ResponseEntity<?> excluirComponente(@PathVariable(value = "id") Long id) {
		componenteServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do Componente enviado com sucesso");
	}
	
	@GetMapping(value = "/componentes/{id}")
	@ApiOperation(value = "Buscar Componente por id", notes = "Buscar Componente por id")
	public ResponseEntity<ComponenteDTO> buscarComponentePorId(@PathVariable(value = "id") Long id) {

		ComponenteDTO dto = componenteServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping("/componentes/combo")
	@ApiOperation(value = "Listar Componentes para combo", notes = "Listar Componentes para combo")
	public ResponseEntity<List<ComponenteDTO>> listarComponentesParaCombo(@RequestParam(value = "idNivel", required = false) Integer idNivel) {

		List<ComponenteDTO> componentes = componenteServico.listarParaCombo(FiltroComponente.builder()
				.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
				.idNivel(idNivel).build());
		
		if (componentes == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(componentes);
	}
	
	@GetMapping(value="/componentes")
	@ApiOperation(value = "Listar Componente", notes = "Listar Componente")
	public ResponseEntity<PaginacaoDTO<ComponenteDTO>> listarComponentes(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "idNivel", required = false) Integer idNivel,
			@RequestParam(value = "ativo", required = false) Boolean ativo,
			@RequestParam(value = "bncc", required = false) Boolean bncc,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<ComponenteDTO> componentes = componenteServico
				.listar(FiltroComponente.builder()
									.id(id)
									.nome(nome)
									.idNivel(idNivel)
									.bncc(bncc)
									.ativo(ativo)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (componentes == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(componentes);
	}
	//#### FIM COMPONENTES
	
	//#### CAMPOS EXPERIENCIAS
	@PostMapping("/campos-experiencias")
	@ApiOperation(value = "Cadastrar Campo Experiencia", notes = "Cadastrar Campo Experiencia")
	public ResponseEntity<String> criarCampoExperiencia(@RequestBody CampoExperienciaDTO dto) {
		campoExperienciaServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do Campo Experiência enviado com sucesso");
	}
	
	@PutMapping("/campos-experiencias/{id}")
	@ApiOperation(value = "Alterar Campo Experiencia", notes = "Alterar Campo Experiencia")
	public ResponseEntity<String> alterarCampoExperiencia(@PathVariable(value = "id") Long id, @RequestBody CampoExperienciaDTO dto) {
		dto.setId(id);
		campoExperienciaServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do Campo Experiência enviado com sucesso");
	}
	
	@DeleteMapping("/campos-experiencias/{id}")
	@ApiOperation(value = "Excluir Campo Experiencia", notes = "Excluir Campo Experiencia")
	public ResponseEntity<?> excluirCampoExperiencia(@PathVariable(value = "id") Long id) {
		campoExperienciaServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do Campo Experiência enviado com sucesso");
	}
	
	@GetMapping(value = "/campos-experiencias/{id}")
	@ApiOperation(value = "Buscar Campo Experiencia por id", notes = "Buscar Campo Experiencia por id")
	public ResponseEntity<CampoExperienciaDTO> buscarCampoExperienciaPorId(@PathVariable(value = "id") Long id) {

		CampoExperienciaDTO dto = campoExperienciaServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping("/campos-experiencias/combo")
	@ApiOperation(value = "Listar Campos Experiencias Ano para combo", notes = "Listar Campos Experiencias para combo")
	public ResponseEntity<List<CampoExperienciaDTO>> listarCamposExperienciasParaCombo(@RequestParam(value = "idNivel", required = false) Integer idNivel,
			@RequestParam(value = "idFaixaAno", required = false) Long idFaixaAno) {

		List<CampoExperienciaDTO> campos = campoExperienciaServico.listarParaCombo(FiltroCampoExperiencia.builder().idNivel(idNivel).build());
		
		if (campos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(campos);
	}
	
	@GetMapping(value="/campos-experiencias")
	@ApiOperation(value = "Listar Campos Experiencias", notes = "Listar Campos Experiencias")
	public ResponseEntity<PaginacaoDTO<CampoExperienciaDTO>> listarCamposExperiencias(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "idNivel", required = false) Integer idNivel,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<CampoExperienciaDTO> campos = campoExperienciaServico
				.listar(FiltroCampoExperiencia.builder()
									.id(id)
									.nome(nome)
									.idNivel(idNivel)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (campos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(campos);
	}
	//#### FIM CAMPOS EXPERIENCIAS
	
	//#### DIREITOS
	@PostMapping("/direitos")
	@ApiOperation(value = "Cadastrar Direito", notes = "Cadastrar Direito")
	public ResponseEntity<String> criarDireito(@RequestBody DireitoDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		direitoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do Direito enviado com sucesso");
	}
	
	@PutMapping("/direitos/{id}")
	@ApiOperation(value = "Alterar Direito", notes = "Alterar Direito")
	public ResponseEntity<String> alterarCampoExperiencia(@PathVariable(value = "id") Long id, @RequestBody DireitoDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		dto.setId(id);
		direitoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do Direito enviado com sucesso");
	}
	
	@DeleteMapping("/direitos/{id}")
	@ApiOperation(value = "Excluir Direito", notes = "Excluir Direito")
	public ResponseEntity<?> excluirDireito(@PathVariable(value = "id") Long id) {
		direitoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do Direito enviado com sucesso");
	}
	
	@GetMapping(value = "/direitos/{id}")
	@ApiOperation(value = "Buscar Direito por id", notes = "Buscar Direito por id")
	public ResponseEntity<DireitoDTO> buscarDireitoPorId(@PathVariable(value = "id") Long id) {

		DireitoDTO dto = direitoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/direitos")
	@ApiOperation(value = "Listar Direitos", notes = "Listar Direitos")
	public ResponseEntity<PaginacaoDTO<DireitoDTO>> listarDireitos(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "codigo", required = false) String codigo,
			@RequestParam(value = "idNivel", required = false) Integer idNivel,
			@RequestParam(value = "ativo", required = false) Boolean ativo,
			@RequestParam(value = "bncc", required = false) Boolean bncc,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<DireitoDTO> direitos = direitoServico
				.listar(FiltroDireito.builder()
									.id(id)
									.codigo(codigo)
									.idNivel(idNivel)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.ativo(ativo)
									.bncc(bncc)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (direitos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(direitos);
	}
	
	@GetMapping(value="/direitos/inclusao-disciplina-direito")
	@ApiOperation(value = "Listar Direitos para Disciplina Direito", notes = "Listar Direitos para Disciplina Direito")
	public ResponseEntity<PaginacaoDTO<DireitoDTO>> listarDireitosParaDisciplinaDireito(@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idNivel", required = false) Integer idNivel,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<DireitoDTO> direitos = direitoServico
				.listarParaDisciplinaDireito(FiltroDireito.builder()
									.idDisciplina(idDisciplina)
									.idNivel(idNivel)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (direitos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(direitos);
	}
	//#### FIM DIREITOS

	//#### OBJETIVOS
	@PostMapping("/objetivos")
	@ApiOperation(value = "Cadastrar Objetivo", notes = "Cadastrar Objetivo")
	public ResponseEntity<String> criarObjetivo(@RequestBody ObjetivoDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		objetivoServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do Objetivo enviado com sucesso");
	}
	
	@PutMapping("/objetivos/{id}")
	@ApiOperation(value = "Alterar Objetivo", notes = "Alterar Objetivo")
	public ResponseEntity<String> alterarObjetivo(@PathVariable(value = "id") Long id, @RequestBody ObjetivoDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		dto.setId(id);
		objetivoServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do Objetivo enviado com sucesso");
	}
	
	@DeleteMapping("/objetivos/{id}")
	@ApiOperation(value = "Excluir Objetivo", notes = "Excluir Objetivo")
	public ResponseEntity<?> excluirObjetivo(@PathVariable(value = "id") Long id) {
		objetivoServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do Objetivo enviado com sucesso");
	}
	
	@GetMapping(value = "/objetivos/{id}")
	@ApiOperation(value = "Buscar Objetivo por id", notes = "Buscar Objetivo por id")
	public ResponseEntity<ObjetivoDTO> buscarObjetivoPorId(@PathVariable(value = "id") Long id) {

		ObjetivoDTO dto = objetivoServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/objetivos")
	@ApiOperation(value = "Listar Objetivos", notes = "Listar Objetivos")
	public ResponseEntity<PaginacaoDTO<ObjetivoDTO>> listarObjetivo(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "codigo", required = false) String codigo,
			@RequestParam(value = "idFaixaAno", required = false) Long idFaixaAno,
			@RequestParam(value = "ativo", required = false) Boolean ativo,
			@RequestParam(value = "bncc", required = false) Boolean bncc,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<ObjetivoDTO> objetivos = objetivoServico
				.listar(FiltroObjetivo.builder()
									.id(id)
									.codigo(codigo)
									.idFaixaAno(idFaixaAno)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.ativo(ativo)
									.bncc(bncc)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (objetivos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(objetivos);
	}
	
	@GetMapping(value="/objetivos/inclusao-disciplina-objetivo")
	@ApiOperation(value = "Listar Objetivos para Disciplina Objetivo", notes = "Listar Objetivos para Disciplina Objetivo")
	public ResponseEntity<PaginacaoDTO<ObjetivoDTO>> listarDireitosParaDisciplinaDireito(@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idFaixaAno", required = false) Long idFaixaAno,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<ObjetivoDTO> objetivos = objetivoServico
				.listarParaDisciplinaObjetivo(FiltroObjetivo.builder()
									.idDisciplina(idDisciplina)
									.idFaixaAno(idFaixaAno)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (objetivos == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(objetivos);
	}
	//#### FIM OBJETIVOS
	
	//#### COMPETENCIAS
	@PostMapping("/competencias")
	@ApiOperation(value = "Cadastrar Competencia", notes = "Cadastrar Competencia")
	public ResponseEntity<String> criarCompetencia(@RequestBody CompetenciaDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		competenciaServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro da Competência enviada com sucesso");
	}
	
	@PutMapping("/competencias/{id}")
	@ApiOperation(value = "Alterar Competencia", notes = "Alterar Competencia")
	public ResponseEntity<String> alterarCompetencia(@PathVariable(value = "id") Long id, @RequestBody CompetenciaDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		dto.setId(id);
		competenciaServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da Competência enviada com sucesso");
	}
	
	@DeleteMapping("/competencias/{id}")
	@ApiOperation(value = "Excluir Competencia", notes = "Excluir Competencia")
	public ResponseEntity<?> excluirCompetencia(@PathVariable(value = "id") Long id) {
		competenciaServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da Competência enviada com sucesso");
	}
	
	@GetMapping(value = "/competencias/{id}")
	@ApiOperation(value = "Buscar Competencia por id", notes = "Buscar Competencia por id")
	public ResponseEntity<CompetenciaDTO> buscarCompetenciaPorId(@PathVariable(value = "id") Long id) {

		CompetenciaDTO dto = competenciaServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/competencias")
	@ApiOperation(value = "Listar Competencias", notes = "Listar Competencias")
	public ResponseEntity<PaginacaoDTO<CompetenciaDTO>> listarCompetencias(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "codigo", required = false) String codigo,
			@RequestParam(value = "idNivel", required = false) Integer idNivel,
			@RequestParam(value = "idComponente", required = false) Long idComponente,
			@RequestParam(value = "ativo", required = false) Boolean ativo,
			@RequestParam(value = "bncc", required = false) Boolean bncc,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<CompetenciaDTO> competencias = competenciaServico
				.listar(FiltroCompetencia.builder()
									.id(id)
									.codigo(codigo)
									.idNivel(idNivel)
									.idComponente(idComponente)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.ativo(ativo)
									.bncc(bncc)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (competencias == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(competencias);
	}
	
	@GetMapping(value="/competencias/ids")
	@ApiOperation(value = "Listar Competencias por ids", notes = "Listar Competencias por ids")
	public ResponseEntity<List<CompetenciaDTO>> listarPorIds(@RequestParam(value = "ids", required = false) Long[] ids)
			throws ExcecaoDeNegocio {

		List<CompetenciaDTO> competencias = competenciaServico
				.listarPorIds(Arrays.asList(ids));
		
		if (competencias == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(competencias);
	}
	
	@GetMapping(value="/competencias/inclusao-disciplina-competencia")
	@ApiOperation(value = "Listar Competencias para Disciplina Competencia", notes = "Listar Competencias para Disciplina Competencia")
	public ResponseEntity<PaginacaoDTO<CompetenciaDTO>> listarCompetenciasParaDisciplinaCompetencia(@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idNivel", required = false) Integer idNivel,
			@RequestParam(value = "idComponente", required = false) Long idComponente,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<CompetenciaDTO> competencias = competenciaServico
				.listarParaDisciplinaCompetencia(FiltroCompetencia.builder()
									.idDisciplina(idDisciplina)
									.idNivel(idNivel)
									.idComponente(idComponente)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (competencias == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(competencias);
	}
	//#### FIM COMPETENCIAS
	
	//#### HABILIDADES
	@PostMapping("/habilidades")
	@ApiOperation(value = "Cadastrar Habilidade", notes = "Cadastrar Habilidade")
	public ResponseEntity<String> criarHabilidade(@RequestBody HabilidadeDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		habilidadeServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro da Habilidade enviada com sucesso");
	}
	
	@PutMapping("/habilidades/{id}")
	@ApiOperation(value = "Alterar Habilidade", notes = "Alterar Habilidade")
	public ResponseEntity<String> alterarHabilidade(@PathVariable(value = "id") Long id, @RequestBody HabilidadeDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		dto.setId(id);
		habilidadeServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da Habilidade enviada com sucesso");
	}
	
	@DeleteMapping("/habilidades/{id}")
	@ApiOperation(value = "Excluir Habilidade", notes = "Excluir Habilidade")
	public ResponseEntity<?> excluirHabilidade(@PathVariable(value = "id") Long id) {
		habilidadeServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da Habilidade enviada com sucesso");
	}
	
	@GetMapping(value = "/habilidades/{id}")
	@ApiOperation(value = "Buscar Habilidade por id", notes = "Buscar Habilidade por id")
	public ResponseEntity<HabilidadeDTO> buscarHabilidadePorId(@PathVariable(value = "id") Long id) {

		HabilidadeDTO dto = habilidadeServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/habilidades")
	@ApiOperation(value = "Listar Habilidades", notes = "Listar Habilidades")
	public ResponseEntity<PaginacaoDTO<HabilidadeDTO>> listarHabilidades(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "codigo", required = false) String codigo,
			@RequestParam(value = "idNivel", required = false) Integer idNivel,
			@RequestParam(value = "idComponente", required = false) Long idComponente,
			@RequestParam(value = "ativo", required = false) Boolean ativo,
			@RequestParam(value = "bncc", required = false) Boolean bncc,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<HabilidadeDTO> habilidades = habilidadeServico
				.listar(FiltroHabilidade.builder()
									.id(id)
									.codigo(codigo)
									.idNivel(idNivel)
									.idComponente(idComponente)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.ativo(ativo)
									.bncc(bncc)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (habilidades == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(habilidades);
	}
	
	@GetMapping(value="/habilidades/inclusao-disciplina-habilidade")
	@ApiOperation(value = "Listar Habilidades para Disciplina Habilidade", notes = "Listar Habilidades para Disciplina Habilidade")
	public ResponseEntity<PaginacaoDTO<HabilidadeDTO>> listarDireitosParaDisciplinaHabilidade(@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idComponente", required = false) Long idComponente,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<HabilidadeDTO> habilidades = habilidadeServico
				.listarParaDisciplinaHabilidade(FiltroHabilidade.builder()
									.idDisciplina(idDisciplina)
									.idComponente(idComponente)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (habilidades == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(habilidades);
	}
	//#### FIM HABILIDADES
	
	//#### VALORES
	@PostMapping("/valores")
	@ApiOperation(value = "Cadastrar Valor", notes = "Cadastrar Valor")
	public ResponseEntity<String> criarValor(@RequestBody ValorDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		valorServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro do Valor enviado com sucesso");
	}
	
	@PutMapping("/valores/{id}")
	@ApiOperation(value = "Alterar Valor", notes = "Alterar Valor")
	public ResponseEntity<String> alterarValor(@PathVariable(value = "id") Long id, @RequestBody ValorDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		dto.setId(id);
		valorServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração do Valor enviado com sucesso");
	}
	
	@DeleteMapping("/valores/{id}")
	@ApiOperation(value = "Excluir Valor", notes = "Excluir Valor")
	public ResponseEntity<?> excluirValor(@PathVariable(value = "id") Long id) {
		valorServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão do Valor enviado com sucesso");
	}
	
	@GetMapping(value = "/valores/{id}")
	@ApiOperation(value = "Buscar Valor por id", notes = "Buscar Valor por id")
	public ResponseEntity<ValorDTO> buscarValorPorId(@PathVariable(value = "id") Long id) {

		ValorDTO dto = valorServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping("/valores/combo")
	@ApiOperation(value = "Listar Valores para combo", notes = "Listar Valores para combo")
	public ResponseEntity<List<ValorDTO>> listarCamposExperienciasParaCombo() {

		List<ValorDTO> valores = valorServico.listarParaCombo(FiltroValor.builder()
																			.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao()).build());
		
		if (valores == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(valores);
	}
	
	@GetMapping(value="/valores")
	@ApiOperation(value = "Listar Valores", notes = "Listar Valores")
	public ResponseEntity<PaginacaoDTO<ValorDTO>> listarValores(
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<ValorDTO> valores = valorServico
				.listar(FiltroValor.builder()
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (valores == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(valores);
	}
	//#### FIM VALORES
	
	//#### ATITUDES
	@PostMapping("/atitudes")
	@ApiOperation(value = "Cadastrar Atitude", notes = "Cadastrar Atitude")
	public ResponseEntity<String> criarAtitude(@RequestBody AtitudeDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		atitudeServico.criar(dto);
		return ResponseEntity.ok().body("Cadastro da Atitude enviada com sucesso");
	}
	
	@PutMapping("/atitudes/{id}")
	@ApiOperation(value = "Alterar Atitude", notes = "Alterar Atitude")
	public ResponseEntity<String> alterarAtitude(@PathVariable(value = "id") Long id, @RequestBody AtitudeDTO dto) {
		dto.setIdInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao());
		dto.setId(id);
		atitudeServico.alterar(dto);
		return ResponseEntity.ok().body("Alteração da Atitude enviada com sucesso");
	}
	
	@DeleteMapping("/atitudes/{id}")
	@ApiOperation(value = "Excluir Atitude", notes = "Excluir Atitude")
	public ResponseEntity<?> excluirAtitude(@PathVariable(value = "id") Long id) {
		atitudeServico.excluir(id);
		return ResponseEntity.ok().body("Exclusão da Atitude enviada com sucesso");
	}
	
	@GetMapping(value = "/atitudes/{id}")
	@ApiOperation(value = "Buscar Atitude por id", notes = "Buscar Atitude por id")
	public ResponseEntity<AtitudeDTO> buscarAtitudePorId(@PathVariable(value = "id") Long id) {

		AtitudeDTO dto = atitudeServico.buscarPorId(id);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/atitudes")
	@ApiOperation(value = "Listar Atitudes", notes = "Listar Atitudes")
	public ResponseEntity<PaginacaoDTO<AtitudeDTO>> listarAtitudes(
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<AtitudeDTO> atitudes = atitudeServico
				.listar(FiltroAtitude.builder()
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (atitudes == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(atitudes);
	}
	//#### FIM ATITUDES

}
