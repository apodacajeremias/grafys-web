package podac.tech.audit;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U> {

	@CreatedBy
	@Column
	@JsonIgnore
	protected U registradoPor;

	@LastModifiedBy
	@Column
	@JsonIgnore
	protected U modificadoPor;

	@CreatedDate
	@JsonIgnore
	protected LocalDateTime fechaRegistro;

	@LastModifiedDate
	@JsonIgnore
	protected LocalDateTime fechaModificacion;

	public U getRegistradoPor() {
		return registradoPor;
	}

	public void setRegistradoPor(U registradoPor) {
		this.registradoPor = registradoPor;
	}

	public U getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(U modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

}
