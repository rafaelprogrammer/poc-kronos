package br.com.kronos.backend.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author p_991173
 *
 */
@Configuration
@Profile("!teste-controller")
public class ConfigDataSource {
	
	
//	@Bean	
//	public DataSource dsH2() {
//		
//		// @formatter:off
//		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder()				
//				.generateUniqueName(true)
//				.setType(H2)
//				.setScriptEncoding("UTF-8")
//				.ignoreFailedDrops(true)
//				.addScripts(format("sql/schema-%s.sql", "h2"), format("sql/data-%s.sql", "h2"));
//		// @formatter:on
//		
//		return builder.build();
//	}	

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public DataSource ds() {
		return DataSourceBuilder.create().build();
	}
	
	
	
}
