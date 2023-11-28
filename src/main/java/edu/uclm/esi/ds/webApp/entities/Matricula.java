package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * ESTA CLASE ES LA ENTIDAD MATRICULA,
 * ES UNA TABLA AUXILIAR QUE NOS AYUDA A CONTROLAR QUE NO HAYA UNA MISMA MATRICULA REGISTRADA DOS VECES EN VEHICULOS DISTINTOS 
 * CONFIGURAMOS LA COLECCION DE LA BASE DE DATOS PARA SU CORRECTO ENLACE MEDIANTE EL DAO
 */
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
