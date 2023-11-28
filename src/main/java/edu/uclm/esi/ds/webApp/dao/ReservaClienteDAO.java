package edu.uclm.esi.ds.webApp.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import edu.uclm.esi.ds.webApp.entities.ReservaCliente;

/**
 * ESTA CLASE ES LA ENCARGADA DE CONECTAR LA ENTIDAD RESERVACLIENTE CON SU TABLA CORRESPONDIENTE EN MONGODB. 
 */
public interface ReservaClienteDAO extends MongoRepository<ReservaCliente, String> {
	List<ReservaCliente> findListByEmail(String email);
	ReservaCliente findByEmail(String email);
}
