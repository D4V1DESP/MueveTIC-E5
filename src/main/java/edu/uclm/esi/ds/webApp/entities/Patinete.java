package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "patinetes")
public class Patinete {
	@Id
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
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getTipo() {
		return tipo;
	}
	public int getBateria() {
		return bateria;
	}
	public String getEstado() {
		return estado;
	}
}
