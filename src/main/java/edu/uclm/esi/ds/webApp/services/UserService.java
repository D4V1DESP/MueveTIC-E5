package edu.uclm.esi.ds.webApp.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uclm.esi.ds.webApp.dao.AdminDAO;
import edu.uclm.esi.ds.webApp.dao.ClienteDAO;
import edu.uclm.esi.ds.webApp.dao.MantenimientoDAO;
import edu.uclm.esi.ds.webApp.entities.Admin;
import edu.uclm.esi.ds.webApp.entities.Mantenimiento;
import edu.uclm.esi.ds.webApp.entities.Cliente;


@Service 
public class UserService {
	
	@Autowired 
	private ClienteDAO clientedao;
	private AdminDAO admindao;
	private MantenimientoDAO mandao;
	
	
	public void AltaAdminMantenimiento(Map <String ,Object> info) {
		String email = info.get("email").toString();
		String nombre = info.get("nombre").toString();
		String apellidos = info.get("apellidos").toString();
		String dni = info.get("dni").toString();
		String contrasena = info.get("contraseña").toString();
		String repetircontrasena = info.get("repetirContraseña").toString();
		boolean activo = Boolean.parseBoolean(info.get("activo").toString());
		String ciudad = info.get("ciudad").toString();
		
		switch(info.get("tipo").toString()){
		
		case "Admin":
			Admin a1= new Admin(email, nombre, apellidos, dni, contrasena, repetircontrasena, ciudad, activo);
			this.admindao.save(a1);
			break;	
		case"mantenimiento":
			
			break;
		case "cliente":
			
			break;
			
		}
		
	}

}
