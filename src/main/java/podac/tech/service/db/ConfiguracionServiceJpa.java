package podac.tech.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import podac.tech.model.Configuracion;
import podac.tech.repository.ConfiguracionRepository;
import podac.tech.service.IConfiguracionService;

@Service
public class ConfiguracionServiceJpa implements IConfiguracionService {

	@Autowired
	private ConfiguracionRepository repo;

	@Override
	public void guardar(Configuracion objeto) {
		this.repo.save(objeto);
	}

	@Override
	public void eliminar(Long id) {
		this.repo.deleteById(id);
	}

	@Override
	public List<Configuracion> buscarTodas() {
		return this.repo.findAll();
	}

	@Override
	public Configuracion buscarPorId(Long id) {
		return this.repo.findById(id).isPresent() ? this.repo.findById(id).get() : null;
	}

	@Override
	public Page<Configuracion> buscarTodas(Pageable page) {
		return this.repo.findAll(page);
	}

	@Override
	public Configuracion ultimoRegistro() {
		Optional<Configuracion> ultimo = this.repo.findTopByOrderByIdDesc();
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
