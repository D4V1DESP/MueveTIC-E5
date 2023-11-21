package edu.uclm.esi.ds.webApp.controllers;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.entities.Coche;
import edu.uclm.esi.ds.webApp.entities.Moto;
import edu.uclm.esi.ds.webApp.entities.Patinete;
import edu.uclm.esi.ds.webApp.services.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@RestController
@RequestMapping("vehiculos")
@CrossOrigin({"*"})

public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	@CrossOrigin("*")
	@PostMapping("/alta")
	public void altaVehiculo(@RequestBody Map<String, Object> info) {
		
				try {
					vehicleService.altaVehiculo(info);
				} catch(DataIntegrityViolationException e) {
					throw new ResponseStatusException (HttpStatus.CONFLICT);
			}
	}
	
	@GetMapping("/coches")
	public List<Coche> listaCoche() {	
			return vehicleService.listaCoches();
	}
	
	@GetMapping("/motos")
	public List <Moto> listaMoto() {
			return vehicleService.listaMotos();
	}
	
	@GetMapping("/patinetes")
	public List <Patinete> listaPatinete(){
		return vehicleService.listaPatinetes();
	}
	@PostMapping("/reservar")
	public void reservarVehiculo(@RequestBody Map<String, Object> info) {
		vehicleService.reservarVehiculo(info);
	}
	
	@PostMapping("/eliminar")
	public void eliminarVehiculo(@RequestBody Map<String, Object> info) {
		
		vehicleService.eliminarTipoVehiculo(info);
	}
	
	@GetMapping("/coches/disponibles")
	public List <Coche> listaCocheDisponible(){
		return vehicleService.listaCochesDisponibles();
	}
	@GetMapping("/motos/disponibles")
	public List <Moto> listaMotoDisponible(){
		return vehicleService.listaMotosDisponibles();
	}
	@GetMapping("/patinetes/disponibles")
	public List <Patinete> listaPatineteDisponible(){
		return vehicleService.listaPatinetesDisponibles();
	}
	
}
