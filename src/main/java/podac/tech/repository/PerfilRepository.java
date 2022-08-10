package podac.tech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import podac.tech.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
	// Para encontrar ultimo registro con X nombre
	Optional<Perfil> findTopByNombreIgnoreCaseOrderByIdDesc(String nombre);

	// Para encontrar ultimo registro GENERAL
	Optional<Perfil> findTopByOrderByIdDesc();

}
