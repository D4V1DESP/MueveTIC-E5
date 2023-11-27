package edu.uclm.esi.ds.webApp.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.entities.Config;
import edu.uclm.esi.ds.webApp.entities.Mantenimiento;
import edu.uclm.esi.ds.webApp.services.ConfigService;

@RestController
@RequestMapping("config")
@CrossOrigin("*")
public class ConfigController {
	
	@Autowired
	ConfigService configservice;
	
	@PostMapping("/Add")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public boolean AddConfig(@RequestBody  Map<String,Object>info) {
		try {
			this.configservice.addNewConfig(info);
		}catch(Exception e) {
			throw new ResponseStatusException (HttpStatus.CONFLICT);
		}
		return true;
		
	}
	@PostMapping("/Update")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public boolean UpdateConfig(@RequestBody Map<String,Object>info) {
		
		try {
			this.configservice.updateConfig(info);
		}catch(Exception e) {
			throw new ResponseStatusException (HttpStatus.CONFLICT);
		}
		return true;
	}
	
	@GetMapping("/Get")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public List<Config> listaConfiguracion(){
		return configservice.getConfigs();
	} 

}
