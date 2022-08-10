package podac.tech.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import podac.tech.audit.Auditable;

@Entity
public class PresupuestoDetalle extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@JsonBackReference
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Presupuesto presupuesto;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Producto producto;

	@Column
	private Double cantidad;

	@Column
	private Double precioProducto;

	@Column
	private Double subtotal;

	@Column
	private String archivo;

	@Column
	private String anotacion;

	@Column
	private Double alto;

	@Transient
	private Double area;

	@Column
	private Double ancho;

	@Column
	private Double tiempoNecesario;

	@Column
	private Double precioPorTiempo;

	@Column
	private Double precioPorDiseno;

	public PresupuestoDetalle() {
	}

	public PresupuestoDetalle(Long id) {
		super();
		this.id = id;
	}

	public PresupuestoDetalle(Presupuesto presupuesto, Producto producto) {
		super();
		this.presupuesto = presupuesto;
		this.producto = producto;
		this.alto = 100d;
		this.ancho = 100d;
		this.anotacion = "N/A";
		this.archivo = "N/A";
		this.cantidad = 1d;
		this.precioPorDiseno = 10000d;
		this.precioPorTiempo = 10000d;
		this.precioProducto = producto.getPrecio();
		this.subtotal = producto.getPrecio();
		this.tiempoNecesario = 1d;
	}

	public PresupuestoDetalle(Producto producto) {
		super();
		this.producto = producto;
		this.alto = 100d;
		this.ancho = 100d;
		this.anotacion = "N/A";
		this.archivo = "N/A";
		this.cantidad = 1d;
		this.precioPorDiseno = 10000d;
		this.precioPorTiempo = 10000d;
		this.precioProducto = producto.getPrecio();
		this.subtotal = producto.getPrecio();
		this.tiempoNecesario = 1d;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Presupuesto getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioProducto() {
		return precioProducto;
	}

	public void setPrecioProducto(Double precioProducto) {
		this.precioProducto = precioProducto;
	}

	// TODO; ESCRIBIR METODO PARA CALCULAR
	public Double getSubtotal() {
		return (Double) ((getPrecioProducto() * cantidad * getArea()) + (precioPorTiempo * tiempoNecesario)
				+ (precioPorDiseno));
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getAnotacion() {
		return anotacion;
	}

	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}

	public Double getAlto() {
		return switch (getProducto().getUnidadCobro()) {
		case "UN": {
			yield 0d;
		}
		case "M2": {
			yield alto;
		}
		case "ML": {
			yield alto;
		}
		default:
			yield 100d;
		};
	}

	public void setAlto(Double alto) {
		this.alto = alto;
	}

	public Double getAncho() {
		return switch (getProducto().getUnidadCobro()) {
		case "UN": {
			yield 0d;
		}
		case "M2": {
			yield ancho;
		}
		case "ML": {
			yield 0d;
		}
		default:
			yield 100d;
		};
	}

	public void setAncho(Double ancho) {
		this.ancho = ancho;
	}

	public Double getArea() {
		return switch (getProducto().getUnidadCobro()) {
		case "UN": {
			yield 1d;
		}
		case "M2": {
			yield (alto * ancho) / 10000;
		}
		case "ML": {
			yield alto / 100;
		}
		default:
			yield 1d;
		};
	}

	public Double getTiempoNecesario() {
		return tiempoNecesario;
	}

	public void setTiempoNecesario(Double tiempoNecesario) {
		this.tiempoNecesario = tiempoNecesario;
	}

	public Double getPrecioPorTiempo() {
		return precioPorTiempo;
	}

	public void setPrecioPorTiempo(Double precioPorTiempo) {
		this.precioPorTiempo = precioPorTiempo;
	}

	public Double getPrecioPorDiseno() {
		return precioPorDiseno;
	}

	public void setPrecioPorDiseno(Double precioPorDiseno) {
		this.precioPorDiseno = precioPorDiseno;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "PresupuestoDetalle [id=" + id + ", presupuesto=" + presupuesto + ", producto=" + producto
				+ ", cantidad=" + cantidad + ", precioProducto=" + precioProducto + ", subtotal=" + subtotal
				+ ", archivo=" + archivo + ", anotacion=" + anotacion + ", alto=" + alto + ", ancho=" + ancho
				+ ", tiempoNecesario=" + tiempoNecesario + ", precioPorTiempo=" + precioPorTiempo + ", precioPorDiseno="
				+ precioPorDiseno + "]";
	}

}
