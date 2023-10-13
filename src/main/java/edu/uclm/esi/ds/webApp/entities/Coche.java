package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "coches")
public class Coche {
	@Id
	private String matricula;
	private String tipo;
	private String direccion;
	private String modelo;
	private int nPlazas;
	private int bateria;
	private String estado;
	
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

	public String getTipo() {
		return tipo;
	}

	public int getnPlazas() {
		return nPlazas;
	}

	public int getBateria() {
		return bateria;
	}

	public String getEstado() {
		return estado;
	}
	
	
	
	
}
