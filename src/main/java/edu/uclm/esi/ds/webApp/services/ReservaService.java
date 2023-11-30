package edu.uclm.esi.ds.webApp.services;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.dao.CorreoDAO;
import edu.uclm.esi.ds.webApp.dao.MatriculaDAO;
import edu.uclm.esi.ds.webApp.dao.CocheDAO;
import edu.uclm.esi.ds.webApp.dao.ConfigDAO;
import edu.uclm.esi.ds.webApp.dao.MatriculaDAO;
import edu.uclm.esi.ds.webApp.dao.MotoDAO;
import edu.uclm.esi.ds.webApp.dao.PatineteDAO;
import edu.uclm.esi.ds.webApp.dao.ReservaClienteDAO;
import edu.uclm.esi.ds.webApp.dao.ReservaMantenimientoDAO;
import edu.uclm.esi.ds.webApp.entities.Correo;
import edu.uclm.esi.ds.webApp.entities.Matricula;
import edu.uclm.esi.ds.webApp.entities.Coche;
import edu.uclm.esi.ds.webApp.entities.Matricula;
import edu.uclm.esi.ds.webApp.entities.Moto;
import edu.uclm.esi.ds.webApp.entities.Patinete;
import edu.uclm.esi.ds.webApp.entities.ReservaCliente;
import edu.uclm.esi.ds.webApp.entities.ReservaMantenimiento;
import edu.uclm.esi.ds.webApp.entities.Vehiculo;
import edu.uclm.esi.ds.webApp.interfaces.ConstReservas;
import edu.uclm.esi.ds.webApp.interfaces.ConstVehiculos;

@Service
public class ReservaService extends ConstReservas {

	@Autowired
	private ReservaClienteDAO reservaClienteDAO;
	@Autowired
	private MatriculaDAO matriculaDAO;
	@Autowired
	private CocheDAO cocheDAO;
	@Autowired
	private MotoDAO motoDAO;
	@Autowired
	private PatineteDAO patineteDAO;
	@Autowired
	private CorreoDAO correoDAO;
	@Autowired
	private ConfigDAO configDAO;
	@Autowired
	private ReservaMantenimientoDAO reservaMantenimientoDAO;

