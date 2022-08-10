package podac.tech.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import podac.tech.model.PresupuestoDetalle;

public interface PresupuestoDetalleRepository extends JpaRepository<PresupuestoDetalle, Long> {

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update PresupuestoDetalle pd set pd.cantidad = :cantidad, pd.precioProducto = :precioProducto, pd.precioPorDiseno = :precioPorDiseno, pd.precioPorTiempo = :precioPorTiempo, pd.tiempoNecesario = :tiempoNecesario, pd.alto = :alto, pd.ancho = :ancho, pd.anotacion = :anotacion where pd.id = :idDetalle")
	void updateFields(@Param("idDetalle") Long idDetalle, @Param("cantidad") Double cantidad,
			@Param("precioProducto") Double precioProducto, @Param("precioPorDiseno") Double precioPorDiseno,
			@Param("precioPorTiempo") Double precioPorTiempo, @Param("tiempoNecesario") Double tiempoNecesario,
			@Param("alto") Double alto,

			@Param("ancho") Double ancho, @Param("anotacion") String anotacion);

	List<PresupuestoDetalle> findByRegistradoPor(String registradoPor);

	Page<PresupuestoDetalle> findByRegistradoPor(String registradoPor, Pageable page);

}
