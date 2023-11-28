package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;


import edu.uclm.esi.ds.webApp.entities.Cliente;

/**
 * ESTA CLASE ES LA ENCARGADA DE CONECTAR LA ENTIDAD CLIENTE CON SU TABLA CORRESPONDIENTE EN MONGODB. 
 */
public interface ClienteDAO extends MongoRepository<Cliente, String>{
	Cliente findByEmail(String email);

	void deleteByemail(String string);
}
