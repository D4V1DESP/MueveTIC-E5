package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Patinete")
public class Patinete {

	
	private String matricula;
	private String tipo;
	private String direccion;
	private String modelo;
	private String color;
	private int bateria;
	private String estado;
	
	
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


	public String getMatricula() {
		return matricula;
	}


	public String getTipo() {
		return tipo;
	}


	public String getDireccion() {
		return direccion;
	}


	public String getModelo() {
		return modelo;
	}


	public String getColor() {
		return color;
	}


	public int getBateria() {
		return bateria;
	}


	public String getEstado() {
		return estado;
	}
}
