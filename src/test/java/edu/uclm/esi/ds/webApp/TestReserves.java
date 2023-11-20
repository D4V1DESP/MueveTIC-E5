package edu.uclm.esi.ds.webApp;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;

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

import edu.uclm.esi.ds.webApp.dao.ReservaClienteDAO;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc

public class TestReserves {
	
	@Autowired
	private MockMvc server;

	
	@Test @Disabled
	void AddClientReserve() throws Exception {
		// todo correcto 
		ResultActions result = this.sendRequest("floresmanu99@gmail.com", "1234CFG");
		result.andExpect(status().isOk()).andReturn();
		// usuario no existe 
		// vehiculo no existe
		//vehiculo ya reservad
		// usuario con reserva activa
		//vehiculo no esta disponible 
		// usuario sin carnet para vehiculo 
		
	}
	@Test
	void TestConfig() throws Exception{
		ResultActions result = this.sendRequestConfig("vehiculoRecarga", "21");
		result.andExpect(status().isOk()).andReturn();
	}
	
	private ResultActions sendRequestConfig(String nombre, String valor) throws Exception, UnsupportedEncodingException {
		JSONObject jsonReserve = new JSONObject()
				.put("nombre", nombre)
				.put("valor", valor);
		RequestBuilder request = MockMvcRequestBuilders.
				post("/config/Add").
				contentType("application/json").
				content(jsonReserve.toString());
		ResultActions resultActions =this.server.perform(request);
		
		return resultActions;
	}

	private ResultActions sendRequest(String email, String vehiculo) throws Exception , UnsupportedEncodingException{
		JSONObject jsonReserve = new JSONObject()
				.put("email", email)
				.put("matricula", vehiculo);
		RequestBuilder request = MockMvcRequestBuilders.
				post("/reservas/usersAdd").
				contentType("application/json").
				content(jsonReserve.toString());
		

		ResultActions resultActions =this.server.perform(request);
		return resultActions;
	}

}
