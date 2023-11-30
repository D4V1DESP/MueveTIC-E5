package edu.uclm.esi.ds.webApp.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uclm.esi.ds.webApp.dao.ConfigDAO;
import edu.uclm.esi.ds.webApp.entities.Config;

@Service
public class ConfigService {
	
	@Autowired
	ConfigDAO configDAO;
	/*
	 * METODO UOPDATECONFIG 
	 * RECIBE COMO ARGUMENTO UN MAPA EL CUAL SIEMPRE VA A CONTENER DOS VALORES, EL NOMBRE DE LA VARIABLE A MODIFICAR Y EL NUEVO VALOR
	 * PRIMERO COMPRUEBA QUE EXISTE Y DESPUES ACTUALIZA SU VALOR EN BBDD.
	 */
	public void updateConfig(Map<String,Object>info) {
		String variable = info.get("nombre").toString();
		Config config = this.configDAO.findBynombre(variable);
		
		config.setValor(Integer.parseInt(info.get("valor").toString()));
		this.configDAO.save(config);
		
	}
	
	public void addNewConfig(Map<String, Object>info) {
		System.out.println(info);
		String variable = info.get("nombre").toString();
		int valor = Integer.parseInt(info.get("valor").toString());
		
		Config config = new Config(variable,valor);
		this.configDAO.save(config);
	}
	
	public List<Config> getConfigs() {
		return configDAO.findAll();
	}
}
