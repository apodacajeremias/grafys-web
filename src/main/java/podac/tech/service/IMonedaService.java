package podac.tech.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import podac.tech.model.Moneda;

public interface IMonedaService {
	void guardar(Moneda objeto);

	void eliminar(Long id);

	List<Moneda> buscarTodas();

	Moneda buscarPorId(Long id);

	Page<Moneda> buscarTodas(Pageable page);

	boolean existeMoneda(String moneda);

	long contarRegistros();

	Moneda buscarPorNombre(String nombre);

	Moneda ultimoRegistro();

	boolean estaVacio();

	boolean existe(Long id);
}
