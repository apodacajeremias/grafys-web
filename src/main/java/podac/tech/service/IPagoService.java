package podac.tech.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import podac.tech.model.Pago;

public interface IPagoService {
	void guardar(Pago objeto);

	void eliminar(Long id);

	List<Pago> buscarTodas();

	Pago buscarPorId(Long id);

	Page<Pago> buscarTodas(Pageable page);

	Pago ultimoRegistro();

	boolean estaVacio();

	boolean existe(Long id);

	Page<Pago> buscarPorCliente(Long id, Pageable page);

	Page<Pago> buscarUltimosPagosPorCliente(Long id, Pageable page);
}
