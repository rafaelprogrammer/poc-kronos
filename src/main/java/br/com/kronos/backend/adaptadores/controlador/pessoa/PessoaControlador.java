package br.com.kronos.backend.adaptadores.controlador.pessoa;

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

import br.com.kronos.backend.adaptadores.controlador.BaseControlador;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroPessoa;
import br.com.kronos.backend.aplicacao.pessoa.api.FotoDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.PessoaDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.PessoaResumoDTO;
import br.com.kronos.backend.aplicacao.pessoa.api.PessoaServico;
import br.com.kronos.backend.aplicacao.pessoa.api.TipoGeneroDTO;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import br.com.kronos.backend.aplicacao.validador.ValidadorDeCPF;
import br.com.kronos.backend.aplicacao.validador.ValidadorDeEmail;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping("/api/pessoas")
@CrossOrigin
public class PessoaControlador extends BaseControlador {

	@NonNull
	private PessoaServico pessoaServico;
	

	@NonNull
	private DateUtil dateUtil;
	
	@NonNull
	private ServicoAutenticacao servicoAutenticacao;

	
	@GetMapping(value="/alterar-dados")
	@ApiOperation(value = "Listar Pessoas", notes = "Listar Pessoas")
	public ResponseEntity<String> teste()
			throws ExcecaoDeNegocio {

		PaginacaoDTO<PessoaDTO> pessoas = pessoaServico.listar(FiltroPessoa.builder().build());

		long index = 0;
		for(int i = 0; i <= pessoas.getDados().size(); i++) {
		
			pessoas.getDados().get(i).setNome("Aluno " + index);
			boolean altera = true;
			String cpf = null;
			boolean valido= false;
			while (altera) {
				 cpf = geraCPF();
			    
			    PaginacaoDTO<PessoaDTO> resultadoPessoa = pessoaServico.listar(FiltroPessoa.builder().cpf(cpf).build());
			    if(resultadoPessoa.getTotal() == null || resultadoPessoa.getTotal() <=0) {
			    	valido = validaCPF(cpf);
			    	altera = false;
			    }
			}
			if (valido) {
//				pessoas.getDados().get(i).setNome("Aluno " + i);
//				pessoas.getDados().get(i).setNomeSocial("Aluno " + i);
//				pessoas.getDados().get(i).setNomeUsual("Aluno " + i);
//				pessoas.getDados().get(i).setCpf(cpf);
				pessoas.getDados().get(i).setEmail("teste" + i  + "@gmail.com");
				pessoaServico.alterar(pessoas.getDados().get(i));
				index++;
			}
		}

		return ResponseEntity.ok().body("OK");
	}
	
	private static String calcDigVerif(String num) {
		Integer primDig, segDig;
		int soma = 0, peso = 10;
		for (int i = 0; i < num.length(); i++)
		soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;

		    if (soma % 11 == 0 | soma % 11 == 1)  
		        primDig = new Integer(0);  
		    else  
		        primDig = new Integer(11 - (soma % 11));  

		    soma = 0;  
		    peso = 11;  
		    for (int i = 0; i < num.length(); i++)  
		            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;  
		      
		    soma += primDig.intValue() * 2;  
		    if (soma % 11 == 0 | soma % 11 == 1)  
		        segDig = new Integer(0);  
		    else  
		        segDig = new Integer(11 - (soma % 11));  

		    return primDig.toString() + segDig.toString();  
		}  

		public static String geraCPF() {  
		    String iniciais = "";  
		    Integer numero;  
		    for (int i = 0; i < 9; i++) {  
		        numero = new Integer((int) (Math.random() * 10));  
		        iniciais += numero.toString();  
		    }  
		    return iniciais + calcDigVerif(iniciais);  
		}  

		public static boolean validaCPF(String cpf) {  
		    if (cpf.length() != 11)  
		        return false;  

		    String numDig = cpf.substring(0, 9);  
		    return calcDigVerif(numDig).equals(cpf.substring(9, 11));  
		} 

		public static void main (String args[]){
			System.out.println(geraCPF());
			System.out.println(validaCPF(geraCPF()));
		}
	
