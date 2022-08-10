package podac.tech.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import podac.tech.model.Presupuesto;

public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {
	// Para encontrar ultimo registro
	Optional<Presupuesto> findTopByOrderByIdDesc();

	Page<Presupuesto> findTop15ByClienteIdOrderByIdDesc(Long id, Pageable page);

	Page<Presupuesto> findByClienteIdOrderById(Long id, Pageable page);

	List<Presupuesto> findByClienteIdOrderById(Long id);

	Page<Presupuesto> findTop15ByRegistradoPorOrderByIdDesc(String username, Pageable page);

	Page<Presupuesto> findByRegistradoPorOrderById(String username, Pageable page);

	List<Presupuesto> findByRegistradoPorOrderById(String username);

	@Transactional
	@Modifying
	@Query("update Presupuesto p set p.estado = true where p.id = :idPresupuesto and p.estado = null")
	void approve(@Param("idPresupuesto") Long id);

	@Transactional
	@Modifying
	@Query("update Presupuesto p set p.estado = false where p.id = :idPresupuesto and p.estado = null")
	void reject(@Param("idPresupuesto") Long id);

	@Transactional
	@Modifying
	@Query("update Presupuesto p set p.descuento = :descuento where p.id = :idPresupuesto")
	void updateDescuento(@Param("idPresupuesto") Long id, @Param("descuento") Double descuento);

	List<Presupuesto> findByClienteIdAndFechaRegistroBetween(Long idCliente, LocalDate fechaInicial,
			LocalDate fechaFinal);

	Page<Presupuesto> findByClienteIdAndFechaRegistroBetween(Long idCliente, LocalDate fechaInicial,
			LocalDate fechaFinal, Pageable page);

	List<Presupuesto> findByFechaRegistroBetween(LocalDate fechaInicial, LocalDate fechaFinal);

	Page<Presupuesto> findByFechaRegistroBetween(LocalDate fechaInicial, LocalDate fechaFinal, Pageable page);

	Optional<Presupuesto> findByClienteIdAndId(long idCliente, long idPresupuesto);

}
