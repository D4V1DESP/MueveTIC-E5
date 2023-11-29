package edu.uclm.esi.ds.webApp.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import edu.uclm.esi.ds.webApp.entities.ReservaMantenimiento;


public interface ReservaMantenimientoDAO extends MongoRepository<ReservaMantenimiento, String> {
	List<ReservaMantenimiento> findListByEmail(String email);
	ReservaMantenimiento findByEmail(String email);
	
	void deleteByEmail(String email);
	
	ReservaMantenimiento findByVehiculo(String vehiculo);
}