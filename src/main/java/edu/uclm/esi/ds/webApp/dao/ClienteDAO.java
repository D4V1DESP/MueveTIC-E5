package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;


import edu.uclm.esi.ds.webApp.entities.Cliente;

public interface ClienteDAO extends MongoRepository<Cliente, String>{

}
