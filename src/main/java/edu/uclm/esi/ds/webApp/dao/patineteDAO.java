package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Patinete;

public interface patineteDAO extends MongoRepository <Patinete, String>{
	
}
