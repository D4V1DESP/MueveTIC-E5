package edu.uclm.esi.ds.webApp.services;

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
import edu.uclm.esi.ds.webApp.entities.Cliente;
import edu.uclm.esi.ds.webApp.entities.Correo;
import edu.uclm.esi.ds.webApp.entities.Usuario;


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
		try {
			Correo c = new Correo(email,info.get("tipo").toString());
			correodao.save(c);
		}catch(DataIntegrityViolationException  e) {
			throw new ResponseStatusException (HttpStatus.CONFLICT);
		}
		String tipo =info.get("tipo").toString();
		switch(tipo){
		
			case "admin":
				
				Admin a1= new Admin(email, dni, nombre, apellidos, contrasena, repetircontrasena, info.get("ciudad").toString(), activo,tipo );
				
				this.admindao.save(a1);
				break;	
			case"mantenimiento":
				
				int experiencia = Integer.parseInt(info.get("experiencia").toString());
				Mantenimiento m1 = new Mantenimiento(email, dni, nombre, apellidos, contrasena, repetircontrasena,info.get("ciudad").toString() , activo, experiencia, tipo);
				
				this.mandao.save(m1);
				break;
			case "cliente":
				String telefono = info.get("telefono").toString();
				char carnet = info.get("carnet").toString().charAt(0);
				String fecha= info.get("fecha").toString();
				Cliente c = new Cliente(email, dni, nombre,apellidos,contrasena, repetircontrasena, activo,telefono,carnet, tipo,fecha) ;
				this.clientedao.save(c);
				break;
			
		}
		
	}



	public Usuario login(Map<String, Object> info) {
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
				return a;
				
			case "cliente":
				Cliente cliente = this.clientedao.findByEmail(email);
				if (!cliente.getContrasena().equals(pass)) {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "credenciales invalidas");
				}
				
				return cliente;
				
			case "mantenimiento":
				Mantenimiento m = this.mandao.findByEmail(email);
				if (!m.getContrasena().equals(pass)) {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "credenciales invalidas");
				}
				
				return m;
				
		
		}
		return null;
		
		
		
	}



	public void updateUsers(Map<String, Object> info) {
		Correo c = this.correodao.findByEmail((String) info.get("email".toString()));
		
		
		if (c!= null) {
			String tipo =c.getTipo();
			
			switch (tipo) {
			case "admin":
				Admin a = this.admindao.findByEmail(c.getEmail());
				a.setNombre(info.get("nombre").toString());
				a.setApellidos(info.get("apellidos").toString());
				a.setDni(info.get("dni").toString());
				a.setCiudad(info.get("ciudad").toString());
				
				this.admindao.save(a);
				
				break;
			case "mantenimiento":
				Mantenimiento m = this.mandao.findByEmail(c.getEmail());
				m.setNombre(info.get("nombre").toString());
				m.setApellidos(info.get("apellidos").toString());
				m.setDni(info.get("dni").toString());
				m.setCiudad(info.get("ciudad").toString());
				m.setExperiencia(Integer.parseInt(info.get("experiencia").toString()));
				
				this.mandao.save(m);
				break;
			case "cliente":
				Cliente c1 = this.clientedao.findByEmail(c.getEmail());
				c1.setNombre(info.get("nombre").toString());
				c1.setApellidos(info.get("apellidos").toString());
				c1.setDni(info.get("dni").toString());
				c1.setTelefono(info.get("telefono").toString());
				c1.setCarnet(info.get("carnet").toString().charAt(0));
				c1.setFechaNacimiento(info.get("fecha").toString());
				this.clientedao.save(c1);
				break;
			}
		}
		
	}
	
	

}
