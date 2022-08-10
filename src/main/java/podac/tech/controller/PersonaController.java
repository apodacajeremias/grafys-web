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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import podac.tech.model.Persona;
import podac.tech.service.IPagoService;
import podac.tech.service.IPersonaService;
import podac.tech.service.IPresupuestoService;

@Controller
@RequestMapping("/persona")
public class PersonaController {

	// Inyectamos una instancia desde nuestro ApplicationContext
	@Autowired
	private IPersonaService personaService;

	@Autowired
	private IPagoService pagoService;

	@Autowired
	private IPresupuestoService presupuestoService;

	/**
	 * Metodo que muestra la lista de Persona sin paginacion
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping({ "/index", "/" })
	public String mostrarIndex(Model model) {
		return "redirect:/persona/indexPaginate";
	}

	/**
	 * Metodo que muestra la lista de Persona con paginacion
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		return "persona/listPersona";
	}

	/**
	 * Método para renderizar el formulario para crear una nueva Persona
	 *
	 * @param Persona
	 * @return
	 */
	@GetMapping("/create")
	public String crear(Persona persona) {
		return "persona/formPersona";
	}

	/**
	 * Método para renderizar el formulario para crear una nueva Persona
	 *
	 * @param Persona
	 * @return
	 */
	@GetMapping("/view/{id}")
	public String ver(@PathVariable("id") Long id, Model model, Pageable page) {
		Persona persona = this.personaService.buscarPorId(id);
		persona.setPagos(this.pagoService.buscarPorCliente(id, page).getContent());
		model.addAttribute("ultimosPresupuestos",
				this.presupuestoService.buscarUltimosPresupuestosPorCliente(id, page));
		model.addAttribute("ultimosPagos", this.pagoService.buscarUltimosPagosPorCliente(id, page));
		model.addAttribute("persona", persona);
		model.addAttribute("prs", persona);
		return "persona/viewPersona";
	}

	/**
	 * Método para guardar una Persona en la base de datos
	 *
	 * @param Persona
	 * @param result
	 * @param model
	 * @param attributes
	 * @return
	 */
	@PostMapping("/save")
	public String guardar(Persona persona, BindingResult result, Model model, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			System.out.println("Existieron errores");
			return "persona/formPersona";
		}

		if (this.personaService.existe(persona.getId())) {

		} else {
			persona.setEstado(true);
		}

		// Guadamos el objeto Persona en la bd
		personaService.guardar(persona);
		attributes.addFlashAttribute("msg", "Los datos de la persona fueron guardados!");

		return "redirect:/persona/indexPaginate";
	}

	/**
	 * Método para guardar una Persona en la base de datos
	 *
	 * @param Persona
	 * @param result
	 * @param model
	 * @param attributes
	 * @return
	 */
	@PutMapping("/save")
	public String actualizar(Persona persona, BindingResult result, Model model, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			System.out.println("Existieron errores");
			return "persona/formPersona";
		}

		if (this.personaService.existe(persona.getId())) {

		} else {
			persona.setEstado(true);
		}

		// Guadamos el objeto Persona en la bd
		personaService.guardar(persona);
		attributes.addFlashAttribute("msg", "Los datos de la persona fueron actualizado!");

		return "redirect:/persona/indexPaginate";
	}

	/**
	 * Método para renderizar el formulario para editar una Persona
	 *
	 * @param idPersona
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idPersona, Model model, Pageable page) {
		model.addAttribute("persona", personaService.buscarPorId(idPersona));

		return "persona/formPersona";
	}

	/**
	 * Método para eliminar una Persona de la base de datos
	 *
	 * @param idPersona
	 * @param attributes
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idPersona, RedirectAttributes attributes) {

		// Eliminamos la Persona.
		personaService.eliminar(idPersona);

		attributes.addFlashAttribute("msg", "La persona fue eliminada!");
		return "redirect:/persona/indexPaginate";
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
		model.addAttribute("personas", this.personaService.buscarTodas(page));

	}

}
