package edu.uclm.esi.ds.webApp.entities;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * ESTA CLASE ES LA ENTIDAD ADMINISTRADOR, HEREDA DE LA CLASE USUARIO Y AÑADE LOS ATRIBUTOS UNICOS DE UN ADMINISTRADOR
 * CONFIGURAMOS LA COLECCION DE LA BASE DE DATOS PARA SU CORRECTO ENLACE MEDIANTE EL DAO
 */

import edu.uclm.esi.ds.webApp.security.Role;

@Document(collection= "Administrador")
public class Admin extends Usuario{
	
	protected String ciudad;
	
	public Admin(String email, String dni, String nombre, String apellidos, String contrasena,
			String repetirContrasena, String ciudad, boolean activo, String tipo, Role role) {
		super(email, dni, nombre, apellidos, contrasena, repetirContrasena, activo,tipo, role);
		this.ciudad= ciudad;
		
	}
	
	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	
}
