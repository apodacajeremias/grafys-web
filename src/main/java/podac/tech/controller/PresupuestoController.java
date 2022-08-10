
package podac.tech.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import podac.tech.model.Persona;
import podac.tech.model.Presupuesto;
import podac.tech.model.PresupuestoDetalle;
import podac.tech.model.Producto;
import podac.tech.service.IMonedaService;
import podac.tech.service.IPersonaService;
import podac.tech.service.IPresupuestoDetalleService;
import podac.tech.service.IPresupuestoService;
import podac.tech.service.IProductoService;

@Controller
@RequestMapping(path = "/presupuesto")
public class PresupuestoController {

	private Presupuesto presupuesto;

	@Autowired
	private IPresupuestoService presupuestoService;

	@Autowired
	private IPresupuestoDetalleService presupuestoDetalleService;

	@Autowired
	private IPersonaService personaService;

	@Autowired
	private IProductoService productoService;

	@Autowired
	private IMonedaService monedaService;

	@GetMapping("/create/{idPersona}")
	public String crear(@PathVariable("idPersona") Long idPersona, Presupuesto presupuesto, Model model) {
		// Creamos modelo inicial de presupuesto
		// Recibimos ID de persona y verificamos si existe
		if (this.personaService.existe(idPersona)) {
			// Pasamos id de cliente a presupuesto
			Persona persona = this.personaService.buscarPorId(idPersona);
			presupuesto.setCliente(persona);
		} else {
			// Si no existe, presupuesto sin cliente: despues vamos a elegir por combobox
			return "redirect:/presupuesto/create";
		}
		model.addAttribute("tituloPagina", "Presupuesto Nuevo");
		model.addAttribute("tituloFormulario", "Crear un presupuesto nuevo");
		model.addAttribute("presupuesto", presupuesto);
		this.presupuesto = presupuesto;
		return "presupuesto/formPresupuesto";
	}

	@GetMapping("/create")
	public String crearSinCliente(Presupuesto presupuesto, Model model) {
		// presupuesto sin cliente: despues vamos a elegir por combobox
		model.addAttribute("tituloPagina", "Presupuesto Nuevo");
		model.addAttribute("tituloFormulario", "Crear un presupuesto nuevo, seleccione el cliente");
		model.addAttribute("presupuesto", presupuesto);
		this.presupuesto = presupuesto;
		return "presupuesto/formPresupuesto";
	}

	@PostMapping(value = "/save")
	public String guardar(Presupuesto presupuesto, BindingResult result, RedirectAttributes att) {
		// Presupuesto iniciar PENDIENTE == NULL
		this.presupuestoService.guardar(presupuesto);
		this.presupuesto = presupuesto;
		return "redirect:/presupuesto/edit/" + presupuesto.getId();
	}

	@PutMapping(value = "/save")
	public String actualizar(Presupuesto presupuesto) {
		this.presupuestoService.actualizarDescuento(presupuesto.getId(), presupuesto.getDescuento());
		return "redirect:/presupuesto/edit/" + presupuesto.getId();
	}

	@GetMapping("/edit/{idPresupuesto}")
	public String editar(@PathVariable("idPresupuesto") Long idPresupuesto, Model model, RedirectAttributes att) {
		// Verificamos si existe, porque el usuario podria pasar esta informacion por
		// URL
		if (this.presupuestoService.existe(idPresupuesto)) {
			// Creamos una variable con id = idPresupuesto,
			// Hibernate solo necesita de este atributo para recuperar todo lo que
			// corresponde
			// Asociamos el detalle con el presupuesto
			this.presupuesto = this.presupuestoService.buscarPorId(idPresupuesto);
			model.addAttribute("tituloPagina", "Presupuesto " + presupuesto.getId());
			model.addAttribute("tituloFormulario", "Editar un presupuesto");
			model.addAttribute("presupuesto", presupuesto);
			return "presupuesto/formPresupuesto";
		} else {
			att.addFlashAttribute("msg", "No se ha podido encontrar el presupuesto, no se puede editar");
			return "redirect:/inicio/";
		}
	}

