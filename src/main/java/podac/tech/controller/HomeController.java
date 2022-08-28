package podac.tech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import podac.tech.model.Configuracion;
import podac.tech.model.Cotizacion;
import podac.tech.model.Empresa;
import podac.tech.model.Moneda;
import podac.tech.model.Perfil;
import podac.tech.model.Persona;
import podac.tech.model.Usuario;
import podac.tech.service.IConfiguracionService;
import podac.tech.service.ICotizacionService;
import podac.tech.service.IEmpresaService;
import podac.tech.service.IMonedaService;
import podac.tech.service.IPerfilService;
import podac.tech.service.IPersonaService;
import podac.tech.service.IPresupuestoService;
import podac.tech.service.IUsuarioService;

@Controller
public class HomeController {

	private Usuario usuario;

	@Value("${podac.tech.version}")
	private String VERSION;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IPersonaService personaService;

	@Autowired
	private IPerfilService perfilService;

	@Autowired
	private IMonedaService monedaService;

	@Autowired
	private IConfiguracionService configuracionService;

	@Autowired
	private IEmpresaService empresaService;

	@Autowired
	private IPresupuestoService presupuestoService;

	@Autowired
	private ICotizacionService cotizacionService;

	@GetMapping("/")
	public String mostrarHome() {
		if (this.perfilService.contarRegistros() <= 0) {
			initPerfil();
		}
		if (this.monedaService.contarRegistros() <= 0) {
			initMoneda();
		}
		if (this.usuarioService.contarRegistros() <= 0) {
			initUsuarioRoot();
		}
		// Si esta vacio, se configura
		if (this.configuracionService.estaVacio()) {
			initConfiguracion();
		} else {
			// Si no esta vacio, se verifica si esta actualizado
			verificarVersion();
		}

		if (this.empresaService.estaVacio()) {
			initEmpresa();
		}

		if (this.cotizacionService.estaVacio()) {
			initCotizacion();
		}

		return "redirect:/inicio";
	}

	/**
	 * Método que esta mapeado al botón Ingresar en el menú
	 *
	 * @param authentication
	 * @param session
	 * @return
	 */
	@GetMapping({ "/inicio", "/init", "/home" })
	public String mostrarIndex(Authentication authentication, HttpSession session, Model model) {

		// Como el usuario ya ingreso, ya podemos agregar a la session el objeto
		// usuario.
		String username = authentication.getName();
		if (session.getAttribute("usuario") == null) {
			this.usuario = usuarioService.buscarPorUsername(username);
			session.setAttribute("usuario", this.usuario);
		}

		model.addAttribute("presupuestosRecientes",
				this.presupuestoService.buscarPresupuestosPorUsuario(authentication.getName()));

		return "inicio";
	}

