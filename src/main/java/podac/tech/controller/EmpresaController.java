package podac.tech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import podac.tech.model.Empresa;
import podac.tech.service.IEmpresaService;
import podac.tech.util.Utileria;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

	@Value("${podac.tech.ruta.imagenes}")
	private String RUTA;

	@Autowired
	private IEmpresaService serviceEmpresa;

	/**
	 * Metodo que muestra la lista de Empresa sin paginacion
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping({ "/", "/index", "/indexPaginate" })
	public String mostrarIndex(Model model) {
		// CONFIGURACION NO TIENE PAGINACION NI INDEX TIENE QUE IR DIRECTO AL /CREATE
		return "redirect:/empresa/create";
	}

	/**
	 * Método para renderizar el formulario para crear una nueva Empresa
	 *
	 * @param Empresa
	 * @return
	 */
	@GetMapping("/create")
	public String crear(Empresa empresa, Model model) {
		// VERIFICAR SI YA EXISTE CONFIGURACION
		// SI NO EXISTE ENTONCES MANDAR AL FORMULARIO VACIO
		if (this.serviceEmpresa.estaVacio()) {
			model.addAttribute("titulo", "Crear nueva empresa");
			return "empresa/formEmpresa";
		} else {
			return "redirect:/empresa/edit";
		}
	}

	/**
	 * Método para guardar una Empresa en la base de datos
	 *
	 * @param Empresa
	 * @param result
	 * @param model
	 * @param attributes
	 * @return
	 */
	@PostMapping("/save")
	public String guardar(Empresa empresa, BindingResult result, Model model, RedirectAttributes attributes,
			@RequestParam("archivoImagen") MultipartFile multiPart) {

		if (result.hasErrors()) {
			System.out.println("Existieron errores");
			return "empresa/formEmpresa";
		}

		if (!multiPart.isEmpty()) {
			String nombreLogotipo = Utileria.guardarArchivo(multiPart, RUTA);
			if (nombreLogotipo != null) {
				empresa.setLogotipo(nombreLogotipo);
			}
		}

		// Si registro NO EXISTE
		if (this.serviceEmpresa.existe(empresa.getId())) {

		} else {
			empresa.setEstado(true);
		}

		// Guadamos el objeto Empresa en la bd
		serviceEmpresa.guardar(empresa);
		attributes.addFlashAttribute("msg", "Los datos de la empresa fueron guardados!");

		// return "redirect:/Empresa/index";
		return "redirect:/";
	}

	/**
	 * Método para renderizar el formulario para editar una Empresa
	 *
	 * No se recibe ID porque siempre se debe modificar solo el ultimo registro, que
	 * puede tener cualquier ID
	 *
	 * @param idEmpresa
	 * @param model
	 * @return
	 */
	@GetMapping("/edit")
	public String editar(Model model) {
		model.addAttribute("titulo", "Editar empresa existente");
		model.addAttribute("empresa", this.serviceEmpresa.ultimoRegistro());
		return "empresa/formEmpresa";
	}

	@ModelAttribute
	private void setGeneric(Model model, Pageable page) {

	}

}
