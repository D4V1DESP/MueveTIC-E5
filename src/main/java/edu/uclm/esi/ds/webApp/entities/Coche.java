package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Coche")
public class Coche {
	
	@Indexed(unique=true)
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

	public String getTipo() {
		return tipo;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getModelo() {
		return modelo;
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
