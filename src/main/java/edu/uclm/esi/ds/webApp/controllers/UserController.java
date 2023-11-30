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
	
	
	/*
	 * OBTIENE UNA LISTA DE ADMINISTRADORES.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR.
	 */
	@GetMapping("/administradores")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	
	public List<Admin> listaAdministrador(){
		return userService.listaAdministradores();
	}
	
	/*
	 * OBTIENE UNA LISTA DE PERSONAL DE MANTENIMIENTO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR.
	 */
	@GetMapping("/mantenimiento")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	
	public List<Mantenimiento> listaMantenimiento(){
		return userService.listaMantenimiento();
	}
	
	/*
	 * OBTIENE UNA LISTA DE CLIENTES.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR.
	 */
	@GetMapping("/cliente")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	
	public List<Cliente> listaCliente(){
		return userService.listaClientes();
	}
	
	/*
	 * AÑADE UN NUEVO USUARIO AL SISTEMA.
	 * EL MAPA DEBE CONTENER LA INFORMACIÓN NECESARIA PARA CREAR EL USUARIO.
	 */
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
	
	/*
	 * ACTUALIZA LA INFORMACIÓN DE UN ADMINISTRADOR.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR.
	 */
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
	
	/*
	 * ACTUALIZA LA INFORMACIÓN DE UN TRABAJADOR DE MANTENIMIENTO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR.
	 */
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

	/*
	 * ACTUALIZA LA INFORMACIÓN DE UN CLIENTE.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR.
	 */
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

	/*
	 * INICIA SESIÓN EN EL SISTEMA.
	 * EL MAPA DEBE CONTENER LA INFORMACIÓN DE INICIO DE SESIÓN.
	 */
	@PostMapping("/login")
	public Usuario login(@RequestBody Map<String, Object> info) {

		try {
			return this.userService.login(info);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
			
		}
	}
	
	/*
	 * AUTENTICA AL USUARIO.
	 * EL MAPA DEBE CONTENER LA INFORMACIÓN DE AUTENTICACIÓN.
	 */
	@PostMapping("/authenticate")
	public String authenticate(@RequestBody Map<String, Object> info) {
		return this.userService.authenticate(info);
	}
	
	/*
	 * VERIFICA UN CÓDIGO.
	 * EL MAPA DEBE CONTENER EL CÓDIGO A VERIFICAR.
	 */
	@PostMapping("/verify")
	public String verifyCode(@RequestBody Map<String, Object> info) throws Exception {
		return userService.verifyCode(info);
	}
	
	/*
	 * OBTIENE UN ADMINISTRADOR POR SU CORREO ELECTRÓNICO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR.
	 */
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
	/*
	 * OBTIENE UN TRABAJADOR DE MANTENIMIENTO POR SU CORREO ELECTRÓNICO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR.
	 */
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
	/*
	 * OBTIENE UN CLIENTE POR SU CORREO ELECTRÓNICO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR.
	 */
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

	/*
	 * ACTUALIZA LA INFORMACIÓN DE UN USUARIO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR.
	 */
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
	
	/*
	 * DARSE DE BAJA DE LA APLICACIÓN.
	 * EL MAPA DEBE CONTENER EL CORREO ELECTRÓNICO DEL USUARIO.
	 */
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

	/*
	 * RECUPERA LA CONTRASEÑA DEL USUARIO.
	 * EL MAPA DEBE CONTENER LA INFORMACIÓN NECESARIA PARA RECUPERAR LA CONTRASEÑA.
	 */
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
	
	/*
	 * ACTUALIZA LA CONTRASEÑA DEL USUARIO.
	 * EL MAPA DEBE CONTENER LA INFORMACIÓN NECESARIA PARA ACTUALIZAR LA CONTRASEÑA.
	 */
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
