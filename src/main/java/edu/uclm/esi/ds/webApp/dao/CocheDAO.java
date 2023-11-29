package edu.uclm.esi.ds.webApp.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Coche;
import edu.uclm.esi.ds.webApp.entities.Vehiculo;

/**
 * ESTA CLASE ES LA ENCARGADA DE CONECTAR LA ENTIDAD COCHE CON SU TABLA CORRESPONDIENTE EN MONGODB. 
 */
public interface CocheDAO extends MongoRepository<Coche, String>{

	void deleteBymatricula(String string);
	
	List<Vehiculo> findByestado(String estado);
	
	Coche findByMatricula(String matricula);
}
