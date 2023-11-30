package edu.uclm.esi.ds.webApp.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
import edu.uclm.esi.ds.webApp.dao.ReservaClienteDAO;
import edu.uclm.esi.ds.webApp.dao.TokenRecoverDAO;
import edu.uclm.esi.ds.webApp.dao.UsuarioDAO;
import edu.uclm.esi.ds.webApp.entities.Admin;
import edu.uclm.esi.ds.webApp.entities.Mantenimiento;
import edu.uclm.esi.ds.webApp.entities.ReservaCliente;
import edu.uclm.esi.ds.webApp.entities.Cliente;
import edu.uclm.esi.ds.webApp.entities.Correo;
import edu.uclm.esi.ds.webApp.entities.Usuario;
import edu.uclm.esi.ds.webApp.interfaces.ConstUsers;
import edu.uclm.esi.ds.webApp.security.Role;
import edu.uclm.esi.ds.webApp.security.config.JwtService;
import edu.uclm.esi.ds.webApp.security.tfa.AESEncryption;
import edu.uclm.esi.ds.webApp.security.tfa.TwoFactorAuthenticationService;
import edu.uclm.esi.ds.webApp.entities.TokenRecover;

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
	private ReservaClienteDAO reservadao;
	@Autowired
	private TokenRecoverDAO tokenRecoverDAO;
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
	@Autowired
	private TwoFactorAuthenticationService tfaService;
	
	@Value("${application.security.secret-key}")
	private String SECRET_KEY;
	/*
	 * METODO: ALTA
	 * DESCRIPCION: Registra un nuevo usuario en el sistema con la información proporcionada.
	 * PARAMETROS:
	 *   - info: Mapa que contiene la información necesaria para el registro del usuario.
	 * EXCEPCIONES LANZADAS:
	 *   - Exception: Se lanza una excepción general en caso de errores inesperados durante el proceso de registro.
	 *   - ResponseStatusException con HttpStatus.CONFLICT en caso de violación de integridad de datos.
	 * RETORNO:
	 *   - En caso de registro exitoso de un cliente con autenticación de dos factores (mFaEnabled), devuelve la URI
	 *     del código QR asociado al servicio de autenticación de dos factores.
	 *   - En otros casos, devuelve un mensaje indicando el éxito del registro.
	 */
	public String Alta(Map<String, Object> info) throws Exception {
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
			boolean mFaEnabled = Boolean.parseBoolean(info.get("mFaEnabled").toString());
			char carnet = info.get("carnet").toString().charAt(0);
			String fecha = info.get("fecha").toString();
			Cliente c = new Cliente(email, dni, nombre, apellidos, pwdEncripted, pwdEncripted, activo, telefono,
					carnet, tipo, fecha, role, mFaEnabled);
			this.clientedao.save(c);
			
			if((boolean) info.get("mFaEnabled")) {
				c.setSecret(AESEncryption.encrypt(tfaService.generateNewSecret(), SECRET_KEY));
				c.setmFaEnabled(true);
				this.clientedao.save(c);
				return tfaService.generateQrCodeImageUri(AESEncryption.decrypt(c.getSecret(), SECRET_KEY));
			}
		}
		
			return "Usuario registrado correctamente";
	}
	/*
	 * METODO: DARROL
	 * DESCRIPCION: Asigna un rol específico según el tipo de usuario.
	 * PARAMETROS:
	 *   - tipo: Tipo de usuario para el cual se determina el rol.
	 * RETORNO:
	 *   - Rol asignado al tipo de usuario.
	 */
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
	/*
	 * METODO: LOGIN
	 * DESCRIPCION: Realiza el inicio de sesión para un usuario con las credenciales proporcionadas.
	 * PARAMETROS:
	 *   - info: Mapa que contiene la información necesaria para el inicio de sesión (EMAIL, CONTRASENA).
	 * EXCEPCIONES LANZADAS:
	 *   - ResponseStatusException con HttpStatus.FORBIDDEN si las credenciales no son válidas.
	 * RETORNO:
	 *   - Usuario autenticado correspondiente al tipo de usuario (Admin, Cliente, Mantenimiento).
	 */
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
	/*
	 * METODO: AUTHENTICATE
	 * DESCRIPCION: Autentica a un usuario utilizando el sistema de autenticación de Spring Security
	 *              y genera un token JWT para la autorización.
	 * PARAMETROS:
	 *   - info: Mapa que contiene la información necesaria para la autenticación (email, CONTRASENA).
	 * RETORNO:
	 *   - Token JWT generado para el usuario autenticado.
	 * EXCEPCIONES LANZADAS:
	 *   - AuthenticationException: Se lanza en caso de fallo en la autenticación.
	 */
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
	/*
	 * METODO: VERIFYCODE
	 * DESCRIPCION: Verifica el código de autenticación de dos factores (2FA) para un cliente y genera un token JWT
	 *              para la autorización.
	 * PARAMETROS:
	 *   - info: Mapa que contiene la información necesaria para la verificación del código (EMAIL, codigo).
	 * RETORNO:
	 *   - Token JWT generado para la autorización del cliente si el código es válido.
	 * EXCEPCIONES LANZADAS:
	 *   - Exception: Se lanza en caso de errores inesperados durante la verificación del código.
	 *   - BadCredentialsException: Se lanza si el código no es correcto.
	 */
	public String verifyCode(Map <String, Object> info) throws Exception {
		Cliente cliente = this.clientedao.findByEmail(info.get(EMAIL).toString());
		
		if(tfaService.isOtpNotValid(AESEncryption.decrypt(cliente.getSecret(), SECRET_KEY), info.get("codigo").toString())) {
			throw new BadCredentialsException("Codigo no correcto");
		} else {
			return jwtService.generateToken(cliente);
		}
	}
	/*
	 * METODO: UPDATEUSERS
	 * DESCRIPCION: Actualiza la información de los usuarios (Admin, Mantenimiento, Cliente) en el sistema.
	 * PARAMETROS:
	 *   - info: Mapa que contiene la información actualizada para los usuarios (EMAIL, NOMBRE, APELLIDOS, dni, etc.).
	 * EXCEPCIONES LANZADAS:
	 *   - ResponseStatusException con HttpStatus.FORBIDDEN si el tipo de usuario es inválido.
	 */
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
	/*
	 * METODO: BAJAUSUARIOS
	 * DESCRIPCION: Da de baja a un cliente, cancelando todas sus reservas y eliminando su información del sistema.
	 * PARAMETROS:
	 *   - info: Mapa que contiene la información necesaria para la baja de usuario (EMAIL).
	 * EXCEPCIONES LANZADAS:
	 *   - ResponseStatusException con HttpStatus.CONFLICT si el cliente tiene reservas activas.
	 */

	public void bajaUsuarios(Map<String, Object> info) {
	    String email = (String) info.get(EMAIL);
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
	    this.usuarioDAO.deleteByemail(email);
	}
	/*
	 * METODO: CHECKUSER
	 * DESCRIPCION: Verifica la existencia de un usuario a través del correo electrónico y genera un token de recuperación.
	 * PARAMETROS:
	 *   - info: Mapa que contiene la información necesaria para la verificación del usuario (email).
	 * RETORNO:
	 *   - true si el usuario existe, false en caso contrario.
	 */
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
	
	/*
	 * METODO: UPDATEPASSWORD
	 * DESCRIPCION: Actualiza la contraseña de un usuario después de un proceso de recuperación.
	 * PARAMETROS:
	 *   - info: Mapa que contiene la información necesaria para la actualización de contraseña (EMAIL, contrasena).
	 */

	public void updatePassword(Map<String, Object> info) {
		String mailEncripted = info.get(EMAIL).toString();
		String password = passwordEncoder.encode(info.get("contrasena").toString());
		TokenRecover token = this.tokenRecoverDAO.findBytoken(mailEncripted);
		String email = token.getEmail();
		this.tokenRecoverDAO.delete(token);
		Usuario usuario = this.usuarioDAO.findByEmail(email);
		usuario.setContrasena(password);
		usuario.setRepetirContrasena(password);
		this.usuarioDAO.save(usuario);
		Correo correo = this.correodao.findByEmail(email);
		if (correo.getTipo().equals(ADMIN)) {
			Admin admin = this.admindao.findByEmail(email);
			admin.setContrasena(password);
			admin.setRepetirContrasena(password);
			this.admindao.save(admin);
			
		}
		else if (correo.getTipo().equals(MANTENIMIENTO)) {
			Mantenimiento mantenimiento = this.mandao.findByEmail(email);
			mantenimiento.setContrasena(password);
			mantenimiento.setRepetirContrasena(password);
			this.mandao.save(mantenimiento);
			
		}
		else if (correo.getTipo().equals(CLIENTE)) {
			Cliente cliente = this.clientedao.findByEmail(email);
			cliente.setContrasena(password);
			cliente.setRepetirContrasena(password);
			this.clientedao.save(cliente);
			
		}
		
		
		
	}
}
