package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.index.Indexed;

public class Vehiculo {
	
	@Indexed (unique = true)
	protected String matricula;
	protected String tipo;
	protected String direccion;
	protected String modelo;
	protected int bateria;
	protected String estado;
	
	
	public String getTipo() {
		return tipo;
	}
	public String getMatricula() {
		return matricula;
	}
	public String getDireccion() {
		return direccion;
	}
	public String getModelo() {
		return modelo;
	}
	public int getBateria() {
		return bateria;
	}
	public String getEstado() {
		return estado;
	}
	
	
}
