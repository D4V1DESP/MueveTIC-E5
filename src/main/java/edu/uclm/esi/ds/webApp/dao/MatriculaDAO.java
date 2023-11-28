package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Matricula;
/**
 * ESTA CLASE ES LA ENCARGADA DE CONECTAR LA ENTIDAD MATRICULA CON SU TABLA CORRESPONDIENTE EN MONGODB. 
 */
public interface MatriculaDAO extends MongoRepository<Matricula, String> {

	void deleteBymatricula(String matricula);

	Matricula findByMatricula(String matricula);
}