	@GetMapping("/view/{idPresupuesto}")
	public String ver(@PathVariable("idPresupuesto") Long idPresupuesto, PresupuestoDetalle detalle, Model model,
			RedirectAttributes att) {
		// Verificamos si existe, porque el usuario podria pasar esta informacion por
		// URL
		if (this.presupuestoService.existe(idPresupuesto)) {
			// Creamos una variable con id = idPresupuesto,
			// Hibernate solo necesita de este atributo para recuperar todo lo que
			// corresponde
			Presupuesto presupuesto = this.presupuestoService.buscarPorId(idPresupuesto);
			model.addAttribute("presupuesto", presupuesto);
			// Asociamos el detalle con el presupuesto
			model.addAttribute("titulo", "Gestionar presupuesto");
			model.addAttribute("detalle", presupuesto.getDetalles());
			return "redirect:/presupuesto/reporteSimple";
		} else {
			att.addFlashAttribute("msg", "No se ha podido encontrar el presupuesto, no se puede gestionar");
			return "redirect:/inicio/";
		}
	}

	@GetMapping("/approve/{idPresupuesto}")
	private String aprobar(@PathVariable("idPresupuesto") Long idPresupuesto, RedirectAttributes att) {
		if (this.presupuestoService.existe(idPresupuesto)) {
			this.presupuestoService.aprobar(idPresupuesto);
			att.addFlashAttribute("Presupuesto aprobado, a partir de ahora puede generar pagos");
			return "redirect:/presupuesto/view/" + idPresupuesto;
		} else {
			att.addFlashAttribute("msg", "No se ha podido encontrar el presupuesto, no se puede aprobar");
			return "redirect:/inicio/";
		}
	}

	@GetMapping("/reject/{idPresupuesto}")
	private String rechazar(@PathVariable("idPresupuesto") Long idPresupuesto, RedirectAttributes att) {
		if (this.presupuestoService.existe(idPresupuesto)) {
			this.presupuestoService.rechazar(idPresupuesto);
			att.addFlashAttribute("Presupuesto rechazado, seguira disponible para observacion");
			return "redirect:/presupuesto/view/" + idPresupuesto;
		} else {
			att.addFlashAttribute("msg", "No se ha podido encontrar el presupuesto, no se puede rechazar");
			return "redirect:/inicio/";
		}
	}

	// NO TOCAR MAS ESTE METODO PORQUE YA FUNCIONA
	// TODO: CREAR OTRO METODO PARA EDITAR EL DETALLE USANDO REQUESTPARAM INDIVIDUAL
	// DE LAS COSAS
//	@PostMapping("/addDetalle")
//	public String guardarDetalle(PresupuestoDetalle detalle, BindingResult result, RedirectAttributes att,
//			Model model) {
//		this.presupuestoDetalleService.guardar(detalle);
//		System.out.println("/saveDetalle: " + detalle);
//		att.addFlashAttribute("msg", "Producto agregado");
//		return "redirect:/presupuesto/edit/" + detalle.getPresupuesto().getId();
//	}

	// TODO: GUARDAR LISTA DE DETALLES EN SESION PARA ESTAR GUARDANDO CADA RATO
	@PostMapping("/addDetalle")
	public String guardarDetalle(Producto producto, BindingResult result, RedirectAttributes att, Model model) {
		Producto productoDatosCompletos = this.productoService.buscarPorId(producto.getId());
		PresupuestoDetalle detalle = new PresupuestoDetalle(this.presupuesto, productoDatosCompletos);
		this.presupuesto.addDetalle(detalle);
		this.presupuestoService.guardar(presupuesto);
		att.addFlashAttribute("msg", "Producto agregado");
		return "redirect:/presupuesto/edit/" + this.presupuesto.getId();
	}

