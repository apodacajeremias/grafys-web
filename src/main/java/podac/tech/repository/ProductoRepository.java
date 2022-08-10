package podac.tech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import podac.tech.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	// Para encontrar ultimo registro
	Optional<Producto> findTopByOrderByIdDesc();

}
