package podac.tech.controller;

import java.util.ArrayList;
import java.util.List;

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

import podac.tech.model.Producto;
import podac.tech.service.IProductoService;

@Controller
@RequestMapping("/producto")
public class ProductoController {

	// Inyectamos una instancia desde nuestro ApplicationContext
	@Autowired
	private IProductoService serviceProducto;

	/**
	 * Metodo que muestra la lista de Producto sin paginacion
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping({ "/", "/index" })
	public String mostrarIndex(Model model) {
		return "redirect:/producto/indexPaginate";
	}

	/**
	 * Metodo que muestra la lista de Producto con paginacion
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		return "producto/listProducto";
	}

	/**
	 * Método para renderizar el formulario para crear una nueva Producto
	 *
	 * @param Producto
	 * @return
	 */
	@GetMapping("/create")
	public String crear(Producto producto) {
		return "producto/formProducto";
	}

	/**
	 * Método para guardar una Producto en la base de datos
	 *
	 * @param Producto
	 * @param result
	 * @param model
	 * @param attributes
	 * @return
	 */
	@PostMapping("/save")
	public String guardar(Producto producto, BindingResult result, Model model, RedirectAttributes attributes
			) {
		if (result.hasErrors()) {
			System.out.println("Existieron errores");
			return "producto/formProducto";
		}

		if (this.serviceProducto.existe(producto.getId())) {

		} else {
			producto.setEstado(true);
		}

		// Guadamos el objeto Producto en la bd
		serviceProducto.guardar(producto);
		attributes.addFlashAttribute("msg", "Los datos del producto fueron guardados!");

		return "redirect:/producto/indexPaginate";
	}

	/**
	 * Método para renderizar el formulario para editar una Producto
	 *
	 * @param idProducto
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idProducto, Model model) {
		model.addAttribute("producto", serviceProducto.buscarPorId(idProducto));
		return "producto/formProducto";
	}

	/**
	 * Método para eliminar una Producto de la base de datos
	 *
	 * @param idProducto
	 * @param attributes
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idProducto, RedirectAttributes attributes) {

		// Eliminamos la Producto.
		serviceProducto.eliminar(idProducto);

		attributes.addFlashAttribute("msg", "El producto fue eliminado!");
		return "redirect:/producto/indexPaginate";
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

		model.addAttribute("unidadesCobro", unidadesCobro());
		model.addAttribute("productos", serviceProducto.buscarTodas(page));
	}

	private List<String> unidadesCobro() {
		List<String> unidadesCobro = new ArrayList<>();
		unidadesCobro.add("M2");
		unidadesCobro.add("UN");
		unidadesCobro.add("ML");
		return unidadesCobro;
	}

}
