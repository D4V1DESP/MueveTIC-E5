package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "ReservaMantenimiento")
public class ReservaMantenimiento {
	
	@Id 
	private String id;
	protected String email;
	protected String vehiculo;
	
	
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
	
	public ReservaMantenimiento(String email, String vehiculo) {
		this.email = email;
		this.vehiculo = vehiculo;
		
	}
	
	
	

}
