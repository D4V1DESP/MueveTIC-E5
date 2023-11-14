package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Config")
public class Config {
	
	private int idConfig;
	private int bateriaRecarga;
	private int vehiculosMantenimiento;
	private int eurosViaje;
	private int bateriaViaje;
	
	
	
	public Config(int idConfig, int bateriaRecarga, int vehiculosMantenimiento, int eurosViaje, int bateriaViaje) {
		super();
		this.idConfig = idConfig;
		this.bateriaRecarga = bateriaRecarga;
		this.vehiculosMantenimiento = vehiculosMantenimiento;
		this.eurosViaje = eurosViaje;
		this.bateriaViaje = bateriaViaje;
	}
	public int getBateriaRecarga() {
		return bateriaRecarga;
	}
	public int getVehiculosMantenimiento() {
		return vehiculosMantenimiento;
	}
	public int getEurosViaje() {
		return eurosViaje;
	}
	public int getBateriaViaje() {
		return bateriaViaje;
	}
	
	
}
