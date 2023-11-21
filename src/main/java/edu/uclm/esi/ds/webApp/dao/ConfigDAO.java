package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Config;

public interface ConfigDAO  extends MongoRepository<Config, String> {
	
	Config findBynombre(String nombre);
	

}
