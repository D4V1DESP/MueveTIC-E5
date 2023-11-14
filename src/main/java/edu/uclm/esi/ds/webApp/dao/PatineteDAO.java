package edu.uclm.esi.ds.webApp.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Coche;
import edu.uclm.esi.ds.webApp.entities.Patinete;
import edu.uclm.esi.ds.webApp.entities.Vehiculo;

public interface PatineteDAO extends MongoRepository <Patinete, String>{

	void deleteBymatricula(String string);
	
	List<Vehiculo> findByestado(String estado);
	
	Patinete findByMatricula(String matricula);
}
