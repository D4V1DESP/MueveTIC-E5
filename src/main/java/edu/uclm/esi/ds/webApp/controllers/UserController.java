package edu.uclm.esi.ds.webApp.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.entities.Admin;
import edu.uclm.esi.ds.webApp.entities.Cliente;
import edu.uclm.esi.ds.webApp.entities.Mantenimiento;
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
	
	@GetMapping("/administradores")
	public List<Admin> listaAdministrador(){
		return userService.listaAdministradores();
	}
	@GetMapping("/mantenimiento")
	public List<Mantenimiento> listaMantenimiento(){
		return userService.listaMantenimiento();
	}
	@GetMapping("/cliente")
	public List<Cliente> listaCliente(){
		return userService.listaClientes();
	}
	
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
			}catch(Exception e) {
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
	
	@GetMapping("/administradores/{email}")
	public ResponseEntity<Usuario> obtenerAdminPorDNI(@PathVariable String email) {
	    // Implementa la lógica para obtener un administrador por su DNI desde la base de datos
	    // Deberías buscar el usuario con el DNI proporcionado y devolverlo como ResponseEntity<Usuario>

	    Usuario administrador = userService.obtenerAdminPorEmail(email); // Implementa esta función en tu userService
	    if (administrador != null) {
	        return new ResponseEntity<>(administrador, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Otra respuesta apropiada si no se encuentra el administrador
	    }
	}
	@GetMapping("/mantenimiento/{email}")
	public ResponseEntity<Usuario> obtenerMantenimientoPorDNI(@PathVariable String email) {
	    // Implementa la lógica para obtener un administrador por su DNI desde la base de datos
	    // Deberías buscar el usuario con el DNI proporcionado y devolverlo como ResponseEntity<Usuario>

	    Usuario mantenimiento = userService.obtenerMantenimientoPorEmail(email); // Implementa esta función en tu userService
	    if (mantenimiento != null) {
	        return new ResponseEntity<>(mantenimiento, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Otra respuesta apropiada si no se encuentra el administrador
	    }
	}
	@GetMapping("/cliente/{email}")
	public ResponseEntity<Usuario> obtenerClientePorDNI(@PathVariable String email) {
	    // Implementa la lógica para obtener un administrador por su DNI desde la base de datos
	    // Deberías buscar el usuario con el DNI proporcionado y devolverlo como ResponseEntity<Usuario>

	    Usuario cliente = userService.obtenerClientePorEmail(email); // Implementa esta función en tu userService
	    if (cliente != null) {
	        return new ResponseEntity<>(cliente, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Otra respuesta apropiada si no se encuentra el administrador
	    }
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
