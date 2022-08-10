package podac.tech.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podac.tech.model.Usuario;
import podac.tech.repository.UsuarioRepository;
import podac.tech.service.IUsuarioService;

@Service
public class UsuarioServiceJpa implements IUsuarioService {

	@Autowired
	private UsuarioRepository repo;

	@Override
	public void guardar(Usuario usuario) {
		repo.save(usuario);
	}

	@Override
	public void eliminar(Long idUsuario) {
		repo.deleteById(idUsuario);
	}

	@Override
	public Usuario buscarPorId(Long idUsuario) {
		Optional<Usuario> optional = repo.findById(idUsuario);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Usuario buscarPorUsername(String username) {
		return repo.findByUsername(username);
	}

	@Override
	public List<Usuario> buscarRegistrados() {
		return repo.findByFechaRegistroNotNull();
	}

	@Transactional
	@Override
	public Long bloquear(Long idUsuario) {
		Long rows = repo.lock(idUsuario);
		return rows;
	}

	@Transactional
	@Override
	public Long activar(Long idUsuario) {
		Long rows = repo.unlock(idUsuario);
		return rows;
	}

	@Override
	public Long contarRegistros() {
		return this.repo.count();
	}

	@Override
	public Usuario ultimoRegistro() {
		Optional<Usuario> ultimo = this.repo.findTopByOrderByIdDesc();
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
	public List<Usuario> buscarTodas() {
		return repo.findAll();
	}

	@Override
	public Page<Usuario> buscarTodas(Pageable page) {
		return repo.findAll(page);
	}

}
