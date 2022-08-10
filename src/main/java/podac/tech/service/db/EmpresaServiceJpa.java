package podac.tech.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import podac.tech.model.Empresa;
import podac.tech.repository.EmpresaRepository;
import podac.tech.service.IEmpresaService;

@Service
public class EmpresaServiceJpa implements IEmpresaService {

	@Autowired
	private EmpresaRepository repo;

	@Override
	public void guardar(Empresa objeto) {
		this.repo.save(objeto);
	}

	@Override
	public void eliminar(Long id) {
		this.repo.deleteById(id);
	}

	@Override
	public List<Empresa> buscarTodas() {
		return this.repo.findAll();
	}

	@Override
	public Empresa buscarPorId(Long id) {
		return this.repo.findById(id).isPresent() ? this.repo.findById(id).get() : null;
	}

	@Override
	public Page<Empresa> buscarTodas(Pageable page) {
		return this.repo.findAll(page);
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
	public Empresa ultimoRegistro() {
		Optional<Empresa> empresa = this.repo.findTopByOrderByIdDesc();
		return empresa.isPresent() ? empresa.get() : null;
	}

}
