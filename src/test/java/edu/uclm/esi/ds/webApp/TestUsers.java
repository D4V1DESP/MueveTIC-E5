package edu.uclm.esi.ds.webApp;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;


import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import edu.uclm.esi.ds.webApp.dao.AdminDAO;
import edu.uclm.esi.ds.webApp.dao.ClienteDAO;
import edu.uclm.esi.ds.webApp.dao.CorreoDAO;
import edu.uclm.esi.ds.webApp.dao.MantenimientoDAO;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestUsers  {
	@Autowired
	private MockMvc server;
	@Autowired 
	private AdminDAO adminDAO;
	@Autowired 
	private MantenimientoDAO mantenimientoDAO;
	@Autowired 
	private ClienteDAO clienteDAO;
	@Autowired
	private CorreoDAO correoDAO;
	@Autowired
	private JWToken testToken;
	
	private String tokenAdmin;	
	
	@BeforeAll
	void obtenerToken() throws Exception{
		tokenAdmin = testToken.generarTokenAdmin();
	}
	
	@Test 
	void testAdduser() throws Exception {
		// pruebas de registros correctos
		
		this.adminDAO.deleteByemail("floresmanu99@gmail.com");
		this.mantenimientoDAO.deleteByemail("pabloGarcia@gmial.com");
		this.clienteDAO.deleteByemail("danielMachuca@gmial.com");
		this.correoDAO.deleteByemail("floresmanu99@gmail.com");
		this.correoDAO.deleteByemail("danielMachuca@gmial.com");
		this.correoDAO.deleteByemail("pabloGarcia@gmial.com");
		
		ResultActions result= this.sendRequest("floresmanu99@gmail.com","05939881Q", "manuel", "flores villajos", "manuelfv99","manuelfv99","true", "admin","puertollano");
		result.andExpect(status().isOk()).andReturn();

		result= this.sendRequest("danielMachuca@gmial.com","05939981Q", "manuel", "flores villajos", "manuelfv99","manuelfv99","true", "cliente", "666697498", "c", "19/10/1999");
		result.andExpect(status().isOk()).andReturn();
		
		result= this.sendRequest("pabloGarcia@gmial.com","05939081Q", "manuel", "flores villajos", "manuelfv99","manuelfv99","true", "mantenimiento","puertollano", "5");
		result.andExpect(status().isOk()).andReturn();
		
		// caso de que insertemos un usuario repetido en la bbdd 
		result= this.sendRequest("floresmanu99@gmail.com","05939881Q", "manuel", "flores villajos", "manuelfv99","manuelfv99","true", "admin","puertollano");
		result.andExpect(status().isConflict()).andReturn();
		
		// caso de que las contraseñas no coincidan
		result= this.sendRequest("floresmanu99@gmail.com","05939881Q", "manuel", "flores villajos", "manuelfv99","aaa","true", "admin","puertollano");
		result.andExpect(status().isConflict()).andReturn();		
		
		
	}

	private ResultActions sendRequest(String email, String dni, String nombre, String apellidos, String contraseña,
			String rcontraseña, String activo, String tipo,String ciudad) throws Exception, UnsupportedEncodingException {
		JSONObject jsonUser = new JSONObject()
				.put("email", email)
				.put("nombre", nombre)
				.put("dni", dni)
				.put("apellidos", apellidos)
				.put("contrasena", contraseña)
				.put("repetirContrasena", rcontraseña)
				.put("tipo", tipo)
				.put("ciudad", ciudad)
				.put("activo", activo);
		
		
		RequestBuilder request = MockMvcRequestBuilders.
				post("/users/AddUser")
				.header("Authorization", "Bearer " + tokenAdmin)
				.contentType("application/json")
				.content(jsonUser.toString());
		

		ResultActions resultActions =this.server.perform(request);
		return resultActions;
	}
	private ResultActions sendRequest(String email, String dni, String nombre, String apellidos, String contraseña,
			String rcontraseña, String activo, String tipo,  String telefono, String carnet, String fecha)throws Exception, UnsupportedEncodingException {
		JSONObject jsonUser = new JSONObject()
				.put("email", email)
				.put("nombre", nombre)
				.put("dni", dni)
				.put("apellidos", apellidos)
				.put("contrasena", contraseña)
				.put("repetirContrasena", rcontraseña)
				.put("tipo", tipo)
				.put("activo", activo)
				.put("telefono", telefono)
				.put("carnet", carnet)
				.put("fecha", fecha);
		
		
		RequestBuilder request = MockMvcRequestBuilders.
				post("/users/AddUser")
				.header("Authorization", "Bearer " + tokenAdmin)
				.contentType("application/json")
				.content(jsonUser.toString());
		

		ResultActions resultActions =this.server.perform(request);
		return resultActions;
	}
	private ResultActions sendRequest(String email, String dni, String nombre, String apellidos, String contraseña,
			String rcontraseña, String activo, String tipo, String ciudad, String experiencia)throws Exception, UnsupportedEncodingException {
		JSONObject jsonUser = new JSONObject()
				.put("email", email)
				.put("nombre", nombre)
				.put("dni", dni)
				.put("apellidos", apellidos)
				.put("contrasena", contraseña)
				.put("repetirContrasena", rcontraseña)
				.put("tipo", tipo)
				.put("ciudad", ciudad)
				.put("activo", activo)
				.put("experiencia", experiencia);
		
		
		
		RequestBuilder request = MockMvcRequestBuilders.
				post("/users/AddUser")
				.header("Authorization", "Bearer " + tokenAdmin)
				.contentType("application/json")
				.content(jsonUser.toString());
		

		ResultActions resultActions =this.server.perform(request);
		return resultActions;
	}
}
