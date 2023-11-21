package edu.uclm.esi.ds.webApp.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.dao.AdminDAO;
import edu.uclm.esi.ds.webApp.dao.ClienteDAO;
import edu.uclm.esi.ds.webApp.dao.CorreoDAO;
import edu.uclm.esi.ds.webApp.dao.MantenimientoDAO;
import edu.uclm.esi.ds.webApp.dao.ReservaClienteDAO;
import edu.uclm.esi.ds.webApp.dao.TokenRecoverDAO;
import edu.uclm.esi.ds.webApp.entities.Admin;
import edu.uclm.esi.ds.webApp.entities.Mantenimiento;
import edu.uclm.esi.ds.webApp.entities.ReservaCliente;
import edu.uclm.esi.ds.webApp.entities.Cliente;
import edu.uclm.esi.ds.webApp.entities.Correo;
import edu.uclm.esi.ds.webApp.entities.Usuario;
import edu.uclm.esi.ds.webApp.entities.TokenRecover;

@Service
public class UserService {

	@Autowired
	private ClienteDAO clientedao;
	@Autowired
	private AdminDAO admindao;
	@Autowired
	private MantenimientoDAO mandao;
	@Autowired
	private CorreoDAO correodao;
	@Autowired 
	private ReservaClienteDAO reservadao;
	@Autowired
	private TokenRecoverDAO tokenRecoverDAO;

	String adminS = "admin";
	String apellidosS = "apellidos";
	String ciudadS = "ciudad";
	String clienteS = "cliente";
	String credencialesS = "credenciales invalidas";
	String emailS = "email";
	String mantenimientoS = "mantenimiento";
	String nombreS = "nombre";

