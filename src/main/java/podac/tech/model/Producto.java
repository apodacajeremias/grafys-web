package podac.tech.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import podac.tech.audit.Auditable;

@Entity
public class Producto extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private boolean estado = true;

	@Column
	private String nombre;

	@Column
	private String unidadCobro = "UN";

	@Column
	private Double precio = 0d;

	public Producto() {
		
	}

	public Producto(Long id, boolean estado, String nombre, String unidadCobro, Double precio) {
		super();
		this.id = id;
		this.estado = estado;
		this.nombre = nombre;
		this.unidadCobro = unidadCobro;
		this.precio = precio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUnidadCobro() {
		return unidadCobro == null ? "UN" : unidadCobro;
	}

	public void setUnidadCobro(String unidadCobro) {
		this.unidadCobro = unidadCobro;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", estado=" + estado + ", nombre=" + nombre + ", unidadCobro=" + unidadCobro
				+ ", precio=" + precio + "]";
	}

}
