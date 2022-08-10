package podac.tech.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import podac.tech.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
	// Para encontrar ultimo registro
	Optional<Pago> findTopByOrderByIdDesc();

	Page<Pago> findByPresupuestoClienteIdOrderByIdDesc(Long id, Pageable page);

	Page<Pago> findTop15ByPresupuestoClienteIdOrderByIdDesc(Long id, Pageable page);

}
