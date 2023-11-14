package edu.uclm.esi.ds.webApp.services;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

import edu.uclm.esi.ds.webApp.dao.ReservaClienteDAO;
import edu.uclm.esi.ds.webApp.entities.ReservaCliente;


public class ReservaService {
	
	@Autowired 
	private ReservaClienteDAO reservaClienteDAO;
	
	public void addReservaCliente(Map<String , Object>info ) {
		String mail = info.get("email").toString();
		ReservaCliente reserva = this.reservaClienteDAO.findByEmail(mail);
		
		
		
	}
	

}
