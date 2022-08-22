package podac.tech.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import podac.tech.model.Configuracion;

public interface IConfiguracionService {

	void guardar(Configuracion objeto);

	void eliminar(Long id);

	List<Configuracion> buscarTodas();

	Configuracion buscarPorId(Long id);

	Page<Configuracion> buscarTodas(Pageable page);

	Configuracion ultimoRegistro();

	boolean estaVacio();

	boolean existe(Long id);

}
