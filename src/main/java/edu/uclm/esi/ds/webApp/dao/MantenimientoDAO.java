package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;


import edu.uclm.esi.ds.webApp.entities.Mantenimiento;

public interface MantenimientoDAO extends  MongoRepository<Mantenimiento, String> {
	Mantenimiento findByEmail(String email);

	void deleteByemail(String string);

}
