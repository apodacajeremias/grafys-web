package podac.tech.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import podac.tech.model.PresupuestoDetalle;

public interface IPresupuestoDetalleService {

	PresupuestoDetalle guardar(PresupuestoDetalle objeto);

	void eliminar(Long id);

	List<PresupuestoDetalle> buscarTodas();

	PresupuestoDetalle buscarPorId(Long id);

	Optional<PresupuestoDetalle> buscarPorIdOptional(Long id);

	List<PresupuestoDetalle> buscarConEjemplo(Example<PresupuestoDetalle> example);

	Page<PresupuestoDetalle> buscarTodas(Pageable page);

	boolean existe(Long id);

	Long indicarPresupuestoPadre(Long id);

	void actualizarDetalle(Long idDetalle, Double cantidad, Double precioProducto, Double precioPorDiseno,
			Double precioPorTiempo, Double tiempoNecesario, Double alto, Double ancho, String anotacion);

	Page<PresupuestoDetalle> buscarDetallesPorUsuario(String username, Pageable page);

	List<PresupuestoDetalle> buscarDetallesPorUsuario(String username);
	
	

}
