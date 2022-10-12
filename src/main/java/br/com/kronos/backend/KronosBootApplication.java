package br.com.kronos.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.com.kronos.backend.*")
public class KronosBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(KronosBootApplication.class, args);
	}

}
