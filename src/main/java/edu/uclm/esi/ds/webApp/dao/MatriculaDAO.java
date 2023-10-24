package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Matricula;

public interface MatriculaDAO extends MongoRepository<Matricula, String> {

	void deleteBymatricula(String matricula);
}
