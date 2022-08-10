package podac.tech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import podac.tech.model.Moneda;
import podac.tech.model.Pago;
import podac.tech.model.Presupuesto;
import podac.tech.service.IMonedaService;
import podac.tech.service.IPagoService;
import podac.tech.service.IPresupuestoService;

@Controller
@RequestMapping("/pago")
public class PagoController {

	// Inyectamos una instancia desde nuestro ApplicationContext
	@Autowired
	private IPagoService pagoService;

	@Autowired
	private IPresupuestoService presupuestoService;

	@Autowired
	private IMonedaService monedaService;

	/**
	 * Método para renderizar el formulario para crear un nuevo pago con presupuesto
	 *
	 * @param Pago
	 * @return
	 */
	@GetMapping("/create/{idPresupuesto}")
	public String crear(@PathVariable("idPresupuesto") Long idPresupuesto, Pago pago, Model model, Pageable page) {
		if (this.presupuestoService.existe(idPresupuesto)) {
			Presupuesto presupuesto = this.presupuestoService.buscarPorId(idPresupuesto);
			pago.setPresupuesto(presupuesto);
			Moneda moneda = this.monedaService.buscarPorId(3l);
			pago.setMoneda(moneda);
		} else {
			return "redirect:/pago/create";
		}
		model.addAttribute("tituloPagina", "Pago Nuevo");
		model.addAttribute("tituloFormulario", "Crear nuevo pago");
		model.addAttribute("pago", pago);
		return "pago/formPago";
	}

	@GetMapping("/create")
	public String crearSinPresupuesto(Pago pago, Model model) {
		// presupuesto sin cliente: despues vamos a elegir por combobox
		pago.setMoneda(new Moneda(3L));
		model.addAttribute("tituloPagina", "Pago Nuevo");
		model.addAttribute("tituloFormulario", "Crear un pago nuevo, seleccione el cliente y su presupuesto");
		model.addAttribute("pago", pago);

		return "pago/formPago";
	}

	@PostMapping(value = "/save")
	public String guardar(Pago pago, BindingResult result, RedirectAttributes att) {
		this.pagoService.guardar(pago);
		return "redirect:/inicio";
	}

//	@PostMapping(value = "/save")
//	public String guardar(@RequestParam("idPresupuesto") Long idPresupuesto, @RequestParam("valor") Double valor) {
//		Pago pago = new Pago();
//		pago.setAnotacion("");
//		pago.setEsGasto(false);
//		pago.setMoneda(new Moneda(3l));
//		pago.setPresupuesto(new Presupuesto(idPresupuesto));
//		pago.setValor(valor);
//		this.pagoService.guardar(pago);
//		return "redirect:/inicio";
//	}

	@GetMapping("/view/{idPago}")
	public String ver(@PathVariable("idPago") Long idPago, Model model, RedirectAttributes att) {
		// Verificamos si existe, porque el usuario podria pasar esta informacion por
		// URL
		if (this.pagoService.existe(idPago)) {
			// Creamos una variable con id = idPresupuesto,
			// Hibernate solo necesita de este atributo para recuperar todo lo que
			// corresponde
			Pago pago = this.pagoService.buscarPorId(idPago);
			model.addAttribute("pago", pago);
			// Asociamos el detalle con el presupuesto
			model.addAttribute("titulo", "Gestionar presupuesto");
			return "pago/viewPago";
		} else {
			att.addFlashAttribute("msg", "No se ha podido encontrar el pago, no se puede gestionar");
			return "redirect:/inicio/";
		}
	}

	@GetMapping("/reporteSimple")
	public String reporteSimple(Model model) {
		// TODO: TRABAJAR EN METODOS PARA UN REPORTE CON FILTROS
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("SUPERVISOR"))) {
			model.addAttribute("reportePagos", this.pagoService.buscarTodas());
		} else {
			model.addAttribute("reportePagos", this.pagoService.buscarTodas());
		}
		return "presupuesto/listPresupuesto";
	}

}
