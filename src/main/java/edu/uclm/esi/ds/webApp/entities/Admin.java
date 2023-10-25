package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "Administrador")
public class Admin extends Usuario{
	protected String ciudad;
	
	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public Admin(String email, String dni, String nombre, String apellidos, String contrasena,
			String repetirContrasena, String ciudad, boolean activo, String tipo) {
		super(email, dni, nombre, apellidos, contrasena, repetirContrasena, activo,tipo);
		this.ciudad= ciudad;
		
	}

}
