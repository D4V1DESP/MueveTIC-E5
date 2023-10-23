package edu.uclm.esi.ds.webApp.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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
			Correo c = new Correo(email);
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
			char carnet = info.get("carnet").toString().charAt(0);
			
			Cliente c = new Cliente(email, nombre, apellidos, dni, contrasena, repetircontrasena, ciudad, activo,telefono, carnet);
			this.clientedao.save(c);
			break;
			
		}
		
	}

}
