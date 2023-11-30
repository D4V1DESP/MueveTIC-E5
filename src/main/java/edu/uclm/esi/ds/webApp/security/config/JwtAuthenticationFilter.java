package edu.uclm.esi.ds.webApp.security.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserDetailsService userDetailsService;

	/*
	 * Realiza la validación y autenticación del token JWT en cada petición HTTP entrante.
	 */
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
			// Evita la autenticación para la ruta de autenticación de usuarios.
			throws ServletException, IOException {
	    		if (request.getServletPath().contains("/users/authenticate")) {
	    			filterChain.doFilter(request, response);
	    		return;
	    	}
	    		
			final String authHeader = request.getHeader("Authorization");
			final String jwt;
			final String userEmail;
			// Verifica si el encabezado de autorización y el formato del token son correctos.
			if(authHeader == null || !authHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}
			
			// Extrae el token JWT y el correo electrónico del usuario.
			jwt = authHeader.substring(7);
			userEmail = jwtService.extractUsername(jwt);
			// Valida y establece la autenticación si el token es válido.
			if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
				if(jwtService.isTokenValid(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							userDetails,
							null,
							userDetails.getAuthorities()
					);
					authToken.setDetails(
							new WebAuthenticationDetailsSource().buildDetails(request)
					);
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
			// Continúa con el filtro para otras peticiones.
			filterChain.doFilter(request, response);
	}

}
