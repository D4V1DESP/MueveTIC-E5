package edu.uclm.esi.ds.webApp;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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
import edu.uclm.esi.ds.webApp.dao.MotoDAO;
import edu.uclm.esi.ds.webApp.dao.PatineteDAO;
import edu.uclm.esi.ds.webApp.dao.ReservaClienteDAO;
import edu.uclm.esi.ds.webApp.entities.Coche;
import edu.uclm.esi.ds.webApp.entities.Moto;
import edu.uclm.esi.ds.webApp.entities.Patinete;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestReserves {
	
	@Autowired
	private MockMvc server;
	@Autowired
	private JWToken testToken;
	@Autowired
	private ReservaClienteDAO reservaClienteDAO;
	@Autowired
	private CocheDAO cocheDAO;
	@Autowired
	private MotoDAO motoDAO;
	@Autowired
	private PatineteDAO patineteDAO;
	
	private String tokenCliente;
	@BeforeAll
	void obtenerToken() throws Exception{
		tokenCliente = testToken.generarTokenCliente();
	}
	@Test @Order(1)
	void AddClientReserve() throws Exception {
		
		this.reservaClienteDAO.deleteByEmail("floresmanu99@gmail.com");
		this.reservaClienteDAO.deleteByEmail("danielMachuca@gmial.com");
		this.reservaClienteDAO.deleteByEmail("NuevoCLienteAutenticado@gmail.com");
		
		Coche coche = this.cocheDAO.findByMatricula("1234CFG");
		coche.setBateria(100);
		cocheDAO.save(coche);
		
		Patinete patinete = this.patineteDAO.findByMatricula("6574OKU");
		patinete.setBateria(100);
		patineteDAO.save(patinete);
		
		Moto moto = this.motoDAO.findByMatricula("4378UIY");
		moto.setBateria(100);
		motoDAO.save(moto);
		
		ResultActions result = this.sendRequest("floresmanu99@gmail.com", "1234CFG");
		result.andExpect(status().isOk()).andReturn();
		
		result = this.sendRequest("danielMachuca@gmial.com", "6574OKU");
		result.andExpect(status().isOk()).andReturn();
		
		result = this.sendRequest("NuevoCLienteAutenticado@gmail.com", "4378UIY");
		result.andExpect(status().isOk()).andReturn();
		
		// usuario no existe 
		result = this.sendRequest("aaa@gmail.com", "1234CFG");
		result.andExpect(status().isConflict()).andReturn();
		// vehiculo no existe
		result = this.sendRequest("floresmanu99@gmail.com", "1111AAA");
		result.andExpect(status().isConflict()).andReturn();
		//vehiculo ya reservad
		result = this.sendRequest("manuPruebaCliente@gmail.com", "1234CFG");
		result.andExpect(status().isConflict()).andReturn();
		// usuario con reserva activa
		result = this.sendRequest("floresmanu99@gmail.com", "2342GSL");
		result.andExpect(status().isConflict()).andReturn();
		
	}
	
	@Test @Order(2)
	void RealizarValoracion() throws Exception {
		
		JSONObject jsoValoracion = new JSONObject();
		jsoValoracion = crearValoracion(jsoValoracion, "floresmanu99@gmail.com", "1234CFG");
		ResultActions resultado = this.sendRequestDarValoracion(jsoValoracion);
		resultado.andExpect(status().isOk()).andReturn();
		
		jsoValoracion = crearValoracion(jsoValoracion, "danielMachuca@gmial.com", "6574OKU");
		resultado = this.sendRequestDarValoracion(jsoValoracion);
		resultado.andExpect(status().isOk()).andReturn();
		
		jsoValoracion = crearValoracion(jsoValoracion, "NuevoCLienteAutenticado@gmail.com", "4378UIY");
		resultado = this.sendRequestDarValoracion(jsoValoracion);
		resultado.andExpect(status().isOk()).andReturn();
		
	}

	private ResultActions sendRequest(String email, String vehiculo) throws Exception , UnsupportedEncodingException{
		JSONObject jsonReserve = new JSONObject()
				.put("email", email)
				.put("matricula", vehiculo);
		RequestBuilder request = MockMvcRequestBuilders.
				post("/reservas/usersAdd").
				header("Authorization", "Bearer " + tokenCliente).
				contentType("application/json").
				content(jsonReserve.toString());
		

		ResultActions resultActions =this.server.perform(request);
		return resultActions;
	}
	
	private ResultActions sendRequestDarValoracion(JSONObject jsoValoracion) throws Exception {
		
		RequestBuilder request = MockMvcRequestBuilders.
				post("/reservas/AddReview").
				header("Authorization", "Bearer " + tokenCliente).
				contentType("application/json").
				content(jsoValoracion.toString());
		
		ResultActions resultActions = this.server.perform(request);
		return resultActions;
	}
	
	private JSONObject crearValoracion(JSONObject jsoValoracion, String email, String matricula) throws Exception{
		jsoValoracion.put("cliente", email);
		jsoValoracion.put("vehiculo", matricula);
		jsoValoracion.put("estrellas", 5);
		jsoValoracion.put("comentario", "Me ha encantado MueveTIC");
		
		return jsoValoracion;
	}

}
