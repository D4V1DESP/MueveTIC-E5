package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;


import edu.uclm.esi.ds.webApp.entities.Correo;

public interface CorreoDAO extends MongoRepository<Correo, String> {
	Correo findByEmail(String email);

	void deleteByemail(String string);

}
