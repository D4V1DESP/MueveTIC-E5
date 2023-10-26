package edu.uclm.esi.ds.webApp.services;


import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.dao.AdminDAO;
import edu.uclm.esi.ds.webApp.dao.ClienteDAO;
import edu.uclm.esi.ds.webApp.dao.CorreoDAO;
import edu.uclm.esi.ds.webApp.dao.MantenimientoDAO;

import edu.uclm.esi.ds.webApp.entities.Admin;
import edu.uclm.esi.ds.webApp.entities.Mantenimiento;
import edu.uclm.esi.ds.webApp.entities.Usuario;

import edu.uclm.esi.ds.webApp.entities.Cliente;
import edu.uclm.esi.ds.webApp.entities.Correo;


@Service 
public class UserService {
	
	@Autowired 
	private ClienteDAO clientedao;
	@Autowired
	private AdminDAO admindao;
	@Autowired
	private MantenimientoDAO mandao;
	@Autowired
	private CorreoDAO correodao;
	
	
	
	public void Alta(Map <String ,Object> info) {
		String email = info.get("email").toString();
		String nombre = info.get("nombre").toString();
		String apellidos = info.get("apellidos").toString();
		String dni = info.get("dni").toString();
		String contrasena = info.get("contrasena").toString();
		String repetircontrasena = info.get("repetirContrasena").toString();
		boolean activo = Boolean.parseBoolean(info.get("activo").toString());
		String ciudad = info.get("ciudad").toString();
		try {
			Correo c = new Correo(email,info.get("tipo").toString());
			correodao.save(c);
		}catch(DataIntegrityViolationException  e) {
			throw new ResponseStatusException (HttpStatus.CONFLICT);
		}
		switch(info.get("tipo").toString()){
		
		case "admin":
			Admin a1= new Admin(email, nombre, apellidos, dni, contrasena, repetircontrasena, ciudad, activo);
			
			this.admindao.save(a1);
			break;	
		case"mantenimiento":
			int experiencia = Integer.parseInt(info.get("experiencia").toString());
			Mantenimiento m1 = new Mantenimiento(email, nombre, apellidos, dni, contrasena, repetircontrasena, ciudad, activo, experiencia);
			
			this.mandao.save(m1);
			break;
		case "cliente":
			String telefono = info.get("telefono").toString();
			String carnet = info.get("carnet").toString();
			
			Cliente c = new Cliente(email, nombre, apellidos, dni, contrasena, repetircontrasena, ciudad, activo,telefono, carnet);
			this.clientedao.save(c);
			break;
			
		}
		
	}



	public ResponseEntity<String> login(Map<String, Object> info) {
		String email = info.get("email").toString();
		String pass = info.get("contrasena").toString();
		
		Correo c = this.correodao.findByEmail(email);
		if (c == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "credenciales invalidas");
		}
		
		String tipo = c.getTipo();
		
		switch (tipo){
			case "admin":
				Admin a = this.admindao.findByEmail(email);
				if (!a.getContrasena().equals(pass)) {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "credenciales invalidas");
				}
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("admin");
				
			case "cliente":
				Cliente cliente = this.clientedao.findByEmail(email);
				if (!cliente.getContrasena().equals(pass)) {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "credenciales invalidas");
				}
				
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("cliente");
				
			case "mantenimiento":
				Mantenimiento m = this.mandao.findByEmail(email);
				if (!m.getContrasena().equals(pass)) {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "credenciales invalidas");
				}
				
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("mantenimiento");
				
		
		}
		return (ResponseEntity<String>) ResponseEntity.status(HttpStatus.FORBIDDEN).body("credenciales invalidas");
		
		
		
	}


/*
	public boolean updateUsuario(String userId, String nombre, String apellidos, String ciudad) {
        Optional<Usuario> optionalUsuario = usuarioDAO.findById(userId);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setCiudad(ciudad);
            // Puedes agregar más campos aquí si es necesario

            // Guarda la actualización en la base de datos
            usuarioDAO.save(usuario);
            return true; // Actualización exitosa
        } else {
            return false; // No se encontró el usuario con el ID especificado
        }
    }
		
*/
	
	public List <Admin> listaAdministradores(){
		return this.admindao.findAll();
	}
	public List <Mantenimiento> listaMantenimiento(){
		return this.mandao.findAll();
	}
	public List <Cliente> listaClientes(){
		return this.clientedao.findAll();
	}

	
	public Usuario obtenerAdminPorEmail(String email) {
		return admindao.findByEmail(email);
	}

	public Usuario obtenerMantenimientoPorEmail(String email) {
		return mandao.findByEmail(email);
	}

	public Usuario obtenerClientePorEmail(String email) {
		return clientedao.findByEmail(email);
	}
	

}
