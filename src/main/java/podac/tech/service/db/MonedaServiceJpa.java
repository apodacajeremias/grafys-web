package podac.tech.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import podac.tech.model.Moneda;
import podac.tech.repository.MonedaRepository;
import podac.tech.service.IMonedaService;

@Service
public class MonedaServiceJpa implements IMonedaService {

	@Autowired
	private MonedaRepository repo;

	@Override
	public void guardar(Moneda objeto) {
		this.repo.save(objeto);
	}

	@Override
	public void eliminar(Long id) {
		this.repo.deleteById(id);
	}

	@Override
	public List<Moneda> buscarTodas() {
		return this.repo.findAll();
	}

	@Override
	public Moneda buscarPorId(Long id) {
		return this.repo.findById(id).isPresent() ? this.repo.findById(id).get() : null;
	}

	@Override
	public Page<Moneda> buscarTodas(Pageable page) {
		return this.repo.findAll(page);
	}

	@Override
	public boolean existeMoneda(String moneda) {
		return this.repo.findTopByNombreIgnoreCaseOrderByIdDesc(moneda).isPresent() ? true : false;
	}

	@Override
	public long contarRegistros() {
		return this.repo.count();
	}

	@Override
	public Moneda buscarPorNombre(String nombre) {
		Optional<Moneda> moneda = this.repo.findTopByNombreIgnoreCaseOrderByIdDesc(nombre);
		return moneda.isPresent() ? moneda.get() : null;
	}

	@Override
	public Moneda ultimoRegistro() {
		Optional<Moneda> ultimo = this.repo.findTopByOrderByIdDesc();
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

}
