package edu.uclm.esi.ds.webApp.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.dao.CocheDAO;
import edu.uclm.esi.ds.webApp.dao.ConfigDAO;
import edu.uclm.esi.ds.webApp.dao.MatriculaDAO;
import edu.uclm.esi.ds.webApp.dao.MotoDAO;
import edu.uclm.esi.ds.webApp.dao.PatineteDAO;
import edu.uclm.esi.ds.webApp.entities.Coche;
import edu.uclm.esi.ds.webApp.entities.Config;
import edu.uclm.esi.ds.webApp.entities.Correo;
import edu.uclm.esi.ds.webApp.entities.Matricula;
import edu.uclm.esi.ds.webApp.entities.Moto;
import edu.uclm.esi.ds.webApp.entities.Patinete;
import edu.uclm.esi.ds.webApp.entities.Vehiculo;
import edu.uclm.esi.ds.webApp.interfaces.ConstVehiculos;

@Service
public class VehicleService extends ConstVehiculos{
	
	@Autowired 
	private CocheDAO cocheDAO;
	@Autowired
	private MotoDAO motoDAO;
	@Autowired
	private PatineteDAO patineteDAO;
	@Autowired
	private MatriculaDAO matriculaDAO;
	@Autowired 
	private ConfigDAO configDAO;
	
	
	public void altaVehiculo(Map <String, Object> info) {
		
		String matricula = info.get(MATRICULA).toString();
		String tipo = info.get("tipo").toString();
		String direccion = info.get("direccion").toString();
		String modelo = info.get("modelo").toString();
		int bateria = Integer.parseInt(info.get("bateria").toString());
		String estado = info.get("estado").toString();
		
		Matricula matriculaVehiculo = new Matricula(matricula, tipo);
		this.matriculaDAO.save(matriculaVehiculo);
		
		if(info.get("tipo").equals(COCHE)) {
			int nPlazas = Integer.parseInt(info.get("nPlazas").toString());
	        Coche coche = new Coche(matricula, tipo, direccion, modelo, nPlazas, bateria, estado);
	        this.cocheDAO.save(coche);
	        
		} else if (info.get("tipo").equals(MOTO)){
			boolean casco = Boolean.parseBoolean(info.get("casco").toString());
	        Moto moto = new Moto(matricula, tipo, direccion, modelo, casco, bateria, estado);
	        this.motoDAO.save(moto);
	        
		} else if(info.get("tipo").equals(PATINETE)) {
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

	public void eliminarTipoVehiculo(Map<String, Object> info) {
		String matricula = info.get(MATRICULA).toString();
		String tipo = info.get("tipo").toString();
		
		this.matriculaDAO.deleteBymatricula(matricula);
		
		if(tipo.equals(COCHE)) {
			this.cocheDAO.deleteBymatricula(matricula);
		
		}else if (tipo.equals(MOTO)) {
			this.motoDAO.deleteBymatricula(matricula);
			
		}else if (tipo.equals(PATINETE)) {
			this.patineteDAO.deleteBymatricula(matricula);
		}
	}

	public List<Vehiculo> listaRecargables(String tipo) {
		
		Config config = this.configDAO.findBynombre("bateriaRecarga");
		
		List <Vehiculo> listaVehiculos = null;
		
		if (tipo.equals(COCHE)) {
			 listaVehiculos = this.cocheDAO.findByestado(DISPONIBLE);
		
		}else if (tipo.equals(MOTO)) {
			listaVehiculos = this.motoDAO.findByestado(DISPONIBLE);
		
		}else if (tipo.equals(PATINETE)) {
			listaVehiculos = this.patineteDAO.findByestado(DISPONIBLE);
		}
		
		if(listaVehiculos == null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		
		for (int i = 0; i < listaVehiculos.size(); i++) {
			  if(listaVehiculos.get(i).getBateria() >= config.getValor()) {	
				  listaVehiculos.remove(i);
			  }
			}
		return listaVehiculos;
	}
	

	public void recargaVehiculo(Map<String, Object> info) {
		String tipo = info.get("tipo").toString();
		String matricula = info.get(MATRICULA).toString();
		
		if(tipo.equals(COCHE)) {
			Coche coche = this.cocheDAO.findByMatricula(matricula);
			coche.setBateria(RECARGA);
			this.cocheDAO.save(coche);
			
		}else if (tipo.equals(MOTO)) {
			Moto moto = this.motoDAO.findByMatricula(matricula);
			moto.setBateria(RECARGA);
			this.motoDAO.save(moto);
		
		}else if (tipo.equals(PATINETE)) {
			Patinete patinete = this.patineteDAO.findByMatricula(matricula);
			patinete.setBateria(RECARGA);
			this.patineteDAO.save(patinete);
		}
	}

	public List<Vehiculo> listaCochesDisponibles() {
		Config config = this.configDAO.findBynombre(BATERIAVIAJE);
        return filtrarPorBateria(this.cocheDAO.findByestado(DISPONIBLE), config);
    }

	public List<Vehiculo> listaMotosDisponibles() {
		Config config = this.configDAO.findBynombre(BATERIAVIAJE);
		return filtrarPorBateria(this.motoDAO.findByestado(DISPONIBLE), config);
	}

	public List<Vehiculo> listaPatinetesDisponibles() {
		Config config = this.configDAO.findBynombre(BATERIAVIAJE);
		return filtrarPorBateria(this.patineteDAO.findByestado(DISPONIBLE), config);
	}
	
	public void reservarVehiculo(Map<String, Object> info) {
		Matricula m = this.matriculaDAO.findByMatricula((String) info.get(MATRICULA));
		
		Coche coche = this.cocheDAO.findByMatricula(m.getMatricula());
			if (coche != null) {
				if(!coche.getEstado().equals(NO_DISPONIBLE)) {
				coche.setEstado(NO_DISPONIBLE);
				this.cocheDAO.save(coche);
				}else {
					throw new ResponseStatusException (HttpStatus.CONFLICT);
				}
			}

			Moto moto = this.motoDAO.findByMatricula(m.getMatricula());
			if (moto != null) {
				if(!moto.getEstado().equals(NO_DISPONIBLE)) {
				moto.setEstado(NO_DISPONIBLE);
				this.motoDAO.save(moto);
				}else {
					throw new ResponseStatusException (HttpStatus.CONFLICT);
				}
			}

			Patinete patinete = this.patineteDAO.findByMatricula(m.getMatricula());
			if (patinete != null) {
				if(!patinete.getEstado().equals(NO_DISPONIBLE)) {
				patinete.setEstado(NO_DISPONIBLE);
				this.patineteDAO.save(patinete);
				}else {
					throw new ResponseStatusException (HttpStatus.CONFLICT);
				}
			}
	}
	
	public List <Vehiculo> filtrarPorBateria(List <Vehiculo> listaVehiculos, Config config){
		for (int i = 0; i < listaVehiculos.size(); i++) {
			  if(listaVehiculos.get(i).getBateria() <= config.getValor()) {	
				  listaVehiculos.remove(i);
			  }
			}
		return listaVehiculos;
	}

}
