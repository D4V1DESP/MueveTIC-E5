package edu.uclm.esi.ds.webApp.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Moto;

public interface MotoDAO extends MongoRepository <Moto, String>{

	void deleteBymatricula(String string);

	List<Moto> findByEstado(String string);
	Moto findByMatricula(String matricula);
}
