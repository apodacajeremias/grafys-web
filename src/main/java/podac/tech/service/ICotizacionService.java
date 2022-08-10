package podac.tech.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import podac.tech.model.Cotizacion;

public interface ICotizacionService {
	void guardar(Cotizacion objeto);

	void eliminar(Long id);

	List<Cotizacion> buscarTodas();

	Cotizacion buscarPorId(Long id);

	Page<Cotizacion> buscarTodas(Pageable page);

	Cotizacion ultimoRegistro();

	boolean estaVacio();

	boolean existe(Long id);

	Cotizacion ultimoRegistroPorMoneda(String monedaNombre);
}
