package edu.uclm.esi.ds.webApp.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uclm.esi.ds.webApp.dao.CocheDAO;
import edu.uclm.esi.ds.webApp.dao.MotoDAO;
import edu.uclm.esi.ds.webApp.dao.PatineteDAO;
import edu.uclm.esi.ds.webApp.entities.Coche;
import edu.uclm.esi.ds.webApp.entities.Moto;
import edu.uclm.esi.ds.webApp.entities.Patinete;

@Service
public class VehicleService {
	
	@Autowired 
	private CocheDAO cocheDAO;
	@Autowired
	private MotoDAO motoDAO;
	@Autowired
	private PatineteDAO patineteDAO;
	
	
	public void altaVehiculo(Map <String, Object> info) {
		String matricula = info.get("matricula").toString();
		String tipo = info.get("tipo").toString();
		String direccion = info.get("direccion").toString();
		String modelo = info.get("modelo").toString();
		int bateria = Integer.parseInt(info.get("bateria").toString());
		String estado = info.get("estado").toString();
		
		if(info.get("tipo").equals("Coche")) {
			int nPlazas = Integer.parseInt(info.get("nPlazas").toString());
	        Coche coche = new Coche(matricula, tipo, direccion, modelo, nPlazas, bateria, estado);
	        this.cocheDAO.save(coche);
	        
		} else if (info.get("tipo").equals("Moto")){
			boolean casco = Boolean.parseBoolean(info.get("casco").toString());
	        Moto moto = new Moto(matricula, tipo, direccion, modelo, casco, bateria, estado);
	        this.motoDAO.save(moto);
	        
		} else if(info.get("tipo").equals("Patinete")) {
			String color = info.get("color").toString();
	        Patinete patinete = new Patinete(matricula, tipo, direccion, modelo, color, bateria, estado);
	        this.patineteDAO.save(patinete);
		}
	}

	public List <Coche> listaCoches() {
		return this.cocheDAO.findAll();
	}
	
	public List <Moto> listaMotos() {
		return this.motoDAO.findAll();
	}
	
	public List <Patinete> listaPatinetes(){
		return this.patineteDAO.findAll();
	}
}
