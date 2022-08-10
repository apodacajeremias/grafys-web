package podac.tech.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import podac.tech.model.Pago;
import podac.tech.repository.PagoRepository;
import podac.tech.service.IPagoService;

@Service
public class PagoServiceJpa implements IPagoService {

	@Autowired
	private PagoRepository repo;

	@Override
	public void guardar(Pago objeto) {
		this.repo.save(objeto);
	}

	@Override
	public void eliminar(Long id) {
		this.repo.deleteById(id);
	}

	@Override
	public List<Pago> buscarTodas() {
		return this.repo.findAll();
	}

	@Override
	public Pago buscarPorId(Long id) {
		return this.repo.findById(id).isPresent() ? this.repo.findById(id).get() : null;
	}

	@Override
	public Page<Pago> buscarTodas(Pageable page) {
		return this.repo.findAll(page);
	}

	@Override
	public Pago ultimoRegistro() {
		Optional<Pago> ultimo = this.repo.findTopByOrderByIdDesc();
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
	public Page<Pago> buscarPorCliente(Long id, Pageable page) {
		return this.repo.findByPresupuestoClienteIdOrderByIdDesc(id, page);
	}

	@Override
	public Page<Pago> buscarUltimosPagosPorCliente(Long id, Pageable page) {
		return this.repo.findTop15ByPresupuestoClienteIdOrderByIdDesc(id, page);
	}

}
