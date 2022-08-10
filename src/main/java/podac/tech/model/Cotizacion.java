package podac.tech.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import podac.tech.audit.Auditable;

@Entity
public class Cotizacion extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private boolean estado = true;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Moneda moneda;

	@Column
	private Double valor;

	public Cotizacion() {
		// TODO Auto-generated constructor stub
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

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}
