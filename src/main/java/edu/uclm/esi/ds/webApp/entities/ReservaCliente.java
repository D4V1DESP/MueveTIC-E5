package edu.uclm.esi.ds.webApp.entities;

public class ReservaCliente {
	
	
	protected String cliente;
	protected String vehiculo;
	protected int valoracion;
	protected String valoracionText;
	protected String estado;
	protected String fecha;
	
	
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
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
	public ReservaCliente(String cliente, String vehiculo, String fecha) {
		super();
		this.cliente = cliente;
		this.vehiculo = vehiculo;
		this.estado = "reservado";
		this.fecha= fecha;
		
	}
	
	
	

}
