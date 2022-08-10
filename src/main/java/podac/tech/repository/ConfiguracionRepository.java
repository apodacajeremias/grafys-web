package podac.tech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import podac.tech.model.Configuracion;

public interface ConfiguracionRepository extends JpaRepository<Configuracion, Long> {

	// Para encontrar ultimo registro
	Optional<Configuracion> findTopByOrderByIdDesc();

}
