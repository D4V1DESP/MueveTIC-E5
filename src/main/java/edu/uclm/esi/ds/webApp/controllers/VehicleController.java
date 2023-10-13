package edu.uclm.esi.ds.webApp.controllers;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.services.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@RestController
@RequestMapping("vehicles")
@CrossOrigin("*")

public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	@PostMapping("/alta")
	public boolean altaVehiculo(@RequestBody Map<String, Object> info) {
		String[] keysJson = JSONObject.getNames(info);
		
		for(String key : keysJson) {
			if(!info.get(key).toString().isBlank()) {
				try {
					vehicleService.altaVehiculo(info);
				} catch(DataIntegrityViolationException e) {
					throw new ResponseStatusException (HttpStatus.CONFLICT);
				}
			} else if(key.equals("Color")) {
				try {
					vehicleService.altaVehiculo(info);
				} catch(DataIntegrityViolationException e) {
					throw new ResponseStatusException (HttpStatus.CONFLICT);
				}
			} else {
				throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "El campo" + key + " esta vacio");
			}
		}
		return true;
	}
	
	
}
