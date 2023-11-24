package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Vehiculo {
	@Id
    private String id;
	@Indexed (unique = true)
	protected String matricula;
	protected String tipo;
	protected String direccion;
	protected String modelo;
	protected int bateria;
	protected String estado;
	
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
	
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

	public void setBateria(int bateria) {
		this.bateria = bateria;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}