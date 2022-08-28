package podac.tech.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import podac.tech.audit.Auditable;

@Entity
public class Persona extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private boolean estado = true;

	@Column
	private String nombre;

	@Column
	private String telefono;

	@Column
	private String direccion;

	@Column
	private String correo;

	@Column
	private boolean esEmpresa;

	@Column
	private String registroTributario;

	@Column
	private boolean esProveedor;

	@JsonManagedReference
	@OneToMany(mappedBy = "cliente")
	private List<Presupuesto> presupuestos = new ArrayList<>();

	@Transient
	private List<Pago> pagos = new ArrayList<>();

	public void definirComoEmpresa(String registroTributario) {
		this.registroTributario = registroTributario;
	}

	public void definirComoProveedor() {
		this.esProveedor = true;
	}

	public Persona() {
		
	}

	public Persona(Long id) {
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public boolean isEsEmpresa() {
		return esEmpresa;
	}

	public void setEsEmpresa(boolean esEmpresa) {
		this.esEmpresa = esEmpresa;
	}

	public String getRegistroTributario() {
		return registroTributario;
	}

	public void setRegistroTributario(String registroTributario) {
		this.registroTributario = registroTributario;
	}

	public boolean isEsProveedor() {
		return esProveedor;
	}

	public void setEsProveedor(boolean esProveedor) {
		this.esProveedor = esProveedor;
	}

	public List<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	@Override
	public String toString() {
		return "Persona [id=" + id + ", estado=" + estado + ", nombre=" + nombre + ", telefono=" + telefono
				+ ", direccion=" + direccion + ", correo=" + correo + ", esEmpresa=" + esEmpresa
				+ ", registroTributario=" + registroTributario + ", esProveedor=" + esProveedor + "]";
	}

}
