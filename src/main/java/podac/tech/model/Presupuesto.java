package podac.tech.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import podac.tech.audit.Auditable;

@Entity
public class Presupuesto extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// TODO: ENUM CON ESTADO, estado = null es PENDIENTE
	@Column
	private Boolean estado = null;

	@JsonBackReference
	@ManyToOne(optional = false)
	private Persona cliente;

	@Column
	private Double total = 0d;

	@Column
	private Double sumatoria = 0d;

	@Column
	private Double descuento = 0d;

	@JsonIgnore
	@ManyToOne(optional = false)
	private Moneda moneda = new Moneda(3L);

	@JsonIgnore
	@ManyToOne(optional = true)
	private Cotizacion cotizacion;

	// @JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy = "presupuesto", cascade = CascadeType.ALL)
	private List<PresupuestoDetalle> detalles = new ArrayList<>();

	// @JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy = "presupuesto")
	private List<Pago> pagos = new ArrayList<>();

	@Transient
	private Double sumaPagos = 0d;

	public Presupuesto() {
		// TODO Auto-generated constructor stub
	}

	public Presupuesto(Long id) {
		super();
		this.id = id;
	}

	public Presupuesto(Long id, Boolean estado) {
		super();
		this.id = id;
		this.estado = estado;
	}

	public Presupuesto(Persona cliente) {
		super();
		this.cliente = cliente;
	}

	public Presupuesto(Persona cliente, Moneda moneda) {
		super();
		this.cliente = cliente;
		this.moneda = moneda;
	}

	public Presupuesto(Moneda moneda) {
		super();
		this.moneda = moneda;
	}

	public Presupuesto(Long id, Boolean estado, Persona cliente, Double total, Double sumatoria, Double descuento,
			Moneda moneda, Cotizacion cotizacion, List<PresupuestoDetalle> detalles, List<Pago> pagos) {
		super();
		this.id = id;
		this.estado = estado;
		this.cliente = cliente;
		this.total = total;
		this.sumatoria = sumatoria;
		this.descuento = descuento;
		this.moneda = moneda;
		this.cotizacion = cotizacion;
		this.detalles = detalles;
		this.pagos = pagos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Persona getCliente() {
		return cliente;
	}

	public void setCliente(Persona cliente) {
		this.cliente = cliente;
	}

	public Double getTotal() {
		return getSumatoria() - descuento;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getSumatoria() {
		return this.getDetalles().stream().mapToDouble(m -> m.getSubtotal()).sum();
	}

	public void setSumatoria(Double sumatoria) {
		this.sumatoria = sumatoria;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Cotizacion getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	public List<PresupuestoDetalle> getDetalles() {
		if (detalles == null) {
			detalles = new ArrayList<>();
		}
		return detalles;
	}

	public void setDetalles(List<PresupuestoDetalle> detalles) {
		this.detalles = detalles;
	}

	public void addDetalle(Producto producto) {
		if (detalles == null) {
			detalles = new ArrayList<>();
		}
		detalles.forEach(detalle -> detalle.setPresupuesto(this));
		detalles.add(new PresupuestoDetalle(this, producto));
	}

	public void addDetalle(PresupuestoDetalle detalle) {
		if (detalles == null) {
			detalles = new ArrayList<>();
		}
		detalles.forEach(det -> det.setPresupuesto(this));
		detalles.add(detalle);
	}

	public void removeDetalle(PresupuestoDetalle detalle) {
		this.detalles.removeIf(d -> d.getId() == detalle.getId());
	}

	public void removeDetalle(Long idDetalle) {
		this.detalles.removeIf(d -> d.getId() == idDetalle);
	}

	public void removeDetalle(int posicionDetalle) {
		this.detalles.remove(posicionDetalle);
	}

	public List<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	public Double getSumaPagos() {
		// TODO: GENERAR METODO PARA CONSIDERAR COTIZACIONES
		try {
			return this.getPagos().stream().mapToDouble(pago -> pago.getValor()).sum();
		} catch (Exception e) {
			return 0d;
		}
	}

	public void setSumaPagos(Double sumaPagos) {
		this.sumaPagos = sumaPagos;
	}

	@Override
	public String toString() {
		return "Presupuesto [id=" + id + ", estado=" + estado + ", cliente=" + cliente + ", total=" + total
				+ ", sumatoria=" + sumatoria + ", descuento=" + descuento + ", moneda=" + moneda + ", cotizacion="
				+ cotizacion + ", detalles=" + detalles + ", pagos=" + pagos + "]";
	}

}
