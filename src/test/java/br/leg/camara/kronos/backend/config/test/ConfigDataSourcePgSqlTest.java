package br.leg.camara.kronos.backend.config.test;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;

import br.com.kronos.backend.config.ConfigDataSource;

@TestConfiguration
@Import({ ConfigDataSource.class })
public class ConfigDataSourcePgSqlTest {
	
	@Bean
    public DataSource ds() throws Exception {
        DataSource embeddedPostgresDS = EmbeddedPostgres.builder()
                .start().getPostgresDatabase();
        return embeddedPostgresDS;
    }
	
	@PostConstruct
	public void executeScript() throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds());
		ClassLoader classLoader = getClass().getClassLoader();
	    File file = new File(classLoader.getResource("sql/data-pg-test.sql").getFile());
	    String data = FileUtils.readFileToString(file, "UTF-8");
	    jdbcTemplate.execute(data);
	}

}
