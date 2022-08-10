package podac.tech.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import podac.tech.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	// Buscar usuario por username
	Usuario findByUsername(String username);

	List<Usuario> findByFechaRegistroNotNull();

	@Modifying
	@Query("UPDATE Usuario u SET u.estado = false WHERE u.id = :paramIdUsuario")
	Long lock(@Param("paramIdUsuario") Long idUsuario);

	@Modifying
	@Query("UPDATE Usuario u SET u.estado = true WHERE u.id = :paramIdUsuario")
	Long unlock(@Param("paramIdUsuario") Long idUsuario);

	// Para encontrar ultimo registro
	Optional<Usuario> findTopByOrderByIdDesc();

}
