package edu.uclm.esi.ds.webApp.services;

import java.util.Calendar;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.dao.ReservaClienteDAO;
import edu.uclm.esi.ds.webApp.entities.ReservaCliente;

@Service
public class ReservaService {
	
	@Autowired 
	private ReservaClienteDAO reservaClienteDAO;
	
	public void addReservaCliente(Map<String , Object>info ) {
		String mail = info.get("email").toString();
		ReservaCliente reserva = this.reservaClienteDAO.findByEmail(mail);
		if (reserva.getEstado().equals("reservado")) {
			
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}else {
			
			Calendar c = Calendar.getInstance();
			String fecha = "";//Integer.toString(c.get(Calendar.DATE))+"/"+Integer.toString(c.get(Calendar.MONTH))+"/"+Integer.toString(c.get(Calendar.YEAR));
			String vehiculo = info.get("vehiculo").toString();
			
			ReservaCliente newReserva = new ReservaCliente(mail, vehiculo,fecha);
			this.reservaClienteDAO.save(newReserva);
			
		
		}
		
	}
	

}
