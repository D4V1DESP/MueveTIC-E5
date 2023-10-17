package edu.uclm.esi.ds.webApp.entities;

public class Cliente extends Usuario{
	
	
	public Cliente(String email, String dni, String nombre, String apellidos, String contrasena,
			String repetirContrasena, String ciudad, boolean activo, String telefono, char carnet) {
		super(email, dni, nombre, apellidos, contrasena, repetirContrasena, ciudad, activo);
		this.telefono = telefono;
		this.carnet = carnet;
	}
	protected String telefono;
	protected char carnet;

	
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public char getCarnet() {
		return carnet;
	}
	public void setCarnet(char carnet) {
		this.carnet = carnet;
	}


}
