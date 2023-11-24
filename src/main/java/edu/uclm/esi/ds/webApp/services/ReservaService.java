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
import edu.uclm.esi.ds.webApp.dao.CocheDAO;
import edu.uclm.esi.ds.webApp.dao.ConfigDAO;
import edu.uclm.esi.ds.webApp.dao.MatriculaDAO;
import edu.uclm.esi.ds.webApp.dao.MotoDAO;
import edu.uclm.esi.ds.webApp.dao.PatineteDAO;
import edu.uclm.esi.ds.webApp.dao.ReservaClienteDAO;
import edu.uclm.esi.ds.webApp.entities.Correo;
import edu.uclm.esi.ds.webApp.entities.Matricula;
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
	@Autowired 
	private CorreoDAO correoDAO;
	@Autowired
	private ConfigDAO configDAO;
	
	
	public void addReservaCliente(Map<String, Object> info) {
	    String email = info.get("email").toString();
	    List<ReservaCliente> reservas = this.reservaClienteDAO.findListByEmail(email);

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
		try {
			
			String email = info.get("cliente").toString();
			String matricula = info.get("vehiculo").toString();
			
			List<ReservaCliente> reservas = this.reservaClienteDAO.findListByEmail(email);
			Matricula m = this.matriculaDAO.findByMatricula(matricula);
			
			for (ReservaCliente reserva : reservas) {
				
			    if (reserva.getEstado().equals("reservado")) {
			        reserva.setEstado("cancelada");
			        this.reservaClienteDAO.save(reserva);
			    }
			}
			String tipo = m.getTipo().toString();
			
		    if(tipo.equals("Coche")) {
		    	Coche coche = this.cocheDAO.findByMatricula(matricula);
		    	coche.setEstado("disponible");
		    	this.cocheDAO.save(coche);
		    }
		    if(tipo.equals("Moto")) {
		    	Moto moto = this.motoDAO.findByMatricula(matricula);
		    	moto.setEstado("disponible");
		    	this.motoDAO.save(moto);
		    }
		    if(tipo.equals("Patinete")) {
		    	Patinete patinete = this.patineteDAO.findByMatricula(matricula);
		    	patinete.setEstado("disponible");
		    	this.patineteDAO.save(patinete);
		    }
		} catch (NullPointerException npe) {
		    npe.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
	}
	public List<ReservaCliente> listaReservasPorEmail(String email) {
		return reservaClienteDAO.findListByEmail(email);
	}

	public ReservaCliente obtenerReservaActivaPorEmail(String email) {
	    List<ReservaCliente> reservas = this.reservaClienteDAO.findListByEmail(email);
	    
	    for (int i = 0; i < reservas.size(); i++) {
	        ReservaCliente reserva = reservas.get(i);
	        
	        if ("reservado".equals(reserva.getEstado())) {
	            return reserva;
	        }
	    }
	    
	    // Devolver null si no se encuentra ninguna reserva en estado 'reservado'
	    return null;
	}
	public List<ReservaCliente> listaReservas() {
		return reservaClienteDAO.findAll();
	}

	public void AddValoracion(Map<String, Object> info) {
		int bateriaViaje =this.configDAO.findBynombre("bateriaViaje").getValor();
		int valorCarga =this.configDAO.findBynombre("bateriaRecarga").getValor();
		String email = info.get("cliente").toString();
		ReservaCliente reservaActiva = obtenerReservaActivaPorEmail(email);
		int valoracion = Integer.parseInt(info.get("estrellas").toString());
		String comentario = info.get("comentario").toString();
		String matricula= info.get("vehiculo").toString();
		
		reservaActiva.setValoracion(valoracion);
		reservaActiva.setValoracionText(comentario);
		reservaActiva.setEstado("finalizada");
		this.reservaClienteDAO.save(reservaActiva);
		
		Matricula m = this.matriculaDAO.findByMatricula(matricula);
		
		if(m.getTipo().equals("Coche")) {
			Coche coche = this.cocheDAO.findByMatricula(matricula);
			coche.setBateria(coche.getBateria()-this.configDAO.findBynombre("bateriaViaje").getValor());
			
			if(coche.getBateria()>=this.configDAO.findBynombre("bateriaRecarga").getValor()) {
				coche.setEstado("disponible");
			}
			this.cocheDAO.save(coche);
		}
		else if (m.getTipo().equals("Moto")) {
			Moto moto = this.motoDAO.findByMatricula(matricula);
			moto.setBateria(moto.getBateria()-this.configDAO.findBynombre("bateriaViaje").getValor());
			
			if(moto.getBateria()>=valorCarga) {
				moto.setEstado("disponible");
			}
			this.motoDAO.save(moto);
			
		}else if(m.getTipo().equals("Patinete")){
			Patinete patin = this.patineteDAO.findByMatricula(matricula);
			patin.setBateria(patin.getBateria()-this.configDAO.findBynombre("bateriaViaje").getValor());
			
			if(patin.getBateria()>=this.configDAO.findBynombre("bateriaRecarga").getValor()) {
				patin.setEstado("disponible");
			}
			this.patineteDAO.save(patin);
		}
		
		
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
