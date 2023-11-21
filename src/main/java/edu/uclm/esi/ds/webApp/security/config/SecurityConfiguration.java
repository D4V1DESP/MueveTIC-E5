package edu.uclm.esi.ds.webApp.security.config;

import org.springframework.beans.factory.annotation.Autowired;
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

import edu.uclm.esi.ds.webApp.security.Role;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

	
	private static final String[] WHITE_LIST_URL = {"/users/AddUser",
            "/users/login",
            "/users/authenticate"};
		
	@Bean
	public SecurityFilterChain securityFilterChain(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider, HttpSecurity http) throws Exception{
		http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(req ->
                req.requestMatchers(WHITE_LIST_URL)
                        .permitAll()
//                        .requestMatchers("/users/UpdateUser/", "/users/administradores/", "/users/mantenimiento/", "/users/cliente/").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers("/users/administradores/{email}/", "/users/mantenimiento/{email}/", "/users/cliente/{email}/").hasRole("ROLE_ADMIN")
//                        .requestMatchers("/vehiculos/alta/", "/vehiculos/eliminar/").hasRole("ROLE_ADMIN")
//        				.requestMatchers("/vehiculos/recargables/", "/vehiculos/recargar/").hasRole("ROLE_MANTENIMIENTO")
//                        .requestMatchers("/vehiculos/coches/", "/vehiculos/motos/", "/vehiculos/patinetes/").hasAnyRole("ROLE_CLIENTE")
                        .anyRequest()
                        .authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		
		
		return http.build();
	}
}
