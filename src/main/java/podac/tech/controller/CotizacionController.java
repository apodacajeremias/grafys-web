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

import podac.tech.model.Cotizacion;
import podac.tech.service.ICotizacionService;
import podac.tech.service.IMonedaService;

@Controller
@RequestMapping("/cotizacion")
public class CotizacionController {

	// Inyectamos una instancia desde nuestro ApplicationContext
	@Autowired
	private ICotizacionService serviceCotizacion;

	@Autowired
	private IMonedaService serviceMoneda;

	/**
	 * Metodo que muestra la lista de Cotizacion sin paginacion
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping({ "/", "/index" })
	public String mostrarIndex(Model model) {
		return "redirect:/cotizacion/indexPaginate";
	}

	/**
	 * Metodo que muestra la lista de Cotizacion con paginacion
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		return "cotizacion/listCotizacion";
	}

	/**
	 * Método para renderizar el formulario para crear una nueva Cotizacion
	 *
	 * @param Cotizacion
	 * @return
	 */
	@GetMapping("/create")
	public String crear(Cotizacion cotizacion) {
		return "cotizacion/formCotizacion";
	}

	/**
	 * Método para guardar una Cotizacion en la base de datos
	 *
	 * @param Cotizacion
	 * @param result
	 * @param model
	 * @param attributes
	 * @return
	 */
	@PostMapping("/save")
	public String guardar(Cotizacion cotizacion, BindingResult result, Model model, RedirectAttributes attributes) {

		if (result.hasErrors()) {

			System.out.println("Existieron errores");
			return "cotizacion/formCotizacion";
		}

		if (this.serviceCotizacion.existe(cotizacion.getId())) {

		} else {
			cotizacion.setEstado(true);
		}

		// Guadamos el objeto Cotizacion en la bd
		serviceCotizacion.guardar(cotizacion);
		attributes.addFlashAttribute("msg", "Los datos de la cotizacion fueron guardados!");

		return "redirect:/cotizacion/indexPaginate";
	}

	/**
	 * Método para renderizar el formulario para editar una Cotizacion
	 *
	 * @param idCotizacion
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idCotizacion, Model model) {
		model.addAttribute("cotizacion", serviceCotizacion.buscarPorId(idCotizacion));
		return "cotizacion/formCotizacion";
	}

	/**
	 * Método para eliminar una Cotizacion de la base de datos
	 *
	 * @param idCotizacion
	 * @param attributes
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idCotizacion, RedirectAttributes attributes) {

		// Eliminamos la Cotizacion.
		serviceCotizacion.eliminar(idCotizacion);

		attributes.addFlashAttribute("msg", "La cotizacion fue eliminada!");
		return "redirect:/cotizacion/indexPaginate";
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
		model.addAttribute("cotizaciones", serviceCotizacion.buscarTodas(page));
		model.addAttribute("monedas", serviceMoneda.buscarTodas(page));
	}

}
