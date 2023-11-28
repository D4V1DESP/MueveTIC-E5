package edu.uclm.esi.ds.webApp.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * ESTA CLASE ES LA ENTIDAD CONFIG,
 * LOS OBJETOS DE ESTA CLASE SON LAS VARIABLES CONFIGURABLES POR UNA ADMINISTRADOR QUE USAREMOS Y NECESITAREMOS EN NUESTRO PROGRAMA.
 * CONFIGURAMOS LA COLECCION DE LA BASE DE DATOS PARA SU CORRECTO ENLACE MEDIANTE EL DAO
 */
@Document(collection= "Config")
public class Config {
	@Id 
	private String id;
	@Indexed(unique=true)
	protected String nombre;
	protected int valor;

	public Config(String nombre, int valor) {
		super();
		this.nombre = nombre;
		this.valor = valor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

}