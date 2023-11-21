package edu.uclm.esi.ds.webApp.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.entities.ReservaCliente;
import edu.uclm.esi.ds.webApp.services.ReservaService;
import edu.uclm.esi.ds.webApp.services.VehicleService;


@RestController
@RequestMapping("reservas")
@CrossOrigin({"*"})
public class ReservaController {
	
	@Autowired
	private ReservaService reservaService;
	@Autowired 
	private VehicleService vehicleService;
	
	@PostMapping ("/usersAdd")
	public boolean AddClientReserve(@RequestBody Map<String, Object> info) {
		vehicleService.reservarVehiculo(info);
		
		try {
			reservaService.addReservaCliente(info);
		}catch(Exception e) {
			throw new ResponseStatusException (HttpStatus.CONFLICT);
		}
		return true;
	}
	
	@PostMapping("/usersCancel")
	public boolean CancelarReserva(@RequestBody Map<String, Object> info) {
		System.out.println("hola");
		//metodo para cambiar estado a disponible 
		
		try {
			reservaService.CancelUserReserve(info);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		return true;
	}
	
	@GetMapping("/reservaActiva/{email}")
	public ResponseEntity<List<ReservaCliente>> obtenerReservaPorEmail(@PathVariable String email) {
	    List<ReservaCliente> reserva = reservaService.obtenerReservaPorEmail(email);
	    if (reserva != null && !reserva.isEmpty()) {
	        return new ResponseEntity<>(reserva, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	@GetMapping("/listaReservas")
	public List<ReservaCliente> obtenerReserva() {
	    return reservaService.listaReservas();
	}

}
