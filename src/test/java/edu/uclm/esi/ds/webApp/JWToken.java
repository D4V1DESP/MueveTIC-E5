package edu.uclm.esi.ds.webApp;

import org.json.JSONObject;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Component
public class JWToken {
	

	@Autowired
	private MockMvc server;

	public String generarTokenAdmin() throws Exception{
		JSONObject jsoAdmin = new JSONObject();
		jsoAdmin.put("email", "NuevoAdminAutenticado@gmail.com");
		jsoAdmin.put("dni", "OASDASJBASDKDA");
		jsoAdmin.put("nombre", "OTRACOMPROBACION");
		jsoAdmin.put("apellidos", "TusApellidos");
		jsoAdmin.put("contrasena", "TuContrasena");
		jsoAdmin.put("repetirContrasena", "TuContrasena");
		jsoAdmin.put("activo", true);
		jsoAdmin.put("tipo", "admin");
		jsoAdmin.put("ciudad", "Toledo");
		
//		server.perform(MockMvcRequestBuilders.post("/users/AddUser")
//				.contentType("application/json")
//				.content(jsoAdmin.toString()));
		
		//Crear y eliminar el usuario, cuando se merge con la parte de Delete
		
		String tokenAdmin = server.perform(MockMvcRequestBuilders.post("/users/authenticate")
		        .contentType("application/json")
		        .content(jsoAdmin.toString()))
		        .andReturn()
		        .getResponse()
		        .getContentAsString();
		
		return tokenAdmin;
	}
	
	public String generarTokenMantenimiento() throws Exception{
			JSONObject jsoMantenimiento = new JSONObject();
			jsoMantenimiento.put("email", "NuevoMantenimientoAutenticado@gmail.com");
			jsoMantenimiento.put("dni", "23451231ASD");
			jsoMantenimiento.put("nombre", "TuNombre");
			jsoMantenimiento.put("apellidos", "TusApellidos");
			jsoMantenimiento.put("contrasena", "TuContrasena");
			jsoMantenimiento.put("repetirContrasena", "TuContrasena");
			jsoMantenimiento.put("activo", true);
			jsoMantenimiento.put("tipo", "mantenimiento");
			jsoMantenimiento.put("ciudad", "Ciudad Real");
			jsoMantenimiento.put("experiencia", "2");
			
//			server.perform(MockMvcRequestBuilders.post("/users/AddUser")
//			.contentType("application/json")
//			.content(jsoMantenimiento.toString()));

			//Crear y eliminar el usuario, cuando se merge con la parte de Delete
			
			String tokenMantenimiento = server.perform(MockMvcRequestBuilders.post("/users/authenticate")
			        .contentType("application/json")
			        .content(jsoMantenimiento.toString()))
			        .andReturn()
			        .getResponse()
			        .getContentAsString();
			
			return tokenMantenimiento;
		}
	
	public String generarTokenCliente() throws Exception{
		JSONObject jsoCliente = new JSONObject();
		jsoCliente.put("email", "NuevoCLienteAutenticado@gmail.com");
		jsoCliente.put("dni", "askdmaskdn");
		jsoCliente.put("nombre", "TuNombre");
		jsoCliente.put("apellidos", "TusApellidos");
		jsoCliente.put("contrasena", "TuContrasena");
		jsoCliente.put("repetirContrasena", "TuContrasena");
		jsoCliente.put("activo", true);
		jsoCliente.put("tipo", "mantenimiento");
		jsoCliente.put("ciudad", "Ciudad Real");
		jsoCliente.put("telefono", "6127341");
		jsoCliente.put("fecha", "05/12/2000");
		jsoCliente.put("carnet", "C");
		
//		server.perform(MockMvcRequestBuilders.post("/users/AddUser")
//		.contentType("application/json")
//		.content(jsoCliente.toString()));

		//Crear y eliminar el usuario, cuando se merge con la parte de Delete
		
		String tokenMantenimiento = server.perform(MockMvcRequestBuilders.post("/users/authenticate")
		        .contentType("application/json")
		        .content(jsoCliente.toString()))
		        .andReturn()
		        .getResponse()
		        .getContentAsString();
		
		return tokenMantenimiento;
	}
}
