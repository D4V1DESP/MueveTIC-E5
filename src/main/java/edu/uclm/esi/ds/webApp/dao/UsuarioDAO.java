package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uclm.esi.ds.webApp.entities.Usuario;
/**
 * ESTA CLASE ES LA ENCARGADA DE CONECTAR LA ENTIDAD USUARIO CON SU TABLA CORRESPONDIENTE EN MONGODB. 
 */
public interface UsuarioDAO extends MongoRepository<Usuario, String> {

}
