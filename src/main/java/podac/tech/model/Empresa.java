package podac.tech.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import podac.tech.audit.Auditable;

@Entity
public class Empresa extends Auditable<String> {
	
	//TODO: DISPONIBILIZAR METODOS DE PAGO PARA IMPRIMIR EN PRESUPUESTO

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
	private String email;

	@Column
	private String sitioWeb;

	@Column
	private String direccion;

	@Column
	private String slogan;

	@Column
	private String registroTributario;
	
	@Column
	private String logotipo;

	public Empresa() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSitioWeb() {
		return sitioWeb;
	}

	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getRegistroTributario() {
		return registroTributario;
	}

	public void setRegistroTributario(String registroTributario) {
		this.registroTributario = registroTributario;
	}

	public String getLogotipo() {
		return logotipo;
	}

	public void setLogotipo(String logotipo) {
		this.logotipo = logotipo;
	}
	
	

}
