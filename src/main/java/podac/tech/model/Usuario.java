package podac.tech.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import podac.tech.audit.Auditable;

@Entity
public class Usuario extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private boolean estado = true;

	@Column
	private String nombre;

	@Column
	private String email;

	@Column
	private String username;

	@Column
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "UsuarioPerfil", joinColumns = @JoinColumn(name = "idUsuario"), inverseJoinColumns = @JoinColumn(name = "idPerfil"))
	private List<Perfil> perfiles;

	// Metodo para agregar perfiles
	public void agregar(Perfil tempPerfil) {
		if (perfiles == null) {
			perfiles = new LinkedList<>();
		}
		perfiles.add(tempPerfil);
	}

	public Usuario() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}

}
