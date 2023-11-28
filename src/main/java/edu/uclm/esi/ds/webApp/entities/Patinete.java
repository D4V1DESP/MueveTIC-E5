package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * ESTA CLASE ES LA ENTIDAD PATINETE,
 * CONFIGURAMOS LA COLECCION DE LA BASE DE DATOS PARA SU CORRECTO ENLACE MEDIANTE EL DAO
 */
@Document(collection = "Patinete")
public class Patinete extends Vehiculo{

	private String color;
	
	
	public Patinete(String matricula, String tipo, String direccion, String modelo, String color, int bateria,
			String estado) {
		this.matricula = matricula;
		this.tipo = tipo;
		this.direccion = direccion;
		this.modelo = modelo;
		this.color = color;
		this.bateria = bateria;
		this.estado = estado;
	}

	public String getColor() {
		return color;
	}

	public void setEstado(String estado) {
		this.estado = estado;
		// TODO Auto-generated method stub
		
	}
}
