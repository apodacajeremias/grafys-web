package podac.tech.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import podac.tech.model.Producto;

public interface IProductoService {
	void guardar(Producto objeto);

	void eliminar(Long id);

	List<Producto> buscarTodas();

	Producto buscarPorId(Long id);

	Page<Producto> buscarTodas(Pageable page);

	Producto ultimoRegistro();

	boolean estaVacio();

	boolean existe(Long id);
}
