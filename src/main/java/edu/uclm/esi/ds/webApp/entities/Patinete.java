package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.mapping.Document;

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
}
