/*package edu.uclm.esi.ds.webApp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthSecurityConfiguration {
	
	@Bean
	SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
		http
		.csrf().disable().
		authorizeHttpRequests().
		requestMatchers("/**")
		.permitAll();
		DefaultSecurityFilterChain filterChain = http.build();
		return filterChain;
	}
}*/
