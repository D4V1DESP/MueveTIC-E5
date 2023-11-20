package edu.uclm.esi.ds.webApp.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.services.ConfigService;

@RestController
@RequestMapping("config")
@CrossOrigin("*")
public class ConfigController {
	
	@Autowired
	ConfigService configservice;
	
	@PostMapping("/Add")
	public boolean AddConfig(Map<String,Object>info) {
		try {
			this.configservice.addNewConfig(info);
		}catch(Exception e) {
			throw new ResponseStatusException (HttpStatus.CONFLICT);
		}
		return true;
		
	}
	@PostMapping("/update")
	public boolean UpdateConfig(Map<String,Object>info) {
		
		try {
			this.configservice.updateConfig(info);
		}catch(Exception e) {
			throw new ResponseStatusException (HttpStatus.CONFLICT);
		}
		return true;
	}

}
