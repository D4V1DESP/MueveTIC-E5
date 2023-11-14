package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import edu.uclm.esi.ds.webApp.entities.ReservaCliente;


public interface ReservaClienteDAO extends MongoRepository<ReservaCliente, String> {
	ReservaCliente findByEmail(String email);
}
