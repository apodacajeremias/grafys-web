package podac.tech.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import podac.tech.audit.Auditable;

@Entity
public class Pago extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private boolean estado = true;

	@Column
	private boolean esGasto;

	@ManyToOne(optional = false)
	private Moneda moneda;

	@ManyToOne(optional = true)
	private Cotizacion cotizacion;

	@JsonBackReference
	@ManyToOne(optional = false)
	private Presupuesto presupuesto;

	@Column
	private Double valor;

	@Column
	private String anotacion;

	public Pago() {
		
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

	public boolean isEsGasto() {
		return esGasto;
	}

	public void setEsGasto(boolean esGasto) {
		this.esGasto = esGasto;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Pago(Long id) {
		super();
		this.id = id;
	}

	public Cotizacion getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	public Presupuesto getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getAnotacion() {
		return anotacion;
	}

	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}

	@Override
	public String toString() {
		return "Pago [id=" + id + ", estado=" + estado + ", esGasto=" + esGasto + ", moneda=" + moneda + ", cotizacion="
				+ cotizacion + ", presupuesto=" + presupuesto + ", valor=" + valor + ", anotacion=" + anotacion + "]";
	}
	
	

}
