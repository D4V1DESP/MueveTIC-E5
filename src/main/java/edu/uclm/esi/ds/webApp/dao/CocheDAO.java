package edu.uclm.esi.ds.webApp.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Coche;

/**
 * ESTA CLASE ES LA ENCARGADA DE CONECTAR LA ENTIDAD COCHE CON SU TABLA CORRESPONDIENTE EN MONGODB. 
 */
public interface CocheDAO extends MongoRepository<Coche, String>{

	void deleteBymatricula(String string);
	List<Coche> findByEstado(String estado);
	Coche findByMatricula(String matricula);
}
