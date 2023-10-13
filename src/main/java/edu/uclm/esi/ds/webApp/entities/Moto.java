package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "motos")
public class Moto {
	@Id
	private String matricula;
	private String tipo;
	private String direccion;
	private String modelo;
	private boolean casco;
	private int bateria;
	private String estado;
	
	public Moto(String matricula, String tipo, String direccion, String modelo, boolean casco, int bateria,
			String estado) {
		this.matricula = matricula;
		this.tipo = tipo;
		this.direccion = direccion;
		this.modelo = modelo;
		this.casco = casco;
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

	public boolean isCasco() {
		return casco;
	}

	public void setCasco(boolean casco) {
		this.casco = casco;
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
