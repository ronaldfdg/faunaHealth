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
					 "/js/**",
					 "/changePassword",
					 "/updatePassword",
					 "/clients/unsuscribe/**",
					 "/appointments/confirm/**",
					 "/confirmMessage").permitAll()
		.antMatchers("/clients/", "/clients/record", "/clients/edit/**", "/clients/searchByDNI", "/clients/searchBy", "/clients/save").hasAnyAuthority("ADMINISTRACION", "GESTION")
		.antMatchers("/clients/delete/**").hasAuthority("ADMINISTRACION")
		.antMatchers("/patient/", "/patient/record/**", "/patient/savePatient", "/patient/edit/**", "/patient/searchByOwner", "/patient/searchBy").hasAnyAuthority("ADMINISTRACION", "GESTION")
		.antMatchers("/patient/delete/**").hasAnyAuthority("ADMINISTRACION")
		.antMatchers("/products/", "/products/searchBy", "/products/register", "/products/edit/**", "/products/record").hasAnyAuthority("ADMINISTRACION", "GESTION")
		.antMatchers("/products/delete/**").hasAnyAuthority("ADMINISTRACION")
		.antMatchers("/sales/", "/sales/register", "/sales/searchByClient", "/sales/searchByDates", "/sales/edit/**", "/sales/viewDetail/**", "/sales/record").hasAnyAuthority("ADMINISTRACION", "GESTION")
		.antMatchers("/sales/delete/**").hasAnyAuthority("ADMINISTRACION")
		.antMatchers("/appointments/**").hasAnyAuthority("ADMINISTRACION", "GESTION")
		.antMatchers("/users/**").hasAnyAuthority("ADMINISTRACION")
		.antMatchers("/providers/**").hasAnyAuthority("ADMINISTRACION")
		.antMatchers("/operations/**").hasAnyAuthority("ADMINISTRACION")
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/login").defaultSuccessUrl("/", true).permitAll()
		.and().logout().deleteCookies("JSESSIONID");
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
