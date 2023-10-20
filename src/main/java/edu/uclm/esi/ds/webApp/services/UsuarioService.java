package edu.uclm.esi.ds.webApp.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uclm.esi.ds.webApp.dao.UsuarioDAO;
import edu.uclm.esi.ds.webApp.entities.Usuario;

@Service
public class UsuarioService {
	
	@Autowired 
	private UsuarioDAO usuarioDAO;
	
	public List<Usuario> listaUsuarios() {
		return this.usuarioDAO.findAll();
	}

}