	public void addReservaCliente(Map<String, Object> info) {
		String email = info.get(EMAIL).toString();
		String vehiculo = (String) info.get(MATRICULA);

		List<ReservaCliente> reservas = this.reservaClienteDAO.findListByEmail(email);

		if (this.correoDAO.findByEmail(email) == null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		if (this.matriculaDAO.findByMatricula(vehiculo) == null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		boolean tieneReservaEnEstadoReservado = false;

		for (ReservaCliente reserva : reservas) {
			if (reserva.getEstado().equals(RESERVADO)) {
				tieneReservaEnEstadoReservado = true;
				break;
			}
		}

		if (!tieneReservaEnEstadoReservado) {
			// No hay reservas en estado "reservado", se puede añadir la nueva reserva
			Calendar c = Calendar.getInstance();
			String fecha = Integer.toString(c.get(Calendar.DATE)) + "/" + Integer.toString(c.get(Calendar.MONTH) + 1)
					+ "/" + Integer.toString(c.get(Calendar.YEAR));

			ReservaCliente newReserva = new ReservaCliente(email, vehiculo, fecha);
			this.reservaClienteDAO.save(newReserva);
		} else {
			// Ya tiene una reserva en estado "reservado"
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
	}
	/*
	 * METODO: CANCELUSERRESERVE
	 * DESCRIPCION: ESTE METODO PRIMERO ENCUENTRA LA RESERVA ACTIVA DE UN CLIENTE DADO Y DESPUES PONE ESTA EN ESTADO CANCELADO 
	 */


	public void CancelUserReserve(Map<String, Object> info) {

		String email = info.get("cliente").toString();
		String matricula = info.get("vehiculo").toString();

		List<ReservaCliente> reservas = this.reservaClienteDAO.findListByEmail(email);
		Matricula m = this.matriculaDAO.findByMatricula(matricula);

		for (ReservaCliente reserva : reservas) {
			if (reserva.getEstado().equals(RESERVADO)) {
				reserva.setEstado("cancelada");
				this.reservaClienteDAO.save(reserva);
			}
		}
		String tipo = m.getTipo().toString();

		if (tipo.equals(COCHE)) {
			Coche coche = this.cocheDAO.findByMatricula(matricula);
			coche.setEstado(DISPONIBLE);
			this.cocheDAO.save(coche);
		}
		if (tipo.equals("Moto")) {
			Moto moto = this.motoDAO.findByMatricula(matricula);
			moto.setEstado(DISPONIBLE);
			this.motoDAO.save(moto);
		}
		if (tipo.equals(PATINETE)) {
			Patinete patinete = this.patineteDAO.findByMatricula(matricula);
			patinete.setEstado(DISPONIBLE);
			this.patineteDAO.save(patinete);
		}

	}

	public List<ReservaCliente> listaReservasPorEmail(String email) {
		return reservaClienteDAO.findListByEmail(email);
	}
	/*
	 * METODO: OBTENER RESERVA ACTIVA POR EMAIL
	 * DESCRIPCION: ESTE METODO OBTIENE LA RESERVA ACTIVA DE UN CLIENTE (EMAIL DE LA ENTRADA DE ARGUMENTOS) Y LO DEVUELVE EN CASO DE QUE ESTA EXISTA
	 */


	public ReservaCliente obtenerReservaActivaPorEmail(String email) {
		List<ReservaCliente> reservas = this.reservaClienteDAO.findListByEmail(email);

		for (int i = 0; i < reservas.size(); i++) {
			ReservaCliente reserva = reservas.get(i);

			if (RESERVADO.equals(reserva.getEstado())) {
				return reserva;
			}
		}

		// Devolver null si no se encuentra ninguna reserva en estado 'reservado'
		return null;
	}

	public List<ReservaCliente> listaReservas() {
		return reservaClienteDAO.findAll();
	}
	public List<ReservaMantenimiento> listaReservasMantenimiento(String email) {
		return reservaMantenimientoDAO.findListByEmail(email);
	}

	/*
	 * METODO: ADDVALORACION
	 * DESCRIPCION: ESTE METODO ES EL ENCARGADO DE FINALIZAR LA RESERVA DE FORMA CORRECTA Y DE PONER TANTO LA VALORACION COMO EL COMENTARIO DE LA 
	 * RESERVA.
	 */

	public void AddValoracion(Map<String, Object> info) {
		int bateriaViaje = this.configDAO.findBynombre("bateriaViaje").getValor();
		int valorCarga = this.configDAO.findBynombre("bateriaRecarga").getValor();
		String email = info.get("cliente").toString();
		ReservaCliente reservaActiva = obtenerReservaActivaPorEmail(email);
		int valoracion = Integer.parseInt(info.get("estrellas").toString());
		String comentario = info.get("comentario").toString();
		String matricula = info.get("vehiculo").toString();

		reservaActiva.setValoracion(valoracion);
		reservaActiva.setValoracionText(comentario);
		reservaActiva.setEstado("finalizada");
		this.reservaClienteDAO.save(reservaActiva);

		Matricula m = this.matriculaDAO.findByMatricula(matricula);

		if (m.getTipo().equals(COCHE)) {
			Coche coche = this.cocheDAO.findByMatricula(matricula);
			coche.setBateria(coche.getBateria() - bateriaViaje);

			if (coche.getBateria() < valorCarga) {
				coche.setEstado(DESCARGADO);
			}else {
				coche.setEstado(DISPONIBLE);
			}
			
			this.cocheDAO.save(coche);
		} else if (m.getTipo().equals("Moto")) {
			Moto moto = this.motoDAO.findByMatricula(matricula);
			moto.setBateria(moto.getBateria() - bateriaViaje);

			if (moto.getBateria() < valorCarga) {
				moto.setEstado(DESCARGADO);
			}
			else {
				moto.setEstado(DISPONIBLE);
			}
			this.motoDAO.save(moto);

		} else if (m.getTipo().equals(PATINETE)) {
			Patinete patin = this.patineteDAO.findByMatricula(matricula);
			patin.setBateria(patin.getBateria() - bateriaViaje);

			if (patin.getBateria() < valorCarga) {
				patin.setEstado(DESCARGADO);
			}
			else {
				patin.setEstado(DISPONIBLE);
			}
			this.patineteDAO.save(patin);
		}

	}

	/*
	 * METODO: CHECKUSER
	 * DESCRIPCION: ESTE METODO COMPRUEBA SI UN USUARIO EXISTE EN EL SISTEMA.
	 */
	public boolean checkUser(String email) {
		boolean checked = false;
		List<Correo> lstUser = this.correoDAO.findAll();
		for (Correo c : lstUser) {
			if (c.getEmail().equals(email)) {
				checked = true;
			}
		}

		return checked;
	}
	/*
	 * METODO: CHECKVEHICLE
	 * DESCRIPCION: ESTE METODO COMPRUEBA SI UN VEHICULO EXISTE EN EL SISTEMA.
	 */
	public boolean checkVehicle(String matricula) {
		boolean checked = false;
		List<Matricula> lstvehicles = this.matriculaDAO.findAll();
		for (Matricula m : lstvehicles) {
			if (m.getMatricula().equals(matricula)) {
				checked = true;
			}
		}

		return checked;
	}
	/*
	 * METODO: ADDRESERVAMANTENIMIENTO
	 * DESCRIPCION: Añade una reserva de mantenimiento al sistema.
	 * PARAMETROS:
	 *   - info: Mapa que contiene la información necesaria para la reserva (EMAIL, MATRICULA).
	 * EXCEPCIONES LANZADAS:
	 *   - ResponseStatusException con HttpStatus.CONFLICT si ocurren conflictos, como email o matrícula no existentes,
	 *     o si el límite de reservas en estado "reservado" se ha alcanzado.
	 */
	public void addReservaMantenimiento(Map<String, Object> info) {
		String email = info.get(EMAIL).toString();
		String vehiculo = (String) info.get(MATRICULA);

		List<ReservaMantenimiento> reservas = this.reservaMantenimientoDAO.findListByEmail(email);

		if (this.correoDAO.findByEmail(email) == null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		if (this.matriculaDAO.findByMatricula(vehiculo) == null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		boolean tieneReservaEnEstadoReservado = true;

		if (reservas.size() >= this.configDAO.findBynombre("vehiculosMantenimiento").getValor()) {
			tieneReservaEnEstadoReservado = false;
		}

		if (tieneReservaEnEstadoReservado) {
			// No hay reservas en estado "reservado", se puede añadir la nueva reserva
			Calendar c = Calendar.getInstance();
			String fecha = Integer.toString(c.get(Calendar.DATE)) + "/" + Integer.toString(c.get(Calendar.MONTH) + 1)
					+ "/" + Integer.toString(c.get(Calendar.YEAR));

			ReservaMantenimiento newReserva = new ReservaMantenimiento(email, vehiculo);
			this.reservaMantenimientoDAO.save(newReserva);
		} else {
			// Ya tiene una reserva en estado "reservado"
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
	}
	/*
	 * METODO: FINALIZARMANTENIMIENTO
	 * DESCRIPCION: Finaliza el mantenimiento de un vehículo y actualiza su estado y batería.
	 * PARAMETROS:
	 *   - info: Mapa que contiene la información necesaria para finalizar el mantenimiento (MATRICULA).
	 * ACCIONES REALIZADAS:
	 *   - Busca la reserva de mantenimiento asociada a la matrícula proporcionada.
	 *   - Obtiene el tipo de vehículo mediante la matrícula.
	 *   - Si el tipo es COCHE, actualiza la batería y estado del coche.
	 *   - Si el tipo es MOTO, actualiza la batería y estado de la moto.
	 *   - Si el tipo es PATINETE, actualiza la batería y estado del patinete.
	 *   - Elimina la reserva de mantenimiento asociada al vehículo.
	 */
	public void finalizarMantenimiento(Map<String, Object> info) {
		String matricula = info.get(MATRICULA).toString();

		ReservaMantenimiento reserva = this.reservaMantenimientoDAO.findByMatricula(matricula);

		Matricula matriculaVehiculo = this.matriculaDAO.findByMatricula(matricula);
		if (matriculaVehiculo.getTipo().equals(COCHE)) {
			Coche coche = this.cocheDAO.findByMatricula(matricula);
			coche.setBateria(100);
			coche.setEstado(DISPONIBLE);
			this.cocheDAO.save(coche);
		} else if (matriculaVehiculo.getTipo().equals("Moto")) {
			Moto moto = this.motoDAO.findByMatricula(matricula);
			moto.setBateria(100);
			moto.setEstado(DISPONIBLE);
			this.motoDAO.save(moto);
		} else if (matriculaVehiculo.getTipo().equals(PATINETE)) {
			Patinete patinete = this.patineteDAO.findByMatricula(matricula);
			patinete.setBateria(100);
			patinete.setEstado(DISPONIBLE);
			this.patineteDAO.save(patinete);
		}
		this.reservaMantenimientoDAO.delete(reserva);
	}
	/*
	 * METODO: CANCELMANTENIMIENTO
	 * DESCRIPCION: Cancela el mantenimiento de un vehículo y actualiza su estado.
	 * PARAMETROS:
	 *   - info: Mapa que contiene la información necesaria para cancelar el mantenimiento (MATRICULA).
	 * ACCIONES:
	 *   - Recupera la reserva de mantenimiento asociada a la matrícula proporcionada.
	 *   - Obtiene el tipo de vehículo a partir de la matrícula y actualiza su estado a DESCARGADO.
	 *   - Elimina la reserva de mantenimiento correspondiente.
	 */
	public void cancelMantenimiento(Map<String, Object> info) {
		String matricula = info.get(MATRICULA).toString();

		ReservaMantenimiento reserva = this.reservaMantenimientoDAO.findByMatricula(matricula);

		Matricula matriculaVehiculo = this.matriculaDAO.findByMatricula(matricula);
		if (matriculaVehiculo.getTipo().equals(COCHE)) {
			Coche coche = this.cocheDAO.findByMatricula(matricula);
			coche.setEstado(DESCARGADO);
			this.cocheDAO.save(coche);
		} else if (matriculaVehiculo.getTipo().equals("Moto")) {
			Moto moto = this.motoDAO.findByMatricula(matricula);
			moto.setEstado(DESCARGADO);
			this.motoDAO.save(moto);
		} else if (matriculaVehiculo.getTipo().equals(PATINETE)) {
			Patinete patinete = this.patineteDAO.findByMatricula(matricula);
			patinete.setEstado(DESCARGADO);
			this.patineteDAO.save(patinete);
		}
		this.reservaMantenimientoDAO.delete(reserva);
		this.reservaMantenimientoDAO.delete(reserva);

	}
}
