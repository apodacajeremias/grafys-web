package podac.tech.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import podac.tech.model.Configuracion;
import podac.tech.service.IConfiguracionService;
import podac.tech.service.IMonedaService;

@Controller
@RequestMapping("/configuracion")
public class ConfiguracionController {

	// Inyectamos una instancia desde nuestro ApplicationContext
	@Autowired
	private IConfiguracionService configuracionService;

	@Autowired
	private IMonedaService monedaService;

	/**
	 * Metodo que muestra la lista de Configuracion sin paginacion
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping({ "/", "/index", "/indexPaginate", "" })
	public String mostrarIndex(Model model) {
		// CONFIGURACION NO TIENE PAGINACION NI INDEX TIENE QUE IR DIRECTO AL /CREATE
		return "redirect:/configuracion/create";
	}

	/**
	 * Método para renderizar el formulario para crear una nueva Configuracion
	 *
	 * @param Configuracion
	 * @return
	 */
	@GetMapping("/create")
	public String crear(Configuracion configuracion, Model model) {
		// VERIFICAR SI YA EXISTE CONFIGURACION
		// SI NO EXISTE ENTONCES MANDAR AL FORMULARIO VACIO
		if (this.configuracionService.estaVacio()) {
			model.addAttribute("titulo", "Crear nueva configuracion");
			return "configuracion/formConfiguracion";
		} else {
			return "redirect:/configuracion/edit";
		}
	}

	/**
	 * Método para guardar una Configuracion en la base de datos
	 *
	 * @param Configuracion
	 * @param result
	 * @param model
	 * @param attributes
	 * @return
	 */
	@PostMapping("/save")
	public String guardar(Configuracion configuracion, BindingResult result, Model model,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			System.out.println("Existieron errores");
			return "configuracion/formConfiguracion";
		}

		// Si registro NO EXISTE
		if (!this.configuracionService.existe(configuracion.getId())) {
			configuracion.setEstado(true);
		}

		// Guadamos el objeto Configuracion en la bd
		configuracionService.guardar(configuracion);
		attributes.addFlashAttribute("msg", "Los datos de la configuracion fueron guardados!");

		// return "redirect:/Configuracion/index";
		return "redirect:/";
	}

	/**
	 * Método para renderizar el formulario para editar una Configuracion
	 *
	 * No se recibe ID porque siempre se debe modificar solo el ultimo registro, que
	 * puede tener cualquier ID
	 *
	 * @param idConfiguracion
	 * @param model
	 * @return
	 */
	@GetMapping("/edit")
	public String editar(Model model) {
		model.addAttribute("titulo", "Editar configuracion existente");
		model.addAttribute("configuracion", this.configuracionService.ultimoRegistro());
		return "configuracion/formConfiguracion";
	}

	@ModelAttribute
	private void setGeneric(Model model, Pageable page) {
		model.addAttribute("monedas", this.monedaService.buscarTodas(page));
		model.addAttribute("idiomas", initIdiomas());
	}

	private List<String> initIdiomas() {
		List<String> idiomas = new ArrayList<>();
		idiomas.add("ES");
		// TODO: AVERIGUAR COMO TRADUCIR PAGINA PARA OTRO IDIOMA
		// idiomas.add("EN");
		// idiomas.add("PT");
		return idiomas;
	}

	@GetMapping("/get")
	@ResponseBody
	public Configuracion ultimaConfiguracion() {
		return this.configuracionService.ultimoRegistro();
	}

	@RequestMapping("/getOne")
	@ResponseBody
	public Optional<Configuracion> getOne() {
		Optional<Configuracion> cfgOpt = Optional.of(this.configuracionService.ultimoRegistro());
		return cfgOpt;
	}

	@RequestMapping("/getLast")
	@ResponseBody
	public Optional<Configuracion> getLast() {
		Optional<Configuracion> cfgOpt = Optional.of(this.configuracionService.ultimoRegistro());
		return cfgOpt;
	}

}