	/**
	 * Método que muestra el formulario para que se registren nuevos usuarios.
	 *
	 * @param usuario
	 * @return
	 */
	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		return "formRegistro";
	}

	/**
	 * Método que guarda en la base de datos el usuario registrado
	 *
	 * @param usuario
	 * @param attributes
	 * @return
	 */
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
		// Recuperamos el password en texto plano
		String pwdPlano = usuario.getPassword();
		// Encriptamos el pwd BCryptPasswordEncoder
		String pwdEncriptado = passwordEncoder.encode(pwdPlano);
		// Hacemos un set al atributo password (ya viene encriptado)
		usuario.setPassword(pwdEncriptado);
		usuario.setEstado(true); // Activado por defecto

		// Creamos el Perfil que le asignaremos al usuario nuevo
		Perfil perfil = new Perfil();
		perfil.setId(3L); // Perfil USUARIO
		usuario.agregar(perfil);

		/**
		 * Guardamos el usuario en la base de datos. El Perfil se guarda automaticamente
		 */
		usuarioService.guardar(usuario);

		attributes.addFlashAttribute("msg", "Has sido registrado. ¡Ahora puedes ingresar al sistema!");

		return "redirect:/login";
	}

	/**
	 * Método para realizar búsquedas desde el formulario de búsqueda del HomePage
	 *
	 * @param vacante
	 * @param model
	 * @return
	 */
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Persona persona, Model model) {

		/**
		 * La busqueda de personas desde el formulario debera de ser únicamente en
		 * Vacantes con estado = true . Entonces forzamos ese filtrado.
		 */
		persona.setEstado(true);

		// Personalizamos el tipo de busqueda...
		ExampleMatcher matcher = ExampleMatcher.matching().
		// and descripcion like '%?%'
				withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());

		Example<Persona> example = Example.of(persona, matcher);
		List<Persona> lista = personaService.buscarConEjemplo(example);
		model.addAttribute("vacantes", lista);
		return "inicio";
	}

	/**
	 * Metodo que muestra la vista de la pagina de Acerca
	 *
	 * @return
	 */
	@GetMapping("/about")
	public String mostrarAcerca() {
		return "acerca";
	}

	/**
	 * Método que muestra el formulario de login personalizado.
	 *
	 * @return
	 */
	@GetMapping("/login")
	public String mostrarLogin() {
		if (this.perfilService.contarRegistros() <= 0) {
			initPerfil();
		}
		if (this.usuarioService.contarRegistros() <= 0) {
			initUsuarioRoot();
		}
		return "formLogin";
	}

	/**
	 * Método personalizado para cerrar la sesión del usuario
	 *
	 * @param request
	 * @return
	 */
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/";
	}

	/**
	 * Utileria para encriptar texto con el algorito BCrypt
	 *
	 * @param texto
	 * @return
	 */
	@GetMapping("/bcrypt/{texto}")
	@ResponseBody
	public String encriptar(@PathVariable("texto") String texto) {
		return texto + " Encriptado en Bcrypt: " + passwordEncoder.encode(texto);
	}

	/**
	 * Metodo que agrega al modelo datos genéricos para todo el controlador
	 *
	 * @param model
	 */
	@ModelAttribute
	public void setGenericos(Model model, HttpSession session, Authentication authentication) {
		model.addAttribute("search", new Persona());
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
	}

	/**
	 * InitBinder para Strings si los detecta vacios en el Data Binding los settea a
	 * NULL
	 *
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	// TODO: CREAR PERFILES INICIALES
	// TODO: VERIFICAR SI YA EXISTEN REGISTROS
	private void initPerfil() {
		String[] ROLES = { "USUARIO", "SUPERVISOR", "ADMINISTRADOR" };
		for (String rol : ROLES) {
			if (!perfilService.existePerfil(rol)) {
				this.perfilService.guardar(new Perfil(rol));
			}
		}
	}

	private void initMoneda() {
		Map<String, String> MONEDAS = new HashMap<>();
		MONEDAS.put("GUARANI", "₲");
		MONEDAS.put("REAL", "R$");
		MONEDAS.put("DOLAR", "$");
		MONEDAS.put("EURO", "€");
		for (Map.Entry<String, String> moneda : MONEDAS.entrySet()) {
			if (!this.monedaService.existeMoneda(moneda.getKey())) {
				String nombre = moneda.getKey();
				this.monedaService.guardar(new Moneda(nombre));
			}
		}
	}

	private void initUsuarioRoot() {
		Usuario root = new Usuario();
		root.setEmail("N/A");
		root.setEstado(true);
		root.setNombre("ROOT");
		root.setPassword(passwordEncoder.encode("root"));
		root.setUsername("root");
		List<Perfil> perfiles = this.perfilService.buscarTodas();
		for (Perfil perfil : perfiles) {
			root.agregar(perfil);
		}
		this.usuarioService.guardar(root);
	}

	private void initConfiguracion() {
		Configuracion configuracion = new Configuracion();
		configuracion.setEstado(true);
		configuracion.setIdioma("ES");
		configuracion.setVersionActual(VERSION);
		configuracion.setMoneda(this.monedaService.buscarPorNombre("GUARANI"));
		this.configuracionService.guardar(configuracion);
	}

	private void verificarVersion() {
		Configuracion configuracion = this.configuracionService.ultimoRegistro();
		// Si version que consta en BD es diferente a version que maneja el sistema, se
		// actualiza registro
		if (!configuracion.getVersionActual().equals(VERSION)) {
			configuracion.setVersionActual(VERSION);
			this.configuracionService.guardar(configuracion);
		}
	}

	private void initEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setEstado(true);
		empresa.setEmail("N/A");
		empresa.setDireccion("N/A");
		empresa.setNombre("N/A");
		empresa.setRegistroTributario("N/A");
		empresa.setSitioWeb("N/A");
		empresa.setSlogan("N/A");
		empresa.setTelefono("N/A");
		empresa.setLogotipo("LogotipoInicial.png");
		this.empresaService.guardar(empresa);
	}

	private void initCotizacion() {
		Cotizacion cotizacionGuarani = new Cotizacion();
		cotizacionGuarani.setMoneda(this.monedaService.buscarPorNombre("GUARANI"));
		cotizacionGuarani.setValor(1d);
		this.cotizacionService.guardar(cotizacionGuarani);

		Cotizacion cotizacionReal = new Cotizacion();
		cotizacionReal.setMoneda(this.monedaService.buscarPorNombre("REAL"));
		cotizacionReal.setValor(1305d);
		this.cotizacionService.guardar(cotizacionReal);

		Cotizacion cotizacionEuro = new Cotizacion();
		cotizacionEuro.setMoneda(this.monedaService.buscarPorNombre("EURO"));
		cotizacionEuro.setValor(6954d);
		this.cotizacionService.guardar(cotizacionEuro);

		Cotizacion cotizacionDolar = new Cotizacion();
		cotizacionDolar.setMoneda(this.monedaService.buscarPorNombre("DOLAR"));
		cotizacionDolar.setValor(6862d);
		this.cotizacionService.guardar(cotizacionDolar);
	}

}
