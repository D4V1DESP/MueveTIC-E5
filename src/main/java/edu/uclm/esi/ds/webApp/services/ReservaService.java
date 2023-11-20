package edu.uclm.esi.ds.webApp.services;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.dao.CorreoDAO;
import edu.uclm.esi.ds.webApp.dao.MatriculaDAO;
import edu.uclm.esi.ds.webApp.dao.ReservaClienteDAO;
import edu.uclm.esi.ds.webApp.entities.Correo;
import edu.uclm.esi.ds.webApp.entities.Matricula;
import edu.uclm.esi.ds.webApp.entities.ReservaCliente;

@Service
public class ReservaService {
	
	@Autowired 
	private ReservaClienteDAO reservaClienteDAO;
	@Autowired 
	private CorreoDAO correoDAO;
	@Autowired
	private MatriculaDAO matriculaDAO;
	
	public void addReservaCliente(Map<String , Object>info ) {
		String email = info.get("email").toString();
		String vehiculo =(String) info.get("matricula");
		List<ReservaCliente> reserva = this.reservaClienteDAO.findByEmail(email);
		if (!checkUser(email)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		/*if (!checkVehicle(vehiculo)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}*/
		//if (EncontrarReservaActiva(reserva) == null ) {
			//throw new ResponseStatusException(HttpStatus.CONFLICT);
		//}else {
			
			Calendar c = Calendar.getInstance();
			String fecha =Integer.toString(c.get(Calendar.DATE))+"/"+Integer.toString(c.get(Calendar.MONTH)+1)+"/"+Integer.toString(c.get(Calendar.YEAR));
			
			
			ReservaCliente newReserva = new ReservaCliente(email, vehiculo,fecha);
		
			this.reservaClienteDAO.save(newReserva);	
		
		
	}

	public void CancelUserReserve(Map<String, Object> info) {
		String email = info.get("email").toString();
		
		List<ReservaCliente> reservas = this.reservaClienteDAO.findByEmail(email);
		ReservaCliente reserva= EncontrarReservaActiva(reservas);
		
		reserva.setEstado("cancelada");
		this.reservaClienteDAO.save(reserva);
	}
	

	public ReservaCliente EncontrarReservaActiva(List<ReservaCliente> lista) {
		ReservaCliente r = null;
		
		for (ReservaCliente reserva: lista ) {
			if (reserva.getEstado().equals("reservado")) {
				r = reserva;
			}
		}
		return r;
	}

	public void AddValoracion(Map<String, Object> info) {
		String email = info.get("email").toString();
		List<ReservaCliente> reserva = this.reservaClienteDAO.findByEmail(email);
		ReservaCliente reservaActiva = this.EncontrarReservaActiva(reserva);
		int valoracion = Integer.parseInt(info.get("estrellas").toString());
		String comentario = info.get("comentario").toString();
		
		reservaActiva.setValoracion(valoracion);
		reservaActiva.setValoracionText(comentario);
		reservaActiva.setEstado("finalizada");
		this.reservaClienteDAO.save(reservaActiva);
		
		
	}
	public boolean checkUser(String email) {
		boolean checked = false;
		List<Correo> lstUser = this.correoDAO.findAll();
		for (Correo c : lstUser) {
			if(c.getEmail().equals(email)) {
				checked = true;
			}
		}
		
		return checked;
	}
	public boolean checkVehicle(String matricula) {
		boolean checked = false;
		List<Matricula> lstvehicles = this.matriculaDAO.findAll();
		for (Matricula m : lstvehicles) {
			if(m.getMatricula().equals(matricula)) {
				checked = true;
			}
		}
		
		return checked;
	}
	
}
