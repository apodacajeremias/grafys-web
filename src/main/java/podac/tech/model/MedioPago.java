package podac.tech.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import podac.tech.audit.Auditable;

public class MedioPago extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private boolean estado = true;

	@Column
	private String nombre;

	@Column(columnDefinition = "TEXT")
	private String detallePago;

}
