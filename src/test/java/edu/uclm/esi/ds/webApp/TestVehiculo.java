package edu.uclm.esi.ds.webApp;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.jacoco.agent.rt.internal_4742761.core.internal.ContentTypeDetector;
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
import edu.uclm.esi.ds.webApp.dao.MotoDAO;
import edu.uclm.esi.ds.webApp.dao.PatineteDAO;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc

public class TestVehiculo {

	@Autowired
	private MockMvc server;
	@Autowired 
	private CocheDAO cocheDAO;
	@Autowired
	private MotoDAO motoDAO;
	@Autowired
	private PatineteDAO patineteDAO;
	
	String matricula = "AD-1234-FG";
	String direccion = "AV.Francia";
	String modelo = "Nissan";
	String bateria = "100";
	String estado = "disponible";
	String nPlazas = "2";
	String color = "Rojo";
	String casco = "true";
	
	
	@Test @Order(1)
	void testDarAltaVehiculo() throws Exception{
		
		String tipo = "Coche";
		JSONObject jsoCoche = new JSONObject();
		jsoCoche = crearTipoVehiculo(jsoCoche, "nPlazas", tipo, nPlazas);
		
		tipo = "Moto";
		JSONObject jsoMoto = new JSONObject();
		jsoMoto = crearTipoVehiculo(jsoMoto, "casco", tipo, casco);
		
		tipo = "Patinete";
		JSONObject jsoPatinete = new JSONObject();
		jsoPatinete = crearTipoVehiculo(jsoPatinete, "color", tipo, color);	
		
		borrarVehiculos(jsoCoche, jsoMoto, jsoPatinete);
		
		ResultActions resultado = this.sendRequestAlta(jsoCoche);
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

	private void borrarVehiculos(JSONObject jsoCoche, JSONObject jsoMoto, JSONObject jsoPatinete) throws Exception {
		this.cocheDAO.deleteBymatricula(jsoCoche.getString("matricula"));
		this.motoDAO.deleteBymatricula(jsoMoto.getString("matricula"));
		this.patineteDAO.deleteBymatricula(jsoPatinete.getString("matricula"));
	}

	private JSONObject crearTipoVehiculo(JSONObject jsoVehiculo, String key2, String atr1, String atr2) throws Exception{
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
}
