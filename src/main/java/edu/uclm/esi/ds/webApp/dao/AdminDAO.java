package edu.uclm.esi.ds.webApp.dao;


import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Admin;


public interface AdminDAO extends MongoRepository<Admin, String> {
	Admin findByEmail(String email);
	
}
