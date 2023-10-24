package edu.uclm.esi.ds.webApp.controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.entities.Admin;
import edu.uclm.esi.ds.webApp.entities.Usuario;
import edu.uclm.esi.ds.webApp.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@RestController
@RequestMapping("users")
@CrossOrigin("*")
public class UserController {
	
	@Autowired 
	private UserService userService;
	
	
	@GetMapping("/obtenerUsuarios")
	public List<Usuario> obtenerUsuarios() {
		
	    return  userService.obtenerUsuarios();
	}
	@GetMapping("/administradores")
	public List<Admin> listaAdministrador(){
		return userService.listaAdministradores();
	}
	
	
	@PostMapping("/AddUser")
	public boolean AnadirUsuario(@RequestBody Map<String, Object> info) {
		String contrasena = info.get("contrasena").toString();
		String rcontrasena = info.get("repetirContrasena").toString();
		if (contrasena.length()< 8 && !contrasena.equals(rcontrasena)) {
			throw new ResponseStatusException (HttpStatus.CONFLICT);
		}
		else {
			try {
				userService.Alta(info);
			}catch(DataIntegrityViolationException e) {
				throw new ResponseStatusException (HttpStatus.CONFLICT);
			}
		}
		return true;
	}
	
	@PostMapping("/login")
	public boolean login(@RequestBody Map<String, Object> info) {
		
		try {
			this.userService.login(info);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
			
		}
		return true;
	}
	
	@PostMapping("/UpdateUser")
	public boolean UpdateUser(@RequestBody Map<String,Object> info) {
		
		try {
			this.userService.updateUsers(info);
		}catch(DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		return true;
	}
	
}
