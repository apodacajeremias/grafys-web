package podac.tech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import podac.tech.model.Perfil;
import podac.tech.service.IPerfilService;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

	// Inyectamos una instancia desde nuestro ApplicationContext
	@Autowired
	private IPerfilService servicePerfil;

	/**
	 * Metodo que muestra la lista de Perfil sin paginacion
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping({ "/", "/index" })
	public String mostrarIndex(Model model) {
		return "redirect:/perfil/indexPaginate";
	}

	/**
	 * Metodo que muestra la lista de Perfil con paginacion
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		return "perfil/listPerfil";
	}

	/**
	 * Método para renderizar el formulario para crear una nueva Perfil
	 *
	 * @param Perfil
	 * @return
	 */
	@GetMapping("/create")
	public String crear(Perfil perfil) {
		return "perfil/formPerfil";
	}

	/**
	 * Método para guardar una Perfil en la base de datos
	 *
	 * @param Perfil
	 * @param result
	 * @param model
	 * @param attributes
	 * @return
	 */
	@PostMapping("/save")
	public String guardar(Perfil perfil, BindingResult result, Model model, RedirectAttributes attributes) {

		if (result.hasErrors()) {

			System.out.println("Existieron errores");
			return "perfil/formPerfil";
		}

		if (this.servicePerfil.existe(perfil.getId())) {
			
		} else {
			perfil.setEstado(true);
		}
		// Guadamos el objeto Perfil en la bd
		servicePerfil.guardar(perfil);
		attributes.addFlashAttribute("msg", "Los datos de la perfil fueron guardados!");

		return "redirect:/perfil/indexPaginate";
	}

	/**
	 * Método para renderizar el formulario para editar una Perfil
	 *
	 * @param idPerfil
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idPerfil, Model model) {
		model.addAttribute("perfil", servicePerfil.buscarPorId(idPerfil));
		return "perfil/formPerfil";
	}

	/**
	 * Método para eliminar una Perfil de la base de datos
	 *
	 * @param idPerfil
	 * @param attributes
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idPerfil, RedirectAttributes attributes) {

		// Eliminamos la Perfil.
		servicePerfil.eliminar(idPerfil);

		attributes.addFlashAttribute("msg", "La perfil fue eliminada!");
		return "redirect:/perfil/indexPaginate";
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
		model.addAttribute("perfiles", servicePerfil.buscarTodas(page));
	}

}
