package br.com.kronos.backend.adaptadores.controlador;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseControlador {
	
	@ExceptionHandler(value = { ExcecaoDeNegocio.class, IllegalArgumentException.class,  MethodArgumentTypeMismatchException.class})
	@ResponseBody
	protected ResponseEntity<String> trataExcecoesDeEntradaDoUsuario(Exception ex) {
		String mensagem = ex.getMessage();
		if (ex instanceof MethodArgumentTypeMismatchException) {
			mensagem = "Requisição inválida. Verifique se o número e tipo dos parâmetros estão corretos";
		}		
		return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
	}
	
//	@ExceptionHandler(value = { ObjetoNaoEncontrado.class})
//	@ResponseBody
//	protected ResponseEntity<String> trataEntidadesNaoEncontradas(ObjetoNaoEncontrado ex) {
//		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//	}

	@ExceptionHandler(value = { AccessDeniedException.class })
	@ResponseBody
	protected ResponseEntity<String> trataExcecaoAcessoNegado(AccessDeniedException ex) {
		log.warn("Requisição inválida. Acesso Negado!", ex);
		return new ResponseEntity<>("\"Requisição inválida. Acesso Negado!\"", HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(value = { RuntimeException.class })
	@ResponseBody
	protected ResponseEntity<String> trataExcecoesGerais(RuntimeException ex) {
		log.error("Erro não tratado pela aplicação", ex);
		return new ResponseEntity<>("\"Ocorreu um erro inesperado\"", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
