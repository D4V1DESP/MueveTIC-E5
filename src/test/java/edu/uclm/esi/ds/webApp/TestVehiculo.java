package edu.uclm.esi.ds.webApp;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.json.JSONObject;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc

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
	void testConsultaVehiculosrecargables() throws Exception{
		
		ResultActions resultado = this.sendRequestConsulta("recargables/Coche");
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestConsulta("recargables/Moto");
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestConsulta("recargables/Patinete");
		resultado.andExpect(status().isOk()).andReturn();
		
		resultado = this.sendRequestConsulta("recargables/coche");
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
				.contentType("application/json")
				.content(jsoVehiculo.toString());
		
		ResultActions resultado = this.server.perform(request);
		return resultado;
		
	}
	
	private ResultActions sendRequestConsulta(String recurso) throws Exception {
		String requestCad = "/vehiculos/" + recurso;
		
		RequestBuilder request = MockMvcRequestBuilders.get(requestCad);
			
		ResultActions resultado = this.server.perform(request);
		return resultado;
	}
	
	
	private ResultActions sendRequestRecarga(JSONObject jsoVehiculo) throws Exception{
		
		RequestBuilder request = MockMvcRequestBuilders.put("/vehiculos/recargar")
				.contentType("application/json")
				.content(jsoVehiculo.toString());
		
		ResultActions resultado = this.server.perform(request);
		return resultado;
	}
}
