package edu.uclm.esi.ds.webApp.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.services.ReservaService;


@RestController
@RequestMapping("reservas")
@CrossOrigin("*")
public class ReservaController {
	
	@Autowired
	private ReservaService reservaService;
	
	@PostMapping ("/usersAdd")
	public boolean AddClientReserve(@RequestBody Map<String, Object> info) {
		try {
			reservaService.addReservaCliente(info);
		}catch(Exception e) {
			throw new ResponseStatusException (HttpStatus.CONFLICT);
		}
		return true;
	}
}
