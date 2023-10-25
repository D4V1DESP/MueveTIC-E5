package edu.uclm.esi.ds.webApp.entities;


import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "Correos")
public class Correo {
	
	public Correo(String email, String tipo) {
		super();
		this.email = email;
		this.tipo= tipo;
	}

	@Indexed(unique =true)
	protected String email; 
	
	
	protected String tipo;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	
	

}
