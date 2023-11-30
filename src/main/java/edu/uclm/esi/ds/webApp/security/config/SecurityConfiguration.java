package edu.uclm.esi.ds.webApp.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import edu.uclm.esi.ds.webApp.security.Role;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

	// URLs permitidas sin autenticación
	private static final String[] WHITE_LIST_URL = {"/users/AddUser",
            "/users/login",
            "/users/authenticate",
            "/users/recover",
            "/users/updatePass",
            "/users/verify"};
		
	/*
     * Configuración de la cadena de filtros de seguridad.
     */
	@Bean
	public SecurityFilterChain securityFilterChain(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider, HttpSecurity http) throws Exception{
		http
		.cors() // Configuración de CORS
		.and()
        .csrf(AbstractHttpConfigurer::disable)	// Deshabilita CSRF
        .authorizeHttpRequests(req ->
                req.requestMatchers(WHITE_LIST_URL)
                        .permitAll()	// Permite el acceso a las URLs de la lista blanca sin autenticación
                        .anyRequest()
                        .authenticated()	// Permite el acceso a las URLs de la lista blanca sin autenticación 
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))	// Política de creación de sesiones sin estado
        .authenticationProvider(authenticationProvider)	// Proveedor de autenticación
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);	// Agrega el filtro de autenticación JWT antes del filtro de usuario y contraseña
		
		
		return http.build();	// Retorna la cadena de filtros de seguridad configurada
	}
}
