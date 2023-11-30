package edu.uclm.esi.ds.webApp.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
	
	ROLE_CLIENTE(Collections.emptySet()),		// Enum que representa el rol de cliente
	ROLE_MANTENIMIENTO(Collections.emptySet()),	// Enum que representa el rol de mantenimiento
	ROLE_ADMIN(Collections.emptySet()),			// Enum que representa el rol de administrador
	CLIENTE(Collections.emptySet()),
	ADMIN(Collections.emptySet()),
	MANTENIMIENTO(Collections.emptySet());

	Role(Set<Object> emptySet) {
		// Constructor vacío para la inicialización de los roles
	}

	/*
	 * Obtiene las autoridades (roles) para el rol actual.
	 */
	public List <SimpleGrantedAuthority> getAuthorities(){
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return authorities;
	}
}
