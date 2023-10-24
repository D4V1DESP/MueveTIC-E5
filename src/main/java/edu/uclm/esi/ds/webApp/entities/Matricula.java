package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Matriculas")
public class Matricula {
	
	@Indexed(unique = true)
	private String matricula;
	private String tipo;
	
	
	public Matricula(String matricula, String tipo) {
		this.matricula = matricula;
		this.tipo = tipo;
	}

	public String getMatricula() {
		return matricula;
	}


	public String getTipo() {
		return tipo;
	}
	
}
