package edu.uclm.esi.ds.webApp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import edu.uclm.esi.ds.webApp.entities.Usuario;

public interface UsuarioDAO extends MongoRepository<Usuario, String> {
    // Puedes agregar métodos personalizados aquí si necesitas realizar consultas específicas sobre usuarios
}
