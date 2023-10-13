package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Moto;

public interface MotoDAO extends MongoRepository <Moto, String>{

}