	@PostMapping("/editDetalle")
	public String editarDetalle(@RequestParam("idPresupuestoEdit") Long idPresupuesto,
			@RequestParam("idDetalleEdit") Long idDetalle, @RequestParam("cantidadEdit") Double cantidad,
			@RequestParam("precioProductoEdit") Double precioProducto,
			@RequestParam("precioPorDisenoEdit") Double precioPorDiseno,
			@RequestParam("precioPorTiempoEdit") Double precioPorTiempo,
			@RequestParam("tiempoNecesarioEdit") Double tiempoNecesario, @RequestParam("altoEdit") Double alto,
			@RequestParam("anchoEdit") Double ancho, @RequestParam("anotacionEdit") String anotacion,
			RedirectAttributes att) {

		this.presupuestoDetalleService.actualizarDetalle(idDetalle, cantidad, precioProducto, precioPorDiseno,
				precioPorTiempo, tiempoNecesario, alto, ancho, anotacion);
		att.addFlashAttribute("msg", "Producto agregado");
		return "redirect:/presupuesto/edit/" + idPresupuesto;
	}

	@GetMapping("/removeDetalle/{idDetalle}")
	public String quitarDetalle(@PathVariable("idDetalle") Long idDetalle, RedirectAttributes attr) {
		System.out.println("ELIMINAR DETALLE ID " + idDetalle);
		this.presupuesto.removeDetalle(idDetalle);
		this.presupuestoService.guardar(this.presupuesto);
		attr.addFlashAttribute("msg", "Registro borrado");
		return "redirect:/presupuesto/edit/" + this.presupuesto.getId();
	}

	@RequestMapping("/getOne")
	@ResponseBody
	public Optional<PresupuestoDetalle> getOne(Long id) {
		return this.presupuestoDetalleService.buscarPorIdOptional(id);
	}

	@GetMapping("/reporteSimple")
	public String reporteSimple(Model model) {
		// TODO: TRABAJAR EN METODOS PARA UN REPORTE CON FILTROS
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("SUPERVISOR"))) {
			model.addAttribute("reportePresupuestos", this.presupuestoService.buscarTodas());
		} else {
			model.addAttribute("reportePresupuestos",
					this.presupuestoService.buscarPresupuestosPorUsuario(auth.getName()));
		}

		return "presupuesto/listPresupuesto";
	}

	@GetMapping("/reporteDetallado")
	public String reporteDetallado(Model model) {
		// TODO: TRABAJAR EN METODOS PARA UN REPORTE CON FILTROS
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("SUPERVISOR"))) {
			model.addAttribute("reporteDetalles", this.presupuestoDetalleService.buscarTodas());
		} else {
			model.addAttribute("reporteDetalles",
					this.presupuestoDetalleService.buscarDetallesPorUsuario(auth.getName()));
		}

		return "presupuestoDetalle/listPresupuestoDetalle";
	}

	@GetMapping("/exportar")
	public ResponseEntity<Resource> exportarPresupuesto(@RequestParam Long idCliente,
			@RequestParam Long idPresupuesto) {
		return this.presupuestoService.exportarPresupuesto(idCliente, idPresupuesto);
	}

//	TODO: Escribir metodo para editar DETALLE

//	TODO: Escribir metodo para eliminar DETALLE

	/**
	 * Metodo que agrega al modelo datos genéricos para todo el controlador
	 *
	 * @param model
	 */
	@ModelAttribute
	public void setGenericos(Model model, HttpSession session) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("productos", this.productoService.buscarTodas());
		model.addAttribute("clientes", this.personaService.buscarTodas());
		model.addAttribute("monedas", this.monedaService.buscarTodas());
		model.addAttribute("tituloPagina", "Presupuesto #");
		model.addAttribute("tituloFormulario", "Formulario");
		model.addAttribute("presupuestoEjemplo", new Presupuesto());
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

		// Para numeros
//		NumberFormat formato = new DecimalFormat();
//		CustomNumberEditor editor = new CustomNumberEditor(Double.class, formato, true);
//		binder.registerCustomEditor(Double.class, editor);
	}

}
