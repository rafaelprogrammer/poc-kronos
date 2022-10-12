package br.com.kronos.backend.adaptadores.controlador.swagger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.kronos.backend.config.Swagger2Config;

@RestController
public class SwaggerController {
	@GetMapping(Swagger2Config.URL_DOCUMENTATION + "/v2/{url}")
	public ModelAndView api(@PathVariable("url") String url) {
		return new ModelAndView("forward:/v2/" + url);
	}

	@GetMapping(Swagger2Config.URL_DOCUMENTATION + "/configuration/{url}")
	public ModelAndView ui(@PathVariable("url") String url) {
		return new ModelAndView("forward:/configuration/" + url);
	}

	@GetMapping(Swagger2Config.URL_DOCUMENTATION + "/swagger-resources")
	public ModelAndView resources() {
		return new ModelAndView("forward:/swagger-resources");
	}

	@GetMapping(Swagger2Config.URL_DOCUMENTATION + "/swagger-resources/configuration/{url}")
	public ModelAndView configuration(@PathVariable("url") String url) {
		return new ModelAndView("forward:/swagger-resources/configuration/" + url);
	}

	@GetMapping(Swagger2Config.URL_DOCUMENTATION + "")
	public ModelAndView documentation() {
		return new ModelAndView("redirect:" + Swagger2Config.URL_DOCUMENTATION + "/swagger-ui.html");
	}
}

