package com.faunahealth.web.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select username, password, Estado from usuarios where username = ?")
		.authoritiesByUsernameQuery("select u.username, r.nombre from usuarios u "
									+ "inner join rol r on r.id = u.idRol "
									+ "where username = ?");
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/bootstrap/**",
					 "/css/**",
					 "/images/**",
					 "/js/**").permitAll()
		.antMatchers("/clients/**").hasAnyAuthority("ADMINISTRACION","GESTION")
		.antMatchers("/patient/**").hasAnyAuthority("ADMINISTRACION", "GESTION")
		.antMatchers("/products/**").hasAnyAuthority("ADMINISTRACION", "GESTION")
		.antMatchers("/sales/**").hasAnyAuthority("ADMINISTRACION", "GESTION")
		.antMatchers("/users/**").hasAnyAuthority("ADMINISTRACION")
		.antMatchers("/providers/**").hasAnyAuthority("ADMINISTRACION")
		.antMatchers("/operations/**").hasAnyAuthority("ADMINISTRACION")
		.antMatchers("/appointments/**").hasAnyAuthority("ADMINISTRACION", "GESTION")
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/login").defaultSuccessUrl("/", true).permitAll()
		.and().logout().deleteCookies("JSESSIONID");
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
