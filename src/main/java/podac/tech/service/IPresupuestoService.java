package podac.tech.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import podac.tech.model.Presupuesto;

public interface IPresupuestoService {
	Presupuesto guardar(Presupuesto objeto);

	void eliminar(Long id);

	List<Presupuesto> buscarTodas();

	Presupuesto buscarPorId(Long id);

	Page<Presupuesto> buscarTodas(Pageable page);

	List<Presupuesto> buscarConEjemplo(Example<Presupuesto> example);

	Page<Presupuesto> buscarConEjemplo(Example<Presupuesto> example, Pageable page);

	Presupuesto ultimoRegistro();

	boolean estaVacio();

	boolean existe(Long id);

	Page<Presupuesto> buscarUltimosPresupuestosPorCliente(Long id, Pageable page);

	Page<Presupuesto> buscarPresupuestosPorCliente(Long id, Pageable page);

	List<Presupuesto> buscarPresupuestosPorCliente(Long id);

	Page<Presupuesto> buscarUltimosPresupuestosPorUsuario(String username, Pageable page);

	Page<Presupuesto> buscarPresupuestosPorUsuario(String username, Pageable page);

	List<Presupuesto> buscarPresupuestosPorUsuario(String username);

	void aprobar(Long id);

	void rechazar(Long id);

	void actualizarDescuento(Long idPresupuesto, Double descuento);

	List<Presupuesto> buscarPorClienteEntreRangoFechas(Long idCliente, LocalDate fechaInicial, LocalDate fechaFinal);

	Page<Presupuesto> buscarPorClienteEntreRangoFechas(Long idCliente, LocalDate fechaInicial, LocalDate fechaFinal,
			Pageable page);

	List<Presupuesto> buscarEntreRangoFechas(LocalDate fechaInicial, LocalDate fechaFinal);

	Page<Presupuesto> buscarEntreRangoFechas(LocalDate fechaInicial, LocalDate fechaFinal, Pageable page);

	ResponseEntity<Resource> exportarPresupuesto(long idCliente, long idPresupuesto);
}
