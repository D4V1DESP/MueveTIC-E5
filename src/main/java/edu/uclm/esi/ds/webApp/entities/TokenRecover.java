package edu.uclm.esi.ds.webApp.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="TokenUserx")
public class TokenRecover {
	
	protected String email;
	protected String hash;
	
	public TokenRecover(String email) {
		this.email= email;
		this.hash= org.apache.commons.codec.digest.DigestUtils.sha512Hex(email);
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	
	

}
