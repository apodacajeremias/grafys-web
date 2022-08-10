package podac.tech.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import podac.tech.model.Empresa;

public interface IEmpresaService {
	void guardar(Empresa objeto);

	void eliminar(Long id);

	List<Empresa> buscarTodas();

	Empresa buscarPorId(Long id);

	Page<Empresa> buscarTodas(Pageable page);

	Empresa ultimoRegistro();

	boolean estaVacio();

	boolean existe(Long id);

}
