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
        			.antMatchers("/api/diarios/**", "/api/frequencias/**", "/api/avaliacoes/**", "/api/avaliacoes/**").hasAnyAuthority(Authority.PROFESSOR.name())
        			.antMatchers("/api/**").hasAnyAuthority(Authority.ADMINISTRADOR.name())
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new AuthenticationTokenFilter(tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }
    
}
