package edu.uclm.esi.ds.webApp.entities;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import edu.uclm.esi.ds.webApp.security.Role;

@Document(collection= "Mantenimiento")
public class Mantenimiento extends Usuario{
	
	protected int experiencia; 
	protected String ciudad;
	
	
	public Mantenimiento(String email, String dni, String nombre, String apellidos, String contrasena,
			String repetirContrasena, String ciudad, boolean activo, int experiencia,String tipo, Role role) {
		super(email, dni, nombre, apellidos, contrasena, repetirContrasena, activo,tipo, role);
		this.experiencia = experiencia;
		this.ciudad= ciudad;
	}

	public int getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}
	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	

	

}
