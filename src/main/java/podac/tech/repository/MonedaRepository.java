package podac.tech.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import podac.tech.model.Moneda;

public interface MonedaRepository extends JpaRepository<Moneda, Long> {
	// Para encontrar ultimo registro con X nombre
	Optional<Moneda> findTopByNombreIgnoreCaseOrderByIdDesc(String nombre);

	// Para encontrar ultimo registro GENERAL
	Optional<Moneda> findTopByOrderByIdDesc();

	List<Moneda> findByIdIn(long[] id);
}
