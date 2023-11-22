package edu.uclm.esi.ds.webApp.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.ds.webApp.dao.AdminDAO;
import edu.uclm.esi.ds.webApp.dao.ClienteDAO;
import edu.uclm.esi.ds.webApp.dao.CorreoDAO;
import edu.uclm.esi.ds.webApp.dao.MantenimientoDAO;
import edu.uclm.esi.ds.webApp.dao.UsuarioDAO;
import edu.uclm.esi.ds.webApp.entities.Admin;
import edu.uclm.esi.ds.webApp.entities.Mantenimiento;
import edu.uclm.esi.ds.webApp.entities.Cliente;
import edu.uclm.esi.ds.webApp.entities.Correo;
import edu.uclm.esi.ds.webApp.entities.Usuario;
import edu.uclm.esi.ds.webApp.interfaces.ConstUsers;
import edu.uclm.esi.ds.webApp.security.Role;
import edu.uclm.esi.ds.webApp.security.config.JwtService;

@Service
public class UserService extends ConstUsers{

	@Autowired
	private ClienteDAO clientedao;
	@Autowired
	private AdminDAO admindao;
	@Autowired
	private MantenimientoDAO mandao;
	@Autowired
	private CorreoDAO correodao;
	@Autowired
	private UsuarioDAO usuarioDAO;
	@Autowired 
	private AuthenticationManager authenticationManager;
	@Autowired
	private  JwtService jwtService;
	@Autowired
	private  PasswordEncoder passwordEncoder;
	@Autowired
	private BCryptPasswordEncoder passwordChecker;

	public void Alta(Map<String, Object> info) {
		String email = info.get(EMAIL).toString();
		String nombre = info.get(NOMBRE).toString();
		String apellidos = info.get(APELLIDOS).toString();
		String dni = info.get("dni").toString();
		String contrasena = info.get(CONTRASENA).toString();
		String repetircontrasena = info.get("repetirContrasena").toString();
		String pwdEncripted = passwordEncoder.encode(contrasena);
		boolean activo = Boolean.parseBoolean(info.get("activo").toString());
		try {
			Correo c = new Correo(email, info.get("tipo").toString());
			correodao.save(c);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		String tipo = info.get("tipo").toString();

		Role role = darRol(tipo);
		Usuario usuario = new Usuario(email, dni, nombre, apellidos, pwdEncripted, pwdEncripted, activo, tipo, role);
		usuarioDAO.save(usuario);
		if (tipo.equals(ADMIN)) {

			Admin a1 = new Admin(email, dni, nombre, apellidos, pwdEncripted, pwdEncripted,
					info.get(CIUDAD).toString(), activo, tipo, role);
			this.admindao.save(a1);

		} else if (tipo.equals(MANTENIMIENTO)) {

			int experiencia = Integer.parseInt(info.get("experiencia").toString());
			Mantenimiento m1 = new Mantenimiento(email, dni, nombre, apellidos, pwdEncripted, pwdEncripted,
					info.get("ciudad").toString(), activo, experiencia, tipo, role);
			this.mandao.save(m1);

		} else if (tipo.equals(CLIENTE)) {
			String telefono = info.get("telefono").toString();
			char carnet = info.get("carnet").toString().charAt(0);
			String fecha = info.get("fecha").toString();
			Cliente c = new Cliente(email, dni, nombre, apellidos, pwdEncripted, pwdEncripted, activo, telefono,
					carnet, tipo, fecha, role);
			this.clientedao.save(c);
		}
	}
	
	public Role darRol(String tipo) {
		
		Role role = null;
		if(tipo.equals(ADMIN)) {
			role = Role.ADMIN;
		}else if (tipo.equals(MANTENIMIENTO)){
			role = Role.MANTENIMIENTO;
		}else if(tipo.equals(CLIENTE)) {
			role = Role.CLIENTE;
		}
		
		return role;
	}

	public Usuario login(Map<String, Object> info) {
		String email = info.get(EMAIL).toString();
		String pass = info.get(CONTRASENA).toString();
		
		Correo c = this.correodao.findByEmail(email);
		if (c == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, CREDENCIALES);
		}

		String tipo = c.getTipo();

		if (tipo.equals(ADMIN)) {
			Admin a = this.admindao.findByEmail(email);
			if(!passwordChecker.matches(pass, a.getContrasena())) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, CREDENCIALES);
			}
			return a;

		} else if (tipo.equals(CLIENTE)) {

			Cliente cliente = this.clientedao.findByEmail(email);
			if(!passwordChecker.matches(pass, cliente.getContrasena())) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, CREDENCIALES);
			}

			return cliente;

		} else if (tipo.equals(MANTENIMIENTO)) {

			Mantenimiento m = this.mandao.findByEmail(email);
			if(!passwordChecker.matches(pass, m.getContrasena())) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, CREDENCIALES);
			}

			return m;
		}
		return null;
	}
	
	public String authenticate(Map<String, Object> info) {
		String email = info.get("email").toString();
		String contrasena = info.get(CONTRASENA).toString();

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
				email,
				contrasena
			)
		);
		
		Usuario usuario = this.usuarioDAO.findByEmail(email);
		return jwtService.generateToken(usuario);
	}

	public void updateUsers(Map<String, Object> info) {
		Correo c = this.correodao.findByEmail((String) info.get(EMAIL));

		if (c != null) {
			String tipo = c.getTipo();

			Usuario usuario = this.usuarioDAO.findByEmail(c.getEmail());
			usuario.setNombre(info.get(NOMBRE).toString());
			usuario.setApellidos(info.get(APELLIDOS).toString());
			usuario.setDni(info.get("dni").toString());
			this.usuarioDAO.save(usuario);
			
			if (tipo.equals(ADMIN)) {

				Admin a = this.admindao.findByEmail(c.getEmail());
				a.setNombre(info.get(NOMBRE).toString());
				a.setApellidos(info.get(APELLIDOS).toString());
				a.setDni(info.get("dni").toString());
				a.setCiudad(info.get(CIUDAD).toString());

				this.admindao.save(a);

			} else if (tipo.equals(MANTENIMIENTO)) {
				Mantenimiento m = this.mandao.findByEmail(c.getEmail());
				m.setNombre(info.get(NOMBRE).toString());
				m.setApellidos(info.get(APELLIDOS).toString());
				m.setDni(info.get("dni").toString());
				m.setCiudad(info.get(CIUDAD).toString());
				m.setExperiencia(Integer.parseInt(info.get("experiencia").toString()));

				this.mandao.save(m);
			} else if (tipo.equals(CLIENTE)) {
				Cliente c1 = this.clientedao.findByEmail(c.getEmail());
				c1.setNombre(info.get(NOMBRE).toString());
				c1.setApellidos(info.get(APELLIDOS).toString());
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
}
