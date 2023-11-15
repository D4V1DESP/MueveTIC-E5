package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Coche")
public class Coche extends Vehiculo{
	
	 private int nPlazas;

	
	public Coche(String matricula, String tipo, String direccion, String modelo, int nPlazas, int bateria,
			String estado) {
		this.matricula = matricula;
		this.tipo = tipo;
		this.direccion = direccion;
		this.modelo = modelo;
		this.nPlazas = nPlazas;
		this.bateria = bateria;
		this.estado = estado;
	}
	
	public int getnPlazas() {
		return nPlazas;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
