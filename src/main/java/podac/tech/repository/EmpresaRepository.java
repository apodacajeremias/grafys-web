package podac.tech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import podac.tech.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	// Para encontrar ultimo registro
	Optional<Empresa> findTopByOrderByIdDesc();

}
