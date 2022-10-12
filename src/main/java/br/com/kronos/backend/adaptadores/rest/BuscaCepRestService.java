package br.com.kronos.backend.adaptadores.rest;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;

public class BuscaCepRestService {
	
	private RestTemplate restTemplate;
	
	
	public BuscaCepRestService(String urlBase) {
		this.restTemplate = new RestTemplateBuilder()
								.rootUri(urlBase)
								.setConnectTimeout(Duration.ofMinutes(1))
								.setReadTimeout(Duration.ofMinutes(1))
								.configure(new RestTemplate());
	}



	public CepDTO buscar(String cep) throws ExcecaoDeNegocio {
		try {
		ResponseEntity<CepDTO> response = restTemplate.getForEntity("/" + cep  + "/json", CepDTO.class);
		if(response.getBody() != null &&  StringUtils.isEmpty(response.getBody().getCep())) {
			throw new ExcecaoDeNegocio("CEP - " + cep + " não localizado.");
		}
		return response.getBody();
		} catch (HttpClientErrorException e) {
			throw new ExcecaoDeNegocio("CEP - " + cep + " não localizado.");
		}
	}

}
