package edu.uclm.esi.ds.webApp.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.uclm.esi.ds.webApp.dao.UsuarioDAO;

@Configuration
public class ApplicationConfig {
	
	/*
	 * Método que proporciona una implementación de UserDetailsService
	 * basada en el UsuarioDAO para buscar usuarios por su correo electrónico.
	 */
	
	@Bean
	public UserDetailsService userDetailsService(UsuarioDAO usuarioDAO){
		return username -> usuarioDAO.findByEmail(username);
	}
	
	/*
	 * Método que crea y configura un AuthenticationProvider basado en DaoAuthenticationProvider.
	 * Establece el UserDetailsService con el UsuarioDAO proporcionado y un PasswordEncoder.
	 */
	@Bean
	public AuthenticationProvider authenticationProvider(UsuarioDAO usuarioDAO) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService(usuarioDAO));
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	/*
	 * Método que proporciona un AuthenticationManager obtenido de la configuración de autenticación de Spring Security.
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	/*
	 * Método que crea y devuelve un PasswordEncoder BCryptPasswordEncoder.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