	public void Alta(Map<String, Object> info) {
		String email = info.get(emailS).toString();
		String nombre = info.get(nombreS).toString();
		String apellidos = info.get(apellidosS).toString();
		String dni = info.get("dni").toString();
		String contrasena = info.get("contrasena").toString();
		String repetircontrasena = info.get("repetirContrasena").toString();
		String pwdEncripted = org.apache.commons.codec.digest.DigestUtils.sha512Hex(contrasena);
		String rpwdEncripted = org.apache.commons.codec.digest.DigestUtils.sha512Hex(repetircontrasena);
		boolean activo = Boolean.parseBoolean(info.get("activo").toString());
		try {
			Correo c = new Correo(email, info.get("tipo").toString());
			correodao.save(c);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		String tipo = info.get("tipo").toString();

		if (tipo.equals(adminS)) {

			Admin a1 = new Admin(email, dni, nombre, apellidos, pwdEncripted, rpwdEncripted,
					info.get(ciudadS).toString(), activo, tipo);
			this.admindao.save(a1);

		} else if (tipo.equals(mantenimientoS)) {

			int experiencia = Integer.parseInt(info.get("experiencia").toString());
			Mantenimiento m1 = new Mantenimiento(email, dni, nombre, apellidos, pwdEncripted, rpwdEncripted,
					info.get("ciudad").toString(), activo, experiencia, tipo);
			this.mandao.save(m1);

		} else if (tipo.equals(clienteS)) {
			String telefono = info.get("telefono").toString();
			char carnet = info.get("carnet").toString().charAt(0);
			String fecha = info.get("fecha").toString();
			Cliente c = new Cliente(email, dni, nombre, apellidos, pwdEncripted, rpwdEncripted, activo, telefono,
					carnet, tipo, fecha);
			this.clientedao.save(c);
		}
	}

	public Usuario login(Map<String, Object> info) {
		String email = info.get(emailS).toString();
		String pass = org.apache.commons.codec.digest.DigestUtils.sha512Hex(info.get("contrasena").toString());

		Correo c = this.correodao.findByEmail(email);
		if (c == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, credencialesS);
		}

		String tipo = c.getTipo();

		if (tipo.equals(adminS)) {
			Admin a = this.admindao.findByEmail(email);
			if (!a.getContrasena().equals(pass)) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, credencialesS);
			}
			return a;

		} else if (tipo.equals(clienteS)) {

			Cliente cliente = this.clientedao.findByEmail(email);
			if (!cliente.getContrasena().equals(pass)) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, credencialesS);
			}

			return cliente;

		} else if (tipo.equals(mantenimientoS)) {

			Mantenimiento m = this.mandao.findByEmail(email);
			if (!m.getContrasena().equals(pass)) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, credencialesS);
			}

			return m;
		}
		return null;
	}

	public void updateUsers(Map<String, Object> info) {
		Correo c = this.correodao.findByEmail((String) info.get(emailS));

		if (c != null) {
			String tipo = c.getTipo();

			if (tipo.equals(adminS)) {

				Admin a = this.admindao.findByEmail(c.getEmail());
				a.setNombre(info.get(nombreS).toString());
				a.setApellidos(info.get(apellidosS).toString());
				a.setDni(info.get("dni").toString());
				a.setCiudad(info.get(ciudadS).toString());

				this.admindao.save(a);

			} else if (tipo.equals(mantenimientoS)) {
				Mantenimiento m = this.mandao.findByEmail(c.getEmail());
				m.setNombre(info.get(nombreS).toString());
				m.setApellidos(info.get(apellidosS).toString());
				m.setDni(info.get("dni").toString());
				m.setCiudad(info.get(ciudadS).toString());
				m.setExperiencia(Integer.parseInt(info.get("experiencia").toString()));

				this.mandao.save(m);
			} else if (tipo.equals(clienteS)) {
				Cliente c1 = this.clientedao.findByEmail(c.getEmail());
				c1.setNombre(info.get(nombreS).toString());
				c1.setApellidos(info.get(apellidosS).toString());
				c1.setDni(info.get("dni").toString());
				c1.setTelefono(info.get("telefono").toString());
				c1.setCarnet(info.get("carnet").toString().charAt(0));
				c1.setFecha(info.get("fecha").toString());
				this.clientedao.save(c1);
			} else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario invalidas");
			}
		}

	}

	public List<Admin> listaAdministradores() {
		return this.admindao.findAll();
	}

	public List<Mantenimiento> listaMantenimiento() {
		return this.mandao.findAll();
	}

	public List<Cliente> listaClientes() {
		return this.clientedao.findAll();
	}

	public Admin obtenerAdminPorEmail(String email) {
		return admindao.findByEmail(email);
	}

	public Mantenimiento obtenerMantenimientoPorEmail(String email) {
		return mandao.findByEmail(email);
	}

	public Cliente obtenerClientePorEmail(String email) {
		return clientedao.findByEmail(email);
	}

	public Admin actualizarAdmin(Admin administradorExistente) {
		return admindao.save(administradorExistente);

	}

	public Mantenimiento actualizarMantenimiento(Mantenimiento mantenimientoExistente) {
		return mandao.save(mantenimientoExistente);
	}

	public Cliente actualizarCliente(Cliente clienteExistente) {
		return clientedao.save(clienteExistente);
	}

	public void bajaUsuarios(Map<String, Object> info) {
	    String email = (String) info.get(emailS);
	    Cliente cliente = this.clientedao.findByEmail(email);
	    List<ReservaCliente> reservasCliente = this.reservadao.findListByEmail(email);

	    if (reservasCliente.stream().anyMatch(reserva -> reserva.getEstado().equals("reservado"))) {
	        throw new ResponseStatusException(HttpStatus.CONFLICT, "El cliente tiene reservas activas.");
	    }
	    for (ReservaCliente reserva : reservasCliente) {
	        reserva.setCliente("Cliente_baja");
	    }

	    this.reservadao.saveAll(reservasCliente);
	    this.clientedao.deleteByemail(email);
	    this.correodao.deleteByemail(email);
	}

	public boolean checkUser(Map<String, Object> info) {
		boolean exist = false;
		TokenRecover token= null;
		String mail = info.get("email").toString();
		
		List<Correo> lstUser = this.correodao.findAll();
		
		for (Correo user : lstUser) {
			if(user.getEmail().equals(mail)) {
				exist =true;
				token= new TokenRecover(user.getEmail());
				this.tokenRecoverDAO.save(token);
			}
		}
		
		return exist;
		
	}

	public void updatePassword(Map<String, Object> info) {
		String mailEncripted = info.get(emailS).toString();
		String password = org.apache.commons.codec.digest.DigestUtils.sha512Hex(info.get("contrasena").toString());
		String rpassword = org.apache.commons.codec.digest.DigestUtils.sha512Hex(info.get("repetirContrasena").toString());
		TokenRecover token = this.tokenRecoverDAO.findBytoken(mailEncripted);
		String email = token.getEmail();
		this.tokenRecoverDAO.delete(token);
		Correo correo = this.correodao.findByEmail(email);
		if (correo.getTipo().equals(adminS)) {
			Admin admin = this.admindao.findByEmail(email);
			admin.setContrasena(password);
			admin.setRepetirContrasena(rpassword);
			this.admindao.save(admin);
			
		}
		else if (correo.getTipo().equals(mantenimientoS)) {
			Mantenimiento mantenimiento = this.mandao.findByEmail(email);
			mantenimiento.setContrasena(password);
			mantenimiento.setRepetirContrasena(rpassword);
			this.mandao.save(mantenimiento);
			
		}
		else if (correo.getTipo().equals(clienteS)) {
			Cliente cliente = this.clientedao.findByEmail(email);
			cliente.setContrasena(password);
			cliente.setRepetirContrasena(rpassword);
			this.clientedao.save(cliente);
			
		}
		
		
		
	}
}
