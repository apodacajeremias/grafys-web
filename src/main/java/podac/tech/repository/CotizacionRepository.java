package podac.tech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import podac.tech.model.Cotizacion;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {
	// Para encontrar ultimo registro
	Optional<Cotizacion> findTopByOrderByIdDesc();

	Optional<Cotizacion> findTopByMonedaNombre(String nombre);
}
