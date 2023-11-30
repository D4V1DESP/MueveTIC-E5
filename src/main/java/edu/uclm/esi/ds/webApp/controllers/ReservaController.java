package edu.uclm.esi.ds.webApp.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.entities.ReservaCliente;
import edu.uclm.esi.ds.webApp.entities.ReservaMantenimiento;
import edu.uclm.esi.ds.webApp.services.ReservaService;
import edu.uclm.esi.ds.webApp.services.VehicleService;


@RestController
@RequestMapping("reservas")
@CrossOrigin("*")
public class ReservaController {
	
	@Autowired
	private ReservaService reservaService;
	@Autowired 
	private VehicleService vehicleService;
	
	/*
	 * AÑADE UNA RESERVA DE CLIENTE Y ASIGNA UN VEHÍCULO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE CLIENTE.
	 */
	@PostMapping ("/usersAdd")
	@PreAuthorize("hasAnyAuthority('ROLE_CLIENTE')")
	public boolean AddClientReserve(@RequestBody Map<String, Object> info) {
		
		
		try {
			reservaService.addReservaCliente(info);
			vehicleService.reservarVehiculo(info);
		}catch(Exception e) {
			throw new ResponseStatusException (HttpStatus.CONFLICT);
		}
		return true;
	}
	
	/*
	 * CANCELA UNA RESERVA DE CLIENTE.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE CLIENTE.
	 */
	@PostMapping("/usersCancel")
	@PreAuthorize("hasAnyAuthority('ROLE_CLIENTE')")
	public boolean CancelarReserva(@RequestBody Map<String, Object> info) {
		
	
		
		try {
			reservaService.CancelUserReserve(info);
			
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		return true;
	}
	
	/*
	 * OBTIENE LA RESERVA ACTIVA DE UN CLIENTE POR SU CORREO ELECTRÓNICO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE CLIENTE.
	 */
	@GetMapping("/reservaActiva/{email}")
	@PreAuthorize("hasAnyAuthority('ROLE_CLIENTE')")
	public ResponseEntity<ReservaCliente> obtenerReservaActivaPorEmail(@PathVariable String email) {
	    ReservaCliente reserva = reservaService.obtenerReservaActivaPorEmail(email);
	    if (reserva != null) {
	        return new ResponseEntity<>(reserva, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	/*
	 * OBTIENE LAS RESERVAS DE UN CLIENTE POR SU CORREO ELECTRÓNICO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE CLIENTE.
	 */
	@GetMapping("/reservasCliente/{email}")
	@PreAuthorize("hasAnyAuthority('ROLE_CLIENTE')")
	public ResponseEntity<List<ReservaCliente>> obtenerReservasPorEmail(@PathVariable String email) {
	    List<ReservaCliente> reserva = reservaService.listaReservasPorEmail(email);
	    if (reserva != null) {
	        return new ResponseEntity<>(reserva, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	/*
	 * OBTIENE LAS RESERVAS DE MANTENIMIENTO POR EL CORREO ELECTRÓNICO DE UN MANTENIMIENTO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE MANTENIMIENTO.
	 */
	@GetMapping("/reservasMantenimiento/{email}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANTENIMIENTO')")
	public ResponseEntity<List<ReservaMantenimiento>> obtenerReservasMantenimiento(@PathVariable String email) {
		List<ReservaMantenimiento> reserva = reservaService.listaReservasMantenimiento(email);
		if (reserva != null) {
	        return new ResponseEntity<>(reserva, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	/*
	 * OBTIENE LA LISTA DE TODAS LAS RESERVAS.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR.
	 */
	@GetMapping("/listaReservas")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public List<ReservaCliente> obtenerReserva() {
	    return reservaService.listaReservas();
	}

	/*
	 * AÑADE UNA VALORACIÓN A UNA RESERVA.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE CLIENTE.
	 */
	@PostMapping("/AddReview")
	@PreAuthorize("hasAnyAuthority('ROLE_CLIENTE')")
	public boolean AñadirValoracion(@RequestBody Map<String,Object> info) {
		//metodo para cambiar estado a disponible y resta bateria
		try {
			reservaService.AddValoracion(info);
		}catch(Exception e) {
			throw new  ResponseStatusException(HttpStatus.CONFLICT);
		}
		return true;
	}
	
	/*
	 * AÑADE UNA RESERVA DE MANTENIMIENTO Y ASIGNA UN VEHÍCULO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE MANTENIMIENTO.
	 */
	@PostMapping ("/mantenimientoAdd")
	@PreAuthorize("hasAnyAuthority('ROLE_MANTENIMIENTO')")
	public boolean AddMantenimientoReserve(@RequestBody Map<String, Object> info) {
		
		
		try {
			reservaService.addReservaMantenimiento(info);
			vehicleService.reservarVehiculo(info);
		}catch(Exception e) {
			throw new ResponseStatusException (HttpStatus.CONFLICT);
		}
		return true;
	}
	
	/*
	 * CANCELA UNA RESERVA DE MANTENIMIENTO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE MANTENIMIENTO.
	 */
	@PostMapping("/mantenimientoCancel")
	@PreAuthorize("hasAnyAuthority('ROLE_MANTENIMIENTO')")
	public void cancelarMantenimiento(@RequestBody Map<String, Object> info) {
		try {
			reservaService.cancelMantenimiento(info);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
	}
	
	/*
	 * FINALIZA UNA RESERVA DE MANTENIMIENTO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE MANTENIMIENTO.
	 */
	@PostMapping("/mantenimientoFinalizar")
	@PreAuthorize("hasAnyAuthority('ROLE_MANTENIMIENTO')")
	public void finalizarMantenimiento(@RequestBody Map<String, Object> info) {
		try {
			reservaService.finalizarMantenimiento(info);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
	}
	
}
