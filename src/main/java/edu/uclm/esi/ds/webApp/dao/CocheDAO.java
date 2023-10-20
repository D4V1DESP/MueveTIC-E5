package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Coche;

public interface CocheDAO extends MongoRepository<Coche, String>{

	void deleteBymatricula(String string);

}
