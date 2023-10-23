package edu.uclm.esi.ds.webApp;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc

public class TestUsers {
	@Autowired
	private MockMvc server;
	
	@Test @Disabled
	void testAdduser() throws Exception {
		// pruebas de registros correctos
		ResultActions result= this.sendRequest("floresmanu99@gmail.com","05939881Q", "manuel", "flores villajos", "manuelfv99","manuelfv99","true", "admin","puertollano");
		result.andExpect(status().isOk()).andReturn();

		result= this.sendRequest("danielMachuca@gmial.com","05939881Q", "manuel", "flores villajos", "manuelfv99","manuelfv99","true", "cliente","puertollano", "666697498", "c");
		result.andExpect(status().isOk()).andReturn();
		
		result= this.sendRequest("pabloGarcia@gmial.com","05939881Q", "manuel", "flores villajos", "manuelfv99","manuelfv99","true", "mantenimiento","puertollano", "5");
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
				post("/users/AddUser").
				contentType("application/json").
				content(jsonUser.toString());
		

		ResultActions resultActions =this.server.perform(request);
		return resultActions;
	}
	private ResultActions sendRequest(String email, String dni, String nombre, String apellidos, String contraseña,
			String rcontraseña, String activo, String tipo,String ciudad,  String telefono, String carnet)throws Exception, UnsupportedEncodingException {
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
				.put("telefono", telefono)
				.put("carnet", carnet);
		
		
		RequestBuilder request = MockMvcRequestBuilders.
				post("/users/AddUser").
				contentType("application/json").
				content(jsonUser.toString());
		

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
				post("/users/AddUser").
				contentType("application/json").
				content(jsonUser.toString());
		

		ResultActions resultActions =this.server.perform(request);
		return resultActions;
	}
}
