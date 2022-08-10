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

import podac.tech.model.Moneda;
import podac.tech.service.IMonedaService;

@Controller
@RequestMapping("/moneda")
public class MonedaController {

	// Inyectamos una instancia desde nuestro ApplicationContext
	@Autowired
	private IMonedaService serviceMoneda;

	/**
	 * Metodo que muestra la lista de Moneda sin paginacion
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping({ "/", "/index" })
	public String mostrarIndex(Model model) {
		return "redirect:/moneda/indexPaginate";
	}

	/**
	 * Metodo que muestra la lista de Moneda con paginacion
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		return "moneda/listMoneda";
	}

	/**
	 * Método para renderizar el formulario para crear una nueva Moneda
	 *
	 * @param Moneda
	 * @return
	 */
	@GetMapping("/create")
	public String crear(Moneda moneda) {
		return "moneda/formMoneda";
	}

	/**
	 * Método para guardar una Moneda en la base de datos
	 *
	 * @param Moneda
	 * @param result
	 * @param model
	 * @param attributes
	 * @return
	 */
	@PostMapping("/save")
	public String guardar(Moneda moneda, BindingResult result, Model model, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			System.out.println("Existieron errores");
			return "moneda/formMoneda";
		}

		if (this.serviceMoneda.existe(moneda.getId())) {

		} else {
			moneda.setEstado(true);
		}

		// Guadamos el objeto Moneda en la bd
		serviceMoneda.guardar(moneda);
		attributes.addFlashAttribute("msg", "Los datos de la moneda fueron guardados!");

		return "redirect:/moneda/indexPaginate";
	}

	/**
	 * Método para renderizar el formulario para editar una Moneda
	 *
	 * @param idMoneda
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idMoneda, Model model) {
		model.addAttribute("moneda", serviceMoneda.buscarPorId(idMoneda));
		return "moneda/formMoneda";
	}

	/**
	 * Método para eliminar una Moneda de la base de datos
	 *
	 * @param idMoneda
	 * @param attributes
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idMoneda, RedirectAttributes attributes) {

		// Eliminamos la Moneda.
		serviceMoneda.eliminar(idMoneda);

		attributes.addFlashAttribute("msg", "La moneda fue eliminada!");
		return "redirect:/moneda/indexPaginate";
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
		model.addAttribute("monedas", serviceMoneda.buscarTodas(page));
	}

}
