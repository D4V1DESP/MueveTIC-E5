package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "Mantenimiento")
public class Mantenimiento extends Usuario{
	
	protected int experiencia; 
	protected String ciudad;
	
	
	public Mantenimiento(String email, String dni, String nombre, String apellidos, String contrasena,
			String repetirContrasena, String ciudad, boolean activo, int experiencia,String tipo) {
		super(email, dni, nombre, apellidos, contrasena, repetirContrasena, activo,tipo);
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
