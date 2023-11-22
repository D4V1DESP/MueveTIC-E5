package edu.uclm.esi.ds.webApp.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Coche;
import edu.uclm.esi.ds.webApp.entities.Moto;
import edu.uclm.esi.ds.webApp.entities.Vehiculo;

public interface MotoDAO extends MongoRepository <Moto, String>{

	void deleteBymatricula(String string);
	
	List<Vehiculo> findByestado(String estado);

	List<Moto> findByEstado(String string);
	Moto findByMatricula(String matricula);
}
