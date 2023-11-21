package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="TokenUserx")
public class TokenRecover {
	
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
