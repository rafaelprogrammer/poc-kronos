package br.com.kronos.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.kronos.backend.adaptadores.seguranca.Authority;
import br.com.kronos.backend.adaptadores.seguranca.filter.AuthenticationTokenFilter;
import br.com.kronos.backend.adaptadores.seguranca.servico.TokenAuthenticationService;


@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

    private final TokenAuthenticationService tokenAuthenticationService;
    
    @Autowired
    protected ConfigSecurity(final TokenAuthenticationService tokenAuthenticationService) {
        super();
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    	web.ignoring().antMatchers("/**/favicon.ico","/**/static/**");
    }
    
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
        .headers()
        	//TODO só ate terminar o componente do calendario
	        .frameOptions().disable()
        .and()
        	.cors()
        	.and()
        		.csrf().disable()
        		.authorizeRequests()
        			.antMatchers("/**").permitAll()
        			.antMatchers("/docs/**").permitAll()
        			.antMatchers("/api/relatorios/**").permitAll()
        			.antMatchers("/api/auth").permitAll()
        			//TODO Permissao total para GET ate ser mapeado as combos publicas etc...
        			.antMatchers(HttpMethod.GET, "/api/**").permitAll()
        			.antMatchers("/api/pessoas/**", "/api/cursos/**", "/api/disciplinas/**", 
        						"/api/matriculas/**", "/api/calendarios/**", "/api/periodos-execucoes/**", "/api/ocorrencias/**").hasAnyAuthority(Authority.SECRETARIO.name())
        			.antMatchers("/api/pessoas/**", "/api/cursos/**", "/api/disciplinas/**", 
    						"/api/matriculas/**").hasAnyAuthority(Authority.AUXILIAR_DE_SECRETARIO.name())
        			.antMatchers("/api/contratos/**").hasAnyAuthority(Authority.TESOUREIRO.name(), Authority.AUXILIAR_DE_TESOUREIRO.name())
        			.antMatchers("/api/horarios/**", "/api/basescurriculares/**", "/api/ocorrencias/**").hasAnyAuthority(Authority.COORDENADOR_DE_CURSO.name())
        			.antMatchers("/api/diarios/**", "/api/frequencias/**", "/api/avaliacoes/**", "/api/avaliacoes/**", "/api/ocorrencias/**").hasAnyAuthority(Authority.PROFESSOR.name())
        			.antMatchers("/api/usuarios/**").hasAnyAuthority(Authority.ADMINISTRADOR_DE_USUARIOS.name())
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new AuthenticationTokenFilter(tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }
    
}
