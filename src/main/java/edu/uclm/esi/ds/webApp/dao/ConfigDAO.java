package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Config;
/**
 * ESTA CLASE ES LA ENCARGADA DE CONECTAR LA ENTIDAD CONFIG CON SU TABLA CORRESPONDIENTE EN MONGODB. 
 */
public interface ConfigDAO  extends MongoRepository<Config, String> {
	
	Config findBynombre(String nombre);
	

}
