package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
* ESTA CLASE ES LA ENTIDAD TOKENRECOVER
* ES LA INTIDAD QUE ULTILIZAMOS COMO APOYO A LA RECUPERACION DE CONTRASREÑAS,
* EN ELLA ALMACENAMOS EL MAIL DEL USUARIO QUE SOLICITO LA RECUPERACION Y GENERAMOS UN SHA512 DE ESTE COMO MANERA DE IDENTIFICARLO DE FORMA NO 
* VISIBLE. 
* CONFIGURAMOS LA COLECCION DE LA BASE DE DATOS PARA SU CORRECTO ENLACE MEDIANTE EL DAO
*/
@Document(collection="TokenUser")
public class TokenRecover {
	@Id
	private String id;
	protected String email;
	protected String token;
	
	public TokenRecover(String email) {
		this.email= email;
		this.token= org.apache.commons.codec.digest.DigestUtils.sha512Hex(email);
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setTOken(String hash) {
		this.token = hash;
	}
	
	
	

}
