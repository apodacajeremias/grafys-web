package podac.tech.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import podac.tech.model.Producto;
import podac.tech.repository.ProductoRepository;
import podac.tech.service.IProductoService;

@Service
public class ProductoServiceJpa implements IProductoService {

	@Autowired
	private ProductoRepository repo;

	@Override
	public void guardar(Producto objeto) {
		this.repo.save(objeto);
	}

	@Override
	public void eliminar(Long id) {
		this.repo.deleteById(id);
	}

	@Override
	public List<Producto> buscarTodas() {
		return this.repo.findAll();
	}

	@Override
	public Producto buscarPorId(Long id) {
		return this.repo.findById(id).isPresent() ? this.repo.findById(id).get() : null;
	}

	@Override
	public Page<Producto> buscarTodas(Pageable page) {
		return this.repo.findAll(page);
	}

	@Override
	public Producto ultimoRegistro() {
		Optional<Producto> ultimo = this.repo.findTopByOrderByIdDesc();
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
