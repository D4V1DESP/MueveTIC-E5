package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * ESTA CLASE ES LA ENTIDAD CLIENTE, HEREDA DE LA CLASE USUARIO Y AÑADE LOS ATRIBUTOS UNICOS DE UN CLIENTE
 * CONFIGURAMOS LA COLECCION DE LA BASE DE DATOS PARA SU CORRECTO ENLACE MEDIANTE EL DAO
 */
@Document(collection= "Cliente")
public class Cliente extends Usuario{
	
	protected String telefono;
	protected char carnet;
	protected String fecha;

	public Cliente(String email, String dni, String nombre, String apellidos, String contrasena,
			String repetirContrasena,  boolean activo, String telefono, char carnet,String tipo,String fecha) {
		super(email, dni, nombre, apellidos, contrasena, repetirContrasena,  activo, tipo);
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
