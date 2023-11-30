package edu.uclm.esi.ds.webApp.security.config;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${application.security.secret-key}")
	private String SECRET_KEY;
	
	/*
	 * Extrae el nombre de usuario del token JWT.
	 */
	public String extractUsername(String token) {
		
		return extractClaim(token, Claims::getSubject);
	}
	
	/*
	 * Extrae cualquier reclamo del token JWT utilizando una función de resolver reclamos.
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	/*
	 * Genera un token JWT con los detalles del usuario y roles proporcionados.
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
	    claims.put("role", userDetails.getAuthorities());
		return generateToken(new HashMap<>(), userDetails);
	}
	
	/*
	 * Genera un token JWT con reclamos adicionales, detalles del usuario y sus roles.
	 */
	public String generateToken(Map <String, Object> extraClaims,
			UserDetails userDetails) {
		
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> roles = new ArrayList<>();
        for(GrantedAuthority authority: authorities){
            if(authority.getAuthority().startsWith("ROLE_")){
                roles.add(authority.getAuthority());
            }
        }
        
        
        // Construcción del token JWT con los reclamos, nombre de usuario, roles, fechas de emisión y expiración.
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.claim("role", roles)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	/*
	 * Verifica si un token JWT es válido para un UserDetails específico.
	 */
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	/*
	 * Comprueba si un token JWT ha expirado.
	 */
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/*
	 * Extrae la fecha de expiración de un token JWT.
	 */
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/*
	 * Extrae todos los reclamos de un token JWT.
	 */
	Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	/*
	 * Obtiene la clave de firma a partir de la clave secreta.
	 */
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
}
