package edu.uclm.esi.ds.webApp.controllers;


import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

<<<<<<< Updated upstream
=======
import edu.uclm.esi.ds.webApp.entities.Usuario;
>>>>>>> Stashed changes
import edu.uclm.esi.ds.webApp.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@RestController
@RequestMapping("users")
@CrossOrigin("*")
public class UserController {
	
	@Autowired 
	private UserService userService;
	
	@PostMapping("/AddUser")
	public boolean anadirUsuario(@RequestBody Map<String, Object> info) {
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
	public Usuario login(@RequestBody Map<String, Object> info) {
		Usuario u;
		try {
			u =this.userService.login(info);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
			
		}
		return u;
	}
	
	@PostMapping("/UpdateUser")
	public boolean updateUser(@RequestBody Map<String,Object> info) {
		
		try {
			this.userService.updateUsers(info);
		}catch(DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		return true;
	}
	
}
