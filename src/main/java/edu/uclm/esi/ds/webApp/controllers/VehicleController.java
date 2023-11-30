package edu.uclm.esi.ds.webApp.controllers;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.entities.Coche;
import edu.uclm.esi.ds.webApp.entities.Moto;
import edu.uclm.esi.ds.webApp.entities.Patinete;
import edu.uclm.esi.ds.webApp.entities.Vehiculo;
import edu.uclm.esi.ds.webApp.services.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@RestController
@RequestMapping("vehiculos")
@CrossOrigin({"*"})
public class VehicleController {
	
	@Autowired
	private VehicleService vehicleService;
	
	/*
	 * DA DE ALTA UN NUEVO VEHÍCULO EN EL SISTEMA.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR.
	 */
	@CrossOrigin("*")
	@PostMapping("/alta")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public void altaVehiculo(@RequestBody Map<String, Object> info) {
		
				try {
					vehicleService.altaVehiculo(info);
				} catch(DataIntegrityViolationException e) {
					throw new ResponseStatusException (HttpStatus.CONFLICT);
			}
	}
	
	/*
	 * OBTIENE LA LISTA DE COCHES.
	 * ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR, MANTENIMIENTO Y CLIENTE.
	 */
	@GetMapping("/coches")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANTENIMIENTO', 'ROLE_CLIENTE')")
	public List<Coche> listaCoche() {	
			return vehicleService.listaCoches();
	}
	
	/*
	 * OBTIENE LA LISTA DE MOTOS.
	 * ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR, MANTENIMIENTO Y CLIENTE.
	 */
	@GetMapping("/motos")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANTENIMIENTO', 'ROLE_CLIENTE')")
	public List <Moto> listaMoto() {
			return vehicleService.listaMotos();
	}
	
	/*
	 * OBTIENE LA LISTA DE PATINETES.
	 * ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR, MANTENIMIENTO Y CLIENTE.
	 */
	@GetMapping("/patinetes")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANTENIMIENTO', 'ROLE_CLIENTE')")
	public List <Patinete> listaPatinete(){
		return vehicleService.listaPatinetes();
	}
	
	/*
	 * RESERVA UN VEHÍCULO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE CLIENTE.
	 */
	@PostMapping("/reservar")
	@PreAuthorize("hasAnyAuthority('ROLE_CLIENTE')")
	public void reservarVehiculo(@RequestBody Map<String, Object> info) {
		vehicleService.reservarVehiculo(info);
	}
	
	/*
	 * ELIMINA UN TIPO DE VEHÍCULO DEL SISTEMA.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR.
	 */
	@PostMapping("/eliminar")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public void eliminarVehiculo(@RequestBody Map<String, Object> info) {
		
		vehicleService.eliminarTipoVehiculo(info);
	}
	
	/*
	 * OBTIENE LA LISTA DE COCHES DISPONIBLES.
	 * ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR, MANTENIMIENTO Y CLIENTE.
	 */
	@GetMapping("/coches/disponibles")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANTENIMIENTO', 'ROLE_CLIENTE')")
	public List <Vehiculo> listaCocheDisponible(){
		return vehicleService.listaCochesDisponibles();
	}
	
	/*
	 * OBTIENE LA LISTA DE MOTOS DISPONIBLES.
	 * ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR, MANTENIMIENTO Y CLIENTE.
	 */
	@GetMapping("/motos/disponibles")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANTENIMIENTO', 'ROLE_CLIENTE')")
	public List <Vehiculo> listaMotoDisponible(){
		return vehicleService.listaMotosDisponibles();
	}
	
	/*
	 * OBTIENE LA LISTA DE PATINETES DISPONIBLES.
	 * ACCESIBLE PARA USUARIOS CON ROL DE ADMINISTRADOR, MANTENIMIENTO Y CLIENTE.
	 */
	@GetMapping("/patinetes/disponibles")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANTENIMIENTO', 'ROLE_CLIENTE')")
	@CrossOrigin("*")
	public List <Vehiculo> listaPatineteDisponible(){
		return vehicleService.listaPatinetesDisponibles();
	}
	
	/*
	 * OBTIENE LA LISTA DE VEHÍCULOS RECARGABLES DE UN TIPO DADO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE MANTENIMIENTO.
	 */
	@GetMapping("/recargables/{tipo}")
	@PreAuthorize("hasAuthority('ROLE_MANTENIMIENTO')")
	public List<Vehiculo> listaVehiculosRecargables(@PathVariable String tipo) {
		
		return vehicleService.listaRecargables(tipo);
	}
	
	/*
	 * REALIZA LA RECARGA DE UN VEHÍCULO.
	 * SOLO ACCESIBLE PARA USUARIOS CON ROL DE MANTENIMIENTO.
	 */
	@PutMapping("/recargar")
	@PreAuthorize("hasAuthority('ROLE_MANTENIMIENTO')")
	public void recargaVehiculo(@RequestBody Map<String, Object> info) {
		
			vehicleService.recargaVehiculo(info);
	}
}
