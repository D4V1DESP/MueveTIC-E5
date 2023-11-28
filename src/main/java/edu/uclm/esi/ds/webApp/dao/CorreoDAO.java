package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;


import edu.uclm.esi.ds.webApp.entities.Correo;
/**
 * ESTA CLASE ES LA ENCARGADA DE CONECTAR LA ENTIDAD CORREO CON SU TABLA CORRESPONDIENTE EN MONGODB. 
 */
public interface CorreoDAO extends MongoRepository<Correo, String> {
	Correo findByEmail(String email);

	void deleteByemail(String string);

}
