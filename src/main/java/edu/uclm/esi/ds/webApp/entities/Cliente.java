package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "Cliente")
public class Cliente extends Usuario{
	
	
	public Cliente(String email, String dni, String nombre, String apellidos, String contrasena,
			String repetirContrasena,  boolean activo, String telefono, char carnet,String tipo,String fecha) {
		super(email, dni, nombre, apellidos, contrasena, repetirContrasena,  activo, tipo);
		this.telefono = telefono;
		this.carnet = carnet;
		this.fechaNacimiento= fecha;
	}
	
	
	protected String telefono;
	protected char carnet;
	protected String fechaNacimiento;

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
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
