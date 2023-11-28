package edu.uclm.esi.ds.webApp.entities;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import edu.uclm.esi.ds.webApp.security.Role;
import edu.uclm.esi.ds.webApp.security.Role;

@Document(collection= "Cliente")
public class Cliente extends Usuario{
	
	protected String telefono;
	protected char carnet;
	protected String fecha;

	public Cliente(String email, String dni, String nombre, String apellidos, String contrasena,
			String repetirContrasena,  boolean activo, String telefono, char carnet,String tipo,String fecha, Role role) {
		super(email, dni, nombre, apellidos, contrasena, repetirContrasena,  activo, tipo, role);
		this.telefono = telefono;
		this.carnet = carnet;
		this.fecha= fecha;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fechaNacimiento) {
		this.fecha = fechaNacimiento;
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
