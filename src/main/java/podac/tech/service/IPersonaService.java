package podac.tech.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import podac.tech.model.Persona;

public interface IPersonaService {
	void guardar(Persona objeto);

	void eliminar(Long id);

	List<Persona> buscarTodas();

	Persona buscarPorId(Long id);

	List<Persona> buscarConEjemplo(Example<Persona> example);

	Page<Persona> buscarTodas(Pageable page);

	Persona ultimoRegistro();

	boolean estaVacio();

	boolean existe(Long id);
}
