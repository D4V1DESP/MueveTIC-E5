package edu.uclm.esi.ds.webApp.entities;

public class Admin extends Usuario{

	public Admin(String email, String dni, String nombre, String apellidos, String contrasena,
			String repetirContrasena, String ciudad, boolean activo) {
		super(email, dni, nombre, apellidos, contrasena, repetirContrasena, ciudad, activo);
		
	}

}
