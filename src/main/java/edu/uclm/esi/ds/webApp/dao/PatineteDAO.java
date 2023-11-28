package edu.uclm.esi.ds.webApp.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Patinete;
/**
 * ESTA CLASE ES LA ENCARGADA DE CONECTAR LA ENTIDAD PATINETE CON SU TABLA CORRESPONDIENTE EN MONGODB. 
 */
public interface PatineteDAO extends MongoRepository <Patinete, String>{

	void deleteBymatricula(String string);

	List<Patinete> findByEstado(String string);
	Patinete findByMatricula(String matricula);
}
