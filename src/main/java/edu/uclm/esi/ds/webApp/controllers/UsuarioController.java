package edu.uclm.esi.ds.webApp.controllers;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.entities.Usuario;
import edu.uclm.esi.ds.webApp.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
@RestController
@RequestMapping("usuarios")
@CrossOrigin("*")

public class UsuarioController {
	@Autowired
    private UsuarioService usuarioService;
	
	
	@GetMapping("/usuarios")
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioService.listaUsuarios();
    }
}
