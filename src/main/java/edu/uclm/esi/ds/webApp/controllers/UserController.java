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
	
	@PutMapping("/administradores/{email}")
	public ResponseEntity<Admin> actualizarAdministrador(@PathVariable String email, @RequestBody Map <String, Object> nuevoAdministrador) {
	    
	    Admin administradorExistente = userService.obtenerAdminPorEmail(email);
	    
	    if (administradorExistente != null) {
	        
	        administradorExistente.setNombre(nuevoAdministrador.get("nombre").toString());
	        administradorExistente.setApellidos(nuevoAdministrador.get("apellidos").toString());
	        administradorExistente.setDni(nuevoAdministrador.get("dni").toString());
	        administradorExistente.setCiudad(nuevoAdministrador.get("ciudad").toString());
	        
	        Admin adminActualizado = userService.actualizarAdmin(administradorExistente);
	              
	        return ResponseEntity.ok(adminActualizado);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PutMapping("/mantenimiento/{email}")
	public ResponseEntity<Mantenimiento> actualizarMantenimiento(@PathVariable String email, @RequestBody Map <String, Object> nuevoMantenimiento) {
	    
	    Mantenimiento mantenimientoExistente = userService.obtenerMantenimientoPorEmail(email);
	    
	    if (mantenimientoExistente != null) {
	        
	    	mantenimientoExistente.setNombre(nuevoMantenimiento.get("nombre").toString());
	    	mantenimientoExistente.setApellidos(nuevoMantenimiento.get("apellidos").toString());
	    	mantenimientoExistente.setDni(nuevoMantenimiento.get("dni").toString());
	    	mantenimientoExistente.setCiudad(nuevoMantenimiento.get("ciudad").toString());
	    	mantenimientoExistente.setExperiencia(Integer.parseInt(nuevoMantenimiento.get("experiencia").toString()));
	        
	        Mantenimiento mantenimientoActualizado = userService.actualizarMantenimiento(mantenimientoExistente);
	        
	        return ResponseEntity.ok(mantenimientoActualizado);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	@PutMapping("/cliente/{email}")
	public ResponseEntity<Cliente> actualizarCliente(@PathVariable String email, @RequestBody Map <String, Object> nuevoCliente){
		
	    Cliente clienteExistente = userService.obtenerClientePorEmail(email);
	    
	    if (clienteExistente != null) {
	        
	    	clienteExistente.setNombre(nuevoCliente.get("nombre").toString());
	    	clienteExistente.setApellidos(nuevoCliente.get("apellidos").toString());
	    	clienteExistente.setDni(nuevoCliente.get("dni").toString());
	    	clienteExistente.setCarnet(nuevoCliente.get("carnet").toString().charAt(0));
	    	clienteExistente.setTelefono(nuevoCliente.get("telefono").toString());
	    	clienteExistente.setFecha(nuevoCliente.get("fecha").toString());
	        
	        Cliente clienteActualizado = userService.actualizarCliente(clienteExistente);
	        
	        return ResponseEntity.ok(clienteActualizado);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
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
	public ResponseEntity<Admin> obtenerAdminPorEmail(@PathVariable String email) {
	    Admin administrador = userService.obtenerAdminPorEmail(email);
	    if (administrador != null) {
	        return new ResponseEntity<>(administrador, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	@GetMapping("/mantenimiento/{email}")
	public ResponseEntity<Mantenimiento> obtenerMantenimientoPorEmail(@PathVariable String email) {

	    Mantenimiento mantenimiento = userService.obtenerMantenimientoPorEmail(email); // Implementa esta función en tu userService
	    if (mantenimiento != null) {
	        return new ResponseEntity<>(mantenimiento, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Otra respuesta apropiada si no se encuentra el administrador
	    }
	}
	
	@GetMapping("/cliente/{email}")
	public ResponseEntity<Usuario> obtenerClientePorEmail(@PathVariable String email) {
	    
	    Cliente cliente = userService.obtenerClientePorEmail(email);
	    if (cliente != null) {
	        return new ResponseEntity<>(cliente, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
	
	@PostMapping("/BajaUser")
	public boolean bajaUsuario(@RequestBody Map<String, Object> info) {
		try {
			this.userService.bajaUsuarios(info);
		}catch(DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		return true;
	}
	
}
