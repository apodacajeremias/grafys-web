package podac.tech.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import podac.tech.audit.Auditable;

@Entity
public class Moneda extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private boolean estado = true;

	@Column
	private String nombre;

	@Column
	private String simbolo;

	@Column
	private String formato;

	public Moneda() {
		// TODO Auto-generated constructor stub
	}

	public Moneda(String nombre, String simbolo) {
		super();
		this.nombre = nombre;
		this.simbolo = simbolo;
	}
	
	

	public Moneda(Long id, boolean estado, String nombre, String simbolo, String formato) {
		super();
		this.id = id;
		this.estado = estado;
		this.nombre = nombre;
		this.simbolo = simbolo;
		this.formato = formato;
	}

	public Moneda(Long id) {
		super();
		this.id = id;
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

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	@Override
	public String toString() {
		return simbolo;
	}

}
