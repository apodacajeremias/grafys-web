package podac.tech.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import podac.tech.model.PresupuestoDetalle;
import podac.tech.repository.PresupuestoDetalleRepository;
import podac.tech.service.IPresupuestoDetalleService;

@Service
public class PresupuestoDetalleServiceJpa implements IPresupuestoDetalleService {

	@Autowired
	private PresupuestoDetalleRepository repo;

	@Override
	public PresupuestoDetalle guardar(PresupuestoDetalle objeto) {
		return this.repo.save(objeto);
	}

	@Override
	public void eliminar(Long id) {
		this.repo.deleteById(id);
	}

	@Override
	public List<PresupuestoDetalle> buscarTodas() {
		return this.repo.findAll();
	}

	@Override
	public PresupuestoDetalle buscarPorId(Long id) {
		return this.repo.findById(id).isPresent() ? this.repo.findById(id).get() : null;
	}

	@Override
	public Page<PresupuestoDetalle> buscarTodas(Pageable page) {
		return this.repo.findAll(page);
	}

	@Override
	public boolean existe(Long id) {
		return id == null ? false : this.repo.existsById(id);
	}

	@Override
	public List<PresupuestoDetalle> buscarConEjemplo(Example<PresupuestoDetalle> example) {
		return null;
	}

	@Override
	public Optional<PresupuestoDetalle> buscarPorIdOptional(Long id) {
		return this.repo.findById(id);
	}

	@Override
	public Long indicarPresupuestoPadre(Long id) {
		Optional<PresupuestoDetalle> detalle = this.repo.findById(id);
		return detalle.isPresent() ? detalle.get().getPresupuesto().getId() : 0L;
	}

	@Override
	public void actualizarDetalle(Long idDetalle, Double cantidad, Double precioProducto, Double precioPorDiseno,
			Double precioPorTiempo, Double tiempoNecesario, Double alto, Double ancho, String anotacion) {
		this.repo.updateFields(idDetalle, cantidad, precioProducto, precioPorDiseno, precioPorTiempo, tiempoNecesario,
				alto, ancho, anotacion);

	}

	@Override
	public Page<PresupuestoDetalle> buscarDetallesPorUsuario(String username, Pageable page) {
		
		return null;
	}

	@Override
	public List<PresupuestoDetalle> buscarDetallesPorUsuario(String username) {
		
		return null;
	}

}
