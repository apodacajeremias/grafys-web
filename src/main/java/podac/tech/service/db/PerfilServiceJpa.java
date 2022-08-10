package podac.tech.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import podac.tech.model.Perfil;
import podac.tech.repository.PerfilRepository;
import podac.tech.service.IPerfilService;

@Service
public class PerfilServiceJpa implements IPerfilService {

	@Autowired
	private PerfilRepository repo;

	@Override
	public void guardar(Perfil objeto) {
		this.repo.save(objeto);
	}

	@Override
	public void eliminar(Long id) {
		this.repo.deleteById(id);
	}

	@Override
	public List<Perfil> buscarTodas() {
		return this.repo.findAll();
	}

	@Override
	public Perfil buscarPorId(Long id) {
		return this.repo.findById(id).isPresent() ? this.repo.findById(id).get() : null;
	}

	@Override
	public Page<Perfil> buscarTodas(Pageable page) {
		return this.repo.findAll(page);
	}

	@Override
	public boolean existePerfil(String perfil) {
		return this.repo.findTopByNombreIgnoreCaseOrderByIdDesc(perfil).isPresent() ? true : false;
	}

	@Override
	public long contarRegistros() {
		return this.repo.count();
	}

	@Override
	public Perfil buscarPorNombre(String nombre) {
		Optional<Perfil> perfil = this.repo.findTopByNombreIgnoreCaseOrderByIdDesc(nombre);
		return perfil.isPresent() ? perfil.get() : null;
	}

	@Override
	public Perfil ultimoRegistro() {
		Optional<Perfil> ultimo = this.repo.findTopByOrderByIdDesc();
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
