package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.TokenRecover;
/**
 * ESTA CLASE ES LA ENCARGADA DE CONECTAR LA ENTIDAD TOKENRECOVER CON SU TABLA CORRESPONDIENTE EN MONGODB. 
 */
public interface TokenRecoverDAO extends MongoRepository<TokenRecover, String> {
	TokenRecover findBytoken(String token);

}
