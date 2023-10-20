package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Cliente")
public class Usuario {
	
	@Indexed(unique=true)
    private String dni;
    private String email;
    private String nombre;
    private String apellidos;
    private String contraseña;
    private String repetirContraseña;
    private String ciudad;
    private boolean activo;
    private String carnet;
    private int telefono;
    
	public Usuario(String dni, String email, String nombre, String apellidos, String contraseña,
			String repetirContraseña, String ciudad, boolean activo, String carnet, int telefono) {	
		this.dni = dni;
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.contraseña = contraseña;
		this.repetirContraseña = repetirContraseña;
		this.ciudad = ciudad;
		this.activo = activo;
		this.carnet = carnet;
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getCarnet() {
		return carnet;
	}
	public void setCarnet(String carnet) {
		this.carnet = carnet;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

    // Getters y setters
}