	@PostMapping(value="/foto", consumes = {"multipart/form-data"})
	@ApiOperation(value = "Alterar Foto", notes = "Alterar Foto")
	public ResponseEntity<String> alterarFoto(@RequestParam(value="file", required = true) MultipartFile file, @RequestParam("idPessoa") Long idPessoa) throws ExcecaoDeNegocio, JsonParseException, JsonMappingException, IOException {
		pessoaServico.alterarFoto(FotoDTO.builder()
				.idPessoa(idPessoa)
				.contentType(file.getContentType())
				.bytes(file.getBytes())
				.tamanho(file.getSize())
				.build());
		return ResponseEntity.ok().body("Cadastro da pessoa enviado com sucesso");
	}

	@PostMapping
	@ApiOperation(value = "Cadastrar Pessoa", notes = "Cadastrar Pessoa")
	public ResponseEntity<String> criar(@RequestBody PessoaDTO pessoaDTO) throws ExcecaoDeNegocio {
		validarDados(pessoaDTO);
		pessoaServico.criar(pessoaDTO);
		return ResponseEntity.ok().body("Cadastro da pessoa enviado com sucesso - " + pessoaDTO.getCpf());
	}

	private void validarDados(PessoaDTO pessoaDTO) {
		ValidadorDeCPF.isValidCPF(pessoaDTO.getCpf());
		if (pessoaServico.verificarCPFCadastrado(pessoaDTO.getId(), pessoaDTO.getCpf())) {
			throw new ExcecaoDeNegocio("CPF j?? cadastrado - " + pessoaDTO.getCpf());
		}
		ValidadorDeEmail.isValidEmailAddressRegex(pessoaDTO.getEmail());
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar Pessoa", notes = "Alterar Pessoa")
	public ResponseEntity<String> alterar(@PathVariable(value = "id") Long id, @RequestBody PessoaDTO pessoaDTO) throws ExcecaoDeNegocio {
		pessoaDTO.setId(id);
		validarDados(pessoaDTO);
		pessoaServico.alterar(pessoaDTO);
		return ResponseEntity.ok().body("Altera????o da pessoa enviada com sucesso - " + pessoaDTO.getCpf());
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir Pessoa", notes = "Excluir Pessoa")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) throws ExcecaoDeNegocio {
		pessoaServico.excluir(id);
		return ResponseEntity.ok().body("Exclus??o da pessoa enviada com sucesso - " + id);
	}
	
	@GetMapping
	@ApiOperation(value = "Listar Pessoas", notes = "Listar Pessoas")
	public ResponseEntity<PaginacaoDTO<PessoaDTO>> listar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "numeroRegistro", required = false) Long numeroRegistro,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<PessoaDTO> pessoas = pessoaServico
				.listar(FiltroPessoa.builder()
									.id(id)
									.nome(nome)
									.idInstituicao(servicoAutenticacao.buscarUsuarioLogado().getIdInstituicao())
									.cpf(cpf)
									.numeroRegistro(numeroRegistro)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (pessoas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(pessoas);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar Pessoa por id", notes = "Buscar Pessoa por id")
	public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable(value = "id") Long id)
			throws ExcecaoDeNegocio {

		PessoaDTO pessoaDTO = pessoaServico.buscarPorId(id);
		if (pessoaDTO == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(pessoaDTO);
	}
	
	@GetMapping(value="/generos")
	@ApiOperation(value = "Listar Tipos de Generos", notes = "Listar Tipos de Generos")
	public ResponseEntity<List<TipoGeneroDTO>> listarTipoGeneros()
			throws ExcecaoDeNegocio {
		return ResponseEntity.ok().body(TipoGeneroDTO.tipos());
	}
	
	@GetMapping("/alunos")
	@ApiOperation(value = "Listar Alunos", notes = "Listar Alunos")
	public ResponseEntity<PaginacaoDTO<PessoaResumoDTO>> listarAlunos(@RequestParam(value = "idCurso", required = false) Long idCurso,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam(value = "numeroRegistro", required = false) Long numeroRegistro,
			@RequestParam(value = "idTurma", required = false) Long idTurma,
			@RequestParam(value = "total", required = false) Integer total,
			@RequestParam(value = "pagina", required = false) Integer pagina)
			throws ExcecaoDeNegocio {

		PaginacaoDTO<PessoaResumoDTO> pessoas = pessoaServico
				.listarParaSelecionarAluno(FiltroPessoa.builder()
									.idCurso(idCurso)
									.nome(nome)
									.ano(ano)
									.numeroRegistro(numeroRegistro)
									.cpf(cpf)
									.idTurma(idTurma)
									.qtdTotal(total)
									.numeroPagina(pagina)
									.build());
		
		if (pessoas == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(pessoas);
	}

}
