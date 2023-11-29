package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "ReservaMantenimiento")
public class ReservaMantenimiento {
	
	@Id 
	private String id;
	protected String email;
	protected String matricula;
	
	
	public String getCliente() {
		return email;
	}
	public void setCliente(String cliente) {
		this.email = cliente;
	}

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public ReservaMantenimiento(String email, String matricula) {
		this.email = email;
		this.matricula = matricula;
		
	}
	
	
	

}
