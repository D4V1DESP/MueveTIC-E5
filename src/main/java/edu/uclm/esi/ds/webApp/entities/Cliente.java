package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "Cliente")
public class Cliente extends Usuario{
	
	
	public Cliente(String email, String dni, String nombre, String apellidos, String contrasena,
			String repetirContrasena,  boolean activo, String telefono, String carnet,String tipo,String fecha) {
		super(email, dni, nombre, apellidos, contrasena, repetirContrasena,  activo, tipo);
		this.telefono = telefono;
		this.carnet = carnet;
		this.fechaNacimiento= fecha;
	}
	
	
	protected String telefono;
	protected String carnet;

	
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCarnet() {
		return carnet;
	}
	public void setCarnet(String carnet) {
		this.carnet = carnet;
	}


}
