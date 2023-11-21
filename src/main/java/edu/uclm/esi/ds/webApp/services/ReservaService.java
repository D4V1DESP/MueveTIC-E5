package edu.uclm.esi.ds.webApp.services;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.dao.CocheDAO;
import edu.uclm.esi.ds.webApp.dao.MatriculaDAO;
import edu.uclm.esi.ds.webApp.dao.MotoDAO;
import edu.uclm.esi.ds.webApp.dao.PatineteDAO;
import edu.uclm.esi.ds.webApp.dao.ReservaClienteDAO;
import edu.uclm.esi.ds.webApp.entities.Coche;
import edu.uclm.esi.ds.webApp.entities.Matricula;
import edu.uclm.esi.ds.webApp.entities.Moto;
import edu.uclm.esi.ds.webApp.entities.Patinete;
import edu.uclm.esi.ds.webApp.entities.ReservaCliente;
import edu.uclm.esi.ds.webApp.entities.Vehiculo;

@Service
public class ReservaService {
	
	@Autowired 
	private ReservaClienteDAO reservaClienteDAO;
	@Autowired
	private MatriculaDAO matriculaDAO;
	@Autowired
	private CocheDAO cocheDAO;
	@Autowired
	private MotoDAO motoDAO;
	@Autowired
	private PatineteDAO patineteDAO;
	
		
	public void addReservaCliente(Map<String, Object> info) {
	    String email = info.get("email").toString();
	    List<ReservaCliente> reservas = this.reservaClienteDAO.findByEmail(email);

	    // Verificar si hay alguna reserva en estado "reservado"
	    boolean tieneReservaEnEstadoReservado = false;

	    for (ReservaCliente reserva : reservas) {
	        if (reserva.getEstado().equals("reservado")) {
	            tieneReservaEnEstadoReservado = true;
	            break;
	        }
	    }

	    if (!tieneReservaEnEstadoReservado) {
	        // No hay reservas en estado "reservado", se puede añadir la nueva reserva
	        Calendar c = Calendar.getInstance();
	        String fecha = Integer.toString(c.get(Calendar.DATE)) + "/" +
	                Integer.toString(c.get(Calendar.MONTH) + 1) + "/" +
	                Integer.toString(c.get(Calendar.YEAR));
	        String vehiculo = (String) info.get("matricula");

	        ReservaCliente newReserva = new ReservaCliente(email, vehiculo, fecha);
	        this.reservaClienteDAO.save(newReserva);
	    } else {
	        // Ya tiene una reserva en estado "reservado"
	        throw new ResponseStatusException(HttpStatus.CONFLICT);
	    }
	}


	public void CancelUserReserve(Map<String, Object> info) {
		String email = info.get("email").toString();
		String matricula = info.get("vehiculo").toString();
		
		List<ReservaCliente> reservas = this.reservaClienteDAO.findByEmail(email);
		Matricula m = this.matriculaDAO.findByMatricula(matricula);
		
	    for (ReservaCliente reserva : reservas) {
	        if (reserva.getEstado().equals("reservado")) {
	            
	            reserva.setEstado("cancelada");
	    		this.reservaClienteDAO.save(reserva);
	        }
	    }
	    if(m.getTipo().equals("Coche")) {
	    	Coche coche = this.cocheDAO.findByMatricula(matricula);
	    	coche.setEstado("disponible");
	    	this.cocheDAO.save(coche);
	    }
	    if(m.getTipo().equals("Moto")) {
	    	Moto moto = this.motoDAO.findByMatricula(matricula);
	    	moto.setEstado("disponible");
	    	this.motoDAO.save(moto);
	    }
	    if(m.getTipo().equals("Patinete")) {
	    	Patinete patinete = this.patineteDAO.findByMatricula(matricula);
	    	patinete.setEstado("disponible");
	    	this.patineteDAO.save(patinete);
	    }
	    
	    
	    
	}

	public List<ReservaCliente> obtenerReservaPorEmail(String email) {
		return reservaClienteDAO.findByEmail(email);
	}
	public List<ReservaCliente> listaReservas() {
		return reservaClienteDAO.findAll();
	}
		
}
