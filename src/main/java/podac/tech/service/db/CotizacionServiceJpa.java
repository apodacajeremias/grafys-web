package podac.tech.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import podac.tech.model.Cotizacion;
import podac.tech.repository.CotizacionRepository;
import podac.tech.service.ICotizacionService;

@Service
public class CotizacionServiceJpa implements ICotizacionService {

	@Autowired
	private CotizacionRepository repo;

	@Override
	public void guardar(Cotizacion objeto) {
		this.repo.save(objeto);
	}

	@Override
	public void eliminar(Long id) {
		this.repo.deleteById(id);
	}

	@Override
	public List<Cotizacion> buscarTodas() {
		return this.repo.findAll();
	}

	@Override
	public Cotizacion buscarPorId(Long id) {
		return this.repo.findById(id).isPresent() ? this.repo.findById(id).get() : null;
	}

	@Override
	public Page<Cotizacion> buscarTodas(Pageable page) {
		return this.repo.findAll(page);
	}

	@Override
	public Cotizacion ultimoRegistro() {
		Optional<Cotizacion> ultimo = this.repo.findTopByOrderByIdDesc();
		return ultimo.isPresent() ? ultimo.get() : null;
	}

	@Override
	public boolean estaVacio() {
		return this.repo.findTopByOrderByIdDesc().isEmpty();
	}

	@Override
	public boolean existe(Long id) {
		return id == null ? false : this.repo.existsById(id);
	}

	@Override
	public Cotizacion ultimoRegistroPorMoneda(String monedaNombre) {
		Optional<Cotizacion> ultimo = this.repo.findTopByMonedaNombre(monedaNombre);
		return ultimo.isPresent() ? ultimo.get() : null;
	}

}
