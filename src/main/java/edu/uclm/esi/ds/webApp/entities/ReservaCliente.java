package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "Reservas")
public class ReservaCliente {
	
	@Id 
	private String id;
	protected String email;
	protected String vehiculo;
	protected int valoracion;
	protected String valoracionText;
	protected String estado;
	protected String fecha;
	
	
	public String getCliente() {
		return email;
	}
	public void setCliente(String cliente) {
		this.email = cliente;
	}
	public String getVehiculo() {
		return vehiculo;
	}
	public void setVehiculo(String vehiculo) {
		this.vehiculo = vehiculo;
	}
	public int getValoracion() {
		return valoracion;
	}
	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}
	public String getValoracionText() {
		return valoracionText;
	}
	public void setValoracionText(String valoracionText) {
		this.valoracionText = valoracionText;
	}
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public ReservaCliente(String email, String vehiculo, String fecha) {
		this.email = email;
		this.vehiculo = vehiculo;
		this.estado = "reservado";
		this.fecha= fecha;
		this.valoracion= -1;
		this.valoracionText ="";
		
	}
	
	
	

}
