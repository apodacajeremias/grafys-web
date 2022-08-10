package podac.tech.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import podac.tech.model.Persona;
import podac.tech.repository.PersonaRepository;
import podac.tech.service.IPersonaService;

@Service
public class PersonaServiceJpa implements IPersonaService {

	@Autowired
	private PersonaRepository repo;

	@Override
	public void guardar(Persona objeto) {
		this.repo.save(objeto);
	}

	@Override
	public void eliminar(Long id) {
		this.repo.deleteById(id);
	}

	@Override
	public List<Persona> buscarTodas() {
		return this.repo.findAll();
	}

	@Override
	public Persona buscarPorId(Long id) {
		return this.repo.findById(id).isPresent() ? this.repo.findById(id).get() : null;
	}

	@Override
	public Page<Persona> buscarTodas(Pageable page) {
		return this.repo.findAll(page);
	}

	@Override
	public List<Persona> buscarConEjemplo(Example<Persona> example) {
		return this.repo.findAll(example);
	}

	@Override
	public Persona ultimoRegistro() {
		Optional<Persona> ultimo = this.repo.findTopByOrderByIdDesc();
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
