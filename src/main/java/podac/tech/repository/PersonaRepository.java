package podac.tech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import podac.tech.model.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
	// Para encontrar ultimo registro
	Optional<Persona> findTopByOrderByIdDesc();
}
