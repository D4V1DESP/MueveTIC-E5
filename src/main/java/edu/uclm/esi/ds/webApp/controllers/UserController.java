package edu.uclm.esi.ds.webApp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import edu.uclm.esi.ds.webApp.services.EmailService;
import edu.uclm.esi.ds.webApp.interfaces.ConstUsers;
import edu.uclm.esi.ds.webApp.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@RestController
@RequestMapping("users")
@CrossOrigin("*")
public class UserController extends ConstUsers{
	
	@Autowired 
	private UserService userService;
	@Autowired 
	private EmailService emailService;
	
	@GetMapping("/administradores")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Admin> listaAdministrador(){
		return userService.listaAdministradores();
	}
	
	@GetMapping("/mantenimiento")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Mantenimiento> listaMantenimiento(){
		return userService.listaMantenimiento();
	}
	
	@GetMapping("/cliente")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Cliente> listaCliente(){
		return userService.listaClientes();
	}
	
	@PostMapping("/AddUser")
	public String anadirUsuario(@RequestBody Map<String, Object> info) {
		String contrasena = info.get("contrasena").toString();
		String rcontrasena = info.get("repetirContrasena").toString();
		if (contrasena.length()< 8 && !contrasena.equals(rcontrasena)) {
			throw new ResponseStatusException (HttpStatus.CONFLICT);
		}
		else {
			try {
				return userService.Alta(info);
			}catch(Exception e) {
				throw new ResponseStatusException (HttpStatus.CONFLICT);
			} 
		}
	}
	
	@PutMapping("/administradores/{email}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Admin> actualizarAdministrador(@PathVariable String email, @RequestBody Map <String, Object> nuevoAdministrador) {
	    
	    Admin administradorExistente = userService.obtenerAdminPorEmail(email);
	    
	    if (administradorExistente != null) {
	        
	        administradorExistente.setNombre(nuevoAdministrador.get(NOMBRE).toString());
	        administradorExistente.setApellidos(nuevoAdministrador.get(APELLIDOS).toString());
	        administradorExistente.setDni(nuevoAdministrador.get("dni").toString());
	        administradorExistente.setCiudad(nuevoAdministrador.get("ciudad").toString());
	        
	        Admin adminActualizado = userService.actualizarAdmin(administradorExistente);
	              
	        return ResponseEntity.ok(adminActualizado);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PutMapping("/mantenimiento/{email}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Mantenimiento> actualizarMantenimiento(@PathVariable String email, @RequestBody Map <String, Object> nuevoMantenimiento) {
	    
	    Mantenimiento mantenimientoExistente = userService.obtenerMantenimientoPorEmail(email);
	    
	    if (mantenimientoExistente != null) {
	        
	    	mantenimientoExistente.setNombre(nuevoMantenimiento.get(NOMBRE).toString());
	    	mantenimientoExistente.setApellidos(nuevoMantenimiento.get(APELLIDOS).toString());
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
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Cliente> actualizarCliente(@PathVariable String email, @RequestBody Map <String, Object> nuevoCliente){
		
	    Cliente clienteExistente = userService.obtenerClientePorEmail(email);
	    
	    if (clienteExistente != null) {
	        
	    	clienteExistente.setNombre(nuevoCliente.get(NOMBRE).toString());
	    	clienteExistente.setApellidos(nuevoCliente.get(APELLIDOS).toString());
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

		try {
			return this.userService.login(info);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
			
		}
	}
	
	@PostMapping("/authenticate")
	public String authenticate(@RequestBody Map<String, Object> info) {
		return this.userService.authenticate(info);
	}
	
	@PostMapping("/verify")
	public String verifyCode(@RequestBody Map<String, Object> info) throws Exception {
		return userService.verifyCode(info);
	}
	
	@GetMapping("/administradores/{email}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Admin> obtenerAdminPorEmail(@PathVariable String email) {
	    Admin administrador = userService.obtenerAdminPorEmail(email);
	    if (administrador != null) {
	        return new ResponseEntity<>(administrador, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	@GetMapping("/mantenimiento/{email}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Mantenimiento> obtenerMantenimientoPorEmail(@PathVariable String email) {

	    Mantenimiento mantenimiento = userService.obtenerMantenimientoPorEmail(email); // Implementa esta función en tu userService
	    if (mantenimiento != null) {
	        return new ResponseEntity<>(mantenimiento, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Otra respuesta apropiada si no se encuentra el administrador
	    }
	}
	
	@GetMapping("/cliente/{email}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Usuario> obtenerClientePorEmail(@PathVariable String email) {
	    
	    Cliente cliente = userService.obtenerClientePorEmail(email);
	    if (cliente != null) {
	        return new ResponseEntity<>(cliente, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}


	@PostMapping("/UpdateUser")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public boolean updateUser(@RequestBody Map<String,Object> info) {
		
		try {
			this.userService.updateUsers(info);
		}catch(DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		return true;
	}
	
	@DeleteMapping("/BajaUser/{email}")
	public boolean bajaUsuario(@PathVariable String email) {
	    Map<String, Object> info = new HashMap<>();
	    info.put("email", email);
	    
	    try {
	        this.userService.bajaUsuarios(info);
	    } catch (DataIntegrityViolationException e) {
	        throw new ResponseStatusException(HttpStatus.CONFLICT);
	    }
	    
	    return true;
	}


	@PostMapping("/recover")
	public boolean recuperarPassword(@RequestBody Map<String,Object> info) {
		try {
			
			if (this.userService.checkUser(info))
				this.emailService.sendRecover(info);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		return true;
	}
	
	@PostMapping("/updatePass")
	public boolean updatePass(@RequestBody Map<String,Object> info) {
		try {
				if(info.get("contrasena").equals(info.get("repetirContrasena"))) {
					this.userService.updatePassword(info);
				}else {
					throw new ResponseStatusException(HttpStatus.CONFLICT);
				}
			
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		return true;
	}
}
