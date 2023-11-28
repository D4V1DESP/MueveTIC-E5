package edu.uclm.esi.ds.webApp.dao;

/**
 * ESTA CLASE ES LA ENCARGADA DE CONECTAR LA ENTIDAD ADMIN CON SU TABLA CORRESPONDIENTE EN MONGODB. 
 */
import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Admin;


public interface AdminDAO extends MongoRepository<Admin, String> {
	
	/*
	 * METODO QUE DEVUELVE UN OBJETO ADMIN PASANDOLE SU EMAIL
	 */
	Admin findByEmail(String email);
	/*
	 * METODO DE BORRADO DE ADMINS A PARTIR DE UN STRING QUE ES SU EMAIL
	 */
	void deleteByemail(String string);
	
}
