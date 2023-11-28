package edu.uclm.esi.ds.webApp.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.uclm.esi.ds.webApp.security.Role;




@Document(collection = "Usuario")
public class Usuario implements UserDetails{
	@Id
    private String id;
	
	@Indexed(unique= true)
	protected String email;
	protected String dni;
	protected String nombre;
	protected String apellidos;
	protected String contrasena;
	protected String repetirContrasena;
	protected boolean activo; 
	protected String tipo;
	
	private Role role;
	
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getRepetirContrasena() {
		return repetirContrasena;
	}
	public void setRepetirContrasena(String repetirContrasena) {
		this.repetirContrasena = repetirContrasena;
	}
	
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}


	public Usuario(String email, String dni, String nombre, String apellidos, String contrasena,
			String repetirContrasena, boolean activo,String tipo, Role role) {
		super();
		this.email = email;
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.contrasena = contrasena;
		this.repetirContrasena = repetirContrasena;
		this.activo = activo;
		this.tipo = tipo;
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}
	
	@Override
	public String getPassword() {
		return contrasena;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
