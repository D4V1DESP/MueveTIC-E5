package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "Mantenimiento")
public class Mantenimiento extends Usuario{
	
	public Mantenimiento(String email, String dni, String nombre, String apellidos, String contrasena,
			String repetirContrasena, String ciudad, boolean activo, int experiencia) {
		super(email, dni, nombre, apellidos, contrasena, repetirContrasena, ciudad, activo);
		this.experiencia = experiencia;
		// TODO Auto-generated constructor stub
	}

	public int getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}

	protected int experiencia; 
	

}
