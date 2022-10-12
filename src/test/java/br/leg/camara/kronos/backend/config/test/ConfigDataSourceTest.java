package br.leg.camara.kronos.backend.config.test;

import static java.lang.String.format;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

import javax.sql.DataSource;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import br.com.kronos.backend.config.ConfigDataSource;

@TestConfiguration
@Import({ ConfigDataSource.class })
public class ConfigDataSourceTest {

	@Bean	
	public DataSource ds() {
		return criar("h2-test");
	}
	
	private DataSource criar(String nome) {
		// @formatter:off
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder()
				.setName(nome)
				.setType(H2)
				.setScriptEncoding("UTF-8")
				.ignoreFailedDrops(true)
				.addScripts(format("sql/schema-%s.sql", nome), format("sql/data-%s.sql", nome));
		// @formatter:on
		
		return builder.build();
	}	
}
