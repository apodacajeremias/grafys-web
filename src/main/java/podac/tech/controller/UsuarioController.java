package podac.tech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import podac.tech.model.Usuario;
import podac.tech.service.IPerfilService;
import podac.tech.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	// Inyectamos una instancia desde nuestro ApplicationContext
	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IPerfilService perfilService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Metodo que muestra la lista de Usuario sin paginacion
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping({ "/index", "/" })
	public String mostrarIndex(Model model) {
		return "redirect:/usuario/indexPaginate";
	}

	/**
	 * Metodo que muestra la lista de Usuario con paginacion
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		return "usuario/listUsuario";
	}

	/**
	 * Método para renderizar el formulario para crear una nueva Usuario
	 *
	 * @param Usuario
	 * @return
	 */
	@GetMapping("/create")
	public String crear(Usuario usuario) {
		return "usuario/formUsuario";
	}

	/**
	 * Método para renderizar el formulario para crear una nueva Usuario
	 *
	 * @param Usuario
	 * @return
	 */
//	@GetMapping("/view/{id}")
	public String ver(@PathVariable("id") Long id, Model model, Pageable page) {
		return "usuario/viewUsuario";
	}

	/**
	 * Método para guardar una Usuario en la base de datos
	 *
	 * @param Usuario
	 * @param result
	 * @param model
	 * @param attributes
	 * @return
	 */
	@PostMapping("/save")
	public String guardar(Usuario usuario, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			System.out.println("Existieron errores");
			return "usuario/formUsuario";
		}
		if (this.usuarioService.existe(usuario.getId())) {

		} else {
			usuario.setEstado(true);
		}

		// Recuperamos el password en texto plano
		String pwdPlano = usuario.getPassword();
		// Encriptamos el pwd BCryptPasswordEncoder
		String pwdEncriptado = passwordEncoder.encode(pwdPlano);
		// Hacemos un set al atributo password (ya viene encriptado)
		usuario.setPassword(pwdEncriptado);

		// Guadamos el objeto Usuario en la bd
		usuarioService.guardar(usuario);
		attributes.addFlashAttribute("msg", "Los datos del usuario fueron guardados!");

		return "redirect:/usuario/indexPaginate";
	}

	/**
	 * Método para guardar una Usuario en la base de datos
	 *
	 * @param Usuario
	 * @param result
	 * @param model
	 * @param attributes
	 * @return
	 */
	@PutMapping("/save")
	public String actualizar(Usuario usuario, BindingResult result, Model model, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			System.out.println("Existieron errores");
			return "usuario/formUsuario";
		}

		if (this.usuarioService.existe(usuario.getId())) {

		} else {
			usuario.setEstado(true);
		}

		// Guadamos el objeto Usuario en la bd
		usuarioService.guardar(usuario);
		attributes.addFlashAttribute("msg", "Los datos de la usuario fueron actualizado!");

		return "redirect:/usuario/indexPaginate";
	}

	/**
	 * Método para renderizar el formulario para editar una Usuario
	 *
	 * @param idUsuario
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idUsuario, Model model, Pageable page, Authentication auth) {
//		Se verifica si el usuario tiene el ROL		
		model.addAttribute("usuario", usuarioService.buscarPorId(idUsuario));
		return "usuario/formUsuario";
	}

	/**
	 * Método para eliminar una Usuario de la base de datos
	 *
	 * @param idUsuario
	 * @param attributes
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idUsuario, RedirectAttributes attributes) {

		// Eliminamos la Usuario.
		usuarioService.eliminar(idUsuario);

		attributes.addFlashAttribute("msg", "La usuario fue eliminada!");
		return "redirect:/usuario/indexPaginate";
	}

	/**
	 * Método para configurar modelo de uso compartido por las hojas
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@ModelAttribute
	public void setGenericos(Model model, Pageable page) {
		model.addAttribute("usuarios", this.usuarioService.buscarTodas(page));
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("perfiles", this.perfilService.buscarTodas());

	}

	// TODO: CAMBIO DE CONTRASENA
	// TODO: ASIGNACION DE PERFIL FUERA AL FORM

}
