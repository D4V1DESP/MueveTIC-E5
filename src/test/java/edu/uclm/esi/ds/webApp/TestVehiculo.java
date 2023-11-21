package edu.uclm.esi.ds.webApp;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
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

import edu.uclm.esi.ds.webApp.dao.CocheDAO;
import edu.uclm.esi.ds.webApp.dao.MatriculaDAO;
import edu.uclm.esi.ds.webApp.dao.MotoDAO;
import edu.uclm.esi.ds.webApp.dao.PatineteDAO;
import edu.uclm.esi.ds.webApp.entities.Admin;
import edu.uclm.esi.ds.webApp.security.Role;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestVehiculo {

	@Autowired
	private MockMvc server;
	@Autowired 
	private CocheDAO cocheDAO;
	@Autowired
	private MotoDAO motoDAO;
	@Autowired
	private PatineteDAO patineteDAO;
	@Autowired
	private MatriculaDAO matriculaDAO;
	
	String matriculaCoche = "1234CFG";
	String matriculaMoto = "4378UIY";
	String matriculaPatinete = "6574OKU";
	String direccion = "AV.Francia";
	String modelo = "Nissan";
	String bateria = "0";
	String estado = "disponible";
	String nPlazas = "2";
	String color = "Rojo";
	String casco = "true";

	private String tokenAdmin;	
	private String tokenMantenimiento;
	
	@BeforeAll
	void obtenerToken() throws Exception{
		tokenAdmin = generarTokenAdmin();
		tokenMantenimiento = generarTokenMantenimiento();
	}
	
	
	@Test @Order(1)
	void testDarAltaVehiculo() throws Exception{

		String tipo = "Coche";
		JSONObject jsoCoche = new JSONObject();
		jsoCoche = crearTipoVehiculo(matriculaCoche, jsoCoche, "nPlazas", tipo, nPlazas);
		
		tipo = "Moto";
		JSONObject jsoMoto = new JSONObject();
		jsoMoto = crearTipoVehiculo(matriculaMoto, jsoMoto, "casco", tipo, casco);
		
		tipo = "Patinete";
		JSONObject jsoPatinete = new JSONObject();
		jsoPatinete = crearTipoVehiculo(matriculaPatinete, jsoPatinete, "color", tipo, color);	
		
		ResultActions resultado = this.sendRequestEliminar(jsoCoche);
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestEliminar(jsoMoto);
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestEliminar(jsoPatinete);
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestAlta(jsoCoche);
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestAlta(jsoMoto);
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestAlta(jsoPatinete);
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestAlta(jsoCoche);
		resultado.andExpect(status().isConflict()).andReturn();
		
	}
	
	@Test @Order(2)
	void testConsultarVehiculos() throws Exception{
		
		ResultActions resultado = this.sendRequestConsulta("coches");
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestConsulta("motos");
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestConsulta("patinetes");
		resultado.andExpect(status().isOk()).andReturn();
	}
	
	@Test @Order(3)
	void testConsultaVehiculosRecargables() throws Exception{
		
		ResultActions resultado = this.sendRequestConsultaRecargables("recargables/Coche");
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestConsultaRecargables("recargables/Moto");
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestConsultaRecargables("recargables/Patinete");
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestConsultaRecargables("recargables/coche");
		resultado.andExpect(status().isConflict()).andReturn();
	}
	
	@Test @Order(4)
	void testRecargaVehiculos() throws Exception{
		
		String tipo = "Coche";
		JSONObject jsoCocheRecarga = new JSONObject();
		jsoCocheRecarga = crearTipoVehiculo(matriculaCoche, jsoCocheRecarga, "nPlazas", tipo, nPlazas);
		
		tipo = "Moto";
		JSONObject jsoMotoRecarga = new JSONObject();
		jsoMotoRecarga = crearTipoVehiculo(matriculaMoto, jsoMotoRecarga, "casco", tipo, casco);
		
		tipo = "Patinete";
		JSONObject jsoPatineteRecarga = new JSONObject();
		jsoPatineteRecarga = crearTipoVehiculo(matriculaPatinete, jsoPatineteRecarga, "color", tipo, color);	
		
		ResultActions resultado = this.sendRequestRecarga(jsoCocheRecarga);
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestRecarga(jsoMotoRecarga);
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestRecarga(jsoPatineteRecarga);
		resultado.andExpect(status().isOk()).andReturn();
	}

	private ResultActions sendRequestEliminar(JSONObject jsoVehiculo) throws Exception {
		
		RequestBuilder request = MockMvcRequestBuilders.post("/vehiculos/eliminar")
				.header("Authorization", "Bearer " + tokenAdmin)
				.contentType("application/json")
				.content(jsoVehiculo.toString());
		
		
		ResultActions resultado = this.server.perform(request);
		return resultado;
	}

	private JSONObject crearTipoVehiculo(String matricula, JSONObject jsoVehiculo, String key2, String atr1, String atr2) throws Exception{
		jsoVehiculo.put("matricula", matricula);
		jsoVehiculo.put("direccion", direccion);
		jsoVehiculo.put("modelo", modelo);
		jsoVehiculo.put("bateria", bateria);
		jsoVehiculo.put("estado", estado);	
		jsoVehiculo.put("tipo", atr1);
		jsoVehiculo.put(key2, atr2);
		return jsoVehiculo;
	}

	private ResultActions sendRequestAlta(JSONObject jsoVehiculo) throws Exception{
		
		RequestBuilder request = MockMvcRequestBuilders.post("/vehiculos/alta")
				.header("Authorization", "Bearer " + tokenAdmin)
				.contentType("application/json")
				.content(jsoVehiculo.toString());
		
		ResultActions resultado = this.server.perform(request);
		return resultado;
		
	}
	
	private ResultActions sendRequestConsulta(String recurso) throws Exception {
		String requestCad = "/vehiculos/" + recurso;
		
		RequestBuilder request = MockMvcRequestBuilders.get(requestCad)
				.header("Authorization", "Bearer " + tokenAdmin);
			
		ResultActions resultado = this.server.perform(request);
		return resultado;
	}
	
	private ResultActions sendRequestConsultaRecargables(String recurso) throws Exception {
		String requestCad = "/vehiculos/" + recurso;
		
		RequestBuilder request = MockMvcRequestBuilders.get(requestCad)
				.header("Authorization", "Bearer " + tokenMantenimiento);
			
		ResultActions resultado = this.server.perform(request);
		return resultado;
	}
	
	private ResultActions sendRequestRecarga(JSONObject jsoVehiculo) throws Exception{
		
		RequestBuilder request = MockMvcRequestBuilders.put("/vehiculos/recargar")
				.header("Authorization", "Bearer " + tokenMantenimiento)
				.contentType("application/json")
				.content(jsoVehiculo.toString());
		
		ResultActions resultado = this.server.perform(request);
		return resultado;
	}
	
	private String generarTokenAdmin() throws Exception{
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
	
	private String generarTokenMantenimiento() throws Exception{
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
		
//		server.perform(MockMvcRequestBuilders.post("/users/AddUser")
//		.contentType("application/json")
//		.content(jsoMantenimiento.toString()));

		//Crear y eliminar el usuario, cuando se merge con la parte de Delete
		
		String tokenMantenimiento = server.perform(MockMvcRequestBuilders.post("/users/authenticate")
		        .contentType("application/json")
		        .content(jsoMantenimiento.toString()))
		        .andReturn()
		        .getResponse()
		        .getContentAsString();
		
		return tokenMantenimiento;
	}
}
