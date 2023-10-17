package edu.uclm.esi.ds.webApp.entities;

public class Usuario {
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public String getRepetirContraseña() {
		return repetirContraseña;
	}
	public void setRepetirContraseña(String repetirContraseña) {
		this.repetirContraseña = repetirContraseña;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	protected String dni;
	protected String nombre;
	protected String apellidos;
	protected String email;
	protected String contraseña;
	protected String repetirContraseña;
	protected String ciudad;
	protected boolean activo; 
}
