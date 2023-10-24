package edu.uclm.esi.ds.webApp.entities;


import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Moto")
public class Moto {
	
	
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

	public String getTipo() {
		return tipo;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getModelo() {
		return modelo;
	}

	public boolean isCasco() {
		return casco;
	}

	public int getBateria() {
		return bateria;
	}

	public String getEstado() {
		return estado;
	}
}
